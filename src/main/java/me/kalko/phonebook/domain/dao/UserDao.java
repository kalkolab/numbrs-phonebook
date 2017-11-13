package me.kalko.phonebook.domain.dao;

import me.kalko.phonebook.domain.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

@RegisterMapper(UserMapper.class)
public interface UserDao {

    @SqlQuery("select * from users where id = :id")
    User getUser(@Bind("id") final long id);

    @SqlQuery("select * from users where username = :name")
    User getUser(@Bind("name") final String name);

    @SqlUpdate("insert into users(username, password) values(:username, :password)")
    void createUser(@BindBean final User user);

    @SqlUpdate("delete from users where id = :id")
    int deleteUser(@Bind("id") final int id);

    @SqlQuery("select last_insert_id();")
    int lastInsertId();
}
