package me.kalko.phonebook.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

    @JsonProperty
    public final String token;

    public Token(String token) {
        this.token = token;
    }
}
