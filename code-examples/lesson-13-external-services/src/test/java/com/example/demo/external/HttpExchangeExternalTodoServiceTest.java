package com.example.demo.external;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HttpExchangeExternalTodoServiceTest {

    private final HttpExchangeExternalTodoClient client = mock(HttpExchangeExternalTodoClient.class);
    private final HttpExchangeExternalTodoService service = new HttpExchangeExternalTodoService(client);

    @Test
    void getTodo_returnsTodo_whenClientSucceeds() {
        when(client.getTodo(1)).thenReturn(new ExternalTodo(1, 1, "Write tests", false));

        ExternalTodo todo = service.getTodo(1);

        assertThat(todo.title()).isEqualTo("Write tests");
    }

    @Test
    void getTodo_throwsNotFound_whenClientThrows404() {
        when(client.getTodo(999)).thenThrow(HttpClientErrorException.create(
                HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        assertThatThrownBy(() -> service.getTodo(999))
                .isInstanceOf(ExternalTodoNotFoundException.class);
    }

}
