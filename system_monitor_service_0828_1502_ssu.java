// 代码生成时间: 2025-08-28 15:02:44
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SystemMonitorService {

    private static final Logger logger = LoggerFactory.getLogger(SystemMonitorService.class);

    private final Environment env;
    private final RestTemplate restTemplate;

    @Autowired
    public SystemMonitorService(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
    }

    // Scheduled to run periodically
    @Scheduled(fixedRate = 5000)
    public void monitorSystem() {
        try {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            Map<String, Object> systemInfo = new HashMap<>();
            systemInfo.put("systemLoadAverage", osBean.getSystemLoadAverage());
            systemInfo.put("cpuLoad", osBean.getSystemCpuLoad());
            systemInfo.put("freeMemory", osBean.getFreePhysicalMemorySize());
            systemInfo.put("totalMemory", osBean.getTotalPhysicalMemorySize());
            systemInfo.put("memoryUsage", osBean.getCommittedVirtualMemorySize() / 1024.0 / 1024.0 / 1024.0);
            systemInfo.put("ipAddress", getIpAddress());

            logger.info("System Monitor Data: " + systemInfo);

            // Here, you can add code to send this data to a monitoring service or save it to a database

        } catch (Exception e) {
            logger.error("Error occurred while monitoring system: " + e.getMessage(), e);
        }
    }

    // Helper method to get the IP address of the server
    private String getIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("Unable to find IP address of the server: " + e.getMessage(), e);
            return "Unknown";
        }
    }

    // Method to handle errors
    public ResponseEntity<String> handleError(Exception e) {
        logger.error("Error encountered: " + e.getMessage(), e);
        return ResponseEntity
                .badRequest()
                .body("Error: " + e.getMessage());
    }
}
