// 代码生成时间: 2025-09-24 18:07:24
package com.example.demo.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

// 使用@SpringBootTest注解来指定Spring Boot测试环境
// 使用ActiveProfiles指定测试使用的profile
// 使用@RunWith来指定测试框架
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class IntegrationTestService {

    @Autowired
    private MockMvc mockMvc; // 用于模拟HTTP请求

    @Autowired
    private ObjectMapper objectMapper; // 用于处理JSON数据

    // 测试接口响应
    @Test
    public void testGetUser() throws Exception {
        // 模拟GET请求到/user路径
        mockMvc.perform(get("/user"))
            .andExpect(status().isOk()) // 期望状态码为200
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) // 期望响应类型为JSON
            .andExpect(jsonPath("$.id").exists()) // 检查JSON中的id字段是否存在
            .andExpect(jsonPath("$.name").exists()); // 检查JSON中的name字段是否存在
    }

    // 测试错误处理
    @Test
    public void testErrorHandling() throws Exception {
        // 模拟GET请求到不存在的路径
        mockMvc.perform(get("/nonexistent"))
            .andExpect(status().isNotFound()) // 期望状态码为404
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) // 期望响应类型为JSON
            .andExpect(jsonPath("$.error").value("Not Found")); // 检查JSON中的error字段是否为"Not Found"
    }

    // 其他测试方法可以继续添加...
}
