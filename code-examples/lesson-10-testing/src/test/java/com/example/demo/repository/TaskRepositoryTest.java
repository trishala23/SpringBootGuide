package com.example.demo.repository;

import com.example.demo.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

// @DataJpaTest starts only the parts of Spring needed to test JPA
// repositories - a real (temporary, in-memory) database, but no web
// server, no controllers. This is called a "slice test": testing one
// layer of the app on its own, which runs much faster than starting the
// whole application.
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void savedTaskCanBeFoundById() {
        Task saved = taskRepository.save(new Task("Write tests", false));

        Task found = taskRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getTitle()).isEqualTo("Write tests");
        assertThat(found.isDone()).isFalse();
    }

}
