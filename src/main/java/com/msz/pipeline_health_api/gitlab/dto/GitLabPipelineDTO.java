package com.msz.pipeline_health_api.gitlab.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object representing a GitLab pipeline.
 */
@Schema(description = "GitLab pipeline details")
public class GitLabPipelineDTO {

    @Schema(description = "Pipeline ID", example = "123456789")
    private final Long id;

    @Schema(description = "Pipeline status", example = "success")
    private final String status;

    @Schema(description = "Git branch reference", example = "main")
    private final String ref;

    @Schema(description = "Pipeline creation timestamp")
    private final Instant createdAt;

    @Schema(description = "GitLab pipeline URL", example = "https://gitlab.com/.../pipelines/123")
    private final String webUrl;

    public GitLabPipelineDTO(Long id, String status, String ref, Instant createdAt, String webUrl) {
        this.id = id;
        this.status = status;
        this.ref = ref;
        this.createdAt = createdAt;
        this.webUrl = webUrl;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getRef() {
        return ref;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getWebUrl() {
        return webUrl;
    }
}