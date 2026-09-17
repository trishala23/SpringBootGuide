package com.example.demo.external;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

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
