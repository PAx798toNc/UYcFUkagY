// 代码生成时间: 2025-08-14 06:09:40
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
# TODO: 优化性能
import org.springframework.web.bind.annotation.RestController;
# 优化算法效率
import org.springframework.http.ResponseEntity;

/**
 * RandomNumberGenerator - Spring Boot component to generate random numbers.
 */
@Component
@RestController
public class RandomNumberGenerator {

    /**
     * Generates a random number between two specified bounds.
     *
# NOTE: 重要实现细节
     * @param min The lower bound (inclusive) of the range.
     * @param max The upper bound (inclusive) of the range.
     * @return ResponseEntity A response entity containing the generated random number.
     */
    @GetMapping(