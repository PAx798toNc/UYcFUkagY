// 代码生成时间: 2025-07-31 08:42:14
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TestReportGenerator is a Spring Boot component responsible for generating test reports.
 */
@Service
public class TestReportGenerator {

    private static final String REPORT_DIRECTORY = "./reports/";
    private static final String REPORT_FILENAME = "test_report_";
    private static final String REPORT_EXTENSION = ".txt";

    @Value("${report.directory:./reports/}")
    private String reportDirectory;

    public void generateReport(String testSuiteName, boolean testResult) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFullReportPath(testSuiteName)))) {
            String header = "Test Report - " + testSuiteName;
            String footer = "Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Write the report header
            writer.println(header);
            writer.println(repeat('-', header.length()));

            // Write the test result
            writer.println("Test Result: " + (testResult ? "PASSED" : "FAILED"));

            // Write the report footer
            writer.println(repeat('-', footer.length()));
            writer.println(footer);
        } catch (IOException e) {
            // Handle the error in a proper way according to the application's error handling strategy
            System.err.println("Error generating test report: " + e.getMessage());
        }
    }

    /**
     * Helper method to create a string of repeated characters.
     * @param c Character to repeat.
     * @param count Number of times to repeat the character.
     * @return A string of repeated characters.
     */
    private String repeat(char c, int count) {
        char[] chars = new char[count];
        for (int i = 0; i < count; i++) {
            chars[i] = c;
        }
        return new String(chars);
    }

    /**
     * Constructs the full path to the report file.
     * @param testSuiteName The name of the test suite.
     * @return The full path to the report file.
     */
    private String getFullReportPath(String testSuiteName) {
        return reportDirectory + REPORT_FILENAME + testSuiteName + REPORT_EXTENSION;
    }
}
