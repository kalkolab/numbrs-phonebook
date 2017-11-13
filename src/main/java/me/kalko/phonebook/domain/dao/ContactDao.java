package me.kalko.phonebook.domain.dao;

import me.kalko.phonebook.domain.Contact;
import me.kalko.phonebook.domain.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(ContactMapper.class)
public interface ContactDao {

    @SqlQuery("select * from contacts where id = :id")
    Contact getContact(@Bind("id") final long id);

    @SqlQuery("select * from contacts where userid = :userid")
    List<Contact> getContacts(@Bind("userid") final long userid);

    @SqlUpdate("insert into contacts(userid, first_name, last_name) values(:userid, :first_name, :last_name)")
    void createContact(@Bind("userid") final long userid, @Bind("first_name") final String first_name, @Bind("last_name") final String last_name);

    @SqlUpdate("update contacts set first_name = coalesce(:first_name, first_name), last_name = coalesce(:last_name, last_name), phones = coalesce(:phones, phones) where id = :id")
    void updateContact(@Bind("id") final long id, @Bind("first_name") final String first_name, @Bind("last_name") final String last_name, @Bind("phones") final String phones);

    @SqlUpdate("delete from contacts where id = :id")
    int deleteContact(@Bind("id") final int id);

    @SqlQuery("select last_insert_id();")
    int lastInsertId();
}
