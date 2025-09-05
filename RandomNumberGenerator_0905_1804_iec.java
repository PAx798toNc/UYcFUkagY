// 代码生成时间: 2025-09-05 18:04:55
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Random;

// RandomNumberGenerator 是一个 Spring Boot 组件，用于生成随机数
@RestController
public class RandomNumberGenerator {

    // 定义一个请求映射方法，用于生成指定范围内的随机数
    @GetMapping("/generate")
    public ResponseEntity<Integer> generateRandomNumber(
            @RequestParam(name = "min", defaultValue = "0") int min,
            @RequestParam(name = "max", defaultValue = "100") int max) {

        // 错误处理：检查参数是否有效
        if (min > max) {
            return ResponseEntity.badRequest().body(null);
        }

        // 生成随机数
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;

        // 返回成功响应和生成的随机数
        return ResponseEntity.ok(randomNumber);
    }
}
