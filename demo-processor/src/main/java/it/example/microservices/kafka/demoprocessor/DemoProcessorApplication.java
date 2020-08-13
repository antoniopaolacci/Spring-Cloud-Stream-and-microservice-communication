package it.example.microservices.kafka.demoprocessor;


import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import reactor.core.publisher.Flux;

@Controller
@SpringBootApplication
public class DemoProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProcessorApplication.class, args);
	}
	
	@Autowired
	private StreamBridge streamBridge;
	
	@PostMapping("/movies")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void send(@RequestBody Movie movie) { 
		streamBridge.send("goodmovies", movie);
	}
	

	@Bean
	public Function<Flux<Movie>, Flux<Movie>> processorBean() {
		return Flux -> Flux.map(this::batmanAdjustFactor).filter(this::filterByRating);
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
