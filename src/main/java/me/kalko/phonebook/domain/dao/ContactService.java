package me.kalko.phonebook.domain.dao;

import me.kalko.phonebook.Utils;
import me.kalko.phonebook.domain.Contact;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

public abstract class ContactService {
    private static final String CONTACT_NOT_FOUND = "Contact id %s not found.";
    private static final String USER_EXISTS = "Contact %s already exists.";

    @CreateSqlObject
    abstract ContactDao contactDao();

    public Contact getContact(long id) {
        Contact contact = contactDao().getContact(id);
        return contact;
    }

    public List<Contact> getContacts(long userId) {
        List<Contact> contacts = contactDao().getContacts(userId);
        return contacts;
    }

    public Contact createContact(long userId, Contact contact) {
        contactDao().createContact(userId, contact.getFirstName(), contact.getLastName());
        return contactDao().getContact(contactDao().lastInsertId());
    }

    public Contact updateContact(long contactId, Contact contact) {
        if (Objects.isNull(contactDao().getContact(contact.getId()))) {
            throw new WebApplicationException(String.format(CONTACT_NOT_FOUND, contact.getId()),
                    Response.Status.NOT_FOUND);
        }
        contactDao().updateContact(contactId, contact.getFirstName(), contact.getLastName(), Utils.serializeList(contact.getPhones()));
        return contactDao().getContact(contactId);
    }

    public void deleteContact(int contactId) {
        contactDao().deleteContact(contactId);
    }
//    public User createUser(User user) {
//        if (contactDao().getUser(user.getName()) != null) {
//            throw new WebApplicationException(String.format(USER_EXISTS, user.getName()), Response.Status.BAD_REQUEST);
//        }
//        contactDao().createUser(user);
//        return contactDao().getUser(contactDao().lastInsertId());
//    }
}
