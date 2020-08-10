package it.example.microservices.kafka.demoproducer;

import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProducerApplication.class, args);
	}
	
	@Bean
	public Supplier<String> supplierBean() {
		return () -> "Batman Begins";
	}

}
