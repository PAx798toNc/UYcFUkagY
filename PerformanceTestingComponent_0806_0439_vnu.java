// 代码生成时间: 2025-08-06 04:39:09
package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class PerformanceTestingComponent {
    
    // RestTemplate for making HTTP requests
    private final RestTemplate restTemplate;
    
    // Executor service for managing parallel requests
    private ExecutorService executor;
    
    @Autowired
    public PerformanceTestingComponent(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
    
    /**
     * Initializes the executor service with a fixed thread pool.
     * @return ExecutorService - the initialized executor service.
     */
    @Bean
    public ExecutorService executorService() {
        // Adjust the number of threads based on your needs
        return Executors.newFixedThreadPool(10);
    }
    
    /**
     * Method to perform performance testing by sending multiple parallel requests.
     * @param url The URL to send requests to.
     * @return String - a message indicating the result of the performance test.
     */
    @GetMapping("/performTest")
    public String performPerformanceTest(@RequestParam String url) {
        // Number of requests to send
        final int numOfRequests = 100;
        
        // Initialize a list to store the futures of the requests
        executor = executorService();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numOfRequests; i++) {
            executor.submit(() -> {
                try {
                    // Send a GET request and handle the response
                    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                    if (response.getStatusCode() == HttpStatus.OK) {
                        // Handle successful response here if needed
                    } else {
                        // Handle error response here if needed
                    }
                } catch (SocketTimeoutException e) {
                    // Handle timeouts
                } catch (Exception e) {
                    // Handle other exceptions
                }
            });
        }
        
        // Wait for all tasks to complete
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Performance test interrupted";
        }
        
        long endTime = System.currentTimeMillis();
        return "Performance test completed. Time taken: " + (endTime - startTime) + "ms";
    }
}
