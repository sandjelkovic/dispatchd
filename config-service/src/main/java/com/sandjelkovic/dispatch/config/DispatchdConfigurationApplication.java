package com.sandjelkovic.dispatch.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class DispatchdConfigurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DispatchdConfigurationApplication.class, args);
	}
}