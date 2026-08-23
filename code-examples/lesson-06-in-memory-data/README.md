# Lesson 6 code example: working with data (in-memory)

Companion project for
[docs/lessons/06-in-memory-data.md](../../docs/lessons/06-in-memory-data.md).

## Run it

```bash
mvn spring-boot:run
```

## Try the endpoints

```bash
curl http://localhost:8080/tasks
curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d '{"title":"Write tests","done":false}'
curl -X DELETE http://localhost:8080/tasks/1
```

Stop the app with `Ctrl+C`.
