package com.msz.pipeline_health_api.health.service;

import com.msz.pipeline_health_api.gitlab.dto.GitLabPipelineDTO;
import com.msz.pipeline_health_api.gitlab.service.GitLabService;
import com.msz.pipeline_health_api.health.dto.HealthResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HealthService {

    private final GitLabService gitLabService;
    private volatile HealthResponse cachedHealth;

    public HealthService(GitLabService gitLabService) {
        this.gitLabService = gitLabService;
    }

    // API call → retourne le cache uniquement
    public HealthResponse getHealth() {

        // init lazy si jamais scheduler pas encore passé
        if (cachedHealth == null) {
            refreshHealth();
        }

        return cachedHealth;
    }

    // Scheduler → seul endroit où on appelle GitLab
    @Scheduled(fixedRate = 30000) // 30 secondes
    public void refreshHealth() {

        try {
            GitLabPipelineDTO pipeline = gitLabService.getLatestPipeline();
            double successRate = gitLabService.getSuccessRate();

            boolean isHealthy = "success".equalsIgnoreCase(pipeline.getStatus());

            HealthResponse.GitLabInfo gitlabInfo =
                    new HealthResponse.GitLabInfo(
                            pipeline.getStatus(),
                            pipeline.getRef(),
                            pipeline.getWebUrl()
                    );

            String status = isHealthy ? "UP" : "DOWN";

            cachedHealth = new HealthResponse(
                    status,
                    Instant.now(),
                    "pipeline-health-api",
                    "1.0.0",
                    gitlabInfo,
                    isHealthy,
                    successRate
            );

        } catch (Exception e) {
            // fallback si GitLab down
            cachedHealth = new HealthResponse(
                    "DOWN",
                    Instant.now(),
                    "pipeline-health-api",
                    "1.0.0",
                    null,
                    false,
                    0.0
            );
        }
    }
}