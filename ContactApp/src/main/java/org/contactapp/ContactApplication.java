package org.contactapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"org.contactapp.repository"})
public class ContactApplication {

	public static void main(String [] args) {
		SpringApplication.run(ContactApplication.class, args);
	}
}