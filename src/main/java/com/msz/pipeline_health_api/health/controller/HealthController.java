package com.msz.pipeline_health_api.health.controller;

import com.msz.pipeline_health_api.health.dto.HealthResponse;
import com.msz.pipeline_health_api.health.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HealthController {

    private final HealthService service;
    private static final Logger log = LoggerFactory.getLogger(HealthController.class);
    public HealthController(HealthService service) {
        this.service = service;
    }

    @GetMapping("/api/health")
    public HealthResponse health() {
        log.info("GET /api/health called from controller");
        return service.getHealth();
    }
}