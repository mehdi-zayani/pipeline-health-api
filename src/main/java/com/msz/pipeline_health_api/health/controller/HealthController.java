package com.msz.pipeline_health_api.health.controller;

import com.msz.pipeline_health_api.health.dto.HealthResponse;
import com.msz.pipeline_health_api.health.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Health", description = "System health monitoring endpoints")
@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService service;
    private static final Logger log = LoggerFactory.getLogger(HealthController.class);

    public HealthController(HealthService service) {
        this.service = service;
    }

    @Operation(
            summary = "Get application health status",
            description = "Returns global system health including GitLab pipeline status and success rate"
    )
    @ApiResponse(responseCode = "200", description = "Health status retrieved successfully")
    @GetMapping
    public HealthResponse health() {
        log.info("GET /api/health called from controller");
        return service.getHealth();
    }
}