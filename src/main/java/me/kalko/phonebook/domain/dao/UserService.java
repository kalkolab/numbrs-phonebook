package me.kalko.phonebook.domain.dao;

import me.kalko.phonebook.Utils;
import me.kalko.phonebook.domain.User;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public abstract class UserService {
    private static final String USER_NOT_FOUND = "User id %s not found.";
    private static final String USER_EXISTS = "User %s already exists.";

    @CreateSqlObject
    abstract UserDao userDao();

    public User getUser(String login) {
        User user = userDao().getUser(login);
        return user;
    }

    public User createUser(User user) {
        if (userDao().getUser(user.getName()) != null) {
            throw new WebApplicationException(String.format(USER_EXISTS, user.getName()), Response.Status.BAD_REQUEST);
        }
        userDao().createUser(user.getName(), Utils.digestPassword(user.getPassword()));
        return userDao().getUser(userDao().lastInsertId());
    }

    public void authenticate(String name, String password) {
        User user = getUser(name);
        if (!user.getPassword().equals(Utils.digestPassword(password))) {
            throw new SecurityException("Invalid username/password");
        }
    }
}
