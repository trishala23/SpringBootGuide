# Lesson 12: What's next

*Estimated time: 15 minutes*

## What you'll learn

- A recap of everything you've built.
- A map of the topics that naturally come next, and why each one
  matters.
- A suggested project to practice everything together.

There's no code in this lesson — it's a wrap-up and a map for where to
go from here, the same way Lesson 1 started with concepts before code.

## Look how far you've come

Starting from nothing, you built a task-management REST API that:

- Runs on its own embedded web server (Lesson 3).
- Exposes GET/POST/DELETE endpoints following REST conventions (Lesson 5).
- Cleanly separates request handling from data storage using dependency
  injection (Lesson 6).
- Persists data in a real relational database through Spring Data JPA
  (Lesson 7).
- Reads its own configuration, including environment-specific overrides
  (Lesson 8).
- Handles errors and validates input consistently, from one central
  place (Lesson 9).
- Has real automated tests covering both the data and web layers (Lesson 10).
- Packages into a single file you can run anywhere Java runs (Lesson 11).

That's a genuinely complete, small, real application — not a toy. Every
concept above shows up, in some form, in large production Spring Boot
systems too.

## Where to go from here

You don't need all of these next — pick whichever solves a problem you
actually have, or sounds most interesting.

**Securing your API**
: Right now, anyone can call every endpoint. **Spring Security** adds
  authentication (proving who you are, e.g. login) and authorization
  (controlling what you're allowed to do). Start with the official
  [Spring Security](https://spring.io/projects/spring-security) project
  page — it has guided tutorials for adding login and protecting
  endpoints.

**More advanced data modeling**
: Lesson 7 covered one entity. Real apps usually need *relationships*
  between entities (a task belonging to a user, a project with many
  tasks), pagination for large lists, and sorting. These build directly
  on the Spring Data JPA concepts from Lesson 7.

**Documenting your API**
: As an API grows, other developers (or your future self) need a
  reference. **springdoc-openapi** generates interactive API
  documentation automatically from your existing controllers, with
  almost no extra code.

**Containers and deployment**
: Lesson 11 got you to a runnable `.jar`. The natural next step is
  packaging that into a **Docker** container — a self-contained,
  portable unit that runs identically on your machine, a teammate's
  machine, or a cloud server — and then deploying it somewhere real.

**Observability**
: **Spring Boot Actuator** adds ready-made endpoints for health checks,
  metrics, and diagnostics — the kind of visibility real running
  applications need, with very little setup.

**Asynchronous and event-driven code**
: Once requests need to trigger slower background work (sending an
  email, processing a file), look into `@Async`, and eventually
  messaging systems like RabbitMQ or Kafka for larger systems.

**Going deeper on testing**
: Lesson 10 was intentionally gentle. From here, look into integration
  tests with `@SpringBootTest`, testing with a real (temporary) database
  using Testcontainers, and measuring test coverage.

## A project to tie it all together

The best way to cement everything from this tutorial is to extend the
task API yourself. Some ideas, roughly in order of difficulty:

1. Add a `priority` field (low/medium/high) to `Task`, with validation
   restricting it to those three values.
2. Add pagination to `GET /tasks` so it doesn't return every task at
   once.
3. Add a `Project` entity that a `Task` belongs to (a one-to-many
   relationship).
4. Add simple authentication so each user only sees their own tasks.
5. Containerize the whole thing with Docker and deploy it somewhere
   public.

## Try it yourself

Pick **one** topic from "Where to go from here" that interests you most.
Write down, in a few sentences: what problem does it solve, and what's
the smallest possible change you could make to this tutorial's project
to try it out?

??? note "Show solution"
    There's no single right answer here — the point is committing to a
    concrete, small next step instead of an open-ended "learn Spring
    Security someday." For example:

    *"I'll try Spring Security. The problem it solves is that anyone can
    currently call DELETE /tasks/{id} on any task. The smallest change
    I could make is adding HTTP Basic authentication so at least a
    username and password are required for every request, before I
    worry about per-user data."*

    A concrete, scoped plan like that is far more likely to actually
    happen than a vague intention to "learn more Spring Security."

## Checklist: before moving on

Before you consider this tutorial complete, make sure you can...

- [ ] Explain, without notes, what each of the 11 previous lessons
      covered.
- [ ] Build a new small Spring Boot project from scratch, from Spring
      Initializr to a running endpoint, without referring back to
      Lesson 3.
- [ ] Name at least one topic from this lesson you plan to explore next.

## Thank you

That's the whole tutorial. If something was unclear, confusing, or just
plain wrong anywhere along the way, contributions are very welcome — see
[CONTRIBUTING.md](https://github.com/trishala23/SpringBootGuide/blob/main/CONTRIBUTING.md).
Good luck with whatever you build next.
