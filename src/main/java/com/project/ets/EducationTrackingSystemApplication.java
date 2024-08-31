package com.project.ets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class EducationTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducationTrackingSystemApplication.class, args);
	}

}
