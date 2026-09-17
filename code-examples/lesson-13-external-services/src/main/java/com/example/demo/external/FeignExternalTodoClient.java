package com.example.demo.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "externalTodos", url = "${external.todos.base-url}")
public interface FeignExternalTodoClient {

    @GetMapping("/todos/{id}")
    ExternalTodo getTodo(@PathVariable("id") long id);

}
