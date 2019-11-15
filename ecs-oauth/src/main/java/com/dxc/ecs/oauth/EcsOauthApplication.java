package com.dxc.ecs.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.dxc.ecs.oauth")
@EnableDiscoveryClient
public class EcsOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcsOauthApplication.class, args);
	}

}
