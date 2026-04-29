package com.msz.pipeline_health_api.dto;

public class HealthResponse {

    private String status;

    public HealthResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}