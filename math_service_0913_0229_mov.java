// 代码生成时间: 2025-09-13 02:29:03
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Service
@RestController
public class MathService {

    private static final String ERROR_MESSAGE = "Invalid input. Please provide valid numbers.";

    @GetMapping("/add")
    public ResponseEntity<Map<String, Double>> add(@RequestParam Double number1, @RequestParam Double number2) {
        Map<String, Double> result = new HashMap<>();
        result.put("result", number1 + number2);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/subtract")
    public ResponseEntity<Map<String, Double>> subtract(@RequestParam Double number1, @RequestParam Double number2) {
        Map<String, Double> result = new HashMap<>();
        result.put("result", number1 - number2);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/multiply")
    public ResponseEntity<Map<String, Double>> multiply(@RequestParam Double number1, @RequestParam Double number2) {
        Map<String, Double> result = new HashMap<>();
        result.put("result", number1 * number2);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/divide")
    public ResponseEntity<Map<String, Double>> divide(@RequestParam Double number1, @RequestParam Double number2) {
        Map<String, Double> result = new HashMap<>();
        if (number2 == 0) {
            result.put("error", "Division by zero is not allowed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        result.put("result", number1 / number2);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_MESSAGE);
    }

    // Additional operations can be added here
}