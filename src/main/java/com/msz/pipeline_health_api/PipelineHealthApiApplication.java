package com.msz.pipeline_health_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PipelineHealthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PipelineHealthApiApplication.class, args);
	}

}
