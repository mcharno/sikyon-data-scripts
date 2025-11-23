package net.charno.db.scripts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.charno.db.DBConnectionHandler;

public class DensityBuilder extends DBConnectionHandler {

    private final static String ACCESS_URL = "jdbc:access:////home/mdc502/projects/sikyon/db/ssp_working.mdb";
    private final static String ACCESS_USER = "";
    private final static String ACCESS_PASSWORD = "";

    private final static String GET_AREAS = "select * from SQU_coords";
    private final static String GET_POT_TOTAL = "select id, potTotal, adjustedVisibilityPot from Squares where id = ?";
    private final static String GET_RESURVEY_SQUARES = ""; // TODO
    private final static String UPDATE_SQUARES_DENSITY = "update Squares set densityPot = ? where id = ?";
    private final static String UPDATE_SQUARES_DENSITY_VIS = "update Squares set densityPotVisibilityAdjusted = ? where id = ?";

    private Statement getAreas;
    private PreparedStatement getPotteryTotal, updateDensity, updateDensityVis;
    private ResultSet rs, areaRS;

    public DensityBuilder() {
        super(ACCESS_USER, ACCESS_PASSWORD, ACCESS_URL, DBConnectionHandler.ACCESS);
    }

    @Override
    public void process() {
        try {
            getAreas = getConnection().createStatement();
            rs = getAreas.executeQuery(GET_AREAS);

            getPotteryTotal = getConnection().prepareStatement(GET_POT_TOTAL);
            updateDensity = getConnection().prepareStatement(UPDATE_SQUARES_DENSITY);
            updateDensityVis = getConnection().prepareStatement(UPDATE_SQUARES_DENSITY_VIS);

            while (rs.next()) {
                int id = rs.getInt("id");
                int area = rs.getInt("AREA_M");

                getPotteryTotal.clearParameters();
                getPotteryTotal.setInt(1, id);
                areaRS = getPotteryTotal.executeQuery();

                if (areaRS.next()) {
                    int potTotal = areaRS.getInt("potTotal");
                    int adjustedVisibilityPotTotal = areaRS.getInt("adjustedVisibilityPot");

                    updateDensity.clearParameters();
                    updateDensity.setDouble(1, (double)potTotal/area);
                    updateDensity.setInt(2, id);
                    System.out.print(id + "... ");
                    updateDensity.executeUpdate();

                    updateDensityVis.clearParameters();
                    updateDensityVis.setDouble(1, (double)adjustedVisibilityPotTotal/area);
                    updateDensityVis.setInt(2, id);
                    System.out.println("yes!");
                    updateDensityVis.executeUpdate();
                }
            }
        } catch (SQLException sqlEx) {
            System.out.println("SQLException!");
        } finally {
            try {
                rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }try {
                areaRS.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }try {
                updateDensity.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }try {
                updateDensityVis.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DensityBuilder builder = new DensityBuilder();
        try {
            builder.connect();
            builder.setAuto(true);
            builder.process();
        } finally {
            builder.disconnect();
        }
    }

}
