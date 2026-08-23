package com.example.demo.exception;

// A custom, meaningful exception instead of returning null or a bare
// ResponseEntity.notFound() scattered through the controller. Any code
// that needs a task can just call taskRepository.findById(id)
// .orElseThrow(() -> new TaskNotFoundException(id)) and stop worrying
// about the "not found" case right there - GlobalExceptionHandler takes
// it from here.
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }

}
