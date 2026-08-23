package com.example.demo;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // A CommandLineRunner's run() method executes once, right after the
    // app starts - a handy place to seed some starter data so the API
    // isn't empty the first time you try it.
    @Bean
    CommandLineRunner seedData(TaskRepository taskRepository) {
        return args -> {
            taskRepository.save(new Task("Learn Spring Boot", false));
            taskRepository.save(new Task("Build a REST API", false));
        };
    }

}
