package me.kalko.phonebook.domain.dao;

import me.kalko.phonebook.domain.User;
import me.kalko.phonebook.domain.dao.UserDao;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Objects;

public abstract class UserService {
    private static final String USER_NOT_FOUND = "User id %s not found.";
    private static final String USER_EXISTS = "User %s already exists.";

    @CreateSqlObject
    abstract UserDao userDao();

    public User getUser(int id) {
        return null;
    }

    public User getUser(String login) {
        User user = userDao().getUser(login);
        if (Objects.isNull(user)) {
            throw new WebApplicationException(String.format(USER_NOT_FOUND, login), Response.Status.NOT_FOUND);
        }
        return user;
    }

    public User createUser(User user) {
        if (userDao().getUser(user.getName()) != null) {
            throw new WebApplicationException(String.format(USER_EXISTS, user.getName()), Response.Status.BAD_REQUEST);
        }
        userDao().createUser(user);
        return userDao().getUser(userDao().lastInsertId());
    }
}
