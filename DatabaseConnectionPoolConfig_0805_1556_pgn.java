// 代码生成时间: 2025-08-05 15:56:40
package com.example.demo.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 配置数据库连接池
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConnectionPoolConfig {

    /**
     * 创建HikariCP连接池
     *
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/your_database");
        config.setUsername("your_username");
        config.setPassword("your_password");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(2000000);
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    /**
     * 创建事务管理器
     *
     * @param dataSource 数据源
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 错误处理方法
     *
     * @param throwable 异常
     * @return 错误信息
     */
    public String handleError(Throwable throwable) {
        // 这里可以添加错误日志记录，例如使用Logback
        // Log.error("Database error: ", throwable);
        return "Error occurred: " + throwable.getMessage();
    }
}
