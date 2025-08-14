// 代码生成时间: 2025-08-15 04:06:38
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RestController
@RequestMapping("/api/rename")
public class BatchFileRenamer {

    @PostMapping
    public ResponseEntity<?> renameFiles(@RequestBody List<RenameRequest> renameRequests) {
        try {
            List<String> errors = renameFilesInternal(renameRequests);
            if (errors.isEmpty()) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body(errors);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    private List<String> renameFilesInternal(List<RenameRequest> renameRequests) throws IOException {
        List<String> errors = new ArrayList<>();
        for (RenameRequest request : renameRequests) {
            Path oldPath = Paths.get(request.getOldName());
            Path newPath = Paths.get(request.getNewName());
            File oldFile = oldPath.toFile();
            if (!oldFile.exists()) {
                errors.add("File not found: " + request.getOldName());
                continue;
            }
            if (newPath.toFile().exists()) {
                errors.add("File already exists: " + request.getNewName());
                continue;
            }
            Files.move(oldPath, newPath);
        }
        return errors;
    }

    // Inner class to represent the rename request
    private static class RenameRequest {
        private String oldName;
        private String newName;

        public String getOldName() {
            return oldName;
        }

        public void setOldName(String oldName) {
            this.oldName = oldName;
        }

        public String getNewName() {
            return newName;
        }

        public void setNewName(String newName) {
            this.newName = newName;
        }
    }
}
