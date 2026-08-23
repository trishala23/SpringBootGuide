package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController tells Spring: "objects this class returns should be
// sent back directly as the response" (as plain text or JSON), instead
// of looking for an HTML page to render. We'll cover this in depth in
// Lesson 5.
@RestController
public class HelloController {

    // @GetMapping("/hello") means: when a GET request arrives at the
    // path "/hello", run this method and send back whatever it returns.
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World! Spring Boot is running.";
    }

}
