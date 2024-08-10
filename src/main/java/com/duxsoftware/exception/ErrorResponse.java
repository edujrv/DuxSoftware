package com.duxsoftware.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty("mensaje")
    private String message;

    @JsonProperty("codigo")
    private int code;
}
