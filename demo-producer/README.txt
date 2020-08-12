## Import the project structure in Eclipse ##
mvn eclipse:eclipse

## To clean, compile and build the project ##
mvn clean install 

## mvn skip test ##
mvn install -DskipTests

## mvn run spring-boot app with profile ##
mvn spring-boot:run
