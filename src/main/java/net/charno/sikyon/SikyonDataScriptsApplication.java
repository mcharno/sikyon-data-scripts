package net.charno.sikyon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Main application class for the Sikyon Data Processing Scripts.
 *
 * This is a modernized version of the archaeological data processing system
 * originally written in 2008. It processes pottery survey data from the
 * Sikyon Survey Project.
 *
 * @author Matthew Charno
 * @version 2.0.0
 * @since 2025
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class SikyonDataScriptsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SikyonDataScriptsApplication.class, args);
    }
}
