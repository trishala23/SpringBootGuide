# Lesson 8: Configuration basics

*Estimated time: 20 minutes*

## What you'll learn

- What `application.properties` is really for, and how `application.yml`
  compares.
- Two ways to read your own settings into your code: `@Value` and
  `@ConfigurationProperties`.
- What a "profile" is and why it's useful (e.g. different settings for
  your laptop versus a real server).

## The concept (plain words)

You've already used `application.properties` in Lesson 7 to configure
the database. **Configuration** just means: values your app needs that
you want to be able to change *without* rewriting Java code — a port
number, a greeting message, a feature toggle, credentials.

Spring Boot reads `src/main/resources/application.properties` (or
`.yml`) automatically on startup and makes every value in it available to
your code.

## `application.properties` vs. `application.yml`

Both formats do exactly the same job — pick whichever reads more clearly
to you. Here's the same configuration in each:

=== "application.properties"

    ```properties
    spring.datasource.url=jdbc:h2:mem:tasksdb
    spring.jpa.hibernate.ddl-auto=update
    app.greeting=Welcome to the Task API!
    app.max-tasks=100
    ```

=== "application.yml"

    ```yaml
    spring:
      datasource:
        url: jdbc:h2:mem:tasksdb
      jpa:
        hibernate:
          ddl-auto: update
    app:
      greeting: Welcome to the Task API!
      max-tasks: 100
    ```

YAML (`.yml`) uses indentation to group related settings, which can read
more cleanly once you have a lot of them — notice `spring.datasource.url`
became nested under `spring:` then `datasource:`. `.properties` is flatter
and arguably harder to misread when you're just skimming for one value.
This tutorial's code examples use `.properties` for consistency, but
either works — Spring Boot doesn't prefer one over the other.

!!! tip
    The full working project (using `.properties`) is at
    [`code-examples/lesson-08-configuration`](https://github.com/trishala23/SpringBootGuide/tree/main/code-examples/lesson-08-configuration).

## Reading your own settings: `@Value`

Add your own setting to `application.properties`:

```properties
app.greeting=Welcome to the Task API!
```

And read it with `@Value`:

```java
@RestController
public class GreetingController {

    @Value("${app.greeting:Hello!}")
    private String greeting;

    @GetMapping("/greeting")
    public String getGreeting() {
        return greeting;
    }

}
```

`${app.greeting:Hello!}` means "read the property `app.greeting`, or use
`Hello!` if it's not set anywhere." `@Value` is quick and fine for one or
two settings, but it gets messy once you have many related values spread
across several classes.

## A tidier way: `@ConfigurationProperties`

For a group of related settings, group them into one typed class instead:

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String greeting;
    private int maxTasks;

    // getters and setters
}
```

```properties
app.greeting=Welcome to the Task API!
app.max-tasks=100
```

Notice `app.max-tasks` (kebab-case, with hyphens, the conventional style
in properties files) automatically matches the Java field `maxTasks`
(camelCase) — Spring Boot calls this "**relaxed binding**," and it works
the same way in both `.properties` and `.yml`.

To activate it, either add `@EnableConfigurationProperties(AppProperties.class)`
somewhere, or — simpler — add `@ConfigurationPropertiesScan` on your main
application class, so Spring Boot finds every `@ConfigurationProperties`
class automatically:

```java
@SpringBootApplication
@ConfigurationPropertiesScan
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

Then inject `AppProperties` like any other bean (remember dependency
injection from Lesson 6):

```java
private final AppProperties appProperties;

public GreetingController(AppProperties appProperties) {
    this.appProperties = appProperties;
}
```

**Rule of thumb:** one or two simple values, `@Value` is fine. Several
related settings, `@ConfigurationProperties` keeps them organized,
type-checked, and easy to find in one place.

## Profiles: different settings for different situations

A **profile** is a named set of overrides — commonly used for "what's
different between my laptop and a real server." Create
`application-dev.properties` next to `application.properties`:

```properties
app.greeting=Welcome to the Task API! [DEV MODE]
```

Anything in `application-dev.properties` overrides the matching key in
`application.properties`, but *only* when the `dev` profile is active.
Activate it by running:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```

or, when running the packaged jar directly (covered in Lesson 11):

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

Visiting `/greeting` now returns the dev-mode message instead of the
default one — without changing any Java code.

## Why this matters

Keeping configuration separate from code means you can change how your
app behaves — which database it talks to, feature toggles, environment-
specific messages — without recompiling or redeploying your code, and
without ever hardcoding secrets or environment-specific values into a
Java file.

## Try it yourself

Add a new setting, `app.max-tasks`, and use it in `TaskController`'s
`createTask` method to reject new tasks once the repository already has
`maxTasks` tasks stored, returning `429 Too Many Requests` in that case.

??? note "Show solution"
    ```java
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
        if (taskRepository.count() >= appProperties.getMaxTasks()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        Task saved = taskRepository.save(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    ```

    `taskRepository.count()` is another method `JpaRepository` gives you
    for free, just like `findAll()` and `save()` from Lesson 7.
    `TaskController` needs `AppProperties` injected through its
    constructor, the same way `GreetingController` does.

## Checklist: before moving on

Before moving on, make sure you can...

- [ ] Explain the difference between `.properties` and `.yml` — and that
      neither is "more correct."
- [ ] Add a custom setting and read it with `@Value`.
- [ ] Explain when you'd reach for `@ConfigurationProperties` instead of
      `@Value`.
- [ ] Explain what a profile is, in one sentence.

## What's next

In [Lesson 9](09-error-handling.md), we'll handle errors properly —
replacing manual `ResponseEntity.notFound()` checks scattered through
controllers with a single, reusable approach.
