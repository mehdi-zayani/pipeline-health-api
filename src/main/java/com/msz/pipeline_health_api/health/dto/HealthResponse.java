package com.msz.pipeline_health_api.health.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Global health response returned by the API.
 * Contains application status and GitLab pipeline information.
 */
@Schema(description = "Global health response")
public class HealthResponse {

    @Schema(description = "Overall application status", example = "UP")
    private final String status;

    @Schema(description = "Timestamp of the health check")
    private final Instant timestamp;

    @Schema(description = "Service name", example = "pipeline-health-api")
    private final String service;

    @Schema(description = "Application version", example = "1.0.0")
    private final String version;

    @Schema(description = "GitLab pipeline information")
    private final GitLabInfo gitlab;

    @JsonProperty("healthy")
    @Schema(description = "Indicates if system is healthy", example = "true")
    private final boolean isHealthy;

    @Schema(description = "Pipeline success rate percentage", example = "100.0")
    private final double pipelineSuccessRate;

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

    public String getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getService() {
        return service;
    }

    public String getVersion() {
        return version;
    }

    public GitLabInfo getGitlab() {
        return gitlab;
    }

    public boolean isHealthy() {
        return isHealthy;
    }

    public double getPipelineSuccessRate() {
        return pipelineSuccessRate;
    }

    // ===============================
    // NESTED DTO
    // ===============================

    /**
     * GitLab pipeline information embedded in health response.
     */
    @Schema(description = "GitLab pipeline details")
    public static class GitLabInfo {

        @Schema(description = "Pipeline status", example = "success")
        private final String status;

        @Schema(description = "Git branch name", example = "main")
        private final String ref;

        @Schema(description = "Pipeline URL", example = "https://gitlab.com/...")
        private final String url;

        public GitLabInfo(String status, String ref, String url) {
            this.status = status;
            this.ref = ref;
            this.url = url;
        }

        public String getStatus() {
            return status;
        }

        public String getRef() {
            return ref;
        }

        public String getUrl() {
            return url;
        }
    }
}