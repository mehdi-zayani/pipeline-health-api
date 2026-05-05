package com.msz.pipeline_health_api.health.service;

import com.msz.pipeline_health_api.gitlab.dto.GitLabPipelineDTO;
import com.msz.pipeline_health_api.gitlab.service.GitLabService;
import com.msz.pipeline_health_api.health.dto.HealthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Service responsible for computing and caching application health status.
 * Health data is periodically refreshed using a scheduler.
 */
@Service
public class HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

    private final GitLabService gitLabService;
    private volatile HealthResponse cachedHealth;

    public HealthService(GitLabService gitLabService) {
        this.gitLabService = gitLabService;
    }

    // =========================
    // PUBLIC API (cache access)
    // =========================

    /**
     * Returns the current health status from cache.
     * Triggers a refresh if cache is not yet initialized.
     *
     * @return current HealthResponse
     */
    public HealthResponse getHealth() {

        log.info("Health endpoint called");

        if (cachedHealth == null) {
            log.warn("Cache empty, triggering manual refresh");
            refreshHealth();
        }

        return cachedHealth;
    }

    // =========================
    // SCHEDULER
    // =========================

    /**
     * Periodically refreshes health data by calling GitLab API.
     * Updates cached health response.
     */
    @Scheduled(fixedRateString = "${health.refresh.rate}")
    public void refreshHealth() {

        log.info("Refreshing health cache...");

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

            log.info("Health cache updated: status={}, successRate={}", status, successRate);

        } catch (Exception e) {

            log.error("Error while refreshing health cache, using fallback", e);

            cachedHealth = new HealthResponse(
                    "DOWN",
                    Instant.now(),
                    "pipeline-health-api",
                    "1.0.0",
                    new HealthResponse.GitLabInfo(
                            "UNKNOWN",
                            "N/A",
                            "N/A"
                    ),
                    false,
                    0.0
            );
        }
    }
}