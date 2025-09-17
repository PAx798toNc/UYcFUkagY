// 代码生成时间: 2025-09-17 16:56:09
package com.example.filerenamer;

import org.springframework.stereotype.Component;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BatchFileRenamer {

    private static final String BASE_DIRECTORY = "/path/to/your/directory"; // Change this to your base directory

    public List<String> renameFiles(String extension, String newExtension) {
        try {
            // List all files in the base directory with the given extension
            List<File> files = listFilesByExtension(extension);

            // Rename all files by replacing the extension with the new one
            return files.stream()
                .map(file -> renameFile(file, newExtension))
                .collect(Collectors.toList());
        } catch (Exception e) {
            // Handle exceptions and return an error message
            throw new RuntimeException("Error occurred while renaming files: " + e.getMessage(), e);
        }
    }

    private List<File> listFilesByExtension(String extension) {
        try (Stream<Path> paths = Files.walk(Paths.get(BASE_DIRECTORY))) {
            return paths
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .filter(file -> file.getName().endsWith("." + extension))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error listing files with extension: " + extension, e);
        }
    }

    private String renameFile(File file, String newExtension) {
        try {
            String oldPath = file.getCanonicalPath();
            String oldName = file.getName();
            String newName = oldName.substring(0, oldName.lastIndexOf('.')) + "." + newExtension;
            String newPath = oldPath.substring(0, oldPath.lastIndexOf('.')) + "." + newExtension;
            boolean success = file.renameTo(new File(newPath));
            if (success) {
                return "Renamed " + oldName + " to " + newName;
            } else {
                return "Failed to rename " + oldName;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error renaming file: " + file.getName(), e);
        }
    }
}
