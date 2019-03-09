package com.dineshonjava.prodos;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProdosClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdosClientApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Traverson traverson() {
		return new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
	}

}
