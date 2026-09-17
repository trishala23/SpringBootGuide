package com.example.demo.external;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface HttpExchangeExternalTodoClient {

    @GetExchange("/todos/{id}")
    ExternalTodo getTodo(@PathVariable long id);

}
