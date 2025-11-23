package net.charno.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.charno.db.DBConnectionHandler;

public class Archspatial extends DBConnectionHandler {

    private static final String USER = "";
    private static final String PW = "";
    private static final String URL = "jdbc:oracle:thin:@maera.york.ac.uk:1521:ADS06";

    private static final String SELECT_TRACTS_SQL = "select XTRACTID_ from SSP_TRACTS_SPATIAL";
    private static final String SELECT_SQUARES_SQL = "select SQUARE_I_1 from SSP_SQUARES_SPATIAL";
    private static final String UPDATE_TRACTS_SQL = "update SSP_TRACTS_SPATIAL set DISPLAY = ? where XTRACTID_= ?";
    private static final String UPDATE_SQUARES_SQL = "update SSP_SQUARES_SPATIAL set DISPLAY = ? where SQUARE_I_1 = ?";

    private static final String UPDATE_SQUAREID_SQL = "update SSP_SQUARES_SPATIAL set SQUAREID = ? where SQUARE_I_1 = ?";

    private static final String UPDATE_TRACTID_SQL = "update SSP_TRACTS_SPATIAL set TRACTID = ? where XTRACTID_ = ?";

    Statement selectTracts, selectSqaures;
    PreparedStatement updateTracts, updateSquares;
    ResultSet tractRS, squareRS;

    PreparedStatement updateSquareIds;

    PreparedStatement updateTractIds;

    public Archspatial() {
        super(USER, PW, URL, DBConnectionHandler.ORACLE);
    }

    @Override
    public void process() {
        throw new UnsupportedOperationException("Doesn't do anything, try one of its other methods!");
    }

    public static void main(String[] args) {
        Archspatial archspatial = new Archspatial();
        try {
            archspatial.connect();
            archspatial.setAuto(true);
            archspatial.cleanIdsAndDisplay();
        } finally {
            archspatial.disconnect();
        }
    }

    public void updateSquareIds(String legacyID, int id) {
        try {
            updateSquareIds = getConnection().prepareStatement(UPDATE_SQUAREID_SQL);
            updateSquareIds.setInt(1, id);
            updateSquareIds.setString(2, legacyID);
//            updateSquareIds.executeUpdate();
            System.out.println(id + " " + legacyID);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                updateSquareIds.close();
            } catch (SQLException ex) {}
        }
    }

    public void updateTractIds(String legacyID, int id) {
        try {
            updateTractIds = getConnection().prepareStatement(UPDATE_TRACTID_SQL);
            updateTractIds.setInt(1, id);
            updateTractIds.setString(2, legacyID);
//            updateTractIds.executeUpdate();
            System.out.println(id + " " + legacyID);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                updateTractIds.close();
            } catch (SQLException ex) {}
        }
    }

    public void cleanIdsAndDisplay() {
        // clean the tract and square ids
        try {
            selectTracts = getConnection().createStatement();
            tractRS = selectTracts.executeQuery(SELECT_TRACTS_SQL);

            // prepared statements
            updateTracts = getConnection().prepareStatement(UPDATE_TRACTS_SQL);

            while (tractRS.next()) {
                String tractId = tractRS.getString("XTRACTID_");
                String _tractId = tractId;

                if (tractId.contains("P0") || tractId.contains("T0")) {
                    _tractId = tractId.replace("P0", "P").replace("T0", "T");
                }

                System.out.println("Setting Display = " + _tractId + " where " + tractId + "...");
                updateTracts.clearParameters();
                updateTracts.setString(1, _tractId);
                updateTracts.setString(2, tractId);
                updateTracts.executeUpdate();
            }

            selectSqaures = getConnection().createStatement();
            squareRS = selectSqaures.executeQuery(SELECT_SQUARES_SQL);

            // prepared statement
            updateSquares = getConnection().prepareStatement(UPDATE_SQUARES_SQL);

            while (squareRS.next()) {
                String squareId = squareRS.getString("SQUARE_I_1");
                String _squareId = squareId;

                if (squareId.contains("P0") || squareId.contains("T0")) {
                    _squareId = squareId.replace("P0", "P").replace("T0", "T");
                }

                System.out.println("Setting Display = " + _squareId + " where " + squareId + "...");
                updateSquares.clearParameters();
                updateSquares.setString(1, _squareId);
                updateSquares.setString(2, squareId);
                updateSquares.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                tractRS.close();
            } catch (SQLException ex) {}
            try {
                squareRS.close();
            } catch (SQLException ex) {}
            try {
                selectTracts.close();
            } catch (SQLException ex) {}
            try {
                selectSqaures.close();
            } catch (SQLException ex) {}
            try {
                updateSquares.close();
            } catch (SQLException ex) {}
            try {
                updateTracts.close();
            } catch (SQLException ex) {}
        }
    }

}
