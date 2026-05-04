package com.msz.pipeline_health_api.gitlab.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msz.pipeline_health_api.gitlab.client.GitLabClient;
import com.msz.pipeline_health_api.gitlab.dto.GitLabPipelineDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;

@Service
public class GitLabService {

    private final GitLabClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Value("${gitlab.project-id}")
    private Long projectId;

    public GitLabService(GitLabClient client,RestTemplate restTemplate) {
        this.client = client;
        this.restTemplate= restTemplate;
    }

    public GitLabPipelineDTO getLatestPipeline() {

        try {

            String response = client.getLatestPipeline(gitlabUrl, projectId);

            // parse JSON array
            JsonNode root = objectMapper.readTree(response);

            if (root.isEmpty()) {
                throw new RuntimeException("No pipeline found");
            }

            JsonNode pipeline = root.get(0);

            return new GitLabPipelineDTO(
                    pipeline.get("id").asLong(),
                    pipeline.get("status").asText(),
                    pipeline.get("ref").asText(),
                    Instant.parse(pipeline.get("created_at").asText()),
                    pipeline.get("web_url").asText()
            );

        } catch (Exception e) {
            throw new RuntimeException("Error while fetching GitLab pipeline", e);
        }
    }
    public double getSuccessRate() {

        try {
            String response = client.getLatestPipeline(gitlabUrl, projectId);

            JsonNode root = objectMapper.readTree(response);

            if (root.isEmpty()) {
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

            return (successCount * 100.0) / total;

        } catch (Exception e) {
            throw new RuntimeException("Error computing success rate", e);
        }
    }
    private String callGitLabWithRetry(String url) {
        int maxAttempts = 3;
        int attempt = 0;

        while (attempt < maxAttempts) {
            try {
                return restTemplate.getForObject(url, String.class);
            } catch (Exception e) {
                attempt++;

                if (attempt >= maxAttempts) {
                    throw new RuntimeException("GitLab API failed after retries", e);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }

        return null;
    }
}