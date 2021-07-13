package com.apirest.disney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class DisneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisneyApplication.class, args);
	}

}
