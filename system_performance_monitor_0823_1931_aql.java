// 代码生成时间: 2025-08-23 19:31:49
package com.example.demo.monitor;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class SystemPerformanceMonitor {

    private final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

    public Health getSystemHealth() {
        try {
            // 获取CPU和内存信息
            double cpuLoad = getCpuLoad();
            long usedMemory = getUsedMemory();

            Map<String, Object> details = new HashMap<>();
            details.put("cpuLoad", cpuLoad);
            details.put("usedMemory", usedMemory);

            // 如果CPU负载超过90%或者内存使用超过90%，则设置健康状态为DOWN
            if (cpuLoad > 90.0 || usedMemory > 90 * 1024 * 1024 * 1024) {
                return Health.down().withDetails(details).build();
            } else {
                return Health.up().withDetails(details).build();
            }
        } catch (Exception e) {
            // 错误处理
            return Health.down().withException(e).build();
        }
    }

    // 获取CPU负载
    private double getCpuLoad() {
        try {
            final ObjectName cpuLoadName = new ObjectName("java.lang:type=OperatingSystem");
            final AttributeList list = mBeanServer.getAttributes(cpuLoadName, new String[] {"SystemCpuLoad"});
            final Attribute attr = (Attribute) list.get(0);
            return (Double) attr.getValue();
        } catch (InstanceNotFoundException | AttributeNotFoundException | MBeanException | ReflectionException e) {
            throw new RuntimeException("Error while fetching CPU load", e);
        }
    }

    // 获取已用内存
    private long getUsedMemory() {
        try {
            final ObjectName memoryName = new ObjectName("java.lang:type=Memory");
            final MemoryMXBean memoryMXBean = ManagementFactory.newPlatformMXBeanProxy(
                    mBeanServer, memoryName.toString(), MemoryMXBean.class);
            return memoryMXBean.getHeapMemoryUsage().getUsed();
        } catch (InstanceNotFoundException | MBeanException | ReflectionException e) {
            throw new RuntimeException("Error while fetching used memory", e);
        }
    }
}
