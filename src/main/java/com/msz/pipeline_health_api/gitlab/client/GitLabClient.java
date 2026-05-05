package com.msz.pipeline_health_api.gitlab.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Low-level HTTP client responsible for communicating with GitLab API.
 */
@Component
public class GitLabClient {

    private static final Logger log = LoggerFactory.getLogger(GitLabClient.class);

    private final RestTemplate restTemplate;

    public GitLabClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches the latest pipeline from GitLab API.
     *
     * @param baseUrl GitLab base API URL
     * @param projectId GitLab project identifier
     * @return raw JSON response as String
     */
    public String getLatestPipeline(String baseUrl, Long projectId) {

        String url = baseUrl + "/projects/" + projectId + "/pipelines?per_page=1";

        log.debug("Calling GitLab API: {}", url);

        return restTemplate.getForObject(url, String.class);
    }
}