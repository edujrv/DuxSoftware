package com.duxsoftware.exception;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String mensaje;
    private int codigo;
}