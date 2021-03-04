package com.banistmo.ctg.mdw.payroll.api.config;


import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.banistmo.ctg.mdw.payroll.api.rest"))
				.paths(PathSelectors.regex("/.*")).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API DE LOTES DE PLANILLA",
				"API encargada de la gestión de la gestión de la carga de los lotes de planilla",
				"API V 1.0.0", "Terms of service",
				new Contact("Pragma S.A", "https://www.pragma.com.co/es/", "pragma@pragma.com.co"), "License of API",
				"API license URL", Collections.emptyList());
	}

}
