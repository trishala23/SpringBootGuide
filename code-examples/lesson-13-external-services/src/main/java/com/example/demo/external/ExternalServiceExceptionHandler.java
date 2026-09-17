package com.example.demo.external;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExternalServiceExceptionHandler {

    @ExceptionHandler(ExternalTodoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ExternalTodoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(ExternalTodoServiceException.class)
    public ResponseEntity<Map<String, String>> handleServiceFailure(ExternalTodoServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "The external todo service is currently unavailable"));
    }

}
