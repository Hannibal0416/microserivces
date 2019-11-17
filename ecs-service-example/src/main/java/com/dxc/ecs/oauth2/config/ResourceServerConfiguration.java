package com.dxc.ecs.oauth2.config;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		logger.info("configure");
		resources.resourceId("resource-server-rest-api").authenticationManager(authenticationManagerBean())
				.tokenExtractor(new CustomTokenExtractor());
	}

	@Bean
	public ResourceServerTokenServices tokenService() {
		logger.info("tokenService");
		CustomRemoteTokenService tokenServices = new CustomRemoteTokenService();
		return tokenServices;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		logger.info("authenticationManagerBean");
		OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
		authenticationManager.setTokenServices(tokenService());
		return authenticationManager;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		logger.info("configure");
		http.httpBasic().disable().headers().disable().cors().disable().authorizeRequests().antMatchers("/user/**").authenticated()
				.antMatchers("/public/**").permitAll().antMatchers("/**").permitAll();;
	}

}

