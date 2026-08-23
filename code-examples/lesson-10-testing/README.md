# Lesson 10 code example: testing your app

Companion project for
[docs/lessons/10-testing.md](../../docs/lessons/10-testing.md).

## Run the tests

```bash
mvn test
```

You should see 4 test classes, 6 tests total, all passing:

- `TaskNotFoundExceptionTest` — a plain unit test.
- `TaskRepositoryTest` — a `@DataJpaTest` slice test.
- `TaskControllerTest` — a `@WebMvcTest` slice test (3 tests).
- `DemoApplicationTests` — the context-load sanity check.

## Run the app

```bash
mvn spring-boot:run
```
