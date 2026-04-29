package com.msz.pipeline_health_api.service;

import com.msz.pipeline_health_api.dto.HealthResponse;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public HealthResponse getHealth() {
        return new HealthResponse("UP");
    }
}