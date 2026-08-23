package com.example.demo;

// A plain Java class describing what one "task" looks like: an id, a
// title, and whether it's done yet. Spring Boot (via a library called
// Jackson, included automatically by spring-boot-starter-web) will
// convert objects of this class to and from JSON for us - we never
// write JSON-parsing code ourselves.
public class Task {

    private Long id;
    private String title;
    private boolean done;

    // Jackson needs a no-argument constructor to build objects when
    // reading JSON from a request body.
    public Task() {
    }

    public Task(Long id, String title, boolean done) {
        this.id = id;
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
