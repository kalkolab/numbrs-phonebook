package me.kalko.phonebook;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.UnauthorizedHandler;
import io.jsonwebtoken.JwtException;
import me.kalko.phonebook.domain.ErrorBean;
import me.kalko.phonebook.domain.User;
import me.kalko.phonebook.domain.dao.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

public class JwtAuthFilter extends AuthFilter<String, User> {
    private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    public static final String TOKEN_HEADER = "Token";
    private AuthTokenAuthenticator authenticator;

    public JwtAuthFilter(UserService userService) {
        this.authenticator = new AuthTokenAuthenticator(userService);
        unauthorizedHandler = (prefix, realm) ->
                Response.status(Response.Status.UNAUTHORIZED)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .entity(new ErrorBean(Response.Status.UNAUTHORIZED.getStatusCode(), "Credentials are required"))
                        .build();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            String token = requestContext.getHeaders().getFirst(TOKEN_HEADER);
            Optional<User> userOptional = authenticator.authenticate(token);
            if (userOptional.isPresent()) {
                requestContext.setSecurityContext(new SecurityContext() {

                    @Override
                    public Principal getUserPrincipal() {
                        return userOptional.get();
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return authorizer.authorize(userOptional.get(), role);
                    }

                    @Override
                    public boolean isSecure() {
                        return requestContext.getSecurityContext().isSecure();
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return SecurityContext.BASIC_AUTH;
                    }

                });
                return;
            }
        } catch (JwtException ex) {
            logger.warn("Error decoding credentials: " + ex.getMessage(), ex);
        } catch (AuthenticationException ex) {
            logger.warn("Error authenticating credentials", ex);
            throw new InternalServerErrorException();
        }
        throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
    }
}
