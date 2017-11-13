package me.kalko.phonebook.domain.dao;

import me.kalko.phonebook.domain.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {
    private static final String ID = "id";
    private static final String NAME = "username";
    private static final String PASSWORD = "password";

    public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new User(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(PASSWORD));
    }
}
