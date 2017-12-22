# instrument-consumer
 
This is an exercise to consume an input file with multiple time series containing price instruments.

This program is intended to consume the time series from the provided file and and pass all of them to the Calculation Engine to be able to perform certain operations.

The Guava cache is used to get the multiplier value from the main table for a given instrument name and the value if cached for 5 seconds.

## Additional notes

 This solution does not load the entire file in memory. 
 
 Each line of the file is processed and then discarded without keeping it in memory.

 YourKit-Java-Profiler has been used to determine the consume of memory and CPU for this solution.

 I have created a huge file of 32GB and this solution was able to process and compute the data without any issue.

# Usage
    mvn clean install 
    
    mvn exec:java -Dexec.mainClass="com.instrument.consumer.main.MainApp"
   
## Tests   
    mvn test

# Stack
  **Java 8**
  
  **Spring JDBC**
  
  **Guava Cache**
  
  **Maven**
  
  **H2 DataBase**
  
  **JUnit**
  
  

  
