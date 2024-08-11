package com.duxsoftware.exception;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de propiedades no reconocidas en el JSON
    @ExceptionHandler({UnrecognizedPropertyException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("La solicitud es invalida")
                .codigo(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .codigo(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("Credenciales incorrectas")
                .codigo(HttpStatus.UNAUTHORIZED.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(JwtAuthenticationException.class)
//    public ResponseEntity<Map<String, Object>> handleJwtAuthenticationException(JwtAuthenticationException ex) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("mensaje", ex.getMessage());
//        response.put("codigo", HttpStatus.UNAUTHORIZED.value());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }

    // Manejo de cualquier otra excepción
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .mensaje("Ocurrió un error")
                .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

