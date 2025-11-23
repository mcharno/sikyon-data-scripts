package net.charno.db.scripts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.charno.db.DBConnectionHandler;

/**
 * This class is for building the SSP_SQUARES_POTTERY table in Oracle. It takes
 * existing data from the SSP_POTTERY_* tables and imports the necessary data
 * into the SSP_SQUARES_POTTERY table.  This table is mostly for the display of
 * pottery data per square in the GIS, so it aggregates data by square and
 * populates the correct field.
 *
 * @author mdc502
 */
public class PotteryTableBuilder extends DBConnectionHandler {
    private static String USER = "";
    private static String PASS = "";
    private static String URL = "jdbc:oracle:thin:@mapserver.york.ac.uk:1521:ADS08";
    
    private static String SELECT_POTTERY = "select ID, LEGACYID, POTTOTAL, ADJUSTEDWALKERSPOT, ADJUSTEDVISIBILITYPOT, DENSITYPOT, DENSITYPOTVISIBILITYADJUSTED, POTWEIGHT, POTBAGS, ROOFTOTAL, ADJUSTEDVISIBILITYROOF, DENSITYROOF, DENSITYROOFVISIBILITYADJUSTED from SSP_SQUARES_ATTRS";
    private static String SELECT_PREHISTORIC_COUNT = "select SQUAREID, count(*) from SSP_POTTERY_PREHISTORIC group by SQUAREID";
    private static String SELECT_HELLENISTIC_COUNT = "select SQUAREID, count(QUANTITY) from SSP_POTTERY_HELLENISTIC group by SQUAREID";
    private static String SELECT_HELLENISTIC_CLASS_1 = "select SQUAREID, count(QUANTITY) from SSP_POTTERY_HELLENISTIC where lower(CLASS) like ";
    private static String SELECT_ROMAN_COUNT = "select SQUAREID, count(QUANTITY) from SSP_POTTERY_ROMAN group by SQUAREID";
    private static String SELECT_ROMAN_CLASS_1 = "select SQUAREID, count(QUANTITY) from SSP_POTTERY_ROMAN where ";
    private static String SELECT_PERIOD_CLASS_2 = " group by SQUAREID";
    private static String INSERT_POTTERY_ATTRS = "insert into SSP_SQUARES_POTTERY (ID, LEGACYID, POT_TOTAL, POT_ADJWALK, POT_ADJVIS, POT_DENS, POT_DENSADJVIS, POT_WEIGHT, POT_BAGS, ROOF_TOTAL, ROOF_ADJVIS, ROOF_DENS, ROOF_DENSADJVIS) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String UPDATE_POTTERY_PREHISTORIC = "update SSP_SQUARES_POTTERY set COUNT_PREHISTORIC = ? where ID = ?";
    private static String UPDATE_POTTERY_HELLENISTIC = "update SSP_SQUARES_POTTERY set COUNT_HELLENISTIC = ? where ID = ?";
    private static String UPDATE_POTTERY_ROMAN = "update SSP_SQUARES_POTTERY set COUNT_ROMAN = ? where ID = ?";
    private static String UPDATE_POTTERY_CLASS_1 = "update SSP_SQUARES_POTTERY set ";
    private static String UPDATE_POTTERY_CLASS_2 = " = ? where ID = ?";

    private Statement stmtGetPottery, stmtGetPrehistoric, stmtGetHellenistic, stmtGetRoman;
    private PreparedStatement pstmtInsertPottery, pstmtInsertPeriods;
    private ResultSet rs, rsPrehistoric, rsHellenistic, rsRoman;

    private Map<Integer, List<Integer>> classMap = new HashMap<Integer, List<Integer>>();
    
    public PotteryTableBuilder() {
        super(USER, PASS, URL, DBConnectionHandler.ORACLE);
    }

    private void populateGeneralData() {
        System.out.println("processing general pottery data...");
        try {
            stmtGetPottery = getConnection().createStatement();
            rs = stmtGetPottery.executeQuery(SELECT_POTTERY);
            
            pstmtInsertPottery = getConnection().prepareStatement(INSERT_POTTERY_ATTRS);

            while (rs.next()) {
                Integer id = rs.getInt(1);
//                System.out.println(id);
                if (id == 1290) {
                    System.out.println("\t...skipping " + id);
                    continue;
                }
                String legacyId = rs.getString(2);
                int potTotal = rs.getInt(3);
                double potAdjWalk = rs.getDouble(4);
                double potAdjVis = rs.getDouble(5);
                double potDensity = rs.getDouble(6);
                double potDensityAdjVis = rs.getDouble(7);
                double potWeight = rs.getDouble(8);
                int potBags = rs.getInt(9);
                int roofTotal = rs.getInt(10);
                double roofAdjVis = rs.getDouble(11);
                double roofDensity = rs.getDouble(12);
                double roofDensityAdjVis = rs.getDouble(13);

                // run prepared statement with above values
                pstmtInsertPottery.clearParameters();
                pstmtInsertPottery.setInt(1, id);
                pstmtInsertPottery.setString(2, legacyId);
                pstmtInsertPottery.setInt(3, potTotal);
                pstmtInsertPottery.setDouble(4, potAdjWalk);
                pstmtInsertPottery.setDouble(5, potAdjVis);
                pstmtInsertPottery.setDouble(6, potDensity);
                pstmtInsertPottery.setDouble(7, potDensityAdjVis);
                pstmtInsertPottery.setDouble(8, potWeight);
                pstmtInsertPottery.setInt(9, potBags);
                pstmtInsertPottery.setInt(10, roofTotal);
                pstmtInsertPottery.setDouble(11, roofAdjVis);
                pstmtInsertPottery.setDouble(12, roofDensity);
                pstmtInsertPottery.setDouble(13, roofDensityAdjVis);
                pstmtInsertPottery.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("PotteryTableBuilder.populateGeneralData :: SQLException");
            e.printStackTrace();
        } finally {
            try {
                pstmtInsertPottery.close();
            } catch (Exception e) {
                System.out.println("pstmtInsertPottery already closed or null!");
            }
            try {
                stmtGetPottery.close();
            } catch (Exception e) {
                System.out.println("stmtGetPottery already closed or null!");
            }
            try {
                rs.close();
            } catch (Exception e) {
                System.out.println("rs already closed or null!");
            }            
        }
    }
    
    @Override
    public void process() {
        try {
            System.out.println("processing periods...");
            stmtGetPrehistoric = getConnection().createStatement();
            stmtGetHellenistic = getConnection().createStatement();
            stmtGetRoman = getConnection().createStatement();

            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_PREHISTORIC);
            rsPrehistoric = stmtGetPrehistoric.executeQuery(SELECT_PREHISTORIC_COUNT);
            while (rsPrehistoric.next()) {
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(1, rsPrehistoric.getInt(2));
                pstmtInsertPeriods.setInt(2, rsPrehistoric.getInt(1));
                pstmtInsertPeriods.executeUpdate();
            }

            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_HELLENISTIC);
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_COUNT);
            while (rsHellenistic.next()) {
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(1, rsHellenistic.getInt(2));
                pstmtInsertPeriods.setInt(2, rsHellenistic.getInt(1));
                pstmtInsertPeriods.executeUpdate();
            }
            
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_ROMAN);
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_COUNT);
            while (rsRoman.next()) {
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(1, rsRoman.getInt(2));
                pstmtInsertPeriods.setInt(2, rsRoman.getInt(1));
                pstmtInsertPeriods.executeUpdate();
            }


            System.out.println("processing class types...");
            System.out.println("\tHellenistic table");
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%table%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            System.out.println("\tRoman table");
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_TBLEAT = 1 or F_TBLDRINK = 1 or F_TBLSERV = 1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_TABLE" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic cooking");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%cook%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            System.out.println("\tRoman cooking");
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_COOKING =1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_COOKING" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic toilet");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%toilet%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            System.out.println("\tRoman toilet");
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_TOILET =1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_TOILET" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic transport");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%transport%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            System.out.println("\tRoman transport");
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_TRANSPORT =1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_TRANSPORT" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic production");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%production%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            System.out.println("\tRoman production");
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_CPRODUCT = 1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_PRODUCTION" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic utility");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%utility%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            System.out.println("\tRoman utility");
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_UTILITY=1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_UTILITY" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic lamp");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%lamp%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            System.out.println("\tRoman lamp");
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_LAMP=1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_LAMP" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic figurine");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%figurine%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_FIGURING" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic votive");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%votive%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_VOTIVE" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tRoman ritual");
            classMap = new HashMap<Integer, List<Integer>>();
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_RITUAL=1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_RITUAL" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic weaving");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%weaving%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            System.out.println("\tRoman weaving");
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_WEAVING=1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_WEAVING" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

            System.out.println("\tHellenistic unknown");
            classMap = new HashMap<Integer, List<Integer>>();
            rsHellenistic = stmtGetHellenistic.executeQuery(SELECT_HELLENISTIC_CLASS_1 + "'%unknown%'" + SELECT_PERIOD_CLASS_2);
            while (rsHellenistic.next()) {
                List<Integer> tableList = new ArrayList<Integer>();
                tableList.add(rsHellenistic.getInt(2));
                classMap.put(rsHellenistic.getInt(1), tableList);
            }
            System.out.println("\tRoman other");
            rsRoman = stmtGetRoman.executeQuery(SELECT_ROMAN_CLASS_1 + "F_OTHER=1" + SELECT_PERIOD_CLASS_2);
            while (rsRoman.next()) {
                Integer id = rsRoman.getInt(1);
                if (classMap.containsKey(id)) {
                    classMap.get(id).add(rsRoman.getInt(2));
                } else {
                    List<Integer> tableList = new ArrayList<Integer>();
                    tableList.add(rsRoman.getInt(2));
                    classMap.put(rsRoman.getInt(1), tableList);
                }
            }
            pstmtInsertPeriods = getConnection().prepareStatement(UPDATE_POTTERY_CLASS_1 + "CL_OTHER" + UPDATE_POTTERY_CLASS_2);
            for (Map.Entry<Integer, List<Integer>> entry : classMap.entrySet()) {
                int total = 0;
                for (Integer value : entry.getValue()) {
                    total += value;
                }
                pstmtInsertPeriods.clearParameters();
                pstmtInsertPeriods.setInt(2, entry.getKey());
                pstmtInsertPeriods.setInt(1, total);
                pstmtInsertPeriods.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("PotteryTableBuilder.process :: SQLException");
            e.printStackTrace();
        } finally {
            try {
                rsPrehistoric.close();
            } catch (Exception e) {
                System.out.println("rsPrehistoric already closed or null!");
            }
            try {
                rsHellenistic.close();
            } catch (Exception e) {
                System.out.println("rsHellenistic already closed or null!");
            }
            try {
                rsRoman.close();
            } catch (Exception e) {
                System.out.println("rsRoman already closed or null!");
            }
            try {
                pstmtInsertPeriods.close();
            } catch (Exception e) {
                System.out.println("pstmtInsertPeriods already closed or null!");
            }
            try {
                stmtGetPrehistoric.close();
            } catch (Exception e) {
                System.out.println("getPrehistoric already closed or null!");
            }
            try {
                stmtGetHellenistic.close();
            } catch (Exception e) {
                System.out.println("getHellenistic already closed or null!");
            }
            try {
                stmtGetRoman.close();
            } catch (Exception e) {
                System.out.println("getRoman already closed or null!");
            }
        }
    }
    
    public static void main(String[] args) {
        PotteryTableBuilder builder = new PotteryTableBuilder();
        try {
            builder.connect();
            builder.setAuto(true);
            builder.populateGeneralData();
            builder.process();
        } finally {
            builder.disconnect();
        }
    }


}
