package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

// This is the whole file. No implementation to write: extending
// JpaRepository<Task, Long> ("Task" is the entity, "Long" is the type of
// its id) gives us findAll(), findById(id), save(entity), deleteById(id),
// existsById(id), and more, generated for us automatically at startup by
// Spring Data JPA.
public interface TaskRepository extends JpaRepository<Task, Long> {
}
