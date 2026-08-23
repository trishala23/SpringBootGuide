package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// @ConfigurationProperties(prefix = "app") binds every property starting
// with "app." into this class's fields. "app.max-tasks" (kebab-case in
// the file) automatically matches the field "maxTasks" (camelCase in
// Java) - Spring Boot calls this "relaxed binding."
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String greeting;
    private int maxTasks;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public int getMaxTasks() {
        return maxTasks;
    }

    public void setMaxTasks(int maxTasks) {
        this.maxTasks = maxTasks;
    }

}
