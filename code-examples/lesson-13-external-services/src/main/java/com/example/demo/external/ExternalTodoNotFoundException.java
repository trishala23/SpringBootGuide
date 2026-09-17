package com.example.demo.external;

public class ExternalTodoNotFoundException extends RuntimeException {

    public ExternalTodoNotFoundException(long id) {
        super("External todo not found with id: " + id);
    }

}
