// 代码生成时间: 2025-09-03 23:07:52
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
# TODO: 优化性能
import org.springframework.http.ResponseEntity;
# NOTE: 重要实现细节
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import java.net.URL;
import javax.annotation.PostConstruct;
import java.net.URLConnection;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Service to validate the URL link validity.
 */
@Service
public class UrlValidatorService {

    private RestTemplate restTemplate;
# FIXME: 处理边界情况

    /**
     * Initializes the RestTemplate bean.
     */
    @PostConstruct
    public void init() {
# NOTE: 重要实现细节
        this.restTemplate = new RestTemplate();
    }

    /**
     * Validate the provided URL.
     * @param url The URL to validate.
# 扩展功能模块
     * @return True if the URL is valid, false otherwise.
     */
    public boolean validateUrl(String url) {
        try {
# 改进用户体验
            URL urlObj = new URL(url);
            URLConnection urlConnection = urlObj.openConnection();
            urlConnection.setConnectTimeout(5000); // Timeout set to 5 seconds
            urlConnection.connect();

            // Check if the URL is reachable and the response status is OK
            return urlConnection.getContentType() != null &&
                   urlConnection.getContentLengthLong() >= 0 &&
                   HttpStatus.OK.value() == restTemplate.headForHeaders(url).getStatusCodeValue();
        } catch (MalformedURLException e) {
            // Log the error (not shown here)
# 扩展功能模块
            return false;
        } catch (IOException e) {
            // Log the error (not shown here)
            return false;
        } catch (RestClientException e) {
            // Log the error (not shown here)
            return false;
        }
    }
}
