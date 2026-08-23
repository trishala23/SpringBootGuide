# Lesson 8 code example: configuration basics

Companion project for
[docs/lessons/08-configuration.md](../../docs/lessons/08-configuration.md).

## Run it

```bash
mvn spring-boot:run
```

Try the default profile:

```bash
curl http://localhost:8080/greeting
curl http://localhost:8080/greeting/config
```

Try the `dev` profile (overrides `app.greeting`):

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```

Stop the app with `Ctrl+C`.
