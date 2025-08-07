// 代码生成时间: 2025-08-08 02:53:01
package com.example.demo.component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
# NOTE: 重要实现细节
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
# 扩展功能模块
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiResponseFormatter extends ResponseEntityExceptionHandler {

    private static final String FAILURE_RESPONSE = "failure";
# 添加错误处理
    private static final String SUCCESS_RESPONSE = "success";

    // 通用响应结构
    public static class ApiResponse<T> {
        private String status;
        private T data;
        private Map<String, String> message;

        public ApiResponse(String status, T data, Map<String, String> message) {
            this.status = status;
            this.data = data;
# 优化算法效率
            this.message = message;
        }

        public ApiResponse(String status, T data) {
            this.status = status;
# 扩展功能模块
            this.data = data;
            this.message = new HashMap<>();
        }

        public String getStatus() {
            return status;
        }

        public T getData() {
            return data;
        }
# 扩展功能模块

        public Map<String, String> getMessage() {
            return message;
        }

        public void setStatus(String status) {
            this.status = status;
# 添加错误处理
        }

        public void setData(T data) {
            this.data = data;
        }
# NOTE: 重要实现细节

        public void setMessage(Map<String, String> message) {
            this.message = message;
        }
    }

    // 异常处理
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse<?> handleException(Exception ex) {
        Map<String, String> message = new HashMap<>();
        message.put("error", ex.getMessage());
        return new ApiResponse<>(FAILURE_RESPONSE, null, message);
# NOTE: 重要实现细节
    }
# 添加错误处理

    // 自定义异常处理
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
# TODO: 优化性能
    @ResponseBody
# 扩展功能模块
    public ApiResponse<?> handleCustomException(CustomException ex) {
        Map<String, String> message = new HashMap<>();
        message.put("customError", ex.getMessage());
        return new ApiResponse<>(FAILURE_RESPONSE, null, message);
# NOTE: 重要实现细节
    }

    // 泛型方法用于成功响应
    public <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ApiResponse<T> response = new ApiResponse<>(SUCCESS_RESPONSE, data);
        return ResponseEntity.ok(response);
# FIXME: 处理边界情况
    }

    // 泛型方法用于失败响应
# 优化算法效率
    public <T> ResponseEntity<ApiResponse<T>> failure(String message) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", message);
        ApiResponse<T> response = new ApiResponse<>(FAILURE_RESPONSE, null, errorMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

// 自定义异常类
class CustomException extends RuntimeException {
    public CustomException(String message) {
# 改进用户体验
        super(message);
    }
}