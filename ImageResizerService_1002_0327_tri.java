// 代码生成时间: 2025-10-02 03:27:24
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageResizerService {

    /**
     * Resizes a list of images to a specified width and height.
     *
     * @param images The list of images to resize.
     * @param targetWidth The target width for the resized images.
     * @param targetHeight The target height for the resized images.
     * @return A list of resized images in byte array form.
     * @throws IOException If an error occurs during image processing.
     */
    public List<byte[]> resizeImages(List<MultipartFile> images, int targetWidth, int targetHeight) throws IOException {

        List<byte[]> resizedImages = new ArrayList<>();

        for (MultipartFile image : images) {
            BufferedImage originalImage = ImageIO.read(image.getInputStream());
            BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);
            if (resizedImage != null) {
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "jpg", bao);
                resizedImages.add(bao.toByteArray());
            } else {
                throw new IOException("Failed to resize image");
            }
        }

        return resizedImages;
    }

    /**
     * Resizes a single image to a specified width and height, keeping the aspect ratio.
     *
     * @param originalImage The original image to resize.
     * @param targetWidth The target width for the resized image.
     * @param targetHeight The target height for the resized image.
     * @return The resized image.
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        double aspectRatio = (double) originalWidth / (double) originalHeight;

        int newWidth, newHeight;
        if (originalWidth > originalHeight) {
            newWidth = targetWidth;
            newHeight = (int) (targetWidth / aspectRatio);
        } else {
            newHeight = targetHeight;
            newWidth = (int) (targetHeight * aspectRatio);
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        return resizedImage;
    }
}