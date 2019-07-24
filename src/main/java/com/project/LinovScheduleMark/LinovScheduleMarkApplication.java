package com.project.LinovScheduleMark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.project"})
@EntityScan(basePackages = "com.project.model")
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
public class LinovScheduleMarkApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(LinovScheduleMarkApplication.class, args);
	}

}
