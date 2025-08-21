// 代码生成时间: 2025-08-21 20:46:14
package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Order(1) // 指定组件的优先级，可根据实际情况调整
public class ProcessManager {
    
    @Value("${process.thread-pool.size:4}")
    private int threadPoolSize; // 从配置文件中获取线程池大小，默认为4

    private final ExecutorService executorService; // 线程池

    // 构造函数，初始化线程池
    public ProcessManager() {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    // 启动进程
    public void startProcess(String command) {
        executorService.submit(() -> {
            try {
                Process process = Runtime.getRuntime().exec(command);
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    throw new RuntimeException("Process exited with non-zero exit code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt(); // 设置中断标志
                throw new RuntimeException("Error starting process", e);
            }
        });
    }

    // 停止所有进程
    public void stopAllProcesses() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow(); // 超时后尝试立即停止所有任务
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow(); // 重新设置中断标志
            Thread.currentThread().interrupt();
        }
    }

    // 错误处理
    @ConditionalOnProperty(name = "process.error-handling.enabled", havingValue = "true")
    public void handleErrors() {
        // 这里可以根据需要实现错误处理逻辑
        // 例如，记录日志、发送通知等
    }
}
