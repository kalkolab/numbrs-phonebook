package me.kalko.phonebook.domain.dao;

import me.kalko.phonebook.Utils;
import me.kalko.phonebook.domain.Contact;
import me.kalko.phonebook.domain.User;
import org.assertj.core.util.Lists;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper implements ResultSetMapper<Contact> {
    private static final String ID = "id";
    private static final String USERID = "userid";
    private static final String FIRSTNAME = "first_name";
    private static final String LASTNAME = "last_name";
    private static final String PHONES = "phones";

    public Contact map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        String phones = resultSet.getString(PHONES);
        return new Contact(resultSet.getInt(ID), resultSet.getInt(USERID), resultSet.getString(FIRSTNAME), resultSet.getString(LASTNAME),
                Utils.deserializeList(resultSet.getString(PHONES)));
    }
}
