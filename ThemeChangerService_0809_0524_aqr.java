// 代码生成时间: 2025-08-09 05:24:29
// Spring Boot Component for Theme Switching
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import java.util.Map;
import java.util.HashMap;

/**
 * ThemeChangerService class handles theme switching functionality.
 */
@Service
public class ThemeChangerService {

    // Autowire the Environment to access application properties
    @Autowired
    private Environment environment;

    // Method to change theme
    public String changeTheme(String themeName) {
        try {
            // Validate if the theme is supported
            String[] supportedThemes = environment.getProperty("app.supportedThemes").split(",");
            for (String theme : supportedThemes) {
                if (theme.trim().equals(themeName.trim())) {
                    // Set the theme in application properties
                    environment.getPropertySources().forEach(ps -> {
                        if (ps instanceof ConfigurableEnvironment) {
                            ((ConfigurableEnvironment) ps).getPropertySources().forEach(ps1 -> {
                                if (ps1.contains(MutablePropertySources.class)) {
                                    ((MutablePropertySources) ps1).addLast(new MapPropertySource("theme", new HashMap<String, Object>() {{
                                        put("app.theme", themeName);
                                    }}));
                                }
                            });
                        }
                    });
                    return "Theme changed to: " + themeName;
                }
            }
            throw new IllegalArgumentException("Theme not supported");
        } catch (Exception e) {
            return "Error changing theme: " + e.getMessage();
        }
    }

    // Method to get current theme
    public String getCurrentTheme() {
        return environment.getProperty("app.theme", "default");
    }

    // Exception handler for theme change
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleThemeChangeError(IllegalArgumentException e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
