package com.msz.pipeline_health_api.gitlab.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msz.pipeline_health_api.gitlab.client.GitLabClient;
import com.msz.pipeline_health_api.gitlab.dto.GitLabPipelineDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;

@Service
public class GitLabService {


    private static final Logger log = LoggerFactory.getLogger(GitLabService.class);

    private final GitLabClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Value("${gitlab.project-id}")
    private Long projectId;

    @Value("${gitlab.retry.max-attempts}")
    private int maxAttempts;

    @Value("${gitlab.retry.delay}")
    private long retryDelay;

    public GitLabService(GitLabClient client) {
        this.client = client;
    }

    // =========================
    // GET LATEST PIPELINE
    // =========================
    public GitLabPipelineDTO getLatestPipeline() {

        log.info("Fetching latest GitLab pipeline (projectId={})", projectId);

        try {
            String response = callGitLabWithRetry();

            JsonNode root = objectMapper.readTree(response);

            if (root.isEmpty()) {
                log.warn("No pipeline found for projectId={}", projectId);
                throw new RuntimeException("No pipeline found");
            }

            JsonNode pipeline = root.get(0);

            GitLabPipelineDTO dto = new GitLabPipelineDTO(
                    pipeline.get("id").asLong(),
                    pipeline.get("status").asText(),
                    pipeline.get("ref").asText(),
                    Instant.parse(pipeline.get("created_at").asText()),
                    pipeline.get("web_url").asText()
            );

            log.info("Pipeline fetched successfully: status={}, ref={}",
                    dto.getStatus(), dto.getRef());

            return dto;

        } catch (Exception e) {
            log.error("Error while fetching GitLab pipeline", e);
            throw new RuntimeException("Error while fetching GitLab pipeline", e);
        }
    }

    // =========================
    // SUCCESS RATE
    // =========================
    public double getSuccessRate() {

        log.info("Computing pipeline success rate (projectId={})", projectId);

        try {
            String response = callGitLabWithRetry();

            JsonNode root = objectMapper.readTree(response);

            if (root.isEmpty()) {
                log.warn("No pipelines found for success rate calculation");
                return 0.0;
            }

            int total = root.size();
            int successCount = 0;

            for (JsonNode pipeline : root) {
                String status = pipeline.get("status").asText();

                if ("success".equalsIgnoreCase(status)) {
                    successCount++;
                }
            }

            double rate = (successCount * 100.0) / total;

            log.info("Success rate calculated: {}%", rate);

            return rate;

        } catch (Exception e) {
            log.error("Error computing success rate", e);
            throw new RuntimeException("Error computing success rate", e);
        }
    }

    // =========================
    // RETRY LOGIC (clean)
    // =========================
    private String callGitLabWithRetry() {

        int attempt = 0;

        while (attempt < maxAttempts) {
            try {
                log.debug("GitLab API call attempt {}/{}", attempt + 1, maxAttempts);

                return client.getLatestPipeline(gitlabUrl, projectId);

            } catch (Exception e) {

                attempt++;
                log.warn("GitLab API failed attempt {}/{}", attempt, maxAttempts);

                if (attempt >= maxAttempts) {
                    log.error("GitLab API failed after {} attempts", maxAttempts, e);
                    throw new RuntimeException("GitLab API failed after retries", e);
                }

                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        throw new RuntimeException("Unexpected retry failure");
    }
}