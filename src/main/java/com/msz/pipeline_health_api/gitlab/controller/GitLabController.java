package com.msz.pipeline_health_api.gitlab.controller;

import com.msz.pipeline_health_api.gitlab.dto.GitLabPipelineDTO;
import com.msz.pipeline_health_api.gitlab.service.GitLabService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "GitLab", description = "GitLab pipeline monitoring endpoints")
@RestController
@RequestMapping("/api/gitlab")
public class GitLabController {

    private final GitLabService service;
    private static final Logger log = LoggerFactory.getLogger(GitLabController.class);

    public GitLabController(GitLabService service) {
        this.service = service;
    }

    @Operation(
            summary = "Get latest GitLab pipeline",
            description = "Fetches the most recent pipeline information from GitLab"
    )
    @ApiResponse(responseCode = "200", description = "Pipeline retrieved successfully")
    @GetMapping("/pipeline/latest")
    public GitLabPipelineDTO latestPipeline() {

        log.info("GET /api/gitlab/pipeline/latest called");

        GitLabPipelineDTO response = service.getLatestPipeline();

        log.info("GitLab pipeline endpoint successfully returned status={}", response.getStatus());

        return response;
    }
}