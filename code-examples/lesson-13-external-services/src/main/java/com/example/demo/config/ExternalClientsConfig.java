package com.example.demo.config;

import com.example.demo.external.HttpExchangeExternalTodoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class ExternalClientsConfig {

    @Bean
    public RestTemplate externalTodoRestTemplate(
            RestTemplateBuilder builder,
            @Value("${external.todos.base-url}") String baseUrl,
            @Value("${external.todos.connect-timeout-ms}") long connectTimeoutMs,
            @Value("${external.todos.read-timeout-ms}") long readTimeoutMs) {
        return builder
                .rootUri(baseUrl)
                .setConnectTimeout(Duration.ofMillis(connectTimeoutMs))
                .setReadTimeout(Duration.ofMillis(readTimeoutMs))
                .build();
    }

    @Bean
    public RestClient externalTodoRestClient(
            @Value("${external.todos.base-url}") String baseUrl,
            @Value("${external.todos.connect-timeout-ms}") long connectTimeoutMs,
            @Value("${external.todos.read-timeout-ms}") long readTimeoutMs) {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofMillis(connectTimeoutMs))
                .withReadTimeout(Duration.ofMillis(readTimeoutMs));
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .build();
    }

    @Bean
    public HttpExchangeExternalTodoClient httpExchangeExternalTodoClient(RestClient externalTodoRestClient) {
        RestClientAdapter adapter = RestClientAdapter.create(externalTodoRestClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(HttpExchangeExternalTodoClient.class);
    }

}
