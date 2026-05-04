package com.msz.pipeline_health_api.health.controller;

import com.msz.pipeline_health_api.health.dto.HealthResponse;
import com.msz.pipeline_health_api.health.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final HealthService service;

    public HealthController(HealthService service) {
        this.service = service;
    }

    @GetMapping("/api/health")
    public HealthResponse health() {
        return service.getHealth();
    }
}