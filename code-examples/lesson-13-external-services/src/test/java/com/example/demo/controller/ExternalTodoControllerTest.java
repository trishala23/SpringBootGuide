package com.example.demo.controller;

import com.example.demo.external.ExternalTodo;
import com.example.demo.external.ExternalTodoNotFoundException;
import com.example.demo.external.FeignExternalTodoService;
import com.example.demo.external.HttpExchangeExternalTodoService;
import com.example.demo.external.RestClientExternalTodoClient;
import com.example.demo.external.RestTemplateExternalTodoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExternalTodoController.class)
class ExternalTodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplateExternalTodoClient restTemplateClient;

    @MockBean
    private RestClientExternalTodoClient restClientClient;

    @MockBean
    private HttpExchangeExternalTodoService httpExchangeService;

    @MockBean
    private FeignExternalTodoService feignService;

    @Test
    void getViaRestTemplate_returnsTodo_whenItExists() throws Exception {
        when(restTemplateClient.getTodo(1)).thenReturn(new ExternalTodo(1, 1, "Write tests", false));

        mockMvc.perform(get("/external-todos/rest-template/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Write tests"));
    }

    @Test
    void getViaFeign_returns404_whenTodoDoesNotExist() throws Exception {
        when(feignService.getTodo(999)).thenThrow(new ExternalTodoNotFoundException(999));

        mockMvc.perform(get("/external-todos/feign/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

}
