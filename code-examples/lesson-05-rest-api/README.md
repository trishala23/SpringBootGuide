# Lesson 5 code example: REST API

Companion project for
[docs/lessons/05-rest-api.md](../../docs/lessons/05-rest-api.md).

## Run it

```bash
mvn spring-boot:run
```

## Try the endpoints

```bash
# List all tasks
curl http://localhost:8080/tasks

# Get one task
curl http://localhost:8080/tasks/1

# Create a task
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Write tests","done":false}'
```

Stop the app with `Ctrl+C`.
