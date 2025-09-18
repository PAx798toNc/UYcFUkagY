// 代码生成时间: 2025-09-18 12:10:05
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
# NOTE: 重要实现细节
import javax.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;

@Component
# 改进用户体验
public class ImageResizerComponent {

    private String uploadDir;

    @PostConstruct
# 增强安全性
    public void init() {
        // Set the directory where images will be saved
        uploadDir = "./uploads/";
# 增强安全性
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
    }

    public ResponseEntity<List<String>> resizeImages(List<MultipartFile> imageFiles, int targetWidth, int targetHeight) {
        List<String> resizedImagePaths = new ArrayList<>();
        try {
# 改进用户体验
            for (MultipartFile imageFile : imageFiles) {
                // Check if the file is not empty
                if (!imageFile.isEmpty()) {
                    String originalFileName = imageFile.getOriginalFilename();
                    BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
                    String resizedFileName = "resized_" + originalFileName;
                    Path resizedFilePath = Paths.get(uploadDir + resizedFileName);

                    // Resize the image
                    BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);
                    ImageIO.write(resizedImage, "jpg", new File(resizedFilePath.toString()));
# TODO: 优化性能

                    // Save the path of the resized image
                    resizedImagePaths.add(resizedFilePath.toString());
                } else {
                    throw new IOException("The image file is empty");
                }
            }
        } catch (IOException e) {
            // Return an error response if an exception occurs
# TODO: 优化性能
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Return the paths of the resized images
        return ResponseEntity.ok(resizedImagePaths);
    }

    // Resize the image to the specified dimensions
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        // Use a Graphics2D object to scale the image
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
# TODO: 优化性能
        java.awt.Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
# 扩展功能模块
        g2d.dispose();
        return resizedImage;
    }
}
