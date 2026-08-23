# Lesson 4: Understanding project structure

*Estimated time: 15 minutes*

## What you'll learn

- What every file and folder in a generated Spring Boot project is for.
- Why the folder layout matters (and what happens if you don't follow it).
- What `pom.xml` actually does.

## The concept (plain words)

Spring Initializr generated a specific folder layout for you in Lesson 3.
It looks like a lot at first glance, but almost all of it is standard —
once you understand it here, every Spring Boot project you ever open will
look familiar.

Here's the shape of the project from Lesson 3
(`code-examples/lesson-03-hello-world`):

```
lesson-03-hello-world/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java
│   │   │       └── HelloController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/demo/
│               └── DemoApplicationTests.java
└── target/              (created after you build — not part of your source)
```

Let's go through each piece.

## `pom.xml` — the project's build file

**POM** stands for "Project Object Model" — don't worry about the name,
what matters is what it does: it tells Maven three things:

1. **What your project is** (its name, group, version).
2. **What it depends on** — other pieces of code your project needs, like
   `spring-boot-starter-web`. Maven downloads these automatically.
3. **How to build it** — turning your source code into a runnable
   program.

The most important part to recognize is the `<parent>` section:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.4</version>
</parent>
```

This is what gives Spring Boot projects their "just works" feeling — the
parent POM pre-picks compatible versions of everything for you, so you
don't have to figure out which version of library A works with version of
library B.

And the `<dependencies>` section is where you add capabilities:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

`spring-boot-starter-web` is a **starter** — a bundle of dependencies for
one job (here: building web apps/REST APIs) so you add one line instead
of ten.

## `src/main/java` — your application code

This is where all your actual Java code lives. Notice the folder path
matches the Java **package** name:
`src/main/java/com/example/demo/` holds classes in the
`com.example.demo` package. Java requires this — it's not a Spring Boot
rule, but you'll see it in every Java project.

- `DemoApplication.java` — the entry point (covered in Lesson 3).
- `HelloController.java` — your web endpoint (also from Lesson 3).

As your app grows, you'll typically add more packages here, like
`com.example.demo.controller`, `com.example.demo.model`, and
`com.example.demo.repository` — we'll build that structure naturally as
we go through Lessons 5–7.

## `src/main/resources` — configuration and non-code files

`application.properties` (or `application.yml` — both work, we compare
them in Lesson 8) is where you configure your app without writing Java
code: which port to run on, database connection details, logging levels,
and so on. Right now it just has one line:

```properties
spring.application.name=demo
```

This folder can also hold static files (HTML/CSS/images) and templates,
if you're serving a traditional website instead of a REST API — not
something this tutorial covers, since we're focused on building APIs.

## `src/test/java` — your tests

Mirrors the `src/main/java` structure, but holds test code instead of
application code. `DemoApplicationTests.java` is a starter test that
checks your app can start up without errors — we'll write more
meaningful tests in Lesson 10.

## `target/` — generated output (don't touch, don't commit)

This folder doesn't exist until you build the project (`mvn package` or
`mvn spring-boot:run`). It holds compiled `.class` files and the final
runnable `.jar` file. It's regenerated every time you build, so it's
listed in `.gitignore` — never edit anything in here by hand, and never
commit it.

## Why this matters

Once you can look at *any* Spring Boot project and immediately know "this
is configuration, this is my code, this is generated output, this is the
build file," you stop feeling lost opening new projects — including much
bigger real-world ones. The layout never really changes; only how much
code is inside each folder grows.

## Try it yourself

Open `code-examples/lesson-03-hello-world/pom.xml` in your editor and find:

1. The Spring Boot version being used.
2. The Java version being targeted.
3. How many `<dependency>` blocks are listed, and what each one is for.

??? note "Show solution"
    Looking at the file:

    1. Spring Boot version: set in the `<parent><version>` tag — `3.3.4`.
    2. Java version: set via `<properties><java.version>17</java.version>`.
    3. Two dependencies:
       - `spring-boot-starter-web` — lets the app handle web
         requests/build a REST API (used for `/hello` in Lesson 3).
       - `spring-boot-starter-test` (scope `test`) — testing tools, only
         included when running tests, not in the final packaged app.

## Checklist: before moving on

Before moving on, make sure you can...

- [ ] Point to where your own Java code goes, versus where configuration
      goes, versus where generated output goes.
- [ ] Explain what a "starter" dependency is, in one sentence.
- [ ] Explain why `target/` is in `.gitignore`.

## What's next

In [Lesson 5](05-rest-api.md), we'll build a real REST API with multiple
endpoints — GET and POST — going well beyond the single `/hello` route.
