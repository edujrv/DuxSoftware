package com.duxsoftware.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class AuthenticationRequest implements Serializable {

    private String username;
    private String password;

    // Default constructor for JSON Parsing
    public AuthenticationRequest() {}

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    // getters and setters
}
