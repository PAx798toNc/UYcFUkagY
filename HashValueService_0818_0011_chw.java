// 代码生成时间: 2025-08-18 00:11:11
 * A Spring Boot component that calculates hash values for given input strings.
 * It includes error handling and follows Spring Boot best practices.
 */

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class HashValueService {

    private static final String HASHING_ALGORITHM = "SHA-256";

    @Autowired
    private ErrorMessageService errorMessageService; // Assuming an ErrorMessageService for error handling

    public String calculateHashValue(String input) throws NoSuchAlgorithmException {
        // Check if input is not null
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        // Initialize MessageDigest with the hashing algorithm
        MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);

        // Hash the input and get byte array
        byte[] hashedBytes = messageDigest.digest(input.getBytes());

        // Convert byte array to Base64 encoded string
        String hashValue = Base64.getEncoder().encodeToString(hashedBytes);

        // Return the hash value
        return hashValue;
    }

    // Error handling service assumed to be part of the application
    // This is a simplified representation, actual implementation may vary
    @Component
    class ErrorMessageService {
        public String createErrorMessage(Exception e) {
            return "Error while calculating hash: " + e.getMessage();
        }
    }
}
