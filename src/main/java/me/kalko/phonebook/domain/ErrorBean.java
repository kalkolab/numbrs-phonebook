package me.kalko.phonebook.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorBean {
    @JsonProperty
    private final int code;

    @JsonProperty
    private final String msg;

    public ErrorBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
