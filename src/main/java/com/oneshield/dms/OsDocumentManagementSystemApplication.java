package com.oneshield.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;



@SpringBootApplication(exclude = { RepositoryRestMvcAutoConfiguration.class, SecurityAutoConfiguration.class })
public class OsDocumentManagementSystemApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OsDocumentManagementSystemApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(OsDocumentManagementSystemApplication.class, args);
	}

}