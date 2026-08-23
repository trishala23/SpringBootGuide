# Lesson 10: Testing your app

*Estimated time: 30 minutes*

## What you'll learn

- Why tests are worth writing, in plain terms.
- The anatomy of a basic JUnit test.
- Two lightweight ways to test a Spring Boot app: a repository test and
  a controller test — neither needs the whole app running.

This lesson is a gentle, practical introduction. We're not covering
testing theory in depth — just enough to comfortably write and trust your
own tests.

## The concept (plain words)

A **test** is a small piece of code that runs another piece of your code
and checks the result is what you expect — automatically, every time,
without you manually clicking through your app to check by hand.

Why bother? Because by Lesson 9, this small app already has real
behavior worth protecting: "a missing task returns 404," "a blank title
is rejected." Without tests, the only way to know those still work after
a change is to restart the app and try it by hand, every time. Tests do
that checking for you, in seconds.

!!! tip
    The full working project is at
    [`code-examples/lesson-10-testing`](https://github.com/trishala23/SpringBootGuide/tree/main/code-examples/lesson-10-testing).

## Step 1: The simplest possible test

No Spring, no database — just plain Java and JUnit (the testing library
Spring Initializr already included for you, inside
`spring-boot-starter-test`):

```java
package com.example.demo.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TaskNotFoundExceptionTest {

    @Test
    void messageIncludesTheMissingId() {
        TaskNotFoundException exception = new TaskNotFoundException(42L);

        assertThat(exception.getMessage()).isEqualTo("Task not found with id: 42");
    }

}
```

`@Test` marks a method as a test JUnit should run. `assertThat(...).isEqualTo(...)`
is an **assertion** — a statement that must be true, or the test fails.
Run every test in the project with:

```bash
mvn test
```

## Step 2: Testing the repository layer

Testing against a full, real running app for every little check is slow.
Spring Boot lets you test just *one layer* at a time — called a **slice
test** — which starts only what that layer needs.

```java
package com.example.demo.repository;

import com.example.demo.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

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
```

`@DataJpaTest` starts a real (temporary, thrown away afterward) database
and your JPA repositories — but no web server, no controllers. It's fast,
and it proves your repository and database mapping actually work
together, not just that your Java compiles.

## Step 3: Testing the controller layer

Same idea, but for the web layer:

```java
package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    void getTask_returnsTask_whenItExists() throws Exception {
        Task task = new Task("Learn Spring Boot", false);
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Learn Spring Boot"));
    }

    @Test
    void getTask_returns404_whenItDoesNotExist() throws Exception {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound());
    }

}
```

A few new pieces, explained plainly:

- **`@WebMvcTest(TaskController.class)`** starts only the web layer for
  this one controller — much faster than starting the whole app.
- **`MockMvc`** lets you send a fake HTTP request (`get("/tasks/1")`)
  without a real server running, and check the response.
- **`@MockBean`** replaces the real `TaskRepository` with a fake
  ("mock") one you fully control in the test. `when(taskRepository.findById(1L)).thenReturn(...)`
  means "when this exact call happens, pretend it returned this value" —
  so the test doesn't depend on a real database at all.
- **`jsonPath("$.title")`** reads a value out of the JSON response, the
  same way you'd read a field from the JSON you saw with `curl` in
  earlier lessons.

This is exactly the same 404 behavior you checked manually with `curl`
in Lesson 9 — except now it's automatic, and it'll catch you immediately
if a future change accidentally breaks it.

## Why this matters

Tests are what let you change code confidently. Without them, every
change means manually re-checking everything by hand and hoping you
didn't forget a case. With them, `mvn test` tells you in seconds whether
anything broke — including edge cases (like the 404 case) that are easy
to forget to check by hand after a change.

## Try it yourself

Add a test to `TaskControllerTest` for the validation behavior from
Lesson 9: posting a task with a blank title should return `400`, with a
`fieldErrors.title` entry in the response.

??? note "Show solution"
    ```java
    @Test
    void createTask_returns400_whenTitleIsBlank() throws Exception {
        String requestBody = """
                {"title": "", "done": false}
                """;

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.title").exists());
    }
    ```

    You'll need two more imports:
    `org.springframework.http.MediaType` and
    `static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post`.

    Notice this test doesn't need to stub `taskRepository` at all —
    validation fails before the controller method's body (and therefore
    the repository) is ever reached.

## Checklist: before moving on

Before moving on, make sure you can...

- [ ] Run `mvn test` and read the pass/fail summary.
- [ ] Explain what an assertion is.
- [ ] Explain what `@WebMvcTest` and `@MockBean` are for, without saying
      "it just works."
- [ ] Write a test for a new endpoint you add, checking both a success
      case and a failure case.

## What's next

In [Lesson 11](11-packaging.md), we'll package this app into a single
runnable file and run it the way it would run outside your editor.
