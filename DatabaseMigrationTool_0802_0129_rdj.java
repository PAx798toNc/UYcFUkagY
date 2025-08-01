// 代码生成时间: 2025-08-02 01:29:09
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatabaseMigrationTool {

    private final DataSource dataSource;

    // Constructor injection of DataSource
    public DatabaseMigrationTool(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Bean for running database migrations on startup
    @Bean
    @Order(1) // Ensure this runs before other @Bean methods
    public ApplicationRunner migrationsRunner() {
        return args -> {
            try {
                // Create a DatabasePopulator to run SQL scripts
                DatabasePopulator populator = new ResourceDatabasePopulator(
                    new ClassPathResource("db/migration/V1__init_db.sql")
                );

                // Run the SQL scripts on the given DataSource
                DatabasePopulatorUtils.execute(populator, dataSource);

                // Additional migrations can be added here by adding more SQL scripts

            } catch (IOException | SQLException e) {
                // Log and handle the error appropriately
                e.printStackTrace();
            }
        };
    }

    // Method to add more migrations as needed
    public void addMigration(String scriptLocation) throws IOException, SQLException {
        DatabasePopulator populator = new ResourceDatabasePopulator(
            new ClassPathResource(scriptLocation)
        );
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}
