// 代码生成时间: 2025-08-08 12:42:32
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Component
@RestControllerAdvice
public class ErrorLogCollector {
    
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogCollector.class);
    
    private final Map<String, Throwable> errorMap = new HashMap<>();
    
    // Method to handle exceptions and log error details
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(HttpServletRequest request, Exception ex) {
        String errorId = generateErrorId();
        errorMap.put(errorId, ex);
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", new Date());
        errorDetails.put("errorId", errorId);
        errorDetails.put("message", ex.getMessage());
        logger.error("Error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
    
    // Helper method to generate unique error IDs
    private String generateErrorId() {
        return System.currentTimeMillis() + Long.toString(System.nanoTime());
    }
    
    // Method to retrieve error details by error ID
    public ResponseEntity<Map<String, Object>> getErrorDetails(String errorId) {
        Throwable error = errorMap.get(errorId);
        if (error == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", new Date());
        errorDetails.put("errorId", errorId);
        errorDetails.put("message", error.getMessage());
        errorDetails.put("stackTrace", getStackTraceAsString(error));
        return ResponseEntity.ok(errorDetails);
    }
    
    // Helper method to convert StackTrace to String
    private String getStackTraceAsString(Throwable throwable) {
        try {
            return org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(throwable);
        } catch (IOException e) {
            return "Stack trace could not be generated";
        }
    }
}