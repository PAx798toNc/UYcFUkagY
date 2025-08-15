// 代码生成时间: 2025-08-16 00:57:31
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class DatabasePoolManager {

    /**
     * 创建数据库连接池
     * 使用DBCP2作为连接池工具
     * @return DataSource实例
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        try {
            // 使用DataSourceBuilder简化配置
            DataSource dataSource = DataSourceBuilder.create()
                .type(BasicDataSource.class)
                .build();
            return dataSource;
        } catch (Exception e) {
            // 错误处理
            throw new RuntimeException("Failed to create DataSource", e);
        }
    }

    // 其他数据库连接池配置可以在这里添加

}