package com.duxsoftware.dto.response;

import java.io.Serializable;


public record AuthenticationResponse(String jwt) implements Serializable {}