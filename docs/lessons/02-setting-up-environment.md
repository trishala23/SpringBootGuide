# Lesson 2: Setting up your environment

*Estimated time: 20 minutes*

## What you'll learn

- How to install the Java Development Kit (**JDK**) — the software that
  lets your computer run and build Java programs.
- How to pick a code editor.
- How to use **Spring Initializr**, a website that generates a ready-to-run
  Spring Boot project for you.

## The concept (plain words)

To build and run a Spring Boot app, your computer needs three things:

1. **A JDK (Java Development Kit).** This is different from just a "JRE"
   (Java Runtime Environment) — a JRE can only *run* Java programs, while
   a JDK can also *build* them. You need the JDK.
2. **A build tool.** This is the program that downloads the pieces of
   code your project depends on and turns your source code into a
   runnable program. We're using **Maven** in this tutorial (it's the
   more common choice for beginners, and Spring Initializr sets it up
   for you automatically).
3. **A code editor.** Any text editor technically works, but a proper
   Java editor gives you autocomplete, error highlighting, and a "run"
   button, which makes learning much smoother.

## Step 1: Install the JDK

We recommend **Java 17** (a "LTS" release — meaning "Long-Term Support,"
a version that keeps getting security updates for years, so it's a safe
default to learn on).

- **Windows/macOS/Linux:** download a JDK from
  [Eclipse Temurin](https://adoptium.net/) (a free, widely-used JDK
  build) and run the installer for your operating system.
- Alternatively, if you use a package manager:
  - macOS (Homebrew): `brew install openjdk@17`
  - Ubuntu/Debian: `sudo apt install openjdk-17-jdk`
  - Windows (winget): `winget install EclipseAdoptium.Temurin.17.JDK`

### Verify it worked

Open a terminal (Command Prompt, PowerShell, or your Terminal app) and run:

```bash
java -version
```

You should see output mentioning `17` somewhere, like:

```
openjdk version "17.0.x" ...
```

## Step 2: Install Maven (optional — Spring Initializr can skip this for you)

Spring Boot projects come with a small "wrapper" script (`mvnw`) that
downloads the right Maven version automatically the first time you run
it — so you don't strictly need Maven installed globally. If you'd like
it installed anyway:

- macOS (Homebrew): `brew install maven`
- Ubuntu/Debian: `sudo apt install maven`
- Windows (winget): `winget install Apache.Maven`

Verify with:

```bash
mvn -version
```

## Step 3: Pick a code editor

Either of these works great and is free:

- **IntelliJ IDEA Community Edition** — download from
  [jetbrains.com/idea](https://www.jetbrains.com/idea/download/). Very
  beginner-friendly for Java specifically; this is what most of the
  screenshots you'll see in Spring Boot tutorials online use.
- **VS Code** — download from [code.visualstudio.com](https://code.visualstudio.com/),
  then install the **"Extension Pack for Java"** and **"Spring Boot
  Extension Pack"** from the Extensions panel.

Either choice is fine for this tutorial — pick whichever feels more
comfortable, or whichever your team/course already uses.

## Step 4: Meet Spring Initializr

[Spring Initializr](https://start.spring.io/) is a website (made by the
Spring team) that generates a starter Spring Boot project for you — the
folder structure, the build file, and a tiny bit of starter code — based
on options you pick. This is how almost every real Spring Boot project
begins.

You'll use it for real in the next lesson, but let's look at what you'll
choose:

| Field | What to pick | Why |
|---|---|---|
| Project | Maven | Matches the build tool we installed above. |
| Language | Java | This is a Java tutorial. |
| Spring Boot version | Latest non-SNAPSHOT release | Avoid "SNAPSHOT" versions — those are still being worked on and can be unstable. |
| Group | `com.example` | A placeholder package name — fine for learning. |
| Artifact | `demo` (or your project's name) | Becomes your project's folder/artifact name. |
| Packaging | Jar | The standard, self-contained packaging Spring Boot apps use. |
| Java | 17 | Matches the JDK you just installed. |
| Dependencies | `Spring Web` (for now) | Adds what you need to build web/REST features, starting in Lesson 5. |

**"Dependency"** just means a piece of pre-written code your project
relies on — Spring Initializr fetches it for you so you don't have to
find and download it yourself.

## Why this matters

Getting the environment right once, up front, saves you from confusing
errors later that have nothing to do with your code — like a build tool
that can't find the right Java version. It's worth these 20 minutes now.

## Try it yourself

Open a terminal and confirm both tools are ready:

```bash
java -version
mvn -version
```

??? note "Show solution"
    You don't need to type anything different — this exercise is just
    about running those two commands and reading the output.

    - `java -version` should print something containing `17` (e.g.
      `openjdk version "17.0.9"`). If it prints a much older version
      (like `1.8`), you likely have an older JDK installed *instead of*
      or *alongside* 17 — you may need to set your `JAVA_HOME` environment
      variable or reinstall.
    - `mvn -version` should print a Maven version and, further down,
      confirm which Java version it's using — make sure that also says 17.

    If either command says "command not found," the JDK/Maven `bin`
    folder isn't on your system's `PATH` yet — revisit the installer for
    your operating system, since most installers offer to do this for
    you automatically if you let them.

## Checklist: before moving on

Before moving on, make sure you can...

- [ ] Run `java -version` and see Java 17 (or newer) in the output.
- [ ] Open your chosen code editor.
- [ ] Explain what Spring Initializr does, in one sentence.

## What's next

In [Lesson 3](03-first-app-hello-world.md), you'll actually use Spring
Initializr to generate your first project, and get "Hello World" running
in your browser.
