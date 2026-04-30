package com.msz.pipeline_health_api.health.service;

import com.msz.pipeline_health_api.gitlab.dto.GitLabPipelineDTO;
import com.msz.pipeline_health_api.gitlab.service.GitLabService;
import com.msz.pipeline_health_api.health.dto.HealthResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HealthService {

    private final GitLabService gitLabService;

    public HealthService(GitLabService gitLabService) {
        this.gitLabService = gitLabService;
    }

    public HealthResponse getHealth() {

        GitLabPipelineDTO pipeline = gitLabService.getLatestPipeline();

        boolean isHealthy = "success".equalsIgnoreCase(pipeline.getStatus());

        HealthResponse.GitLabInfo gitlabInfo =
                new HealthResponse.GitLabInfo(
                        pipeline.getStatus(),
                        pipeline.getRef(),
                        pipeline.getWebUrl()
                );

        String status = isHealthy ? "UP" : "DOWN";

        return new HealthResponse(
                status,
                Instant.now(),
                "pipeline-health-api",
                "1.0.0",
                gitlabInfo,
                isHealthy
        );
    }
}