package com.example.demo.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// The simplest kind of test: no Spring, no database, no web server -
// just plain Java, checking that one class behaves the way we expect.
class TaskNotFoundExceptionTest {

    @Test
    void messageIncludesTheMissingId() {
        TaskNotFoundException exception = new TaskNotFoundException(42L);

        assertThat(exception.getMessage()).isEqualTo("Task not found with id: 42");
    }

}
