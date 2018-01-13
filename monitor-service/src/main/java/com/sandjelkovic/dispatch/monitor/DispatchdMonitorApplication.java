package com.sandjelkovic.dispatch.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Disconnect Admin server until it supports Spring Boot 2
// See https://github.com/codecentric/spring-boot-admin/issues/465

//@EnableAdminServer
public class DispatchdMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DispatchdMonitorApplication.class, args);
	}
}
