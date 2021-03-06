# Spring Cloud Stream Microservices Communications
**Spring Cloud Stream**, interconnecting microservices.

Spring Cloud Stream makes it very easy for developers to create Java applications that communicate through message brokers such as Kafka and RabbitMQ.

Spring Cloud Stream allows us to do this by minimizing boilerplate and maximizing the utility of the Spring Framework, so you can focus on implementing your business logic. 

Spring Cloud Stream provide defaults for pretty much everything, such as connecting to the broker, declaring inbound/outbound topics, and serializing/deserializing data.

In this example (reference David's example https://github.com/takeaway/spring-cloud-stream-examples) we have three different java maven module:

- producer is a demo service that puts movies with a name and rating on Kafka topic *"movies"*
- processor is a demo service that reads from *"movies"*, filters out movies below a certain rating and puts on *"goodmovies"* 
- consumer is a demo service that reads from Kafka topic *"goodmovies"* and prints message contents to standard output

![](https://github.com/antoniopaolacci/Spring-Cloud-Stream-and-microservice-communication/blob/master/kafka-1.jpg)

The following _Bean_ is a supplier, needs to read some data from inbound topic and outputs other data to outbound topic. Spring cloud stream makes very easy serialize and deserialize automatically your data. JSON is the default content-type of spring cloud stream but if you want, you can change this format with xml, proto-buffer, etc.. 

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

The properties file _.../src/main/resources/application.properties_ define the topics.

```yaml
spring.cloud.stream.bindings.processorBean-in-0.destination=movies
spring.cloud.stream.bindings.processorBean-out-0.destination=goodmovies
```

You don't need to define transformation JSON to string, it's free! The following Bean is the consumer, it is unaware of the content-type, the content will be transformed in a String and printed to the screen.

```java
@Bean
public Consumer<String> consumerBean() {
	return System.out::println;
}
```
![](https://github.com/antoniopaolacci/Spring-Cloud-Stream-and-microservice-communication/blob/master/kafka-2.jpg)

##### Reactive Application  

If you want to make your application **reactive** use Flux\<Movie\>.

**Reactive applications** are message-driven applications that decide the next step based on arrival of message. Requests of data that may or may not be available and recipients await the arrival of messages when data is ready. Common scenario: _John orders pizza, phones Bob, invites him to come, heads home, and gets his pizza delivered. But this time, he waits until Bob comes and only after that he turns the movie on. This is what the **reactive approach** is about. You wait till all async actions (changes) are completed and then proceed with further actions._

Another explanation could be: [https://stackoverflow.com/questions/51870883/differences-between-reactive-programming-and-message-queue](https://stackoverflow.com/questions/51870883/differences-between-reactive-programming-and-message-queue)

**RxJava** was the first Reactive Extension API specific for the Java platform. It works with Java 6 and provides an opportunity to write asynchronous, event-based programs for both Java and Android  Java, which is very convenient.

**Spring Reactor** is another framework for Java from  Spring developers. It is quite similar to RxJava but has simpler  abstraction. The framework has managed to win popularity due to the  possibility to leverage benefits of Java 8.

The following _Bean_ is a reactive supplier:

```java
@Bean
public Function<Flux<Movie>, Flux<Movie>> processorBean() {
	return Flux -> Flux.map(this::batmanAdjustFactor).filter(this::filterByRating);
}
```
Few advantages of java reactive approach:

- less code to do reactively 
- your application benefits of best handling IO operation (db communication, http calls, multitasking, etc..)
- if you have everythings reactive you are 99% *buzzword compliant* ;)