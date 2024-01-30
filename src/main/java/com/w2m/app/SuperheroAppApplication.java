package com.w2m.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SuperheroAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperheroAppApplication.class, args);
	}

}
