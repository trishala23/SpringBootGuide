# Lesson 13 code example: connecting to external services

Companion project for
[docs/lessons/13-connecting-external-services.md](../../docs/lessons/13-connecting-external-services.md).

Calls the public [JSONPlaceholder](https://jsonplaceholder.typicode.com)
practice API four different ways: `RestTemplate`, `RestClient`, a
declarative HTTP interface (`@HttpExchange`), and Feign.

## Run it

```bash
mvn spring-boot:run
```

## Try it

```bash
curl http://localhost:8080/external-todos/rest-template/1
curl http://localhost:8080/external-todos/rest-client/1
curl http://localhost:8080/external-todos/http-interface/1
curl http://localhost:8080/external-todos/feign/1

# 404 with a clean JSON error body
curl http://localhost:8080/external-todos/rest-template/999999
```

Stop the app with `Ctrl+C`.

## Run the tests

```bash
mvn test
```

None of the tests make a real network call — they use Spring's
`MockRestServiceServer` (for the `RestTemplate`/`RestClient` clients) or
plain Mockito (for the declarative and Feign clients) to stand in for
JSONPlaceholder.
