# instrument-consumer
 
This is an exercise to consume an input file with multiple time series containing price instruments.

This program is intended to consume the time series from the provided file and and pass all of them to the Calculation Engine to be able to perform certain operations.

The Guava cache is used to get the multiplier value from the main table for a given instrument name and the value if cached for 5 seconds.


# Usage
  1) mvn clean install - Will build and download all dependencies
  2) mvn exec:java -Dexec.mainClass="com.instrument.consumer.main.MainApp"

## Tests  
  mvn test

# Stack
  **Java 8**
  
  **Spring JDBC**
  
  **Guava Cache**
  
  **Maven**
  
  **H2 DataBase**
  
  

  
