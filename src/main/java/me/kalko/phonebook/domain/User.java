package me.kalko.phonebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.security.auth.Subject;
import java.security.Principal;

public class User implements Principal {

    @JsonProperty
    private int id;

    @JsonProperty
    private final String username;

    @JsonIgnore
    private final String password;

    public User(@JsonProperty("username") String username, @JsonProperty("password")  String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public boolean implies(Subject subject) {
        return false;
    }
}
