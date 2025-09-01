// 代码生成时间: 2025-09-01 21:04:09
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

@Component
public class ImageResizerComponent {

    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageResizerComponent(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Resizes a list of images to the specified dimensions.
     * 
     * @param images List of images to resize.
     * @param width The new width of the images.
     * @param height The new height of the images.
     * @return A list of resized images.
     * @throws IOException If an error occurs during image processing.
     */
    public List<File> resizeImages(List<MultipartFile> images, int width, int height) throws IOException {
        List<File> resizedImages = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                resizedImages.add(resizeImage(image.getInputStream(), width, height));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to resize image", e);
            }
        }
        return resizedImages;
    }

    /**
     * Resizes an image to the specified dimensions.
     * 
     * @param inputStream The input stream of the image to resize.
     * @param width The new width of the image.
     * @param height The new height of the image.
     * @return The resized image file.
     * @throws IOException If an error occurs during image processing.
     */
    private File resizeImage(InputStream inputStream, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(inputStream);
        if (originalImage == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image format");
        }

        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, width, height, null);
        graphics.dispose();

        File resizedFile = File.createTempFile("resized_", "." + getExtension(inputStream));
        ImageIO.write(resizedImage, getExtension(inputStream), resizedFile);
        return resizedFile;
    }

    /**
     * Retrieves the file extension from the image input stream.
     * 
     * @param inputStream The input stream of the image.
     * @return The file extension of the image.
     */
    private String getExtension(InputStream inputStream) throws IOException {
        String fileName = inputStream.toString();
        int lastIndex = fileName.lastIndexOf('.');
        return lastIndex > 0 ? fileName.substring(lastIndex + 1) : null;
    }
}
