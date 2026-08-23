# Spring Boot Guide

A beginner-friendly, hands-on tutorial that teaches **Spring Boot** from
scratch to anyone who already knows basic Java. Plain language, small
lessons (15–30 minutes each), working code for every lesson, and a
"try it yourself" exercise in each one.

📖 **Read the tutorial online:** the published site (once GitHub Pages is
enabled — see [Deployment](#deployment) below) will live at
`https://trishala23.github.io/SpringBootGuide/`.

## Prerequisites

- Basic Java knowledge (variables, classes, methods). No Spring
  experience required.
- Java 17+ and Maven to run the code examples (Lesson 2 walks through
  installing these).
- A code editor — IntelliJ IDEA Community Edition or VS Code both work
  well.

## How to use this repo

- **To read the tutorial:** visit the GitHub Pages site linked above, or
  browse the Markdown files directly under [`docs/lessons/`](docs/lessons).
- **To run the code for a lesson:** open the matching folder under
  [`code-examples/`](code-examples), e.g. `code-examples/lesson-03-hello-world`,
  and run `mvn spring-boot:run` inside it.
- **To preview the site locally:**

  ```bash
  pip install -r requirements.txt
  mkdocs serve
  ```

  then open `http://127.0.0.1:8000`.

## Table of contents

| # | Lesson | Status |
|---|--------|--------|
| 1 | [What is Spring Boot & why use it](docs/lessons/01-what-is-spring-boot.md) | ✅ |
| 2 | [Setting up your environment](docs/lessons/02-setting-up-environment.md) | ✅ |
| 3 | [Your first Spring Boot app ("Hello World")](docs/lessons/03-first-app-hello-world.md) | ✅ |
| 4 | [Understanding project structure](docs/lessons/04-project-structure.md) | ✅ |
| 5 | [Building a REST API](docs/lessons/05-rest-api.md) | ✅ |
| 6 | [Working with data (in-memory first)](docs/lessons/06-in-memory-data.md) | ✅ |
| 7 | [Connecting to a real database](docs/lessons/07-database.md) | ✅ |
| 8 | [Configuration basics](docs/lessons/08-configuration.md) | ✅ |
| 9 | Error handling basics | 🔜 |
| 10 | Testing your app | 🔜 |
| 11 | Packaging & running your app | 🔜 |
| 12 | What's next | 🔜 |

This table is updated as each lesson is merged to `main`.

## Repository structure

```
docs/                   Tutorial content, built into a site by MkDocs
  index.md               Landing page / table of contents
  lessons/                One Markdown file per lesson (01-*.md, 02-*.md, ...)
code-examples/          One runnable Maven project per lesson that has code
GUIDELINES.md           Writing style, naming conventions, review checklist
CONTRIBUTING.md         How to contribute, branch naming, commit style
CODE_OF_CONDUCT.md      Community guidelines
mkdocs.yml               Site configuration (nav, theme, plugins)
.github/workflows/       CI: builds and deploys the site on push to main
```

## Deployment

The site is built with **[MkDocs](https://www.mkdocs.org/)** and the
**[Material theme](https://squidfunk.github.io/mkdocs-material/)**. We
chose MkDocs Material over plain Jekyll (GitHub Pages' default) because it
gives us collapsible "show solution" blocks, built-in Mermaid diagrams,
search, and code-copy buttons out of the box — all the interactive touches
this tutorial needs — without adding a Node.js/React build step the way
Docusaurus would.

`.github/workflows/deploy-docs.yml` builds the site and deploys it to
GitHub Pages automatically on every push to `main`.

**One-time setup** (repository admin, done once in the GitHub UI): go to
**Settings → Pages** and set **Source** to **GitHub Actions**. After that,
every merge to `main` republishes the site automatically.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for branch naming and commit
message rules, and [GUIDELINES.md](GUIDELINES.md) for how lessons are
written and structured. Please also review our
[Code of Conduct](CODE_OF_CONDUCT.md).

## License

[MIT](LICENSE)
