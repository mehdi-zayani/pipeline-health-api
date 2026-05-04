package com.msz.pipeline_health_api.health.dto;

import java.time.Instant;

public class HealthResponse {

    private String status;
    private Instant timestamp;
    private String service;
    private String version;
    private GitLabInfo gitlab;
    private boolean isHealthy;
    private double pipelineSuccessRate;
    public HealthResponse(String status, Instant timestamp, String service,
                          String version, GitLabInfo gitlab,
                          boolean isHealthy, double pipelineSuccessRate) {
        this.status = status;
        this.timestamp = timestamp;
        this.service = service;
        this.version = version;
        this.gitlab = gitlab;
        this.isHealthy = isHealthy;
        this.pipelineSuccessRate = pipelineSuccessRate;
    }

    public String getStatus() { return status; }

    public Instant getTimestamp() { return timestamp; }

    public String getService() { return service; }

    public String getVersion() { return version; }

    public GitLabInfo getGitlab() { return gitlab; }

    public boolean isHealthy() { return isHealthy; }

    public double getPipelineSuccessRate() {
        return pipelineSuccessRate;
    }
    // nested DTO
    public static class GitLabInfo {
        private String status;
        private String ref;
        private String url;

        public GitLabInfo(String status, String ref, String url) {
            this.status = status;
            this.ref = ref;
            this.url = url;
        }

        public String getStatus() { return status; }
        public String getRef() { return ref; }
        public String getUrl() { return url; }

    }
}