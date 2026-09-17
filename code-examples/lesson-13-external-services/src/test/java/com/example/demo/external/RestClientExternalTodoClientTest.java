package com.example.demo.external;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class RestClientExternalTodoClientTest {

    @Test
    void getTodo_returnsTodo_whenExternalServiceRespondsSuccessfully() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost");
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(builder).build();
        mockServer.expect(requestTo("http://localhost/todos/1"))
                .andRespond(withSuccess(
                        "{\"id\":1,\"userId\":1,\"title\":\"Write tests\",\"completed\":false}",
                        MediaType.APPLICATION_JSON));

        RestClientExternalTodoClient client = new RestClientExternalTodoClient(builder.build());

        ExternalTodo todo = client.getTodo(1);

        assertThat(todo.title()).isEqualTo("Write tests");
        assertThat(todo.completed()).isFalse();
    }

    @Test
    void getTodo_throwsNotFound_whenExternalServiceReturns404() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost");
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(builder).build();
        mockServer.expect(requestTo("http://localhost/todos/999"))
                .andRespond(withStatus(NOT_FOUND));

        RestClientExternalTodoClient client = new RestClientExternalTodoClient(builder.build());

        assertThatThrownBy(() -> client.getTodo(999))
                .isInstanceOf(ExternalTodoNotFoundException.class);
    }

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

}
