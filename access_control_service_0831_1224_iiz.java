// 代码生成时间: 2025-08-31 12:24:33
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class AccessControlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessControlServiceApplication.class, args);
    }

    @RestController
    public class SecuredController {

        @GetMapping("/public")
        public String publicEndpoint() {
            return "Public endpoint accessible by all users";
        }

        @GetMapping("/private")
        @PreAuthorize("hasRole('USER')") // Only users with the 'USER' role can access this endpoint
        public String privateEndpoint() {
            return "Private endpoint accessible by users with the 'USER' role";
        }

        @GetMapping("/admin")
        @PreAuthorize("hasRole('ADMIN')") // Only users with the 'ADMIN' role can access this endpoint
        public String adminEndpoint() {
            return "Admin endpoint accessible by users with the 'ADMIN' role";
        }
    }

    public static class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/public").permitAll() // Public endpoint
                    .antMatchers("/private").hasRole("USER") // Private endpoint for users
                    .antMatchers("/admin").hasRole("ADMIN") // Admin endpoint for admins
                    .anyRequest().authenticated() // All other requests need authentication
                    .and()
                .httpBasic() // Enable HTTP Basic authentication
                .formLogin() // Enable form login
                .and()
                .logout() // Enable logout
                .invalidateHttpSession(true) // Invalidate session on logout
                .deleteCookies("JSESSIONID"); // Delete JSESSIONID cookie on logout
        }
    }
}
