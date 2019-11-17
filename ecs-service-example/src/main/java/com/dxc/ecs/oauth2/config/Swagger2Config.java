package com.dxc.ecs.oauth2.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author by Hannibal
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {


	/**
	 * https://www.cnblogs.com/Irving/p/9580791.html
	 *
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
//        .securityContexts(Collections.singletonList(securityContext()))
        .securitySchemes(Arrays.asList(apiCookieKey()));
	}

	/**
	 * 
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ECS")
				.description("RestDoc")
				.termsOfServiceUrl("")
				.contact(new Contact("Hannibal", "Hannibal", "wen-shun.han@dxc.com")).version("1.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").build();
				
	}
	
	@Bean
    public SecurityScheme apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, "apiKey", "header");
    }

    @Bean
    public SecurityScheme apiCookieKey() {
        return new ApiKey(HttpHeaders.COOKIE, "access_token", "cookie");
    }

//    private OAuth securitySchema() {
//
//        List<AuthorizationScope> authorizationScopeList = new ArrayList();
//        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
//        authorizationScopeList.add(new AuthorizationScope("write", "access all"));
//        List<GrantType> grantTypes = new ArrayList();
//        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
//        grantTypes.add(passwordCredentialsGrant);
//
//        return new OAuth("oauth2", authorizationScopeList, grantTypes);
//    }
}