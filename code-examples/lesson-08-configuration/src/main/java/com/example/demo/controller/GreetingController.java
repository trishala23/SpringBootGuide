package com.example.demo.controller;

import com.example.demo.config.AppProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    // @Value pulls in a single property by key, with a default value
    // after the colon in case the property is missing.
    @Value("${app.greeting:Hello!}")
    private String greeting;

    private final AppProperties appProperties;

    public GreetingController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    // The @Value-injected version - fine for one or two simple values.
    @GetMapping("/greeting")
    public String getGreeting() {
        return greeting;
    }

    // The @ConfigurationProperties version - better once you have
    // several related settings, since they're grouped into one typed,
    // testable object instead of scattered @Value fields.
    @GetMapping("/greeting/config")
    public String getGreetingFromConfig() {
        return appProperties.getGreeting() + " (max tasks allowed: " + appProperties.getMaxTasks() + ")";
    }

}
