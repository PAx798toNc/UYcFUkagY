// 代码生成时间: 2025-08-26 11:28:57
package com.yourcompany.yourproject.monitor;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

@Component
@RestController
@RequestMapping("/api/monitor")
public class SystemPerformanceMonitor {

    private final HealthIndicator healthIndicator;

    @Autowired
    public SystemPerformanceMonitor(HealthIndicator healthIndicator) {
        this.healthIndicator = healthIndicator;
    }

    @GetMapping("/performance")
    public ResponseEntity<Map<String, Object>> getSystemPerformance() {
        try {
            Map<String, Object> performanceData = new HashMap<>();
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

            // CPU usage
            double cpuLoad = osBean.getSystemCpuLoad();
            performanceData.put("cpuLoad", cpuLoad);

            // Memory usage
            long usedMemory = osBean.getCommittedVirtualMemorySize() - osBean.getFreePhysicalMemorySize();
            long totalMemory = osBean.getTotalPhysicalMemorySize();
            double memoryUsage = (double) usedMemory / totalMemory;
            performanceData.put("memoryUsage", memoryUsage);

            // Disk usage
            double diskUsage = osBean.getFreeSwapSpaceSize() / osBean.getTotalSwapSpaceSize();
            performanceData.put("diskUsage", diskUsage);

            return ResponseEntity.ok(performanceData);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving system performance data", e);
        }
    }

    @GetMapping("/health")
    public Health getHealth() {
        return healthIndicator.health();
    }
}
