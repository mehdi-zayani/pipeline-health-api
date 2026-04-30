package com.msz.pipeline_health_api.gitlab.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GitLabClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getLatestPipeline(String baseUrl, Long projectId) {

        String url = baseUrl + "/projects/" + projectId + "/pipelines?per_page=1";

        return restTemplate.getForObject(url, String.class);
    }
}