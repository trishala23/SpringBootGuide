# Lesson 3: Your first Spring Boot app ("Hello World")

*Estimated time: 25 minutes*

## What you'll learn

- How to generate a project with Spring Initializr.
- The two Java classes every simple Spring Boot web app starts with.
- How to run the app and see it respond in a browser.

## The concept (plain words)

We're going to build the smallest possible Spring Boot web app: one that,
when you visit a web address, sends back the text "Hello, World!". It
sounds tiny, but by the end of this lesson your computer will be running
a real web server that you wrote yourself.

## Step 1: Generate the project

Go to [start.spring.io](https://start.spring.io/) and fill in the options
from Lesson 2:

- Project: **Maven**
- Language: **Java**
- Spring Boot: latest stable (non-SNAPSHOT)
- Group: `com.example`
- Artifact: `demo`
- Packaging: **Jar**
- Java: **17**
- Dependencies: **Spring Web**

Click **Generate**, then unzip the downloaded file. Open the folder in
your code editor.

!!! tip
    A ready-made copy of exactly what you'll build in this lesson is also
    in this repository at
    [`code-examples/lesson-03-hello-world`](https://github.com/trishala23/SpringBootGuide/tree/main/code-examples/lesson-03-hello-world),
    in case you want to compare or skip straight to running it.

## Step 2: Look at what was generated

Two Java files matter for now (we'll tour the *rest* of the folder in
Lesson 4):

`src/main/java/com/example/demo/DemoApplication.java`:

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

This is the **entry point** — the class Java runs first, just like any
Java program's `main` method. `@SpringApplication.run(...)` is what
actually starts Spring Boot: it configures everything automatically and
starts the built-in web server.

`@SpringBootApplication` is an **annotation** — a label starting with `@`
that tells Spring "treat this class specially." This particular one
combines three jobs into one label (auto-configuration, component
scanning, and letting this class hold configuration) — you don't need to
memorize the three names, just know that this one annotation is what
turns a plain class into the root of your Spring Boot app.

## Step 3: Add a controller

Now let's make the app actually respond to something. Create a new file
next to `DemoApplication.java` called `HelloController.java`:

```java
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World! Spring Boot is running.";
    }

}
```

A **controller**, in Spring Boot, is a class whose job is to handle
incoming web requests and decide what to send back. `@RestController`
tells Spring "whatever methods in this class return should be sent
straight back as the response." `@GetMapping("/hello")` says "when
someone visits `/hello` with a GET request" (the kind of request your
browser sends just by visiting a web address) "run this method."

We'll go much deeper on controllers in Lesson 5 — for now, just know that
this small amount of code is a fully working web endpoint.

## Step 4: Run it

In a terminal, inside your project folder, run:

```bash
mvn spring-boot:run
```

Wait for a few seconds — you'll see a banner with "Spring" ASCII art, and
then a log line ending in "Started DemoApplication." That means your
app is running and listening on **port 8080** (a port is just a numbered
"door" a program listens on for incoming requests — 8080 is Spring
Boot's default).

Now open a browser and go to:

```
http://localhost:8080/hello
```

`localhost` means "this same computer." You should see:

```
Hello, World! Spring Boot is running.
```

That text came from the `return` statement in `HelloController` — your
code is genuinely handling that request.

To stop the app, go back to your terminal and press `Ctrl+C`.

## Why this matters

This is the smallest possible version of the request/response flow from
Lesson 1's diagram, actually running on your machine. Every feature you
add in later lessons — REST APIs, databases, configuration — builds on
this exact same pattern: a controller receives a request and returns a
response.

## Try it yourself

Add a second endpoint that says hello to a specific name, so that
visiting `http://localhost:8080/hello/Alice` returns `Hello, Alice!`.

Hint: `@GetMapping("/hello/{name}")` lets you capture part of the URL,
and `@PathVariable String name` as a method parameter gives you that
captured value.

??? note "Show solution"
    ```java
    package com.example.demo;

    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class HelloController {

        @GetMapping("/hello")
        public String sayHello() {
            return "Hello, World! Spring Boot is running.";
        }

        @GetMapping("/hello/{name}")
        public String sayHelloTo(@PathVariable String name) {
            return "Hello, " + name + "!";
        }

    }
    ```

    The `{name}` in the path is a placeholder. Spring Boot matches
    whatever text appears there and hands it to your method through the
    `@PathVariable` parameter — as long as the parameter name (`name`)
    matches the placeholder name.

## Checklist: before moving on

Before moving on, make sure you can...

- [ ] Generate a project from Spring Initializr with the right options.
- [ ] Explain what `@SpringBootApplication` and `@RestController` each do,
      in one plain sentence.
- [ ] Run the app with `mvn spring-boot:run` and see `Hello, World!` in
      your browser.
- [ ] Add a new endpoint that returns different text at a different path.

## What's next

In [Lesson 4](04-project-structure.md), we'll slow down and tour every
file and folder Spring Initializr generated, so nothing in your project
feels like a mystery.
