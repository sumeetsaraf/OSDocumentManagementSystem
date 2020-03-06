package com.oneshield.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;


@SpringBootApplication

public class OsDocumentManagementSystemApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OsDocumentManagementSystemApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(OsDocumentManagementSystemApplication.class, args);
	}

}