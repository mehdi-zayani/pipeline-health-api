package com.msz.pipeline_health_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class HealthResponse {
    private final String status;
    private final Instant timestamp;
    private final String service;
    private final String version;
}