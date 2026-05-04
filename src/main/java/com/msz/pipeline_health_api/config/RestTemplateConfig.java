package com.msz.pipeline_health_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        // timeout connexion
        factory.setConnectTimeout(3000);

        // timeout  response
        factory.setReadTimeout(3000);

        return new RestTemplate(factory);
    }
}