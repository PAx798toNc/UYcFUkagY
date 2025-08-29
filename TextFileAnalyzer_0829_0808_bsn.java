// 代码生成时间: 2025-08-29 08:08:28
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.List;

@Component
@ConditionalOnProperty(name = "textanalyzer.enabled", havingValue = "true")
public class TextFileAnalyzer {

    private final ResourceLoader resourceLoader;

    @Autowired
    public TextFileAnalyzer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Analyzes the content of a text file.
     *
     * @param filePath the path to the text file
     * @return a list of strings representing each line of the file, or an error message if the file cannot be read
     */
    public List<String> analyzeFile(String filePath) {
        try {
            Resource resource = resourceLoader.getResource(filePath);
            if (!resource.exists()) {
                throw new IllegalArgumentException("File does not exist: " + filePath);
            }
            return Files.readAllLines(Paths.get(resource.getURI()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }
}
