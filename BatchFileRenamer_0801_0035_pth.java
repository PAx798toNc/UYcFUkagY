// 代码生成时间: 2025-08-01 00:35:13
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component for batch file renaming.
 */
@Component
public class BatchFileRenamer {

    private static final String UPLOAD_DIR = "./uploads/"; // Directory for uploaded files

    public List<String> renameFiles(List<MultipartFile> files, List<String> newNames) {
        if (files.size() != newNames.size()) {
            throw new IllegalArgumentException("The number of files and new names must match");
        }

        return files.stream()
            .map(file -> renameFile(file, newNames.get(files.indexOf(file))))
            .collect(Collectors.toList());
    }

    private String renameFile(MultipartFile file, String newName) {
        try {
            // Ensure the upload directory exists
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Original file path
            Path original = Paths.get(UPLOAD_DIR + file.getOriginalFilename());

            // New file path
            Path renamed = Paths.get(UPLOAD_DIR + newName);

            // Move and rename the file
            Files.move(original, renamed);

            // Return the new file name
            return renamed.getFileName().toString();
        } catch (IOException e) {
            throw new RuntimeException("Error renaming file", e);
        }
    }
}
