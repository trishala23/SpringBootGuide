package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

// @Entity tells Spring Data JPA "this class maps to a database table."
// By default the table is named after the class ("task") and each field
// becomes a column.
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank means: this field must be present and contain at least
    // one non-whitespace character. It only takes effect where a
    // controller asks for validation with @Valid (see TaskController).
    @NotBlank(message = "title must not be blank")
    private String title;

    private boolean done;

    public Task() {
    }

    public Task(String title, boolean done) {
        this.title = title;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
