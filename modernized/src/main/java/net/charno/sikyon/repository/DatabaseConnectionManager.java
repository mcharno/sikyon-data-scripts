package net.charno.sikyon.repository;

import net.charno.sikyon.config.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manages database connections for MS Access and Oracle databases.
 * Provides cross-platform compatible connection management.
 */
@Component
public class DatabaseConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionManager.class);

    private final DatabaseProperties databaseProperties;

    public DatabaseConnectionManager(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    /**
     * Creates a connection to the main MS Access database using UCanAccess.
     * UCanAccess is cross-platform and doesn't require MS Access to be installed.
     */
    public Connection getAccessMainConnection() throws SQLException {
        String path = databaseProperties.access().main();
        logger.debug("Connecting to Access database: {}", path);

        String url = "jdbc:ucanaccess://" + path;
        Properties props = new Properties();
        props.setProperty("immediatelyReleaseResources", "true");

        return DriverManager.getConnection(url, props);
    }

    /**
     * Creates a connection to the working MS Access database.
     */
    public Connection getAccessWorkingConnection() throws SQLException {
        String path = databaseProperties.access().working();
        logger.debug("Connecting to working Access database: {}", path);

        String url = "jdbc:ucanaccess://" + path;
        Properties props = new Properties();
        props.setProperty("immediatelyReleaseResources", "true");

        return DriverManager.getConnection(url, props);
    }

    /**
     * Creates a connection to the reference MS Access database.
     */
    public Connection getAccessReferenceConnection() throws SQLException {
        String path = databaseProperties.access().reference();
        logger.debug("Connecting to reference Access database: {}", path);

        String url = "jdbc:ucanaccess://" + path;
        Properties props = new Properties();
        props.setProperty("immediatelyReleaseResources", "true");

        return DriverManager.getConnection(url, props);
    }

    /**
     * Creates a connection to the primary Oracle database.
     */
    public Connection getOraclePrimaryConnection() throws SQLException {
        var config = databaseProperties.oracle().primary();
        logger.debug("Connecting to primary Oracle database: {}", config.url());

        return DriverManager.getConnection(
            config.url(),
            config.username(),
            config.password()
        );
    }

    /**
     * Creates a connection to the backup Oracle database.
     */
    public Connection getOracleBackupConnection() throws SQLException {
        var config = databaseProperties.oracle().backup();
        logger.debug("Connecting to backup Oracle database: {}", config.url());

        return DriverManager.getConnection(
            config.url(),
            config.username(),
            config.password()
        );
    }

    /**
     * Safely closes a database connection, suppressing any exceptions.
     */
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    logger.debug("Connection closed successfully");
                }
            } catch (SQLException e) {
                logger.warn("Error closing connection", e);
            }
        }
    }
}
