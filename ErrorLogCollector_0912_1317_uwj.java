// 代码生成时间: 2025-09-12 13:17:41
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ControllerAdvice
@Configuration
@ConditionalOnWebApplication
public class ErrorLogCollector implements WebMvcConfigurer {

    // 错误处理方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse handleException(Exception ex) {
        // 记录错误日志
        logError(ex);
        // 返回错误响应对象，包含错误信息和状态码
        return new ErrorResponse("Error occurred", ex.getMessage());
    }

    // 私有方法，用于记录错误日志
    private void logError(Exception ex) {
        // 实际的日志记录代码，例如使用Logback或者SLF4J等日志框架
        // 这里仅作为示例，不包含具体的实现
        System.err.println("Error: " + ex.getMessage());
        ex.printStackTrace();
    }

    // ErrorResponse类，用于封装错误响应
    public static class ErrorResponse {
        private String status;
        private String message;

        public ErrorResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        // Getter和Setter方法
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}