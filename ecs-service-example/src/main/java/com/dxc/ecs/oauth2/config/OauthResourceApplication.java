package com.dxc.ecs.oauth2.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dxc.ecs.oauth2")
@EnableDiscoveryClient
public class OauthResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthResourceApplication.class, args);
	}

}

