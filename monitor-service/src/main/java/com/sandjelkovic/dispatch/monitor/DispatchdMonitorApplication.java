package com.sandjelkovic.dispatch.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class DispatchdMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DispatchdMonitorApplication.class, args);
    }
}
