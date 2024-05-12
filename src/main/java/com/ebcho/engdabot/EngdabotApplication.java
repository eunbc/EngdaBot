package com.ebcho.engdabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EngdabotApplication {
	public static void main(String[] args) {
		SpringApplication.run(EngdabotApplication.class, args);
	}

}
