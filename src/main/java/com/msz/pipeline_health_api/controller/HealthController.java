package com.msz.pipeline_health_api.controller;

import com.msz.pipeline_health_api.dto.HealthResponse;
import com.msz.pipeline_health_api.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/api/health")
    public HealthResponse health() {
        return healthService.getHealth();
    }
}