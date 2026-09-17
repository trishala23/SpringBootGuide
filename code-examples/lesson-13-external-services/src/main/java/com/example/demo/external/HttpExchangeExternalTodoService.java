package com.example.demo.external;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@Component
public class HttpExchangeExternalTodoService {

    private final HttpExchangeExternalTodoClient client;

    public HttpExchangeExternalTodoService(HttpExchangeExternalTodoClient client) {
        this.client = client;
    }

    public ExternalTodo getTodo(long id) {
        try {
            return client.getTodo(id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ExternalTodoNotFoundException(id);
        } catch (RestClientException e) {
            throw new ExternalTodoServiceException("Declarative HTTP interface call to external todo service failed", e);
        }
    }

}
