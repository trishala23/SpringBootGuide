package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication turns this one class into three things at once:
// 1. Configuration  - it can define beans (objects Spring manages for you).
// 2. Auto-configuration - Spring Boot sets sensible defaults based on
//    what's on your classpath (e.g. it sees Spring Web and starts a
//    built-in web server for you).
// 3. Component scanning - Spring looks in this package (and sub-packages)
//    for classes it should manage, like our HelloController.
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
