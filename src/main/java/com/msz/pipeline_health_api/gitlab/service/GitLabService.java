package com.msz.pipeline_health_api.gitlab.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msz.pipeline_health_api.gitlab.client.GitLabClient;
import com.msz.pipeline_health_api.gitlab.dto.GitLabPipelineDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GitLabService {

    private final GitLabClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Value("${gitlab.project-id}")
    private Long projectId;

    public GitLabService(GitLabClient client) {
        this.client = client;
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
}