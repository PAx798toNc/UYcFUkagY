// 代码生成时间: 2025-09-01 10:04:00
package com.example.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class XssProtectionComponent extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 设置请求头以防止XSS攻击
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        
        // 继续处理其他过滤器
        filterChain.doFilter(request, response);
    }

    // 错误处理方法
    @Override
    public void afterPropertiesSet() {
        // 检查是否配置了错误处理路径
        if (getServletContext().getErrorPages().length == 0) {
            registerErrorPage("/error");
        }
    }

    // 注册错误页面
    private void registerErrorPage(String errorPageUrl) {
        // 这里可以根据实际情况配置错误页面
        // 例如: 可以使用Spring MVC的@ControllerAdvice来全局处理错误
    }
}
