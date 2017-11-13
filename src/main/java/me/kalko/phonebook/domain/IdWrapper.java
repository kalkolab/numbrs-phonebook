package me.kalko.phonebook.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdWrapper {
    @JsonProperty
    private final int id;

    public IdWrapper(int id) {
        this.id = id;
    }
}
