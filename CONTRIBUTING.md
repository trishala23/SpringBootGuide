# Contributing

Thanks for helping make this tutorial better! This project is a
beginner-friendly guide to Spring Boot, and every contribution should keep
that spirit. Please also read [GUIDELINES.md](GUIDELINES.md) for the
writing-style and structure rules before you write content.

## Prerequisites for contributing

- Git installed locally.
- Java 17+ and Maven, if you're touching a `code-examples/` project.
- Python 3.9+, if you want to preview the docs site locally with MkDocs:

  ```bash
  pip install -r requirements.txt
  mkdocs serve
  ```

  Then open `http://127.0.0.1:8000`.

## Branching rules

- **Never commit directly to `main`.** All work happens on a branch and
  lands via a pull request.
- Branch names describe the change and use one of these prefixes:
  - `lesson-NN-slug` — a new or reworked lesson (e.g. `lesson-05-rest-api`).
  - `fix-...` — a bug fix or correction (e.g. `fix-readme-typo`).
  - `chore-...` — repo setup, tooling, CI, or non-lesson maintenance
    (e.g. `chore-setup-pages`).
  - `docs-...` — changes to repo-level docs (README, this file, etc.)
    that aren't a lesson.
- Keep a branch focused on one distinct change. Don't mix a new lesson
  with an unrelated bug fix.

## Commit message style

- Use the imperative mood, like you're giving an instruction:
  `Add lesson 05: building a REST API`, not "Added" or "Adds".
- First line under ~72 characters, summarizing the change.
- Prefix with a type when useful: `feat:`, `fix:`, `docs:`, `chore:`.
  Example: `feat: add lesson 07 - connecting to a real database`.
- Add a short body (a few lines) if the change needs more context than
  the summary line gives.

## Pull requests

- Open a PR from your branch into `main`.
- Write a short description covering: what changed, why, and how you
  tested it (e.g. "ran `mvn spring-boot:run` on the lesson-05 example and
  hit both endpoints with curl").
- Run through the **review checklist** in `GUIDELINES.md` §4 before
  requesting review or merging.
- A PR should only be merged once the lesson (or fix) is complete — no
  half-finished lessons on `main`.

## Reporting problems

If you spot an error, a confusing explanation, or broken code in a
lesson, please open an issue (or better, a `fix-...` branch and a PR)
describing what's wrong and where.
