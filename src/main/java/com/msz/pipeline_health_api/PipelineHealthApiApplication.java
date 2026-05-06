package com.msz.pipeline_health_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point of the Pipeline Health API application.
 * Enables Spring Boot auto-configuration and scheduling support.
 */
@SpringBootApplication
@EnableScheduling
public class PipelineHealthApiApplication {

	/**
	 * Application bootstrap method.
	 *
	 * @param args runtime arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(PipelineHealthApiApplication.class, args);
	}
}