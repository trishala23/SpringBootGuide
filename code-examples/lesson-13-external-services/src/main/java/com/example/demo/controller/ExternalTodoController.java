package com.example.demo.controller;

import com.example.demo.external.ExternalTodo;
import com.example.demo.external.FeignExternalTodoService;
import com.example.demo.external.HttpExchangeExternalTodoService;
import com.example.demo.external.RestClientExternalTodoClient;
import com.example.demo.external.RestTemplateExternalTodoClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external-todos")
public class ExternalTodoController {

    private final RestTemplateExternalTodoClient restTemplateClient;
    private final RestClientExternalTodoClient restClientClient;
    private final HttpExchangeExternalTodoService httpExchangeService;
    private final FeignExternalTodoService feignService;

    public ExternalTodoController(
            RestTemplateExternalTodoClient restTemplateClient,
            RestClientExternalTodoClient restClientClient,
            HttpExchangeExternalTodoService httpExchangeService,
            FeignExternalTodoService feignService) {
        this.restTemplateClient = restTemplateClient;
        this.restClientClient = restClientClient;
        this.httpExchangeService = httpExchangeService;
        this.feignService = feignService;
    }

    @GetMapping("/rest-template/{id}")
    public ExternalTodo getViaRestTemplate(@PathVariable long id) {
        return restTemplateClient.getTodo(id);
    }

    @GetMapping("/rest-client/{id}")
    public ExternalTodo getViaRestClient(@PathVariable long id) {
        return restClientClient.getTodo(id);
    }

    @GetMapping("/http-interface/{id}")
    public ExternalTodo getViaHttpInterface(@PathVariable long id) {
        return httpExchangeService.getTodo(id);
    }

    @GetMapping("/feign/{id}")
    public ExternalTodo getViaFeign(@PathVariable long id) {
        return feignService.getTodo(id);
    }

}
