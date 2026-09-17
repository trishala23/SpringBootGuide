# Lesson 13: Connecting to external services

*Estimated time: 40 minutes*

## What you'll learn

- What it means for your app to call *another* service over HTTP, and why
  that's different from calling your own database.
- Four ways Spring Boot lets you make that call: `RestTemplate` (the
  older, still very common way), the modern `RestClient`, a declarative
  HTTP interface (Spring's own built-in equivalent of Feign), and Feign
  itself.
- Why an external call always needs an explicit timeout.
- How to turn a failure from someone else's service into a clean,
  deliberate response from your own API, instead of leaking it.
- How to test code that calls an external service without actually
  calling it every time your test suite runs.

## The concept (plain words)

Every lesson so far has your task API talking to one thing: its own
database. An **external service** is anything else your app talks to over
HTTP that it doesn't own or control — a payments provider, a
notifications service, or, in this lesson, a public practice API. Calling
one means the same four steps every time: build a request (a URL, maybe
some data), send it, read back a response, and decide what to do if
something goes wrong. Spring Boot gives you more than one tool for that
last part, and interviewers (see [Lesson 14](14-interview-questions.md#connecting-to-external-services))
expect you to know the tradeoffs, not just one of them.

This lesson calls [JSONPlaceholder](https://jsonplaceholder.typicode.com),
a free, stable practice API that a lot of tutorials use. Its `/todos/{id}`
endpoint returns exactly the shape you'd expect from this tutorial's own
`Task` — a `title` and a `completed` flag — which makes it a natural
stand-in for "some other team's task service."

## Code example

!!! tip
    The full working project is at
    [`code-examples/lesson-13-external-services`](https://github.com/trishala23/SpringBootGuide/tree/main/code-examples/lesson-13-external-services).

First, a plain record for what comes back:

```java
package com.example.demo.external;

public record ExternalTodo(long id, long userId, String title, boolean completed) {
}
```

### Way 1: `RestTemplate`

The original Spring client. Still extremely common in existing
codebases, even though it's in maintenance mode (it works fine, but gets
no new features):

```java
@Component
public class RestTemplateExternalTodoClient {

    private final RestTemplate restTemplate;

    public RestTemplateExternalTodoClient(@Qualifier("externalTodoRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExternalTodo getTodo(long id) {
        try {
            return restTemplate.getForObject("/todos/{id}", ExternalTodo.class, id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ExternalTodoNotFoundException(id);
        } catch (RestClientException e) {
            throw new ExternalTodoServiceException("RestTemplate call to external todo service failed", e);
        }
    }

}
```

### Way 2: `RestClient`

Spring's modern, synchronous client (added in Spring 6.1) — the
recommended default for a blocking app like this one going forward, with
a more fluent API than `RestTemplate`:

```java
@Component
public class RestClientExternalTodoClient {

    private final RestClient restClient;

    public RestClientExternalTodoClient(@Qualifier("externalTodoRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public ExternalTodo getTodo(long id) {
        try {
            return restClient.get()
                    .uri("/todos/{id}", id)
                    .retrieve()
                    .body(ExternalTodo.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ExternalTodoNotFoundException(id);
        } catch (RestClientException e) {
            throw new ExternalTodoServiceException("RestClient call to external todo service failed", e);
        }
    }

}
```

### Way 3: a declarative HTTP interface

Instead of writing the call by hand, declare an interface describing it,
and Spring generates a working implementation for you — the same idea as
the [Spring Data JPA repositories](07-database.md) from Lesson 7, applied
to HTTP calls instead of database queries:

```java
public interface HttpExchangeExternalTodoClient {

    @GetExchange("/todos/{id}")
    ExternalTodo getTodo(@PathVariable long id);

}
```

A small config bean turns that interface into a real, callable client,
backed by the same `RestClient` from Way 2:

```java
@Bean
public HttpExchangeExternalTodoClient httpExchangeExternalTodoClient(RestClient externalTodoRestClient) {
    RestClientAdapter adapter = RestClientAdapter.create(externalTodoRestClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
    return factory.createClient(HttpExchangeExternalTodoClient.class);
}
```

### Way 4: Feign

If you've worked in a Spring Cloud / microservices codebase, this is the
one you've probably seen most. It's the same declarative idea as Way 3,
using annotations from the `spring-cloud-starter-openfeign` dependency
instead of Spring's built-in ones:

```java
@FeignClient(name = "externalTodos", url = "${external.todos.base-url}")
public interface FeignExternalTodoClient {

    @GetMapping("/todos/{id}")
    ExternalTodo getTodo(@PathVariable("id") long id);

}
```

Feign needs `@EnableFeignClients` on your `@SpringBootApplication` class,
and it pulls in Spring Cloud as a dependency — worth it if you're already
in a Spring Cloud microservices codebase, more than this single tutorial
project strictly needs otherwise.

### Configuring timeouts

Every one of the four ways above is built from a `RestTemplate` or
`RestClient` that's configured **once**, with an explicit connect and
read timeout — never left at the default, which for most HTTP clients is
no timeout at all:

```java
@Bean
public RestTemplate externalTodoRestTemplate(
        RestTemplateBuilder builder,
        @Value("${external.todos.base-url}") String baseUrl,
        @Value("${external.todos.connect-timeout-ms}") long connectTimeoutMs,
        @Value("${external.todos.read-timeout-ms}") long readTimeoutMs) {
    return builder
            .rootUri(baseUrl)
            .setConnectTimeout(Duration.ofMillis(connectTimeoutMs))
            .setReadTimeout(Duration.ofMillis(readTimeoutMs))
            .build();
}
```

The base URL and both timeouts come from `application.properties`, the
same externalized-configuration approach from [Lesson 8](08-configuration.md):

```properties
external.todos.base-url=https://jsonplaceholder.typicode.com
external.todos.connect-timeout-ms=3000
external.todos.read-timeout-ms=5000
```

### Not leaking the failure

The controller doesn't touch any of this directly — every endpoint just
calls its client and lets a `@RestControllerAdvice` translate the two
exceptions above, the same pattern [Lesson 9](09-error-handling.md) built
for the task API itself:

```java
@RestControllerAdvice
public class ExternalServiceExceptionHandler {

    @ExceptionHandler(ExternalTodoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ExternalTodoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(ExternalTodoServiceException.class)
    public ResponseEntity<Map<String, String>> handleServiceFailure(ExternalTodoServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "The external todo service is currently unavailable"));
    }

}
```

Run the app (`mvn spring-boot:run`) and try all four:

```bash
curl http://localhost:8080/external-todos/rest-template/1
curl http://localhost:8080/external-todos/rest-client/1
curl http://localhost:8080/external-todos/http-interface/1
curl http://localhost:8080/external-todos/feign/1
```

All four return the same JSON shape — which client you used is an
implementation detail your caller never needs to know.

### Testing it without a real network call

Spring's `MockRestServiceServer` lets you test `RestTemplate` and
`RestClient` code exactly like [Lesson 10](10-testing.md) tested the
database layer — no real network call, no flakiness:

```java
@Test
void getTodo_throwsNotFound_whenExternalServiceReturns404() {
    RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost").build();
    MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    mockServer.expect(requestTo("http://localhost/todos/999"))
            .andRespond(withStatus(HttpStatus.NOT_FOUND));

    RestTemplateExternalTodoClient client = new RestTemplateExternalTodoClient(restTemplate);

    assertThatThrownBy(() -> client.getTodo(999))
            .isInstanceOf(ExternalTodoNotFoundException.class);
}
```

Notice this test never calls `new RestTemplateExternalTodoClient(...)`
through Spring at all — because the client uses constructor injection
(the same reason from [Lesson 14's intermediate section](14-interview-questions.md#why-is-constructor-injection-preferred-over-field-injection)),
it's just a plain Java object in this test, with a `RestTemplate` that's
been told exactly what to respond with instead of calling the internet.

## Why this matters

An external service is a dependency your app doesn't control — it can be
slow, it can be down, and it can change its mind about what it returns.
Every real production incident that starts with "the payments provider
was having issues" is really a story about whether the *calling* app
handled that gracefully: did it hang forever (no timeout), did it show
the caller a confusing foreign error (no translation), and could anyone
have caught the bug before it shipped (no test)? This lesson's pattern —
timeout, translate, test — is the difference between one flaky dependency
being a minor blip and it taking your whole app down with it.

## Try it yourself

Add a test to `RestClientExternalTodoClientTest` proving that when the
external service responds with a `500` (not a `404`),
`RestClientExternalTodoClient` throws `ExternalTodoServiceException` —
not `ExternalTodoNotFoundException`.

??? note "Show solution"
    ```java
    @Test
    void getTodo_throwsServiceException_whenExternalServiceReturns500() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost");
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(builder).build();
        mockServer.expect(requestTo("http://localhost/todos/1"))
                .andRespond(withServerError());

        RestClientExternalTodoClient client = new RestClientExternalTodoClient(builder.build());

        assertThatThrownBy(() -> client.getTodo(1))
                .isInstanceOf(ExternalTodoServiceException.class);
    }
    ```

    You'll need one more static import:
    `org.springframework.test.web.client.response.MockRestResponseCreators.withServerError`.

    This matters because a `404` and a `500` mean two different things —
    "this doesn't exist" is a `404` you can show to a caller as-is, but
    "the dependency is broken" is a `502` your own API should report
    without ever mentioning JSONPlaceholder by name. Mixing the two into
    one generic exception would erase that distinction.

## Checklist: before moving on

Before moving on, make sure you can...

- [ ] Explain the difference between `RestTemplate`, `RestClient`, a
      declarative HTTP interface, and Feign — and which one you'd reach
      for in a new, plain Spring MVC app.
- [ ] Explain why every one of this lesson's clients is built with an
      explicit connect and read timeout.
- [ ] Explain why the controller never sees `HttpClientErrorException` or
      `FeignException` directly.
- [ ] Write a test for an external call using `MockRestServiceServer`,
      without needing a real network connection.

## What's next

You've now called someone else's API the way you'd expect any production
Spring Boot app to. From here,
[Lesson 14's "Connecting to external services"](14-interview-questions.md#connecting-to-external-services)
section covers the same material from an interview angle — retries,
circuit breakers, and testing with WireMock — worth a read now that
you've built the real thing.
