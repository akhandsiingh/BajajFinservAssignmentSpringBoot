package com.example.webhooksolver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WebhooksolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebhooksolverApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean
	public CommandLineRunner commandLineRunner(WebhookService service) {
		return args -> {
			service.executeFlow();
		};
	}
}
