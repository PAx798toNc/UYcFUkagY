// 代码生成时间: 2025-10-09 03:52:20
package com.example.demo.component;

import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;
import java.util.NoSuchElementException;

@Component
@RestController
public class RandomNumberGenerator {

    private final Random random = new Random();

    private static final int MAX_NUMBER = 100; // Maximum number limit for the random generator
    private static final int MIN_NUMBER = 1; // Minimum number limit for the random generator

    /**
     * Generate a random number between the specified range.
     * If no range is specified, it generates a number between 1 and 100.
     * @return ResponseEntity with the generated random number
     */
    @GetMapping("/random")
    public ResponseEntity<Integer> generateRandomNumber(@RequestParam(value = "min", required = false) Integer min,
                                                         @RequestParam(value = "max", required = false) Integer max) {

        try {
            // Default values if no range is provided
            int lowerBound = (min != null) ? min : MIN_NUMBER;
            int upperBound = (max != null) ? max : MAX_NUMBER;
            
            // Check if the lower bound is less than the upper bound
            if (lowerBound >= upperBound) {
                return ResponseEntity.badRequest().body(-1);
            }
            
            // Generate random number within the bounds
            int randomNumber = lowerBound + random.nextInt(upperBound - lowerBound + 1);
            return ResponseEntity.ok(randomNumber);
        } catch (IllegalArgumentException e) {
            // Handle exception for invalid arguments
            return ResponseEntity.badRequest().body(-1);
        }
    }
}