package net.charno.sikyon.script;

import net.charno.sikyon.model.SquareDensity;
import net.charno.sikyon.repository.SquareRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Density Builder Script
 *
 * Modernized version of the original DensityBuilder.
 * Calculates pottery and roof tile density values for each excavation square.
 *
 * To run: mvn spring-boot:run -Dspring-boot.run.arguments=--script=density-builder
 */
@Component
@ConditionalOnProperty(name = "script", havingValue = "density-builder")
public class DensityBuilderScript implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DensityBuilderScript.class);

    private final SquareRepository squareRepository;

    public DensityBuilderScript(SquareRepository squareRepository) {
        this.squareRepository = squareRepository;
    }

    @Override
    public void run(String... args) {
        logger.info("=".repeat(60));
        logger.info("DENSITY BUILDER SCRIPT");
        logger.info("Calculating pottery and roof tile densities");
        logger.info("=".repeat(60));

        try {
            List<SquareDensity> densities = squareRepository.findSquareDensities();

            logger.info("Calculated densities for {} squares", densities.size());

            // Log summary statistics
            double avgPotteryDensity = densities.stream()
                .mapToDouble(SquareDensity::potteryDensity)
                .average()
                .orElse(0.0);

            double avgRoofTileDensity = densities.stream()
                .mapToDouble(SquareDensity::roofTileDensity)
                .average()
                .orElse(0.0);

            logger.info("Average pottery density: {:.2f} sherds/m²", avgPotteryDensity);
            logger.info("Average roof tile density: {:.2f} tiles/m²", avgRoofTileDensity);

            logger.info("Density calculations completed successfully");

        } catch (Exception e) {
            logger.error("Error during density calculation", e);
            System.exit(1);
        }
    }
}
