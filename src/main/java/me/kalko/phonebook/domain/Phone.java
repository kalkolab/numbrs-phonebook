package me.kalko.phonebook.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Phone {
    @JsonProperty
    private final String phone;

    public Phone(@JsonProperty("phone") String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
