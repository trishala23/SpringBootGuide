# Lesson 7 code example: connecting to a real database

Companion project for
[docs/lessons/07-database.md](../../docs/lessons/07-database.md).

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

Browse the actual database at <http://localhost:8080/h2-console> — use
JDBC URL `jdbc:h2:mem:tasksdb`, user `sa`, empty password.

Stop the app with `Ctrl+C`.
