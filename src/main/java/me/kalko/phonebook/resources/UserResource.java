package me.kalko.phonebook.resources;

import me.kalko.phonebook.Utils;
import me.kalko.phonebook.domain.Token;
import me.kalko.phonebook.domain.User;
import me.kalko.phonebook.domain.dao.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    @Context
    private UriInfo uriInfo;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response login(User user) {
        authenticate(user.getName(), user.getPassword());
        String token = Utils.issueToken(user.getName(), uriInfo);

        return Response.status(Response.Status.CREATED).entity(new Token(token)).build();
    }

    private void authenticate(String name, String password) {
        User user = userService.getUser(name);
        if (!user.getPassword().equals(Utils.digestPassword(password))) {
            throw new SecurityException("Invalid username/password");
        }
    }

}
