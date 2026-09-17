package com.example.demo.external;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class RestTemplateExternalTodoClientTest {

    @Test
    void getTodo_returnsTodo_whenExternalServiceRespondsSuccessfully() {
        RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost").build();
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo("http://localhost/todos/1"))
                .andRespond(withSuccess(
                        "{\"id\":1,\"userId\":1,\"title\":\"Write tests\",\"completed\":false}",
                        MediaType.APPLICATION_JSON));

        RestTemplateExternalTodoClient client = new RestTemplateExternalTodoClient(restTemplate);

        ExternalTodo todo = client.getTodo(1);

        assertThat(todo.title()).isEqualTo("Write tests");
        assertThat(todo.completed()).isFalse();
    }

    @Test
    void getTodo_throwsNotFound_whenExternalServiceReturns404() {
        RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost").build();
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo("http://localhost/todos/999"))
                .andRespond(withStatus(NOT_FOUND));

        RestTemplateExternalTodoClient client = new RestTemplateExternalTodoClient(restTemplate);

        assertThatThrownBy(() -> client.getTodo(999))
                .isInstanceOf(ExternalTodoNotFoundException.class);
    }

}
