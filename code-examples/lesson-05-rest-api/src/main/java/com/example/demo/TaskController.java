package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

// @RequestMapping("/tasks") sets a shared path prefix for every method
// below, so we don't have to repeat "/tasks" in each @GetMapping /
// @PostMapping.
@RestController
@RequestMapping("/tasks")
public class TaskController {

    // A simple in-memory list standing in for a database, just for this
    // lesson. It resets every time the app restarts. Lesson 6 gives this
    // its own proper home, and Lesson 7 replaces it with a real database.
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public TaskController() {
        tasks.add(new Task(nextId.getAndIncrement(), "Learn Spring Boot", false));
        tasks.add(new Task(nextId.getAndIncrement(), "Build a REST API", false));
    }

    // GET /tasks -> return every task as a JSON array.
    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    // GET /tasks/{id} -> return one task, or a 404 if it doesn't exist.
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Optional<Task> found = tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();

        return found
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /tasks -> read a Task from the request body (JSON), assign
    // it a new id, store it, and return the saved task with a 201
    // Created status.
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
        newTask.setId(nextId.getAndIncrement());
        tasks.add(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

}
