package com.msz.pipeline_health_api.gitlab.dto;

import java.time.Instant;

public class GitLabPipelineDTO {

    private Long id;
    private String status;
    private String ref;
    private Instant createdAt;
    private String webUrl;

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