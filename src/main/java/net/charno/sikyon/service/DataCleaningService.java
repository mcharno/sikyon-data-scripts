package net.charno.sikyon.service;

import net.charno.sikyon.repository.SquareRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for cleaning and normalizing archaeological data.
 */
@Service
public class DataCleaningService {

    private static final Logger logger = LoggerFactory.getLogger(DataCleaningService.class);

    private final SquareRepository squareRepository;

    // Tables that require ID normalization
    private static final List<String> TABLES_TO_CLEAN = List.of(
        "Arch_InSitu",
        "Brick_Tile",
        "Roof_Tiles",
        "Pottery",
        "Special_Finds"
    );

    public DataCleaningService(SquareRepository squareRepository) {
        this.squareRepository = squareRepository;
    }

    /**
     * Normalizes square and tract IDs across all tables.
     * Equivalent to the original DBCleaner script.
     */
    public void normalizeSquareAndTractIds() {
        logger.info("Starting normalization of Square and Tract IDs");

        int totalUpdated = 0;

        for (String tableName : TABLES_TO_CLEAN) {
            try {
                int updated = squareRepository.updateSquareIds(tableName);
                totalUpdated += updated;
                logger.info("Updated {} records in table: {}", updated, tableName);
            } catch (Exception e) {
                logger.error("Error updating table: {}", tableName, e);
            }
        }

        logger.info("Normalization complete. Total records updated: {}", totalUpdated);
    }

    /**
     * Normalizes a single pottery shape string.
     * Converts to lowercase and handles special cases.
     */
    public String normalizeShape(String shape) {
        if (shape == null || shape.isBlank()) {
            return shape;
        }

        return shape.toLowerCase().trim();
    }

    /**
     * Normalizes square identifier with special case handling.
     */
    public String normalizeSquareId(String squareId) {
        if (squareId == null || squareId.isBlank()) {
            return squareId;
        }

        String normalized = squareId.trim()
            .replaceAll("\\s+", "")
            .replaceAll("([A-Z])0+([0-9])", "$1$2")
            .replaceAll("PO", "P")
            .replaceAll("TO", "T");

        // Special case: SP07.04 should be SP07.03
        if ("SP07.04".equals(normalized)) {
            normalized = "SP07.03";
        }

        return normalized;
    }
}
