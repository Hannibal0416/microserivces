package com.dxc.ecs.zuulgateway.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author by Abbot
 * @date 2018/09/13 10:41
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {


	/**
	 * 扫描controller包生成API接口文档
	 *
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo());
	}

	/**
	 * Docket 对象
	 * 
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ECS").description("Rest风格的Api文档")
				.termsOfServiceUrl("").contact(new Contact("Abbot", "www.baidu.com", "471567754@qq.com")).version("1.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").build();
	}
}