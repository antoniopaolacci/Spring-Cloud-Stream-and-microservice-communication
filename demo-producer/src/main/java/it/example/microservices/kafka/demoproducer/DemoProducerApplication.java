package it.example.microservices.kafka.demoproducer;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
	public Supplier<Movie> supplierBean() {
		
		return () -> {
			List<Movie> movies = new LinkedList<>(Arrays.asList(
					
					new Movie("Batman and Robin",4),
					new Movie("Batman Begins",5),
					new Movie("Apocalipse Now",8),
					new Movie("Pearl Harbur",4),
					new Movie("Blues Brothers",9),
					new Movie("Moonlight",3)
					
					));
			
			Collections.shuffle(movies);
			return movies.get(0);

		};
	}

}
