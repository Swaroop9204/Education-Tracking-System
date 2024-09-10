package com.project.ets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@SpringBootApplication
@EnableAsync
@EnableCaching
public class EducationTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducationTrackingSystemApplication.class, args);
	}

}
