package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

// @Repository marks this class as the place responsible for storing and
// retrieving data. Right now it's just a list in memory, but a
// controller using this class doesn't need to know that - in Lesson 7
// we swap what's inside this class for a real database, and the
// controller won't need to change at all.
@Repository
public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public TaskRepository() {
        save(new Task(null, "Learn Spring Boot", false));
        save(new Task(null, "Build a REST API", false));
    }

    public List<Task> findAll() {
        return tasks;
    }

    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(nextId.getAndIncrement());
            tasks.add(task);
        } else {
            tasks.removeIf(existing -> existing.getId().equals(task.getId()));
            tasks.add(task);
        }
        return task;
    }

    public boolean deleteById(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }

}
