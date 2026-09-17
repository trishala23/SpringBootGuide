package com.example.demo.external;

import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class FeignExternalTodoService {

    private final FeignExternalTodoClient client;

    public FeignExternalTodoService(FeignExternalTodoClient client) {
        this.client = client;
    }

    public ExternalTodo getTodo(long id) {
        try {
            return client.getTodo(id);
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new ExternalTodoNotFoundException(id);
            }
            throw new ExternalTodoServiceException("Feign call to external todo service failed", e);
        }
    }

}
