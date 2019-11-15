package com.dxc.ecs.oauth2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Oauth2GlobalMethodConfig extends GlobalMethodSecurityConfiguration {
	Logger logger = LoggerFactory.getLogger(Oauth2GlobalMethodConfig.class);

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		logger.info("createExpressionHandler");
		return new OAuth2MethodSecurityExpressionHandler();
	}

}