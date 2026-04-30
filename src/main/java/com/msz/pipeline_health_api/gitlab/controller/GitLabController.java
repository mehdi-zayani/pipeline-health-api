package com.msz.pipeline_health_api.gitlab.controller;

import com.msz.pipeline_health_api.gitlab.dto.GitLabPipelineDTO;
import com.msz.pipeline_health_api.gitlab.service.GitLabService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitLabController {

    private final GitLabService service;

    public GitLabController(GitLabService service) {
        this.service = service;
    }

    @GetMapping("/api/gitlab/pipeline/latest")
    public GitLabPipelineDTO latestPipeline() {
        return service.getLatestPipeline();
    }
}