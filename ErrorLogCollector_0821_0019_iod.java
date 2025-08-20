// 代码生成时间: 2025-08-21 00:19:26
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 错误日志收集器组件，用于统一处理和记录应用中的错误日志
 */
@Component
@ControllerAdvice
public class ErrorLogCollector {

    private static final Logger logger = LoggerFactory.getLogger(ErrorLogCollector.class);

    /**
     * 处理所有方法参数验证异常
     *
     * @param ex 异常对象
     * @return ResponseEntity 响应实体
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));

        logger.error("Validation error occurred: {}", ex.getMessage());

        return new ResponseEntity<>(Collections.singletonMap("error", "Method argument not valid"), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理所有未捕获异常
     *
     * @param ex 异常对象
     * @return ResponseEntity 响应实体
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);

        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
