package me.kalko.phonebook;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import me.kalko.phonebook.domain.User;
import me.kalko.phonebook.domain.dao.UserService;

import java.util.Optional;

public class AuthTokenAuthenticator implements Authenticator<String, User> {
    private final UserService userService;

    public AuthTokenAuthenticator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        Claims claims = Jwts.parser().setSigningKey(Utils.KEY).parseClaimsJws(token).getBody();
        return Optional.ofNullable(userService.getUser(claims.getSubject()));
    }
}
