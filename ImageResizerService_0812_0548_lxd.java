// 代码生成时间: 2025-08-12 05:48:52
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageResizerService {

    // Resizes a single image file.
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        java.awt.Image scaledImage = originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
# 扩展功能模块
        resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        return resizedImage;
    }
# NOTE: 重要实现细节

    // Saves a resized image to a file.
    private void saveImage(BufferedImage resizedImage, String path) throws IOException {
        File outputFile = new File(path);
        ImageIO.write(resizedImage, "jpg", outputFile);
    }
# 扩展功能模块

    // Batch resizes images and returns a list of paths to the resized images.
    public List<String> batchResizeImages(List<MultipartFile> imageFiles, int width, int height) {
        List<String> resizedImagePaths = new ArrayList<>();
# TODO: 优化性能

        for (MultipartFile file : imageFiles) {
# NOTE: 重要实现细节
            try {
                BufferedImage originalImage = ImageIO.read(file.getInputStream());
                BufferedImage resizedImage = resizeImage(originalImage, width, height);

                // Create a unique path for each resized image.
# 添加错误处理
                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
# NOTE: 重要实现细节
                Path path = Paths.get("resized_images", uniqueFileName);
# 优化算法效率

                saveImage(resizedImage, path.toString());
# 优化算法效率
                resizedImagePaths.add(path.toString());
            } catch (IOException e) {
                // Handle error for the current file and continue with the next.
                System.err.println("Error resizing image: " + file.getOriginalFilename());
            }
        }

        return resizedImagePaths;
    }
}
