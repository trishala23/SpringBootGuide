# Lesson 11: Packaging & running your app

*Estimated time: 15 minutes*

## What you'll learn

- How to turn your whole project into one runnable file.
- How to run that file with nothing but Java installed — no Maven, no
  IDE.
- How to change settings (like the port) without touching code or
  rebuilding.

## The concept (plain words)

So far you've always run the app with `mvn spring-boot:run`, inside your
project folder, with Maven available. That's great for development, but
it's not how you'd hand your app to someone else, or run it on a server —
they shouldn't need your source code, Maven, or an IDE at all.

The fix is **packaging**: bundling your compiled code, and everything it
depends on, into a single file you can run anywhere Java is installed.

## Step 1: Package the app

```bash
mvn clean package
```

`clean` removes any old build output first, and `package` compiles your
code, runs your tests (from Lesson 10 — the build actually stops if a
test fails, which is exactly the safety net you want), and produces a
`.jar` file in the `target/` folder:

```
target/demo-0.0.1-SNAPSHOT.jar
```

!!! tip
    The full working project is at
    [`code-examples/lesson-11-packaging`](https://github.com/trishala23/SpringBootGuide/tree/main/code-examples/lesson-11-packaging).

## What's actually inside that jar?

A normal Java `.jar` just holds your compiled classes — it still needs
all its dependencies (Spring, Tomcat, everything) available separately to
run. Spring Boot's Maven plugin repackages it into what's often called a
**fat jar** or **executable jar**: your code, *every* dependency it
needs, and even an embedded web server, all bundled into that one file.
That's why it's around 40-50 MB, rather than a few KB — and why it can
run completely on its own.

## Step 2: Run the packaged app

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Notice there's no `mvn` in that command at all — this only needs a JDK
(or even just a JRE) installed. This is exactly how you'd run the app on
a real server, in a container, or hand it to a teammate who's never
opened your source code.

Visit `http://localhost:8080/tasks` — same app, same behavior, just
running from the packaged file instead of through Maven.

Stop it the same way as always: `Ctrl+C` in the terminal it's running in.

## Step 3: Change settings without rebuilding

Remember `application.properties` and profiles from Lesson 8? Any of
those settings can also be overridden right on the command line, without
touching a file or rebuilding the jar:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=9090
```

Now the app listens on port 9090 instead of the default 8080 — useful
when you need to run two instances side by side, or when a server
already has something else using port 8080. The same `--spring.profiles.active=dev`
flag from Lesson 8 works exactly the same way here too.

## Running it in the background

While you're testing, it's often handy to run the app without tying up
your terminal:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar > app.log &
```

`> app.log` sends its output to a file instead of your screen, and `&`
runs it in the background. Find and stop it later with:

```bash
# find the process
ps aux | grep demo-0.0.1-SNAPSHOT.jar

# stop it (replace 12345 with the PID from the line above)
kill 12345
```

## Why this matters

Packaging is the bridge between "code that runs on my machine, through
my IDE" and "a real, deployable program." Every deployment method you'll
encounter later — a cloud platform, a Docker container, a plain server —
starts from this same packaged jar.

## Try it yourself

Package the app, then run two copies of it at the same time on different
ports (`8080` and `8081`), and confirm both respond independently with
`curl`.

??? note "Show solution"
    ```bash
    mvn clean package

    java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8080 > app-8080.log &
    java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8081 > app-8081.log &

    curl http://localhost:8080/tasks
    curl http://localhost:8081/tasks
    ```

    Both should respond with the same starter tasks — they're two
    completely independent instances of the same app, each with its own
    in-memory H2 database, just listening on different ports. Stop both
    with `kill` using the PIDs from `ps aux | grep demo-0.0.1-SNAPSHOT.jar`.

## Checklist: before moving on

Before moving on, make sure you can...

- [ ] Run `mvn clean package` and find the resulting `.jar` file.
- [ ] Explain what a "fat jar" is, in one sentence.
- [ ] Run the packaged app with `java -jar` — no Maven involved.
- [ ] Override a setting (like the port) from the command line.

## What's next

In Lesson 12, we'll wrap up with a look at what to explore next now that
you know the fundamentals.
