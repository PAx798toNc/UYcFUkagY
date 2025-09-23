// 代码生成时间: 2025-09-23 23:09:04
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Component
public class AccessControlComponent {

    // 使用@Secured注解控制访问权限，只有具有特定角色的用户才能访问
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String adminAccess() {
        return "Access granted to admin";
    }

    // 使用@PreAuthorize注解，可以基于方法的参数来动态控制访问权限
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dynamic")
    public String dynamicAccess() {
        return "Access granted based on role";
    }

    // 异常处理器，处理访问被拒绝的情况
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Access Denied");
        response.put("message", "You do not have the required permissions to access this resource");
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(response);
    }

    // 异常处理器，处理认证异常
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Authentication Failed");
        response.put("message", "The username or password provided is incorrect");
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(response);
    }
}
