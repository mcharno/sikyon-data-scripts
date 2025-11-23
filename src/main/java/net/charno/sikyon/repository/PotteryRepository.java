package net.charno.sikyon.repository;

import net.charno.sikyon.model.HellenisticPotteryRecord;
import net.charno.sikyon.model.PotteryRecord;
import net.charno.sikyon.model.RomanPotteryRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

/**
 * Repository for accessing pottery data from MS Access databases.
 */
@Repository
public class PotteryRepository {

    private static final Logger logger = LoggerFactory.getLogger(PotteryRepository.class);

    private final DatabaseConnectionManager connectionManager;

    public PotteryRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Retrieves all Roman pottery records from the main database.
     */
    public List<RomanPotteryRecord> findAllRomanPottery() {
        List<RomanPotteryRecord> records = new ArrayList<>();
        String query = "SELECT * FROM RomanPottery";

        try (Connection conn = connectionManager.getAccessMainConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                records.add(mapRomanPotteryRecord(rs));
            }
            logger.info("Retrieved {} Roman pottery records", records.size());

        } catch (SQLException e) {
            logger.error("Error retrieving Roman pottery records", e);
            throw new RuntimeException("Failed to retrieve Roman pottery records", e);
        }

        return records;
    }

    /**
     * Retrieves all Hellenistic pottery records from the main database.
     */
    public List<HellenisticPotteryRecord> findAllHellenisticPottery() {
        List<HellenisticPotteryRecord> records = new ArrayList<>();
        String query = "SELECT * FROM HellenisticPottery";

        try (Connection conn = connectionManager.getAccessMainConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                records.add(mapHellenisticPotteryRecord(rs));
            }
            logger.info("Retrieved {} Hellenistic pottery records", records.size());

        } catch (SQLException e) {
            logger.error("Error retrieving Hellenistic pottery records", e);
            throw new RuntimeException("Failed to retrieve Hellenistic pottery records", e);
        }

        return records;
    }

    /**
     * Retrieves pottery counts grouped by square and period.
     */
    public Map<String, Map<String, Integer>> getPotteryCountsBySquareAndPeriod() {
        Map<String, Map<String, Integer>> counts = new HashMap<>();
        String query = """
            SELECT SquareID, Period, COUNT(*) as Count
            FROM Pottery
            GROUP BY SquareID, Period
            """;

        try (Connection conn = connectionManager.getAccessMainConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String squareId = rs.getString("SquareID");
                String period = rs.getString("Period");
                int count = rs.getInt("Count");

                counts.computeIfAbsent(squareId, k -> new HashMap<>())
                      .put(period, count);
            }
            logger.info("Retrieved pottery counts for {} squares", counts.size());

        } catch (SQLException e) {
            logger.error("Error retrieving pottery counts", e);
            throw new RuntimeException("Failed to retrieve pottery counts", e);
        }

        return counts;
    }

    private RomanPotteryRecord mapRomanPotteryRecord(ResultSet rs) throws SQLException {
        Map<String, Double> periodPercentages = new HashMap<>();
        Map<String, Integer> vesselParts = new HashMap<>();
        Map<String, Integer> functions = new HashMap<>();
        Map<String, String> decorativeTechniques = new HashMap<>();

        // Map period percentages
        periodPercentages.put("EarlyRoman", getDoubleOrZero(rs, "EarlyRomanPercent"));
        periodPercentages.put("MidRoman", getDoubleOrZero(rs, "MidRomanPercent"));
        periodPercentages.put("LateRoman", getDoubleOrZero(rs, "LateRomanPercent"));

        // Map vessel parts
        vesselParts.put("Rim", getIntOrZero(rs, "Rim"));
        vesselParts.put("Base", getIntOrZero(rs, "Base"));
        vesselParts.put("Handle", getIntOrZero(rs, "Handle"));

        // Map functions
        functions.put("Cooking", getIntOrZero(rs, "Cooking"));
        functions.put("Storage", getIntOrZero(rs, "Storage"));
        functions.put("Transport", getIntOrZero(rs, "Transport"));

        return new RomanPotteryRecord(
            rs.getString("SquareID"),
            periodPercentages,
            vesselParts,
            functions,
            decorativeTechniques,
            rs.getString("Notes")
        );
    }

    private HellenisticPotteryRecord mapHellenisticPotteryRecord(ResultSet rs) throws SQLException {
        return new HellenisticPotteryRecord(
            rs.getString("SquareID"),
            getIntOrZero(rs, "Quantity"),
            rs.getString("Class"),
            rs.getString("Date"),
            rs.getString("Fabric"),
            rs.getString("Shape"),
            rs.getString("Type"),
            rs.getString("VesselPortion"),
            rs.getString("Century"),
            rs.getBoolean("Hellenistic"),
            rs.getBoolean("Roman"),
            rs.getBoolean("IndexSherd"),
            rs.getString("Notes")
        );
    }

    private double getDoubleOrZero(ResultSet rs, String columnName) {
        try {
            return rs.getDouble(columnName);
        } catch (SQLException e) {
            return 0.0;
        }
    }

    private int getIntOrZero(ResultSet rs, String columnName) {
        try {
            return rs.getInt(columnName);
        } catch (SQLException e) {
            return 0;
        }
    }
}
