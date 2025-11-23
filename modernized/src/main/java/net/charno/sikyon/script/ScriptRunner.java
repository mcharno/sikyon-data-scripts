package net.charno.sikyon.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Interactive script runner that displays available scripts when no script is specified.
 */
@Component
@ConditionalOnProperty(name = "script", matchIfMissing = true, havingValue = "none")
public class ScriptRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ScriptRunner.class);

    @Override
    public void run(String... args) {
        logger.info("=".repeat(70));
        logger.info(" SIKYON DATA PROCESSING SCRIPTS - MODERN VERSION 2.0");
        logger.info("=".repeat(70));
        logger.info("");
        logger.info("Available Scripts:");
        logger.info("");
        logger.info("  1. db-cleaner          - Normalize Square and Tract IDs");
        logger.info("  2. density-builder     - Calculate pottery and roof tile densities");
        logger.info("  3. pottery-hellenistic - Export Hellenistic pottery data to CSV");
        logger.info("  4. pottery-roman       - Clean and export Roman pottery data");
        logger.info("  5. pottery-builder     - Build pottery tables for GIS integration");
        logger.info("  6. main-database       - Generate main database with percentages");
        logger.info("");
        logger.info("Usage:");
        logger.info("  mvn spring-boot:run -Dspring-boot.run.arguments=--script=<script-name>");
        logger.info("");
        logger.info("Example:");
        logger.info("  mvn spring-boot:run -Dspring-boot.run.arguments=--script=db-cleaner");
        logger.info("");
        logger.info("Configuration:");
        logger.info("  Database paths and settings can be configured in:");
        logger.info("  - src/main/resources/application.yml");
        logger.info("  - Environment variables (DB_ACCESS_MAIN, DB_ORACLE_URL, etc.)");
        logger.info("");
        logger.info("=".repeat(70));
    }
}
