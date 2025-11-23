package net.charno.sikyon.script;

import net.charno.sikyon.service.DataCleaningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Database Cleaner Script
 *
 * Modernized version of the original DBCleaner.
 * Normalizes Square and Tract IDs across all database tables.
 *
 * To run: mvn spring-boot:run -Dspring-boot.run.arguments=--script=db-cleaner
 */
@Component
@ConditionalOnProperty(name = "script", havingValue = "db-cleaner")
public class DatabaseCleanerScript implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseCleanerScript.class);

    private final DataCleaningService dataCleaningService;

    public DatabaseCleanerScript(DataCleaningService dataCleaningService) {
        this.dataCleaningService = dataCleaningService;
    }

    @Override
    public void run(String... args) {
        logger.info("=".repeat(60));
        logger.info("DATABASE CLEANER SCRIPT");
        logger.info("Normalizing Square and Tract IDs");
        logger.info("=".repeat(60));

        try {
            dataCleaningService.normalizeSquareAndTractIds();
            logger.info("Database cleaning completed successfully");
        } catch (Exception e) {
            logger.error("Error during database cleaning", e);
            System.exit(1);
        }
    }
}
