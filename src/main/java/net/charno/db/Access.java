package net.charno.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Access extends DBConnectionHandler {

    private static final String URL = "jdbc:access://///home/mdc502/projects/Sikyon/_working/ssp_working.mdb";
    
    private static final String SELECT_SQUARES = "select * from Squares";
    
    Statement selectSquares;
    ResultSet squareRS;

    public Access() {
        super("", "", URL, DBConnectionHandler.ACCESS);
    }

    @Override
    public void process() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void insertCorrectIds() {
        Archspatial archspatial = new Archspatial();

        try {
            archspatial.connect();
            archspatial.setAuto(true);

            selectSquares = getConnection().createStatement();
            squareRS = selectSquares.executeQuery(SELECT_SQUARES);

            while (squareRS.next()) {
                String legacyID = squareRS.getString("legacyID");
                int id = squareRS.getInt("id");

                archspatial.updateSquareIds(legacyID, id);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                squareRS.close();
            } catch (SQLException ex) {}
            try {
                selectSquares.close();
            } catch (SQLException ex) {}

            archspatial.disconnect();
        }
    }

}
