package me.kalko.phonebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Contact {

    private int id;

    @JsonIgnore
    private int userId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private List<String> phones;

    public Contact(@JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = new ArrayList<>();
    }

    public Contact(int id, int userId, String firstName, String lastName) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = new ArrayList<>();
    }

    public Contact(int id, int userId, String firstName, String lastName, List<String> phones) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = phones;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void addPhone(String phone) {
        if (StringUtils.isNotEmpty(phone) && !phones.contains(phone))
            phones.add(phone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        if (userId != contact.userId) return false;
        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null) return false;
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null) return false;
        return phones != null ? phones.equals(contact.phones) : contact.phones == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phones != null ? phones.hashCode() : 0);
        return result;
    }
}
