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
    public ResponseEntity<Map<String, Object>> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "La solicitud es invalida");
        response.put("codigo", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());
        response.put("codigo", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Credenciales incorrectas");
        response.put("codigo", HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Ocurrió un error");
        response.put("codigo", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

