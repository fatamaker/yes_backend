package com.yesmine.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@ComponentScan(basePackages = "com.yesmine")
@EnableJpaRepositories(basePackages = "com.yesmine.repository")
@EntityScan(basePackages = "com.yesmine.model")
@SpringBootApplication
public class YesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(YesBackendApplication.class, args);
	}

}
