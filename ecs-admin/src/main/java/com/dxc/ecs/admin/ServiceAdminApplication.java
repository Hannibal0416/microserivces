package com.dxc.ecs.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@Configuration
@EnableAdminServer
@EnableDiscoveryClient
@EnableAutoConfiguration
public class ServiceAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAdminApplication.class, args);
	}
}
