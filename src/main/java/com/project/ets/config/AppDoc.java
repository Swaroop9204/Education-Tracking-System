package com.project.ets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class AppDoc {
	
	Info info() {
		return new Info().title("Education-Tracking-System-REStFul API").version("v1").description("a API where student can view rating and add the student to the requirement"
				+ " and trainer can create mocks and enter the rating of each student");
	}
	
	@Bean
    OpenAPI openApi() {
		return new OpenAPI().info(info());
	}

}
