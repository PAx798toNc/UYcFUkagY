// 代码生成时间: 2025-10-05 00:00:28
import org.springframework.stereotype.Component;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

@Component
@RestController
public class FolderOrganizer {

    private final ResourceLoader resourceLoader;

    // Constructor with ResourceLoader
    public FolderOrganizer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/organize/{path}")
    public ResponseEntity<String> organizeFiles(@PathVariable String path) {
        try {
            Resource resource = resourceLoader.getResource("file:" + path);
            Path dirPath = resource.getFile().toPath().getParent();
            if (dirPath == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Path not found");
            }
            Files.walk(dirPath)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        // Your file organizing logic here, e.g., moving files to a new directory
                        // This is a placeholder for the logic
                        // Path newDirPath = Paths.get("...");
                        // Files.move(file, newDirPath.resolve(file.getFileName()));
                    } catch (IOException e) {
                        throw new RuntimeException("Error organizing files", e);
                    }
                });
            return ResponseEntity.ok("Files organized successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accessing the directory");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error organizing files");
        }
    }
}
