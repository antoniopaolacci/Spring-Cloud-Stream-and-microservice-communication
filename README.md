# Spring Cloud Stream Microservices Communications
Spring Cloud Stream, interconnecting microservices.

Spring Cloud Stream makes it very easy for developers to create Java applications that communicate through message brokers such as Kafka and RabbitMQ.

Spring Cloud Stream allows us to do this by minimizing boilerplate and maximizing the utility of the Spring Framework, so you can focus on implementing your business logic. 

Spring Cloud Stream provide defaults for pretty much everything, such as connecting to the broker, declaring inbound/outbound topics, and serializing/deserializing data.

![](https://github.com/antoniopaolacci/Spring-Cloud-Stream-and-microservice-communication/blob/master/kafka-1.jpg)

The following _Bean_ is a supplier, needs to read some data from inbound topic and outputs other data to outbound topic. Spring cloud stream makes very easy serialize and deserialize automatically your data.JSON is the default content-type but if you want, you can change this format with: xml, proto-buffer, etc.. You don't need to define transformation JSON to a string, it's free!

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



If you want to make your application **reactive** use Flux<Movie>

**Reactive applications** are message-driven applications that decide the next step based on arrival of message. Requests of data that may or may not be available and recipients await the arrival of messages when data is ready. Common scenario: _John orders pizza, phones Bob, invites him to come, heads home, and gets his pizza delivered. But this time, he waits until Bob comes and only  after that he turns the movie on. This is what the **reactive approach** is about. You wait till all async actions (changes) are completed and then proceed with further actions._

**RxJava** was the first Reactive Extension API specific for the Java platform. It works with Java 6 and provides an opportunity to write asynchronous, event-based programs for both Java and Android  Java, which is very convenient.

**Spring Reactor** is another framework for Java from  Spring developers. It is quite similar to RxJava but has simpler  abstraction. The framework has managed to win popularity due to the  possibility to leverage benefits of Java 8.

The following _Bean_ is a reactive supplier:

```java
@Bean
public Function<Flux<Movie>, Flux<Movie>> processorBean() {
	return Flux -> Flux.map(this::batmanAdjustFactor).filter(this::filterByRating);
}
```
Few advantages:

- less code to do reactively 
- your application benefits of best handling IO operation (db communication, http calls, multitasking)
- if you have everythings reactive you are 99% *buzzword compliant*