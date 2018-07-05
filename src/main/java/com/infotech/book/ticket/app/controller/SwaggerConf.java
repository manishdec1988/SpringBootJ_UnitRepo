package com.infotech.book.ticket.app.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
public class SwaggerConf {

	@Bean
	//comment
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
				.apiInfo(apiInfo()).select().paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return or(regex("/api/tickets.*"), regex("/api/tickets.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ReservationManagementSystem API")
				.description("ReservationManagementSystemApp")
				.termsOfServiceUrl("http://javainuse.com")
				.contact("ManishKumar.Singh@LibertyMutual.com").license("ReservationManagementSystem License")
				.licenseUrl("ManishKumar.Singh@LibertyMutual.com").version("1.0").build();
	}

}