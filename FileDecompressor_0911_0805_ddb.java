// 代码生成时间: 2025-09-11 08:05:24
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

@Component
public class FileDecompressor {

    private static final String TEMP_DIR = "/tmp/";

    private void decompressFile(String sourceFile, String destDirectory) throws IOException, ArchiveException, CompressorException {
        // Determine the type of compression based on the file extension
        String fileExtension = getFileExtension(sourceFile);
        InputStream inputStream = new FileInputStream(sourceFile);

        switch (fileExtension) {
            case ".tar":
                decompressTar(inputStream, destDirectory);
                break;
            case ".gz":
            case ".tgz":
                decompressGzip(inputStream, destDirectory);
                break;
            case ".zip":
                decompressZip(inputStream, destDirectory);
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type for decompression");
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }

    private void decompressTar(InputStream inputStream, String destDirectory) throws ArchiveException, IOException {
        ArchiveInputStream archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.TAR, inputStream);
        ArchiveEntry entry;
        while ((entry = archiveInputStream.getNextEntry()) != null) {
            File outputFile = new File(destDirectory, entry.getName());
            if (entry.isDirectory()) {
                if (!outputFile.exists()) {
                    Files.createDirectories(outputFile.toPath());
                }
            } else {
                Files.copy(archiveInputStream, outputFile.toPath());
            }
        }
        archiveInputStream.close();
    }

    private void decompressGzip(InputStream inputStream, String destDirectory) throws IOException {
        CompressorInputStream gzipInputStream = new CompressorStreamFactory().createCompressorInputStream(CompressorStreamFactory.GZIP, inputStream);
        try (OutputStream outputStream = new FileOutputStream(destDirectory + "/" + new File(inputStream.toString()).getName().replaceAll(".(gz|tgz)", ""))); {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
        }
    }

    private void decompressZip(InputStream inputStream, String destDirectory) throws IOException {
        ArchiveInputStream archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, inputStream);
        ArchiveEntry entry;
        while ((entry = archiveInputStream.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                File outputFile = new File(destDirectory, entry.getName());
                Files.copy(archiveInputStream, outputFile.toPath());
            }
        }
        archiveInputStream.close();
    }

    // Service method to decompress a file uploaded via MultipartFile
    public void decompressFile(MultipartFile file) {
        try {
            String destinationDir = TEMP_DIR + "decompressed/";
            Files.createDirectories(Paths.get(destinationDir));
            decompressFile(file.getInputStream(), destinationDir);
        } catch (Exception e) {
            // Error handling
            throw new RuntimeException("Error decompressing file", e);
        }
    }
}
