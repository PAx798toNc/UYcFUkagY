// 代码生成时间: 2025-10-08 01:30:27
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class DuplicateFileDetector {
    private static final Map<String, Path> fileMap = new HashMap<>();

    /**
     * Checks if the provided file is a duplicate.
     *
     * @param file The file to check.
     * @return true if the file is a duplicate, false otherwise.
     * @throws IOException When an I/O error occurs.
     */
    public boolean isDuplicateFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("The provided file is null or empty.");
        }

        String fileHash = calculateFileHash(file);
        return fileMap.containsKey(fileHash);
    }

    /**
     * Stores the new file and updates the hash map.
     *
     * @param file The new file to store.
     * @param filePath The path to store the file.
     * @return true if the file was stored successfully, false if it's a duplicate.
     * @throws IOException When an I/O error occurs.
     */
    public boolean storeNewFile(MultipartFile file, String filePath) throws IOException {
        if (isDuplicateFile(file)) {
            return false;
        }

        Path path = Paths.get(filePath);
        Files.copy(file.getInputStream(), path);

        String fileHash = calculateFileHash(file);
        fileMap.put(fileHash, path);
        return true;
    }

    /**
     * Calculates the hash of the file.
     *
     * @param file The file to calculate the hash for.
     * @return The hash of the file.
     * @throws IOException When an I/O error occurs.
     */
    private String calculateFileHash(MultipartFile file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(file.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algorithm SHA-256 is not available.", e);
        }
    }

    /**
     * Converts a byte array to a hex string.
     *
     * @param bytes The byte array to convert.
     * @return The hex string representation of the byte array.
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(String.format("%02x", aByte));
        }
        return sb.toString();
    }
}
