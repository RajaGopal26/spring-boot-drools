package com.drools.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ObjectVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by Stephan on 25-06-14.
 */
@Configuration
public class SwaggerConfig {

	public Docket emzeeApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.drools.controller"))
				.paths(regex("/.*"))
				.build()
				.apiInfo(metaData());
	}
	
	private ApiInfo metaData(){
		return new ApiInfo("Drools Sample", "Rule Engine", "2.0", "Terms of Use", new Contact("Raja Gopal MRG", "www.aalam.com", "rajagopal.mahendran@aalamsoft.com"), "Aalam License", "www.aalam.com/license", Arrays.asList(new ObjectVendorExtension("Trivent"), new ObjectVendorExtension("BI3")));
	}

}