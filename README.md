# Spring Cloud Stream Microservices Communications
Spring Cloud Stream, interconnecting microservices.

Spring Cloud Stream makes it very easy for developers to create Java applications that communicate through message brokers such as Kafka and RabbitMQ.

Spring Cloud Stream allows us to do this by minimizing boilerplate and maximizing the utility of the Spring Framework, so you can focus on implementing your business logic. 

Spring Cloud Stream provide defaults for pretty much everything, such as connecting to the broker, declaring inbound/outbound topics, and serializing/deserializing data.

![](https://github.com/antoniopaolacci/Spring-Cloud-Stream-and-microservice-communication/blob/master/kafka-1.jpg)

The following _Bean_ is a supplier, needs to read some data from inbound topic and outputs other data to outbound topic. 

Spring cloud stream makes very easy serialize and deserialize automatically your data.

JSON is the default content-type but if you want, you can change this format with: XML, proto-buffer, etc... 

You don't need to define transformation JSON to a string, it's free!

```java
@Bean
public Function<Movie, Movie> processorBean() {

    return theMovie -> {

        Movie correctedMovie = batmanAdjustFactor(theMovie);

        if(filterByRating(correctedMovie)){
            return correctedMovie;
        } else {
            return null;
        }
    
    };	

}
```



The following Bean is the consumer, it is unaware of the content type, the content will be transformed in a String and print to the screen.

```java
@Bean
public Consumer<String> consumerBean() {
	return System.out::println;
}
```
![](https://github.com/antoniopaolacci/Spring-Cloud-Stream-and-microservice-communication/blob/master/kafka-2.jpg)