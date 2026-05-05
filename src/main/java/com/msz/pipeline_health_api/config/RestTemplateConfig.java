package com.msz.pipeline_health_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for RestTemplate bean.
 * Defines HTTP client settings such as connection and read timeouts.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates a RestTemplate bean with custom timeout configuration.
     *
     * @return configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        // Connection timeout (time to establish connection)
        factory.setConnectTimeout(3000);

        // Read timeout (time waiting for response)
        factory.setReadTimeout(3000);

        return new RestTemplate(factory);
    }
}