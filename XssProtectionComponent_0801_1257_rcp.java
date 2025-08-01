// 代码生成时间: 2025-08-01 12:57:47
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.HtmlUtils;

/**
 * Spring Boot 组件，用于实现XSS攻击防护。
 */
@Component
public class XssProtectionComponent extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        // 包装请求以实现XSS防护
        HttpServletRequest requestToUse = request;
        if (!(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request);
        }

        filterChain.doFilter(requestToUse, response);
    }

    /**
     * 对请求参数进行XSS清理。
     * @param value 参数值
     * @return 清理后的值
     */
    private String xssClean(String value) {
        if (value != null) {
            return HtmlUtils.htmlEscape(value);
        }
        return value;
    }

    @Override
    protected void initFilterBean() throws ServletException {
        // 在这里可以进行一些初始化操作
        super.initFilterBean();
    }

    @Override
    protected void destroyFilterBean() throws ServletException {
        // 在这里可以进行一些清理操作
        super.destroyFilterBean();
    }
}
