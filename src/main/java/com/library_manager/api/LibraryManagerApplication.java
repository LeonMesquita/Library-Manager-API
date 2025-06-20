package com.library_manager.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagerApplication.class, args);
	}

}
