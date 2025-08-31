// 代码生成时间: 2025-08-31 16:33:08
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
# 扩展功能模块
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
# NOTE: 重要实现细节
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
# NOTE: 重要实现细节
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
# NOTE: 重要实现细节
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.demo.repository.UserRepository;

@Service
public class UserAuthenticationService implements UserDetailsService, AuthenticationProvider {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中获取用户
        com.example.demo.model.User user = userRepository.findByUsername(username).orElseThrow("User not found");
        // 将用户信息封装成Spring Security的用户信息对象
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
# 改进用户体验
                .build();
    }
# 增强安全性
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        UserDetails user = loadUserByUsername(username);
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
# 添加错误处理
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
# 扩展功能模块
        return new BCryptPasswordEncoder();
    }
    
    @Configuration
    @EnableWebSecurity
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private UserAuthenticationService userAuthenticationService;
        
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(userAuthenticationService);
# 添加错误处理
        }
        
        @Override
# 增强安全性
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout();
        }
    }
}
# NOTE: 重要实现细节