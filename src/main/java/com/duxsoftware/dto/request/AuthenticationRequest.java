package com.duxsoftware.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {

    private String username;
    private String password;
}
