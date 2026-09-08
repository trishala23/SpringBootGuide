# Lesson 13: Spring Boot interview questions (beginner to advanced)

*Estimated time: 55 minutes*

## What you'll learn

- The Spring Boot interview questions that come up again and again, grouped
  from beginner to advanced.
- Plain-word answers you can actually say out loud in an interview, not
  just recognize when you read them.
- How to reason through **scenario-based questions** — "here's a bug, what
  would you check?" — the kind interviewers use to see if you can actually
  debug, not just define terms.
- How each answer connects back to something you already built earlier in
  this tutorial, so you're explaining real experience, not memorized
  theory.

Like [Lesson 12](12-whats-next.md), this lesson has no new project to
build. It's a reference you can revisit before an interview — read it
straight through once, then come back and quiz yourself on individual
sections.

## How to use this lesson

For each question below:

1. Cover the answer and try to explain it out loud first, as if someone
   asked you across a table.
2. Then open "Show answer" and compare.
3. If you struggled, jump back to the lesson linked in parentheses — every
   answer here is something you already touched earlier in this guide.

The questions are grouped into **Beginner**, **Intermediate**, **Advanced**,
and **Scenario-based**. Interviewers usually start beginner and go deeper
based on your answers, so read them in that order the first time through.
The scenario section is different on purpose: instead of asking "what is
X," it describes a bug or a situation and asks what you'd actually do —
closer to how senior interviews (and real jobs) work.

## Code example

Several intermediate and advanced answers below refer back to this one
example, so it's worth looking at once up front: two ways of getting a
dependency into a class, which come up in almost every Spring interview.

```java
// Field injection - works, but interviewers will ask you why it's discouraged
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
}
```

```java
// Constructor injection - the recommended way
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
```

Both compile and both work. The difference is *why* the second one is
preferred — that's covered in the intermediate section below.

## Beginner questions

### What is Spring Boot, and how is it different from the Spring Framework?

??? note "Show answer"
    The **Spring Framework** is a large toolkit for building Java
    applications, built around dependency injection (Lesson 1). Using it
    on its own means writing a lot of setup code and picking every piece
    (web server, JSON library, database driver) yourself.

    **Spring Boot** is built on top of Spring. It adds **auto-configuration**
    (sensible defaults, configured for you) and **starter dependencies**
    (bundles of libraries that work together), so you get a running web
    application in minutes instead of hours. You saw this directly in
    [Lesson 3](03-first-app-hello-world.md): one `@SpringBootApplication`
    class and a single dependency were enough to run a real web server.

### What is auto-configuration?

??? note "Show answer"
    Auto-configuration is Spring Boot looking at what's on your
    classpath (which dependencies you added) and what beans you've
    already defined, and then configuring sensible defaults for the rest
    automatically. Add `spring-boot-starter-web`, and Spring Boot notices
    it and configures an embedded Tomcat server and a JSON converter for
    you, with no XML or manual setup. You can always override any of it
    yourself in `application.properties` (Lesson 8) — auto-configuration
    only fills in what you haven't configured.

### What is a "starter" dependency?

??? note "Show answer"
    A starter (like `spring-boot-starter-web` or
    `spring-boot-starter-data-jpa`) is a single dependency that pulls in
    everything commonly needed for one job, at compatible versions. Instead
    of individually adding and version-matching a web server, a JSON
    library, and a validation library, you add one starter and Spring Boot
    Initializr/Maven brings in a tested set that works together. This is
    why [Lesson 2](02-setting-up-environment.md) only needed you to tick a
    few checkboxes on Spring Initializr.

### What does `@SpringBootApplication` actually do?

??? note "Show answer"
    It's a shortcut for three annotations combined:

    - `@Configuration` — this class can define beans.
    - `@EnableAutoConfiguration` — turn on Spring Boot's auto-configuration.
    - `@ComponentScan` — look for other Spring-managed classes (marked
      `@Component`, `@Service`, etc.) in this package and below.

    You used this in every single lesson without necessarily knowing what
    it expanded to — it's on the class with `main()` in every project
    since [Lesson 3](03-first-app-hello-world.md).

### What is dependency injection, in plain words?

??? note "Show answer"
    Instead of a class creating the objects it depends on itself (with
    `new`), it declares what it needs, and Spring hands ("injects") a
    ready-made instance to it. This is why in
    [Lesson 6](06-in-memory-data.md) `TaskController` never wrote
    `new TaskRepository()` — Spring created one `TaskRepository` and
    handed it to the controller automatically. The benefit: the
    controller doesn't need to know *how* to build a `TaskRepository`,
    only that it needs one — which makes swapping implementations (like
    the in-memory-to-database change in Lesson 7) painless.

### What's the difference between `@Component`, `@Service`, `@Repository`, and `@Controller`?

??? note "Show answer"
    All four do the same core thing: they mark a class as a Spring bean,
    so it gets picked up by component scanning and can be injected
    elsewhere. `@Component` is the generic one; the others are more
    specific names for the same mechanism, used to signal *intent*:

    - `@Service` — business logic.
    - `@Repository` — data access (and it also translates database
      exceptions into a consistent Spring exception type).
    - `@Controller` / `@RestController` — handles web requests.

    Using the specific one where it fits makes code easier to navigate —
    anyone reading `TaskRepository` immediately knows its job from the
    annotation alone.

### What is `application.properties` (or `.yml`) for?

??? note "Show answer"
    It's where you configure your app without touching Java code — server
    port, database connection details, logging levels, your own custom
    settings. [Lesson 8](08-configuration.md) covered reading values from
    it with `@Value` and `@ConfigurationProperties`, and how
    profile-specific files (`application-dev.properties`) let the same
    code behave differently per environment.

### Why does Spring Boot include an embedded server instead of needing one installed separately?

??? note "Show answer"
    Because it means the app is completely self-contained: `mvn
    spring-boot:run` (or the packaged `.jar` from
    [Lesson 11](11-packaging.md)) starts its own Tomcat server internally
    — nothing to install, configure, or version-match separately on the
    machine running it. That's a big part of why Spring Boot apps are
    easy to hand off and deploy anywhere Java runs.

## Intermediate questions

### What's the difference between `@Controller` and `@RestController`?

??? note "Show answer"
    `@RestController` is `@Controller` plus `@ResponseBody` applied to
    every method automatically, meaning return values are written
    straight into the HTTP response body (as JSON, usually) instead of
    being treated as the name of an HTML view template. Every controller
    you built from [Lesson 5](05-rest-api.md) onward was a
    `@RestController`, because this whole tutorial builds a JSON API, not
    server-rendered HTML pages.

### Why is constructor injection preferred over field injection?

??? note "Show answer"
    Look back at the [code example](#code-example) above. With
    constructor injection:

    - The dependency can be `final`, so it's impossible to reassign it by
      accident after construction.
    - You can't create a `TaskService` without providing a
      `TaskRepository` — a missing dependency fails immediately and
      obviously, at construction time, instead of showing up later as a
      confusing `NullPointerException`.
    - Plain unit tests (no Spring container at all) can just call
      `new TaskService(fakeRepository)` directly.

    Field injection (`@Autowired` on the field) needs Spring's container
    to set the field via reflection, so it's harder to test in isolation
    and doesn't get any of the guarantees above.

### What are Spring profiles, and why would you use them?

??? note "Show answer"
    A profile is a named set of configuration that's only active in
    certain situations — for example, `application-dev.properties`
    pointing at a local database, and `application-prod.properties`
    pointing at a production one. You activate one with
    `spring.profiles.active=dev`. This lets the exact same code run
    against different configuration per environment, which
    [Lesson 8](08-configuration.md) demonstrated with the H2-to-real-database
    switch.

### What does `@Transactional` do?

??? note "Show answer"
    It wraps a method in a database transaction: every database operation
    inside either **all** succeed together, or if any of them throws an
    exception, **all** of them are rolled back — the database ends up as
    if none of them happened. This matters the moment one logical
    operation touches more than one row or table (like moving money
    between two accounts): without it, a failure partway through could
    leave the database in an inconsistent state.

### How does Spring Boot validate incoming data?

??? note "Show answer"
    With Bean Validation annotations (`@NotBlank`, `@Size`, `@Min`, and so
    on) on your request model, plus `@Valid` on the controller parameter
    to tell Spring to actually check them. If validation fails, Spring
    throws an exception before your controller method body even runs.
    [Lesson 9](09-error-handling.md) covered catching that exception with
    a `@ControllerAdvice` to turn it into a clean `400 Bad Request`
    response instead of a raw stack trace.

### What is a DTO, and why not just return your `@Entity` objects directly from a controller?

??? note "Show answer"
    A **DTO** (Data Transfer Object) is a plain class shaped exactly like
    what an API should send or receive — separate from your `@Entity`
    class, which is shaped for the database. Returning entities directly
    is tempting (it's less code) but tends to cause problems: you can
    accidentally expose internal fields you didn't mean to, changing a
    database column can silently change your API's JSON shape, and lazy
    JPA relationships can trigger extra, unexpected database queries or
    serialization errors. A DTO decouples "what the database looks like"
    from "what the API promises," at the cost of a small amount of extra
    mapping code.

### What does `@ControllerAdvice` with `@ExceptionHandler` do?

??? note "Show answer"
    It centralizes error handling. Instead of every controller method
    having its own try/catch, one `@ControllerAdvice` class listens for
    specific exception types thrown anywhere in the app and converts each
    into a consistent HTTP response. This is exactly what
    [Lesson 9](09-error-handling.md) built: a `TaskNotFoundException`
    thrown from a controller becomes a clean `404` with a JSON error
    body, from one single place.

### What scope do Spring beans have by default, and what does that mean?

??? note "Show answer"
    **Singleton** — by default, Spring creates exactly *one* instance of
    each bean per application, and hands out that same instance every
    time it's injected somewhere. That's why it's important beans like
    `TaskRepository` don't hold request-specific mutable state in
    instance fields: that same single instance is shared, potentially
    across many simultaneous requests.

### How do Spring Data JPA repository interfaces work, given you never write an implementation?

??? note "Show answer"
    You write an interface (`interface TaskRepository extends
    JpaRepository<Task, Long>`) and Spring Data JPA generates a working
    implementation for you at startup, based on the interface you
    extended and the method names you declare (like
    `findByTitleContaining`, which it turns into a real query just from
    the method's name). This is why [Lesson 7](07-database.md) never
    needed you to write any SQL or JDBC code to save and fetch tasks.

## Advanced questions

### How does Spring Boot's auto-configuration actually decide what to configure?

??? note "Show answer"
    Each auto-configuration class is annotated with conditions like
    `@ConditionalOnClass` (only apply if a certain class is on the
    classpath), `@ConditionalOnMissingBean` (only apply if you haven't
    already defined your own bean of that type), or
    `@ConditionalOnProperty` (only apply if a config value is set a
    certain way). Spring Boot ships a long list of these auto-configuration
    classes (registered via a file under
    `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`),
    evaluates every condition on startup, and activates only the ones that
    match. That's *why* adding `spring-boot-starter-data-jpa` and defining
    a `DataSource` is enough to get a working `EntityManager` — and why
    defining your own bean of a type Spring Boot would otherwise
    auto-configure silently takes priority over the default.

### What is the Spring bean lifecycle?

??? note "Show answer"
    In rough order: the container instantiates the bean, injects its
    dependencies (constructor or field injection), calls any
    `@PostConstruct`-annotated method (a hook for "run this once
    everything's wired up"), and the bean is then ready to use for the
    rest of the application's life (assuming singleton scope). On shutdown,
    any `@PreDestroy`-annotated method runs, giving you a chance to release
    resources (close a connection pool, flush a buffer) cleanly.
    `BeanPostProcessor` implementations can also hook into this process to
    modify beans right after creation — this is how many Spring Boot
    internals (like AOP proxies, discussed next) actually get applied.

### How does something like `@Transactional` work, mechanically?

??? note "Show answer"
    Through **AOP (Aspect-Oriented Programming)** and proxies. When a bean
    has a method annotated `@Transactional`, Spring doesn't hand callers
    the real object directly — it wraps it in a **proxy** object with the
    same public methods. Calling the annotated method actually calls the
    proxy first, which starts a transaction, calls the real method, then
    commits or rolls back based on whether an exception was thrown. This
    is also why `@Transactional` silently doesn't work when you call an
    annotated method from *another method in the same class* — that
    internal call bypasses the proxy entirely and goes straight to the
    real object.

### What's the N+1 query problem, and how do you fix it?

??? note "Show answer"
    It happens when you fetch a list of *N* entities, and then for each
    one, a related entity is lazily loaded with its own separate query —
    turning what looks like one operation into 1 + N database round
    trips. For example, fetching 50 tasks, then accessing
    `task.getProject().getName()` on each one, if `project` is lazily
    loaded, fires 50 extra queries. Fixes include fetching the
    relationship eagerly up front for that specific query (JPQL
    `JOIN FETCH`, or a Spring Data `@EntityGraph`), rather than changing
    the mapping to always-eager, which just moves the same cost elsewhere.

### How would you add authentication and authorization to the API built in this tutorial?

??? note "Show answer"
    Add `spring-boot-starter-security`, which by default locks down every
    endpoint immediately (a good sign it's working). From there, a common
    approach for a REST API is: a `SecurityFilterChain` bean defining which
    endpoints need authentication, a login endpoint that issues a signed
    **JWT** (JSON Web Token) after checking credentials, and a filter that
    reads that token from the `Authorization` header on every later request
    to identify the user — no server-side session needed. This is exactly
    the "Securing your API" next step named in
    [Lesson 12](12-whats-next.md#where-to-go-from-here).

### What does Spring Boot Actuator give you, and why does it matter in production?

??? note "Show answer"
    Actuator adds ready-made HTTP endpoints for operational visibility —
    `/actuator/health` (is the app and its dependencies, like the
    database, up?), `/actuator/metrics` (request counts, memory, custom
    counters), and others — with almost no code, just adding the starter
    and choosing which endpoints to expose. In production, this is what
    load balancers use to know whether to send traffic to an instance,
    and what monitoring dashboards read to alert someone before users
    notice a problem.

### When would you reach for reactive programming (Spring WebFlux) instead of the traditional (Spring MVC) approach used in this tutorial?

??? note "Show answer"
    Everything in this tutorial uses Spring MVC's traditional model: one
    thread handles one request from start to finish, blocking while it
    waits on the database. That's simple to reason about and completely
    fine for most applications. **WebFlux** uses a small, fixed pool of
    threads that never block — while waiting on a slow database or
    another network call, that thread is freed to handle other work, and
    a callback resumes the original request later. It shines under very
    high concurrent load with lots of I/O waiting (thousands of
    simultaneous slow requests), at the cost of a genuinely harder
    programming model. Most teams should default to Spring MVC unless
    they've measured a real need for the alternative.

### Why is relying on `spring.jpa.hibernate.ddl-auto=update` risky in production, and what's the alternative?

??? note "Show answer"
    `ddl-auto=update` (used for convenience early in
    [Lesson 7](07-database.md)) lets Hibernate guess and apply schema
    changes automatically based on your `@Entity` classes. It's fast for
    local development, but in production it's risky: Hibernate's guess
    about how to change a live schema (say, splitting one column into two)
    might not match what you actually intended, there's no history of what
    changed or when, and there's no clean way to roll a bad change back.
    The standard alternative is a migration tool like **Flyway** or
    **Liquibase**: you write each schema change as its own versioned,
    reviewable script, it runs automatically on startup, and every
    environment ends up with an identical, auditable history of exactly
    how the schema got to its current shape.

### How would you resolve a circular dependency between two Spring beans?

??? note "Show answer"
    A circular dependency (bean A needs bean B in its constructor, and
    bean B needs bean A in its constructor) usually means the two classes
    are too tightly coupled and is often a sign to rethink the design —
    for example, pulling the shared logic both depend on into a third
    class they both use instead. When redesigning genuinely isn't
    practical, options include injecting one side lazily
    (`@Lazy`, so Spring defers creating it until it's actually used) or
    switching one side to field/setter injection so the constructors don't
    directly depend on each other. Reaching for `@Lazy` as a first fix
    without understanding why the cycle exists is usually treating the
    symptom, not the cause.

## Scenario-based questions

These don't have a one-line definition as the answer. Read the scenario,
decide what you'd actually check first, *then* open the answer — the
order you'd investigate in matters as much as the root cause itself.

### `GET /tasks/999` for a task that doesn't exist returns a `500` error with a stack trace, instead of a clean `404`. What's happening, and how do you fix it?

??? note "Show answer"
    Somewhere the code is calling something like
    `taskRepository.findById(id).get()` and letting the `Optional`'s own
    `NoSuchElementException` escape, instead of turning a missing task
    into a deliberate outcome. The fix, straight from
    [Lesson 9](09-error-handling.md): call
    `.orElseThrow(() -> new TaskNotFoundException(id))` instead of `.get()`,
    and make sure a `@ControllerAdvice` with an `@ExceptionHandler` for
    `TaskNotFoundException` maps it to a `404` response. The underlying
    lesson: an unchecked exception you didn't plan for always becomes a
    raw `500` — every "expected failure" (not found, invalid input) needs
    to be turned into a specific exception you deliberately handle.

### Your app works fine locally but fails to start in production with `Failed to configure a DataSource: 'url' attribute is not specified`. What do you check?

??? note "Show answer"
    Locally you likely have an `application-dev.properties` (or an H2
    in-memory default) supplying a database URL, and production doesn't
    have the equivalent values set. Check, in order: which profile is
    actually active in production (`spring.profiles.active`, often set
    via an environment variable like `SPRING_PROFILES_ACTIVE`), whether
    the production profile's properties file exists and is actually being
    packaged into the deployed artifact, and whether the database
    connection details are meant to come from environment variables
    instead (common for secrets, so they're not committed to the repo).
    This is the same profile mechanism from
    [Lesson 8](08-configuration.md), just missing its production half.

### A teammate's app fails to start with `Parameter 0 of constructor in TaskService required a single bean, but 2 were found`. Explain the error and how you'd fix it.

??? note "Show answer"
    Spring found two beans that both match the type `TaskService`'s
    constructor is asking for, and refuses to guess which one you meant —
    this usually happens after someone adds a second `@Bean` or
    `@Component` of the same interface type (for example, two different
    `PaymentGateway` implementations). Fix it by telling Spring which one
    you mean: mark the default choice `@Primary`, or use `@Qualifier`
    with a bean name at the injection point, whichever expresses the
    intent more clearly. It's the same dependency-injection mechanism
    from [Lesson 6](06-in-memory-data.md) — it just breaks the moment
    "exactly one candidate" stops being true.

### A colleague added `@Transactional` to `TaskService.markAllDone()`, but it calls another `@Transactional` method on `this` internally, and rollback isn't happening on failure. Why not?

??? note "Show answer"
    This is the AOP proxy issue from the [advanced section](#how-does-something-like-transactional-work-mechanically)
    above, showing up as a real bug. `@Transactional` only takes effect
    when the call comes through Spring's proxy — an *external* caller
    invoking `markAllDone()` goes through the proxy correctly, but
    `markAllDone()` calling another method on `this` is a plain Java call
    that bypasses the proxy entirely, so the inner method's
    `@Transactional` is silently ignored. The fix is usually to move the
    inner method to a separate bean and inject that bean, so the call
    goes through a real proxy again — not to add more annotations to the
    same class.

### After adding a `GET /tasks` endpoint that also returns each task's project name, response times get much worse as the task count grows. What's going on?

??? note "Show answer"
    This is the [N+1 query problem](#whats-the-n1-query-problem-and-how-do-you-fix-it)
    from the advanced section, caught in the wild: fetching *N* tasks
    triggers one query per task to lazily load its `project`, on top of
    the original query — so response time scales with the number of
    tasks instead of staying flat. Confirm it by turning on SQL logging
    (`spring.jpa.show-sql=true`) and counting queries for one request.
    Fix it by fetching the relationship up front for this specific query,
    with a JPQL `JOIN FETCH` or a Spring Data `@EntityGraph`, rather than
    making the mapping eager everywhere (which just pays the same cost on
    every other query that doesn't need it).

### Your `TaskControllerTest` suite (from Lesson 10) passes every time you run it alone, but fails intermittently when the full test suite runs in CI. What would you investigate?

??? note "Show answer"
    Intermittent failures that depend on *which other tests ran first*
    almost always mean shared state is leaking between tests — most often
    a real database or an `@MockBean` whose stubbed behavior wasn't reset
    between tests. Check whether the failing tests use `@SpringBootTest`
    or `@DataJpaTest` against a real (even if temporary) database without
    each test cleaning up the rows it created, and whether tests run in a
    fixed order that happens to hide the problem locally. The fix is
    usually making each test independent — wrapping each in its own
    transaction that rolls back automatically (`@DataJpaTest` does this
    by default), or explicitly clearing state in `@BeforeEach` — never
    "pin the test order" as a fix, since that just hides the same bug.

### You're asked to add "mark multiple tasks as done in one request" to the API from this tutorial. How would you design that endpoint?

??? note "Show answer"
    There's no single correct answer, but a solid response walks through
    the same decisions this tutorial made for the existing endpoints:
    pick a method and path that match REST conventions — a `PATCH
    /tasks/done` (or `/tasks/bulk-complete`) accepting a JSON body like
    `{"ids": [1, 2, 3]}`, since this is a partial update, not replacing
    or creating full resources. Validate the list isn't empty
    ([Lesson 9](09-error-handling.md)'s validation approach applies
    here too), decide what happens if one of the IDs doesn't exist (fail
    the whole request with a `404` listing the missing ID, or silently
    skip it — and say out loud which you'd pick and why), and return a
    response that confirms what actually changed rather than just `200
    OK` with no body. Naming the tradeoff explicitly (all-or-nothing vs.
    best-effort) is what separates a strong answer from just describing
    the happy path.

### Production users start reporting they occasionally see another user's tasks after you add authentication. Where would you start looking?

??? note "Show answer"
    "Occasionally" and "another user's data" together point at shared
    mutable state on a singleton bean — remember from the
    [intermediate section](#what-scope-do-spring-beans-have-by-default-and-what-does-that-mean)
    that Spring beans are singletons by default, shared across every
    concurrent request. If a `@Service` or `@Controller` stores the
    "current user" or a per-request result in an instance field instead
    of a local variable or the request-scoped security context, one
    user's request can overwrite it while another user's request is
    still reading it. The fix is making sure request-specific data always
    lives in method parameters/local variables (or a properly
    request-scoped bean), never in a singleton's instance field — and
    this bug is a good example of why that rule exists, not just a style
    preference.

## Why this matters

Interviewers aren't testing whether you've memorized definitions — they're
checking whether you actually understand the tools you've been using, and
whether you can reason about *why* something works, not just recite that
it does. Every question above traces back to something you personally
built and ran earlier in this tutorial, which means you can answer from
real experience instead of a memorized script — and that's exactly what
makes an answer sound confident instead of rehearsed.

## Try it yourself

Pick four questions above — one beginner, one intermediate, one advanced,
and one scenario — and write out your answer from memory, in your own
words, without looking. Then compare against the hidden answer.

??? note "Show solution"
    There's no single right answer here, but a strong self-check is
    whether your written answer does two things the model answers above
    do:

    1. Explains the concept in plain words first, *before* using its
       technical name.
    2. Ties back to something concrete — ideally a specific lesson, file,
       or line of code from this tutorial's task API, not just an
       abstract definition.

    If your answer to an advanced question was just a memorized
    one-liner with no concrete example attached, that's a sign to go
    back to that topic and actually try it hands-on before your next
    interview, rather than re-reading the definition again.

## Checklist: before moving on

Before you consider yourself interview-ready, make sure you can...

- [ ] Answer every beginner question above out loud, in plain words, with
      no notes.
- [ ] Explain constructor injection versus field injection, and *why*
      one is preferred — not just which one is preferred.
- [ ] Pick at least three advanced questions and explain them using a
      concrete example from the task API you built in this tutorial.
- [ ] For at least two scenario questions, say out loud what you'd check
      *first* and why, before jumping to the fix.
- [ ] Say, honestly, which topics above you're still shaky on — and go
      back to that lesson before an interview, not after.

## What's next

There's no Lesson 14 — this closes out the tutorial that
[Lesson 12](12-whats-next.md) wrapped up. If a topic above felt shaky,
that's your cue for where to go next: re-read the linked lesson, then
pick the matching topic from
[Lesson 12's "Where to go from here"](12-whats-next.md#where-to-go-from-here)
to go deeper hands-on. Good luck in the interview.
