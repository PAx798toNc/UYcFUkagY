// 代码生成时间: 2025-08-04 05:16:11
package com.example.monitor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class SystemPerformanceMonitor {

    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    public SystemPerformanceMonitor() {
        this.restTemplate = new RestTemplate();
        this.executorService = Executors.newCachedThreadPool();
    }

    /**
     * 获取系统基本信息
     *
     * @return 系统信息的字符串表示
     */
    public String getSystemInfo() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        InetAddress localHost = getLocalHost();
        long uptime = runtimeBean.getUptime();
        String jvmName = runtimeBean.getVmName();
        String jvmVersion = runtimeBean.getVmVersion();
        String uptimeStr = formatUptime(uptime);
        return "System Info: " +
                "
  Host: " + localHost.getHostAddress() +
                "
  JVM Name: " + jvmName +
                "
  JVM Version: " + jvmVersion +
                "
  Uptime: " + uptimeStr;
    }

    /**
     * 获取CPU使用率
     *
     * @return CPU使用率
     */
    public double getCpuLoad() {
        com.sun.management.OperatingSystemMXBean osBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getSystemCpuLoad();
    }

    /**
     * 获取当前线程信息
     *
     * @return 线程信息字符串
     */
    public String getThreadInfo() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        StringBuilder sb = new StringBuilder();
        for (ThreadInfo threadInfo : threadInfos) {
            sb.append(threadInfo).append("
");
        }
        return sb.toString();
    }

    /**
     * 模拟网络请求
     *
     * @param url 请求的URL
     * @return 响应实体
     */
    public ResponseEntity<String> makeNetworkRequest(String url) {
        try {
            return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (Exception e) {
            // 错误处理
            return ResponseEntity.status(500).body("Error making network request");
        }
    }

    /**
     * 格式化系统运行时间
     *
     * @param uptime 系统运行时间（毫秒）
     * @return 格式化后的运行时间字符串
     */
    private String formatUptime(long uptime) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(uptime) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(uptime) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(uptime) % 24;
        long days = TimeUnit.MILLISECONDS.toDays(uptime);
        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
    }

    /**
     * 获取本地主机地址
     *
     * @return 本地主机的InetAddress对象
     */
    private InetAddress getLocalHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // 错误处理
            throw new RuntimeException("Unable to find local host", e);
        }
    }
}
