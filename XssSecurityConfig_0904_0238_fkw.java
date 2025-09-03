// 代码生成时间: 2025-09-04 02:38:23
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class XssSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 其他安全配置
            .and()
            .addFilterBefore(xssFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // 定义XSS过滤器
    @Bean
    public OncePerRequestFilter xssFilter() {
        XssFilter filter = new XssFilter();
        return filter;
    }

    public class XssFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {

            // 设置请求和响应的编码
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");

            // 检查请求参数
            String parameter = request.getParameter("");
            if (parameter != null) {
                // 过滤XSS攻击
                parameter = parameter.replaceAll("\<script.*?\</script\>", "").replaceAll("<.*?script.*?>.*?<\/script>.*?", "");
            }

            // 继续过滤链
            filterChain.doFilter(request, response);
        }
    }
}

// XSS过滤器中的错误处理
class XssException extends RuntimeException {
    public XssException(String message) {
        super(message);
    }
}
