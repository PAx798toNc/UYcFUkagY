// 代码生成时间: 2025-08-11 23:27:23
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.exception.LiquibaseException;
import liquibase.Contexts;
import liquibase.LabelExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DatabaseMigrationTool implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationTool.class);

    private final DatabaseConfig databaseConfig;

    @Autowired
    public DatabaseMigrationTool(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @PostConstruct
    public void init() {
        try {
            migrateDatabase();
        } catch (Exception e) {
            logger.error("Database migration failed", e);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // This method is intentionally left blank.
    }

    private void migrateDatabase() throws LiquibaseException, SQLException {
        // Establish a connection to the database.
        Connection connection = databaseConfig.createConnection();
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
        Liquibase liquibase = new Liquibase("master.xml", new ClassLoaderResourceAccessor(), database);

        // Perform the migration.
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @Configuration
    public static class DatabaseConfig {

        private final String url;
        private final String username;
        private final String password;

        public DatabaseConfig(String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public Connection createConnection() throws SQLException {
            // Create and return a database connection.
            return DriverManager.getConnection(url, username, password);
        }
    }
}
