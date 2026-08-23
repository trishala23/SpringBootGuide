# Lesson 9 code example: error handling basics

Companion project for
[docs/lessons/09-error-handling.md](../../docs/lessons/09-error-handling.md).

## Run it

```bash
mvn spring-boot:run
```

## Try it

```bash
# 404 with a JSON error body
curl http://localhost:8080/tasks/999

# 400 with a validation error
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"","done":false}'

# 201, valid request
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Write tests","done":false}'
```

Stop the app with `Ctrl+C`.
