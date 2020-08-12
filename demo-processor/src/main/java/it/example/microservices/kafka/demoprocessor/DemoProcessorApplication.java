package it.example.microservices.kafka.demoprocessor;


import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProcessorApplication.class, args);
	}
	

	@Bean
	public Function<Movie, Movie> processorBean() {
		
		return theMovie -> {
			
			Movie correctedMovie = batmanAdjustFactor(theMovie);
			
				if(filterByRating(correctedMovie)){
					return correctedMovie;
				} else {
					// Null would means that you can output any messages
					return null;
				}
		};	
	
	}
	
	private boolean filterByRating(Movie m){
		return m.getRating() >= 6;
	}
	
	private Movie batmanAdjustFactor(Movie m) {
		
		if(m.getName().toLowerCase().contains("batman")) {
			m.setRating(m.getRating()+1);
		}
		
		return m;

	}
	
}
