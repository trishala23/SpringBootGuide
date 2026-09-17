package com.example.demo.external;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
