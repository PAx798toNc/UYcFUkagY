// 代码生成时间: 2025-08-19 06:09:33
 * The component provides a REST endpoint to generate random numbers,
 * including error handling and following Spring Boot best practices.
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RandomNumberGenerator {

    // Random number generator instance
    private final Random random = new Random();

    /**
     * Generates a random number within a specified range.
     * 
     * @param min The minimum value of the range (inclusive).
     * @param max The maximum value of the range (exclusive).
     * @return ResponseEntity containing the generated random number.
     */
    @GetMapping("/random")
    public ResponseEntity<Integer> generateRandomNumber(
            @RequestParam(name = "min", required = false, defaultValue = "0") Integer min,
            @RequestParam(name = "max", required = false, defaultValue = "100") Integer max) {

        // Validate the input range
        if (min >= max) {
            return ResponseEntity.badRequest().body(null);
        }

        // Generate a random number within the specified range
        int randomNumber = ThreadLocalRandom.current().nextInt(min, max);

        // Return the generated random number
        return ResponseEntity.ok(randomNumber);
    }
}