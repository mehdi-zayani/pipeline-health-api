package com.msz.pipeline_health_api.service;

import com.msz.pipeline_health_api.dto.HealthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HealthService {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${app.version}")
    private String version;

    public HealthResponse getHealth() {
        return new HealthResponse(
                "UP",
                Instant.now(),
                serviceName,
                version
        );
    }
}