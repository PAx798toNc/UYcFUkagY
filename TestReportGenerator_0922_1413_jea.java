// 代码生成时间: 2025-09-22 14:13:09
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// TestReportGenerator Component
@Component
@RestController
@RequestMapping("/api")
public class TestReportGenerator {

    // Inject a property for the test report file path
    @Value("{test.report.file.path}")
    private String testReportFilePath;

    // Endpoint to generate and return the test report
    @GetMapping("/report")
    public ResponseEntity<String> generateTestReport() {
        try {
            // Read the contents of the test report file
            String reportContent = new String(Files.readAllBytes(Paths.get(testReportFilePath)));
            // Return the report contents as a response
            return ResponseEntity.ok(reportContent);
        } catch (IOException e) {
            // Handle exceptions by returning an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while generating the test report.");
        }
    }

    // Exception handler method to catch any RuntimeException and return a user-friendly message
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
    }
}
