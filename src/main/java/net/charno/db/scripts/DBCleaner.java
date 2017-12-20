package net.charno.db.scripts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.charno.db.DBConnectionHandler;

public class DBCleaner extends DBConnectionHandler {

    final static String ACCESS_URL = "jdbc:access:////home/mdc502/projects/sikyon/db/sik_db-working.mdb";
    final static String ACCESS_USER = "";
    final static String ACCESS_PASSWORD = "";

    final static String GET_SQUARE_NUMBERS = "select id, legacyID from Squares";
    final static String GET_TRACT_NUMBERS = "select id, legacyID from Tracts";
    final static String GET_TRACT_ID = "select id from Tracts where legacyID = ?";
    final static String UPDATE_SQUARE_TRACT = "update Squares set tract = ? where id = ?";
    final static String UPDATE_SQUARE_TRACT_ID = "update sq set tractID = ? where id = ?";
    final static String UPDATE_SQUARE_ID = "update Squares set square = ? where id = ?";
    final static String UPDATE_SQUARE_TYPE = "update Squares set type = ? where id = ?";
    final static String UPDATE_TRACT_ID = "update Tracts set tract = ? where id = ?";
    final static String UPDATE_TRACT_TYPE = "update Tracts set type = ? where id = ?";
    final static String UPDATE_ARCH_INSITU = "update Arch_InSitu set id = ? where squareID = ?";
    final static String UPDATE_ARCH_NOT_INSITU = "update Arch_Not_InSitu set id = ? where squareID = ?";
    final static String UPDATE_BRICK_TILE = "update Brick_Tile set id = ? where squareID = ?";
    final static String UPDATE_OTHER_FINDS = "update Other_Finds set id = ? where square_id = ?";
    final static String UPDATE_ROOF_TILES = "update Roof_Tiles set id = ? where squareID = ?";
    final static String UPDATE_SQU_COORDS = "update SQU_Coords set id = ? where squareID = ?";
    
    private Statement getSquareNumbers, getTractNumbers;
    private ResultSet squareRS, tractRS, tractIdRS;
    private PreparedStatement getTractIds, updateSquares, updateSquareType, updateSquareTract, updateSquareTractId, updateTractId, updateTractType, updateAIS, updateANIS, updateBT, updateOF, updateRT, updateSQU;

    private int zeroCounter = 0, tractCounter = 0;

    public DBCleaner() {
        super(ACCESS_USER, ACCESS_PASSWORD, ACCESS_URL, DBConnectionHandler.ACCESS);
    }

    public static void main(String[] args) {
        DBCleaner renamer = new DBCleaner();
        try {
            renamer.connect();
            renamer.setAuto(true);
            renamer.process();
        } finally {
            renamer.disconnect();
        }
    }

    @Override
    public void process() {

        try {

            getTractNumbers = getConnection().createStatement();
            tractRS = getTractNumbers.executeQuery(GET_TRACT_NUMBERS);

            updateTractId = getConnection().prepareStatement(UPDATE_TRACT_ID);
            updateTractType = getConnection().prepareStatement(UPDATE_TRACT_TYPE);

            while (tractRS.next()) {
                String tractId = null;
                String type = "";
                int id = tractRS.getInt("id");
                final String originalTractId = tractRS.getString("legacyID");
//                System.out.print(originalTractId + " to... ");

                if (originalTractId.contains("P0") || originalTractId.contains("T0")) {
                    tractId = originalTractId.replace("P0", "P").replace("T0", "T");
                } else {
                    tractId = originalTractId;
                }

                if (tractId.endsWith("R")) {
                    tractId = tractId.substring(0, tractId.length()-1);
                    type = "R";
                }

//                System.out.println(tractId + " " + type);
                updateTractId.clearParameters();
                updateTractId.setString(1, tractId);
                updateTractId.setInt(2, id);
//                updateTractId.executeUpdate();

                if (!"".equals(type)) {
                    updateTractType.clearParameters();
                    updateTractType.setString(1, type);
                    updateTractType.setInt(2, id);
//                    updateTractType.executeUpdate();
                }

            }

            getSquareNumbers = getConnection().createStatement();
            squareRS = getSquareNumbers.executeQuery(GET_SQUARE_NUMBERS);

            getTractIds = getConnection().prepareStatement(GET_TRACT_ID);
            updateSquares = getConnection().prepareStatement(UPDATE_SQUARE_ID);
            updateSquareType = getConnection().prepareStatement(UPDATE_SQUARE_TYPE);
            updateSquareTract = getConnection().prepareStatement(UPDATE_SQUARE_TRACT);
            updateSquareTractId = getConnection().prepareStatement(UPDATE_SQUARE_TRACT_ID);
//            updateAIS = getConnection().prepareStatement(UPDATE_ARCH_INSITU);
//            updateANIS = getConnection().prepareStatement(UPDATE_ARCH_NOT_INSITU);
            updateBT = getConnection().prepareStatement(UPDATE_BRICK_TILE);
            updateOF = getConnection().prepareStatement(UPDATE_OTHER_FINDS);
            updateRT = getConnection().prepareStatement(UPDATE_ROOF_TILES);
            updateSQU = getConnection().prepareStatement(UPDATE_SQU_COORDS);

            while (squareRS.next()) {
                String squareId = null, originalTract = null, tract = null;
                String type = "";
                int id = squareRS.getInt("id");
                final String originalSquareId = squareRS.getString("legacyID");
//                System.out.println(originalSquareId);
                squareId = originalSquareId;

                if (originalSquareId.contains(".")) {
                    originalTract = originalSquareId.substring(0, originalSquareId.indexOf("."));
                } else {
                    originalTract = squareId;
                }
//                System.out.println(tractId);

                if (originalSquareId.endsWith("R")) {
                    squareId = squareId.substring(squareId.indexOf(".")+1, squareId.length()-1);
                    // add yes to resurvey
                    type = "R";
                } else if (originalSquareId.endsWith("T")) {
                    squareId = squareId.substring(squareId.indexOf(".")+1, squareId.length()-1);
                    // add yes to terrace
                    type = "T";
                } else {
                    squareId = squareId.substring(squareId.indexOf(".")+1);
                }
//                System.out.println(squareId);

        // SQUARE ID ZERO REMOVAL
                // find where the tract numbers are
                int tractStart = originalSquareId.indexOf("P") + 1;

                if (tractStart < 0) {
                    tractStart = originalSquareId.indexOf("T") + 1;
                }
                if (tractStart < 0) {
                    continue;
                }

                // replace any zeroes from tract names
                tract = originalTract.replace("P0", "P").replace("T0", "T");


        // TRACT ID POPULATOR
                if ("SP82".equals(originalSquareId)) {
                    squareId = "0";
                } else if ("ST02".equals(originalSquareId) || "ST03".equals(originalSquareId) || "ST04".equals(originalSquareId) || "ST05".equals(originalSquareId) || "ST06".equals(originalSquareId) || "ST08".equals(originalSquareId)) {
                    squareId = "1";
                }

                tract = tract.toUpperCase();
                int tractId = -1;

                getTractIds.clearParameters();
                getTractIds.setString(1, originalTract);
                tractIdRS = getTractIds.executeQuery();
                if (tractIdRS.next()) {
                    tractId = tractIdRS.getInt("id");
                }


                System.out.println(originalTract + " :: " + tract + " :: " + tractId);
//                updateSquareTract.clearParameters();
//                updateSquareTract.setString(1, tract);
//                updateSquareTract.setInt(2, id);
//                updateSquareTract.executeUpdate();

                if (tractId != -1) {
                    updateSquareTractId.clearParameters();
                    updateSquareTractId.setInt(1, tractId);
                    updateSquareTractId.setInt(2, id);
                    updateSquareTractId.executeUpdate();
                }
//
//                updateSquares.clearParameters();
//                updateSquares.setInt(1, Integer.parseInt(squareId));
//                updateSquares.setInt(2, id);
////                updateSquares.executeUpdate();
////
//                if (!"".equals(type)) {
//                    updateSquareType.clearParameters();
//                    updateSquareType.setString(1, type);
//                    updateSquareType.setInt(2, id);
////                    updateSquareType.executeUpdate();
//                }

                // NOT USED!!!
//                updateAIS.clearParameters();
//                updateAIS.setInt(1, id);
//                updateAIS.setString(2, originalSquareId);
//                updateAIS.executeUpdate();
//
//                updateANIS.clearParameters();
//                updateANIS.setInt(1, id);
//                updateANIS.setString(2, originalSquareId);
//                updateANIS.executeUpdate();

//                updateBT.clearParameters();
//                updateBT.setInt(1, id);
//                updateBT.setString(2, originalSquareId);
////                updateBT.executeUpdate();
//
//                updateOF.clearParameters();
//                updateOF.setInt(1, id);
//                updateOF.setString(2, originalSquareId);
////                updateOF.executeUpdate();
//
//                updateRT.clearParameters();
//                updateRT.setInt(1, id);
//                updateRT.setString(2, originalSquareId);
////                updateRT.executeUpdate();
//
//                updateSQU.clearParameters();
//                updateSQU.setInt(1, id);
//                updateSQU.setString(2, originalSquareId);
////                updateSQU.executeUpdate();
                
                tractCounter++;
            }
            System.out.println(zeroCounter + " squareIDs corrected!");
            System.out.println(tractCounter + " tractIDs entered!");

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                squareRS.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                tractRS.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                updateSquares.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                updateSquareTract.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                updateSquareType.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                updateTractId.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                updateBT.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                updateOF.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                updateRT.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                updateSQU.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                getTractNumbers.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                getSquareNumbers.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            System.out.println("Closed Connected Objects");
        }
    }


}
