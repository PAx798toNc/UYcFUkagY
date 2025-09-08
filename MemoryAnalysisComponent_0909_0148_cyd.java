// 代码生成时间: 2025-09-09 01:48:29
import org.springframework.stereotype.Component;
import org.springframework.boot.system.ApplicationHome;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.management.JMX;

@Component
public class MemoryAnalysisComponent {

    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    public void analyzeMemoryUsage() throws IOException {
        // 获取当前Spring Boot应用程序的目录
        ApplicationHome home = new ApplicationHome();
        String applicationDir = home.getDir().getAbsolutePath();

        // 计算应用程序目录下的文件和文件夹占用的内存
        long usedMemory = calculateDirectorySize(applicationDir);

        // 输出内存使用情况
        System.out.println("Used Memory: " + usedMemory + " bytes");
    }

    // 计算指定目录下的文件和文件夹占用的内存
    private long calculateDirectorySize(String path) throws IOException {
        long length = 0;
        // 检查路径是否存在
        if (Files.exists(Paths.get(path))) {
            // 遍历目录下的所有文件和文件夹
            List<File> files = org.apache.commons.io.FileUtils.listFilesAndDirs(new File(path), null, true);
            for (File file : files) {
                if (file.isFile()) {
                    // 文件大小累加
                    length += file.length();
                }
            }
        }
        return length;
    }

    // 错误处理方法
    public void handleError(Exception e) {
        // 日志记录错误信息（假设使用一个日志框架，例如SLF4J）
        // logger.error("Error occurred during memory analysis", e);

        // 这里可以根据需要添加更多的错误处理逻辑
        System.err.println("An error occurred: " + e.getMessage());
    }

    // 可以添加一个公开方法，以便外部调用
    public void performMemoryAnalysis() {
        try {
            analyzeMemoryUsage();
        } catch (IOException e) {
            handleError(e);
        }
    }
}
