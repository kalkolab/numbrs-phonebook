package me.kalko.phonebook.resources;

import me.kalko.phonebook.Utils;
import me.kalko.phonebook.domain.ErrorBean;
import me.kalko.phonebook.domain.IdWrapper;
import me.kalko.phonebook.domain.Token;
import me.kalko.phonebook.domain.User;
import me.kalko.phonebook.domain.dao.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/")
public class UserResource {
    private final UserService userService;

    @Context
    private UriInfo uriInfo;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/user")
    public Response login(User user) {
        userService.authenticate(user.getName(), user.getPassword());
        String token = Utils.issueToken(user.getName(), uriInfo);

        return Response.status(Response.Status.CREATED).entity(new Token(token)).build();
    }

    @POST
    @Path("/register")
    public Response register(User user) {
        if (userService.getUser(user.getName()) != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorBean(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "User already exists: " + user.getName())).build();
        }
        User createdUser = userService.createUser(user);
        return Response.status(Response.Status.CREATED).entity(new IdWrapper(createdUser.getId())).build();
    }

}
