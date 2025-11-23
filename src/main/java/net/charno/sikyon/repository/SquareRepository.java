package net.charno.sikyon.repository;

import net.charno.sikyon.model.SquareDensity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for accessing square and tract reference data.
 */
@Repository
public class SquareRepository {

    private static final Logger logger = LoggerFactory.getLogger(SquareRepository.class);

    private final DatabaseConnectionManager connectionManager;

    public SquareRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Updates square and tract IDs with normalized values.
     */
    public int updateSquareIds(String tableName) {
        int updatedCount = 0;

        try (Connection conn = connectionManager.getAccessWorkingConnection()) {
            conn.setAutoCommit(false);

            // Get all records that need updating
            String selectQuery = String.format("SELECT ID, SquareID, TractID FROM %s", tableName);
            List<IdUpdate> updates = new ArrayList<>();

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectQuery)) {

                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String squareId = rs.getString("SquareID");
                    String tractId = rs.getString("TractID");

                    String normalizedSquareId = normalizeId(squareId);
                    String normalizedTractId = normalizeId(tractId);

                    if (!squareId.equals(normalizedSquareId) || !tractId.equals(normalizedTractId)) {
                        updates.add(new IdUpdate(id, normalizedSquareId, normalizedTractId));
                    }
                }
            }

            // Update records
            String updateQuery = String.format(
                "UPDATE %s SET SquareID = ?, TractID = ? WHERE ID = ?", tableName);

            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                for (IdUpdate update : updates) {
                    pstmt.setString(1, update.squareId);
                    pstmt.setString(2, update.tractId);
                    pstmt.setInt(3, update.id);
                    pstmt.addBatch();
                }

                int[] results = pstmt.executeBatch();
                updatedCount = results.length;
            }

            conn.commit();
            logger.info("Updated {} records in table {}", updatedCount, tableName);

        } catch (SQLException e) {
            logger.error("Error updating square IDs in table {}", tableName, e);
            throw new RuntimeException("Failed to update square IDs", e);
        }

        return updatedCount;
    }

    /**
     * Retrieves square density information.
     */
    public List<SquareDensity> findSquareDensities() {
        List<SquareDensity> densities = new ArrayList<>();
        String query = """
            SELECT SquareID, Area, PotteryCount, RoofTileCount, VisibilityFactor
            FROM Squares
            WHERE Area > 0
            """;

        try (Connection conn = connectionManager.getAccessReferenceConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String squareId = rs.getString("SquareID");
                double area = rs.getDouble("Area");
                int potteryCount = rs.getInt("PotteryCount");
                int roofTileCount = rs.getInt("RoofTileCount");
                double visibilityFactor = rs.getDouble("VisibilityFactor");

                densities.add(SquareDensity.calculate(
                    squareId, area, potteryCount, roofTileCount, visibilityFactor));
            }

            logger.info("Retrieved density data for {} squares", densities.size());

        } catch (SQLException e) {
            logger.error("Error retrieving square densities", e);
            throw new RuntimeException("Failed to retrieve square densities", e);
        }

        return densities;
    }

    /**
     * Normalizes a square or tract ID.
     */
    private String normalizeId(String id) {
        if (id == null || id.isBlank()) {
            return id;
        }

        return id.trim()
            .replaceAll("\\s+", "")
            .replaceAll("([A-Z])0+([0-9])", "$1$2")
            .replaceAll("PO", "P")
            .replaceAll("TO", "T")
            .replaceAll("R$", "");
    }

    private record IdUpdate(int id, String squareId, String tractId) {}
}
