# Contributor & Style Guidelines

This file is the rulebook for writing and organizing lessons in this repo.
Read it before you write or edit a lesson. It keeps every lesson feeling
like it was written by the same friendly teacher, even though many
different people contribute.

## 1. Writing style rules

- **Write for someone who knows basic Java but has never touched Spring
  Boot.** Never assume they've used a web framework before.
- **Use simple, plain words.** If you must use a technical term (like
  "dependency injection" or "bean"), explain it in one plain sentence the
  *first time* it appears in a lesson, right where it's used — don't send
  the reader elsewhere to look it up.
- **Short sentences.** If a sentence has more than one comma, consider
  splitting it in two.
- **One concept per section.** Each `##` heading should teach exactly one
  idea. If you find yourself writing "and also," that's a sign you need a
  new section.
- **Every lesson must include:**
  1. A short concept explanation in plain words.
  2. At least one working code example.
  3. A "Why this matters" note — one or two sentences on why a beginner
     should care.
  4. A "Try it yourself" exercise with a hidden/expandable solution.
  5. A "Before moving on, make sure you can..." checklist at the end.
- **Tone:** encouraging and conversational, like explaining something to a
  friend over coffee. It's fine to say "don't worry if this feels
  confusing at first" or "you'll use this a lot, so it's worth slowing
  down here."
- **Diagrams:** use a Mermaid diagram or simple ASCII art whenever showing
  a flow (a request traveling through the app, the MVC pattern, etc.)
  would make something click faster than a paragraph would.

## 2. Folder & file naming conventions

- Lesson content lives in `docs/lessons/`, one Markdown file per lesson,
  named `NN-short-slug.md` (two-digit, zero-padded number, then a
  lowercase hyphenated slug). Example: `05-rest-api.md`.
- Runnable code for a lesson lives in `code-examples/lesson-NN-slug/`,
  using the **same number and slug** as the lesson it belongs to, so it's
  obvious which lesson a folder supports.
- Every code example is a complete, runnable Maven project (it must build
  with `mvn spring-boot:run` with no extra setup beyond what the lesson
  describes).
- Images/diagrams that can't be plain Mermaid live in `docs/assets/`.

## 3. How lessons are numbered and structured

- Lessons are numbered in the order a beginner should read them (see the
  table of contents in `docs/index.md` and the `nav:` list in
  `mkdocs.yml` — both must always match).
- Each lesson should take a beginner **15–30 minutes** to finish,
  including the exercise. If a topic needs longer than that, split it
  into two lessons rather than writing one long one.
- Every lesson file follows this internal structure:

  ```markdown
  # Lesson NN: Title

  *Estimated time: NN minutes*

  ## What you'll learn
  ## The concept (plain words)
  ## Code example
  ## Why this matters
  ## Try it yourself
  ??? "Show solution"
  ## Checklist: before moving on
  ## What's next
  ```

- Inserting a new lesson in the middle of the sequence means renumbering
  every following lesson's file, folder, and nav entry — do this in the
  same branch/PR so nothing points to a stale number.

## 4. Review checklist before merging a branch

Before opening a PR (or before merging, if you're reviewing someone
else's), confirm:

- [ ] The change lives on its own branch, named per `CONTRIBUTING.md`.
- [ ] No jargon appears without an inline plain-word explanation.
- [ ] The lesson includes all five required parts (see section 1).
- [ ] Any code example actually builds and runs (`mvn spring-boot:run` or
      `mvn test`, whichever applies) — don't paste code you haven't run.
- [ ] `mkdocs.yml`'s `nav:` and `docs/index.md`'s table of contents are
      updated if a lesson was added, renamed, or renumbered.
- [ ] Links between lessons ("In the last lesson, we...") still point to
      the right file after any renumbering.
- [ ] The commit message and PR description clearly explain *what
      changed* and *why*.
