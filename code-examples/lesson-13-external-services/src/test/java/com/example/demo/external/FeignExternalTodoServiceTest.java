package com.example.demo.external;

import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FeignExternalTodoServiceTest {

    private final FeignExternalTodoClient client = mock(FeignExternalTodoClient.class);
    private final FeignExternalTodoService service = new FeignExternalTodoService(client);

    @Test
    void getTodo_returnsTodo_whenClientSucceeds() {
        when(client.getTodo(1)).thenReturn(new ExternalTodo(1, 1, "Write tests", false));

        ExternalTodo todo = service.getTodo(1);

        assertThat(todo.title()).isEqualTo("Write tests");
    }

    @Test
    void getTodo_throwsNotFound_whenClientThrows404() {
        FeignException notFound = mock(FeignException.class);
        when(notFound.status()).thenReturn(404);
        when(client.getTodo(999)).thenThrow(notFound);

        assertThatThrownBy(() -> service.getTodo(999))
                .isInstanceOf(ExternalTodoNotFoundException.class);
    }

}
