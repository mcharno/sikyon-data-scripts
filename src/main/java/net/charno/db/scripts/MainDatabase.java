package net.charno.db.scripts;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.charno.db.DBConnectionHandler;
import net.charno.db.model.HellenisticPottery;
import net.charno.db.model.Pottery;
import net.charno.db.model.RomanPottery;

public class MainDatabase extends DBConnectionHandler {
    private static final String DB_URL = "jdbc:access:////home/mdc502/projects/sikyon/db/ssp_main.mdb";

    private static final String insertRomanRecord = "insert into Pottery_Roman"
            + "(ID, squareID, squareName, index_, catalogue, date_, shape, form, quantity, fabric, notes, biblio, noRom, p_ERom, p_MidRom, p_LRom, p_EByz, p_MidByzI, p_MidByzII, p_LByz, p_EOtt, p_LOtt, p_Mod, p_Unknown, p_Rom, p_Byz, p_HellERom, p_Class, p_Archaic, p_Hell, v_Rim, v_Neck, v_Shoulder, v_Handle, v_Body, v_Base, v_Foot, v_Other, v_Nozzle, v_Disc, v_Lower, v_Upper, v_Spout, f_Cooking, f_Storage, f_Transport, f_TblEat, f_tblDrink, f_tblServ, f_Ritual, f_Lamp, f_Toilet, f_Weaving, f_CProduct, f_Utility, f_Other, d_notes, d_Slip, d_Glaze, d_Paint, d_Incised, d_Stamped, d_Moulded, import, fw, imitation) "
            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String selectLegacyIDs = "select ID, LEGACYID from Squares";
    private static final String SELECT_LEGACY_ID = "select ID from SQUARES where LEGACYID = ?";
    private static final String SELECT_DISTINCT_SQUARE_IDS = "select distinct(ID),LEGACYID from SQUARES";
    private static final String selectHellRecords = "select * from Pottery_Hellenistic";
    private static final String selectRomRecords = "select SQUAREID from POTTERY_ROMAN";
    private static final String SELECT_PREHISTORIC_POTTERY = "select SHAPE, DATE from POTTERY_PREHISTORIC where SQUAREID = ?";
    private static final String SELECT_HELLENISTIC_POTTERY = "select ID,QUANTITY from POTTERY_HELLENISTIC where SQUAREID = ? order by ID asc";
    private static final String SELECT_HELLENISTIC_POTTERY_DETAILS = "select POTTERYID,f.TYPE,d.DATE from POTTERY_HELLENISTIC h, POTTERY_HELLENISTIC_CLASS f, POTTERY_HELLENISTIC_DATES d where POTTERYID = ? and h.ID = f.POTTERYID and h.ID = d.POTTERYID";
    private static final String SELECT_ROMAN_POTTERY = "select ID,QUANTITY,DATES,P_EROM,P_MIDROM,P_LROM,P_EBYZ,P_MIDBYZI,P_MIDBYZII,P_LBYZ,P_EOTT,P_LOTT,P_MOD,P_UNKNOWN,P_ROM,P_BYZ,P_HELLEROM,P_CLASS,P_ARCHAIC,P_HELL,F_COOKING,F_STORAGE,F_TRANSPORT,F_TBLEAT,F_TBLDRINK,F_TBLSERVE,F_RITUAL,F_LAMP,F_TOILET,F_WEAVING,F_CPRODUCT,F_UTILITY,F_OTHER from POTTERY_ROMAN where SQUAREID = ?";
    private static final String SELECT_HELL_COUNT = "select count(POTTERYID) from POTTERY_HELLENISTIC_CLASS f, POTTERY_HELLENISTIC_DATES d where POTTERYID = ? and f.POTTERYID = d.POTTERYID";

    private static final String updateRomPottery1 = "update Pottery_Roman set legacyID = ? where squareId = ?";
    private static final String updateRomPottery2 = "update POTTERY_ROMAN set SQUAREID = ? where SQUARENAME = ?";
    private static final String updateHellSquareIDsSQL = "update Pottery_Hellenistic set squareID = ? where ID = ?";

    private static final String setHellShapeToLowerCase = "update Pottery_Hellenistic set Shape = lower(Shape)";
    private static final String deleteBadSquares = "delete from Pottery_Hellenistic where squareName = 'ST 33'";
    private static final String findSquareId = "select id from Squares where tract = ? and square = ? and type = ?";
    private static final String insertClassValues = "insert into Pottery_Hellenistic_Class(potteryID, type) values(?,?)";
    private static final String insertDateValues = "insert into Pottery_Hellenistic_Dates(potteryID, date) values (?,?)";
    private static final String insertVesselValues = "insert into Pottery_Hellenistic_Vessels(potteryID, type) values (?,?)";


    private Statement stmt, setHellShapes, deleteSquares, getHellenisitcRecords, getLegacyIDs, getRomSquares;
    private PreparedStatement getSquareIds, insertRomanRecordsPS, insertClasses, insertDates, insertVessels, updateLegacyIDs, updateHellSquareIDs;
    private PreparedStatement updateRomRecordsPS;
    private PreparedStatement getPrehistoricPotteryPS, getHellenisticPotteryPS, getHellenisticPotteryDetailsPS, getHellenisticPotteryCountsPS, getRomanPotteryPS;
    private ResultSet rs, squareIdRS;
    private ResultSet prehistoricPotteryRS, hellenisticPotteryRS, hellenisticPotteryDetailsRS, hellenisticPotteryCountRS, romanPotteryRS;

    private List<RomanPottery> romanPotteryList;
    private List<HellenisticPottery> hellenisticPotteryList;
    private List<Pottery> potteryList = new ArrayList<Pottery>();

    private String id;

    private int romanCounter = 0, hellenisticCounter = 0;
    private int potteryCounter = 1;

    public MainDatabase() {
        super("", "", DB_URL, DBConnectionHandler.ACCESS);
    }

    public static void main(String[] args) {
        MainDatabase loader = new MainDatabase();
        try {
            loader.connect();
            loader.setAuto(true);
            loader.process();
        } finally {
            loader.disconnect();
        }
    }

    @Override
    public void process() {
        try {
//            loadRomanModel();
//            updateRomanSquareIds();
//            writeRomanCSV();
            
//            loadHellenisticModel();
//            updateHellenisticLegacyIds();
//            updateHellenisticSquareIds();
//            writeHellenisticCSV();
//            processHellenisticRecords();
            
            buildPotteryTable();
            writePotteryCSV();
        } finally {
            System.out.println(romanCounter + " roman records processed");
            System.out.println(hellenisticCounter + " hellenistic records processed");
        }
    }
    
    public void buildPotteryTable() {
        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery(SELECT_DISTINCT_SQUARE_IDS);
            
            getPrehistoricPotteryPS = getConnection().prepareStatement(SELECT_PREHISTORIC_POTTERY);
            getHellenisticPotteryPS = getConnection().prepareStatement(SELECT_HELLENISTIC_POTTERY);
            getHellenisticPotteryDetailsPS = getConnection().prepareStatement(SELECT_HELLENISTIC_POTTERY_DETAILS);
            getHellenisticPotteryCountsPS = getConnection().prepareStatement(SELECT_HELL_COUNT);
            getRomanPotteryPS = getConnection().prepareStatement(SELECT_ROMAN_POTTERY);
            
            while (rs.next()) {
                Pottery potteryValues = new Pottery();
                int count;
                double original;
                int readPottery = 0;
                
                int squareId = rs.getInt("ID");
                String legacyId = rs.getString("LEGACYID");
                System.out.println(squareId);
                // prehistoric
                getPrehistoricPotteryPS.clearParameters();
                getPrehistoricPotteryPS.setInt(1, squareId);
                prehistoricPotteryRS = getPrehistoricPotteryPS.executeQuery();
                while(prehistoricPotteryRS.next()) {
                    // function
                    String shape = prehistoricPotteryRS.getString("SHAPE");
                    String date = prehistoricPotteryRS.getString("DATE");
                    if ("EH II".equals(date) || "EH?".equals(date)) {
                        if ("Krater".equals(shape) || "basin? Oatmeal".equals(shape) || "sauceboat".equals(shape) || "stirrup jar".equals(shape)) {
                            count = potteryValues.getEarlyHelladicTableService();
                            potteryValues.setEarlyHelladicTableService(count++);
                        } else if ("Kylix".equals(shape) || "Vapheio cup".equals(shape) || "goblet?".equals(shape)) {
                            count = potteryValues.getEarlyHelladicTableDrink();
                            potteryValues.setEarlyHelladicTableDrink(count++);
                        } else if ("alabastron?".equals(shape)) {
                            count = potteryValues.getEarlyHelladicToilet();
                            potteryValues.setEarlyHelladicToilet(count++);
                        } else if ("bowl with incurving".equals(shape) || "bowl with incurving or sauceboat".equals(shape) || "deep bowl".equals(shape)) {
                            count = potteryValues.getEarlyHelladicTableEat();
                            potteryValues.setEarlyHelladicTableEat(count++);
                        } else if ("open shape".equals(shape)) {
                            count = potteryValues.getEarlyHelladicOpenShape();
                            potteryValues.setEarlyHelladicOpenShape(count++);
                        } else if ("tripod cooking Aeginetan".equals(shape) || "tripod cooking local".equals(shape)) {
                            count = potteryValues.getEarlyHelladicCooking();
                            potteryValues.setEarlyHelladicCooking(count++);
                        } else {
                            count = potteryValues.getEarlyHelladicOther();
                            potteryValues.setEarlyHelladicOther(count++);
                        }
                        readPottery++;
                    } else if ("Neo".equals(date) || "Neo Yes Tom".equals(date) || "Neo?".equals(date)) {
                        if ("Krater".equals(shape) || "basin? Oatmeal".equals(shape) || "sauceboat".equals(shape) || "stirrup jar".equals(shape)) {
                            count = potteryValues.getNeolithicTableService();
                            potteryValues.setNeolithicTableService(count++);
                        } else if ("Kylix".equals(shape) || "Vapheio cup".equals(shape) || "goblet?".equals(shape)) {
                            count = potteryValues.getNeolithicTableDrink();
                            potteryValues.setNeolithicTableDrink(count++);
                        } else if ("alabastron?".equals(shape)) {
                            count = potteryValues.getNeolithicToilet();
                            potteryValues.setNeolithicToilet(count++);
                        } else if ("bowl with incurving".equals(shape) || "bowl with incurving or sauceboat".equals(shape) || "deep bowl".equals(shape)) {
                            count = potteryValues.getNeolithicTableEat();
                            potteryValues.setNeolithicTableEat(count++);
                        } else if ("open shape".equals(shape)) {
                            count = potteryValues.getNeolithicOpenShape();
                            potteryValues.setNeolithicOpenShape(count++);
                        } else if ("tripod cooking Aeginetan".equals(shape) || "tripod cooking local".equals(shape)) {
                            count = potteryValues.getNeolithicCooking();
                            potteryValues.setNeolithicCooking(count++);
                        } else {
                            count = potteryValues.getNeolithicOther();
                            potteryValues.setNeolithicOther(count++);
                        }
                        readPottery++;
                    } else if ("MH".equals(date) || "MH/LH".equals(date) || "MH?".equals(date)) {
                        if ("Krater".equals(shape) || "basin? Oatmeal".equals(shape) || "sauceboat".equals(shape) || "stirrup jar".equals(shape)) {
                            count = potteryValues.getMiddleHelladicTableService();
                            potteryValues.setMiddleHelladicTableService(count++);
                            count = potteryValues.getLateHelladicTableService();
                            potteryValues.setLateHelladicTableService(count++);
                        } else if ("Kylix".equals(shape) || "Vapheio cup".equals(shape) || "goblet?".equals(shape)) {
                            count = potteryValues.getMiddleHelladicTableDrink();
                            potteryValues.setMiddleHelladicTableDrink(count++);
                            count = potteryValues.getLateHelladicTableDrink();
                            potteryValues.setLateHelladicTableDrink(count++);
                        } else if ("alabastron?".equals(shape)) {
                            count = potteryValues.getMiddleHelladicToilet();
                            potteryValues.setMiddleHelladicToilet(count++);
                            count = potteryValues.getLateHelladicToilet();
                            potteryValues.setLateHelladicToilet(count++);
                        } else if ("bowl with incurving".equals(shape) || "bowl with incurving or sauceboat".equals(shape) || "deep bowl".equals(shape)) {
                            count = potteryValues.getMiddleHelladicTableEat();
                            potteryValues.setMiddleHelladicTableEat(count++);
                            count = potteryValues.getLateHelladicTableEat();
                            potteryValues.setLateHelladicTableEat(count++);
                        } else if ("open shape".equals(shape)) {
                            count = potteryValues.getMiddleHelladicOpenShape();
                            potteryValues.setMiddleHelladicOpenShape(count++);
                            count = potteryValues.getLateHelladicOpenShape();
                            potteryValues.setLateHelladicOpenShape(count++);
                        } else if ("tripod cooking Aeginetan".equals(shape) || "tripod cooking local".equals(shape)) {
                            count = potteryValues.getMiddleHelladicCooking();
                            potteryValues.setMiddleHelladicCooking(count++);
                            count = potteryValues.getLateHelladicCooking();
                            potteryValues.setLateHelladicCooking(count++);
                        } else {
                            count = potteryValues.getMiddleHelladicOther();
                            potteryValues.setMiddleHelladicOther(count++);
                            count = potteryValues.getLateHelladicOther();
                            potteryValues.setLateHelladicOther(count++);
                        }
                        readPottery++;
                    } else if ("LH".equals(date) || "LH ?".equals(date) || "LH?".equals(date)) {
                        if ("Krater".equals(shape) || "basin? Oatmeal".equals(shape) || "sauceboat".equals(shape) || "stirrup jar".equals(shape)) {
                            count = potteryValues.getLateHelladicTableService();
                            potteryValues.setLateHelladicTableService(count++);
                        } else if ("Kylix".equals(shape) || "Vapheio cup".equals(shape) || "goblet?".equals(shape)) {
                            count = potteryValues.getLateHelladicTableDrink();
                            potteryValues.setLateHelladicTableDrink(count++);
                        } else if ("alabastron?".equals(shape)) {
                            count = potteryValues.getLateHelladicToilet();
                            potteryValues.setLateHelladicToilet(count++);
                        } else if ("bowl with incurving".equals(shape) || "bowl with incurving or sauceboat".equals(shape) || "deep bowl".equals(shape)) {
                            count = potteryValues.getLateHelladicTableEat();
                            potteryValues.setLateHelladicTableEat(count++);
                        } else if ("open shape".equals(shape)) {
                            count = potteryValues.getLateHelladicOpenShape();
                            potteryValues.setLateHelladicOpenShape(count++);
                        } else if ("tripod cooking Aeginetan".equals(shape) || "tripod cooking local".equals(shape)) {
                            count = potteryValues.getLateHelladicCooking();
                            potteryValues.setLateHelladicCooking(count++);
                        } else {
                            count = potteryValues.getLateHelladicOther();
                            potteryValues.setLateHelladicOther(count++);
                        }
                        readPottery++;
                    } else if ("IIA/B".equals(date) || "LH IIA/B".equals(date)) {
                        if ("Krater".equals(shape) || "basin? Oatmeal".equals(shape) || "sauceboat".equals(shape) || "stirrup jar".equals(shape)) {
                            count = potteryValues.getLateHelladicIITableService();
                            potteryValues.setLateHelladicIITableService(count++);
                        } else if ("Kylix".equals(shape) || "Vapheio cup".equals(shape) || "goblet?".equals(shape)) {
                            count = potteryValues.getLateHelladicIITableDrink();
                            potteryValues.setLateHelladicIITableDrink(count++);
                        } else if ("alabastron?".equals(shape)) {
                            count = potteryValues.getLateHelladicIIToilet();
                            potteryValues.setLateHelladicIIToilet(count++);
                        } else if ("bowl with incurving".equals(shape) || "bowl with incurving or sauceboat".equals(shape) || "deep bowl".equals(shape)) {
                            count = potteryValues.getLateHelladicIITableEat();
                            potteryValues.setLateHelladicIITableEat(count++);
                        } else if ("open shape".equals(shape)) {
                            count = potteryValues.getLateHelladicIIOpenShape();
                            potteryValues.setLateHelladicIIOpenShape(count++);
                        } else if ("tripod cooking Aeginetan".equals(shape) || "tripod cooking local".equals(shape)) {
                            count = potteryValues.getLateHelladicIICooking();
                            potteryValues.setLateHelladicIICooking(count++);
                        } else {
                            count = potteryValues.getLateHelladicIIOther();
                            potteryValues.setLateHelladicIIOther(count++);
                        }
                        readPottery++;
                    } else if ("IIIA/B".equals(date) || "IIIA1".equals(date) || "LH IIIB?".equals(date) || "LH IIIC Middle/Late".equals(date)) {
                        if ("Krater".equals(shape) || "basin? Oatmeal".equals(shape) || "sauceboat".equals(shape) || "stirrup jar".equals(shape)) {
                            count = potteryValues.getLateHelladicIIITableService();
                            potteryValues.setLateHelladicIIITableService(count++);
                        } else if ("Kylix".equals(shape) || "Vapheio cup".equals(shape) || "goblet?".equals(shape)) {
                            count = potteryValues.getLateHelladicIIITableDrink();
                            potteryValues.setLateHelladicIIITableDrink(count++);
                        } else if ("alabastron?".equals(shape)) {
                            count = potteryValues.getLateHelladicIIIToilet();
                            potteryValues.setLateHelladicIIIToilet(count++);
                        } else if ("bowl with incurving".equals(shape) || "bowl with incurving or sauceboat".equals(shape) || "deep bowl".equals(shape)) {
                            count = potteryValues.getLateHelladicIIITableEat();
                            potteryValues.setLateHelladicIIITableEat(count++);
                        } else if ("open shape".equals(shape)) {
                            count = potteryValues.getLateHelladicIIIOpenShape();
                            potteryValues.setLateHelladicIIIOpenShape(count++);
                        } else if ("tripod cooking Aeginetan".equals(shape) || "tripod cooking local".equals(shape)) {
                            count = potteryValues.getLateHelladicIIICooking();
                            potteryValues.setLateHelladicIIICooking(count++);
                        } else {
                            count = potteryValues.getLateHelladicIIIOther();
                            potteryValues.setLateHelladicIIIOther(count++);
                        }
                        readPottery++;
                    } else {
                        if ("Krater".equals(shape) || "basin? Oatmeal".equals(shape) || "sauceboat".equals(shape) || "stirrup jar".equals(shape)) {
                            original = potteryValues.getUnknownTableService();
                            potteryValues.setUnknownTableService(original+1.0);
                        } else if ("Kylix".equals(shape) || "Vapheio cup".equals(shape) || "goblet?".equals(shape)) {
                            original = potteryValues.getUnknownTableDrink();
                            potteryValues.setUnknownTableDrink(original+1.0);
                        } else if ("alabastron?".equals(shape)) {
                            original = potteryValues.getUnknownToilet();
                            potteryValues.setUnknownToilet(original+1.0);
                        } else if ("bowl with incurving".equals(shape) || "bowl with incurving or sauceboat".equals(shape) || "deep bowl".equals(shape)) {
                            original = potteryValues.getUnknownTableEat();
                            potteryValues.setUnknownTableEat(original+1.0);
                        } else if ("open shape".equals(shape)) {
                            original = potteryValues.getUnknownOpenShape();
                            potteryValues.setUnknownOpenShape(original+1.0);
                        } else if ("tripod cooking Aeginetan".equals(shape) || "tripod cooking local".equals(shape)) {
                            original = potteryValues.getUnknownCooking();
                            potteryValues.setUnknownCooking(original+1.0);
                        } else {
                            original = potteryValues.getUnknownOther();
                            potteryValues.setUnknownOther(original+1.0);
                        }
                        readPottery++;
                    }
                }
                // hellenistic
                getHellenisticPotteryPS.clearParameters();
                getHellenisticPotteryPS.setInt(1, squareId);
                hellenisticPotteryRS = getHellenisticPotteryPS.executeQuery();
                
                int previousPotteryId = 0;
                while (hellenisticPotteryRS.next()) {
                    int potteryId = hellenisticPotteryRS.getInt("ID");
                    int quantity = hellenisticPotteryRS.getInt("QUANTITY");
                    if (potteryId != previousPotteryId) {
                        readPottery += quantity;
                    }   
                    previousPotteryId = potteryId;
                    
                    getHellenisticPotteryDetailsPS.clearParameters();
                    getHellenisticPotteryDetailsPS.setInt(1, potteryId);
                    hellenisticPotteryDetailsRS = getHellenisticPotteryDetailsPS.executeQuery();
                    while (hellenisticPotteryDetailsRS.next()) {
                        String date = hellenisticPotteryDetailsRS.getString("DATE");
                        String type = hellenisticPotteryDetailsRS.getString("TYPE");
                        double increment;
                        
                        getHellenisticPotteryCountsPS.clearParameters();
                        getHellenisticPotteryCountsPS.setInt(1, potteryId);
                        hellenisticPotteryCountRS = getHellenisticPotteryCountsPS.executeQuery();
                        if (hellenisticPotteryCountRS.next()) {
                            int hellCount = hellenisticPotteryCountRS.getInt(1);
                            if (hellCount == 0) {
                                hellCount = 1;
                            } 
                            increment = (double)quantity/(double)hellCount;
                        } else {
                            increment = 1.0;
                        }
                        
                        if ("Class".equals(date)) {
                            if ("bowl/lekane/krater".equals(type) || "utility".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalUtility();
                                potteryValues.setClassicalUtility(original+increment);
                            } else if ("ceramic production".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalCeramicProduction();
                                potteryValues.setClassicalCeramicProduction(original+increment);
                            } else if ("cook".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalCooking();
                                potteryValues.setClassicalCooking(original+increment);
                            } else if ("figurine".equals(type.toLowerCase()) || "votive".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalRitual();
                                potteryValues.setClassicalRitual(original+increment);
                            } else if ("lamp".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalLamp();
                                potteryValues.setClassicalLamp(original+increment);
                            } else if ("storage".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalStorage();
                                potteryValues.setClassicalStorage(original+increment);
                            } else if ("table drink".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalTableDrink();
                                potteryValues.setClassicalTableDrink(original+increment);
                            } else if ("table eat".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalTableEat();
                                potteryValues.setClassicalTableEat(original+increment);
                            } else if ("table service".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalTableService();
                                potteryValues.setClassicalTableService(original+increment);
                            } else if ("table unknown".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalTableUnknown();
                                potteryValues.setClassicalTableUnknown(original+increment);
                            } else if ("toilet".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalToilet();
                                potteryValues.setClassicalToilet(original+increment);
                            } else if ("transport".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalTransport();
                                potteryValues.setClassicalTransport(original+increment);
                            } else if ("weaving".equals(type.toLowerCase())) {
                                original = potteryValues.getClassicalWeaving();
                                potteryValues.setClassicalWeaving(original+increment);
                            }
                        } else if ("Hell".equals(date)) {
                            if ("bowl/lekane/krater".equals(type) || "utility".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticUtility();
                                potteryValues.setHellenisticUtility(original+increment);
//                                System.out.println(hellenisticPotteryDetailsRS.getInt("POTTERYID") + ": " + original + " + " + increment);
                            } else if ("ceramic production".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticCeramicProduction();
                                potteryValues.setHellenisticCeramicProduction(original+increment);
                            } else if ("cook".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticCooking();
                                potteryValues.setHellenisticCooking(original+increment);
                            } else if ("figurine".equals(type.toLowerCase()) || "votive".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticRitual();
                                potteryValues.setHellenisticRitual(original+increment);
                            } else if ("lamp".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticLamp();
                                potteryValues.setHellenisticLamp(original+increment);
                            } else if ("storage".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticStorage();
                                potteryValues.setHellenisticStorage(original+increment);
                            } else if ("table drink".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticTableDrink();
                                potteryValues.setHellenisticTableDrink(original+increment);
                            } else if ("table eat".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticTableEat();
                                potteryValues.setHellenisticTableEat(original+increment);
                            } else if ("table service".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticTableService();
                                potteryValues.setHellenisticTableService(original+increment);
                            } else if ("table unknown".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticTableUnknown();
                                potteryValues.setHellenisticTableUnknown(original+increment);
                            } else if ("toilet".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticToilet();
                                potteryValues.setHellenisticToilet(original+increment);
                            } else if ("transport".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticTransport();
                                potteryValues.setHellenisticTransport(original+increment);
                            } else if ("weaving".equals(type.toLowerCase())) {
                                original = potteryValues.getHellenisticWeaving();
                                potteryValues.setHellenisticWeaving(original+increment);
                            }
                        } else if ("L Hell/ER".equals(date)) {
                            if ("bowl/lekane/krater".equals(type) || "utility".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanUtility();
                                potteryValues.setLateHellenisticEarlyRomanUtility(original+increment);
                            } else if ("ceramic production".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanCeramicProduction();
                                potteryValues.setLateHellenisticEarlyRomanCeramicProduction(original+increment);
                            } else if ("cook".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanCooking();
                                potteryValues.setLateHellenisticEarlyRomanCooking(original+increment);
                            } else if ("figurine".equals(type.toLowerCase()) || "votive".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanRitual();
                                potteryValues.setLateHellenisticEarlyRomanRitual(original+increment);
                            } else if ("lamp".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanLamp();
                                potteryValues.setLateHellenisticEarlyRomanLamp(original+increment);
                            } else if ("storage".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanStorage();
                                potteryValues.setLateHellenisticEarlyRomanStorage(original+increment);
                            } else if ("table drink".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanTableDrink();
                                potteryValues.setLateHellenisticEarlyRomanTableDrink(original+increment);
                            } else if ("table eat".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanTableEat();
                                potteryValues.setLateHellenisticEarlyRomanTableEat(original+increment);
                            } else if ("table service".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanTableService();
                                potteryValues.setLateHellenisticEarlyRomanTableService(original+increment);
                            } else if ("table unknown".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanTableUnknown();
                                potteryValues.setLateHellenisticEarlyRomanTableUnknown(original+increment);
                            } else if ("toilet".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanToilet();
                                potteryValues.setLateHellenisticEarlyRomanToilet(original+increment);
                            } else if ("transport".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanTransport();
                                potteryValues.setLateHellenisticEarlyRomanTransport(original+increment);
                            } else if ("weaving".equals(type.toLowerCase())) {
                                original = potteryValues.getLateHellenisticEarlyRomanWeaving();
                                potteryValues.setLateHellenisticEarlyRomanWeaving(original+increment);
                            }
                        } else if ("Pre-Class".equals(date)) {
                            if ("bowl/lekane/krater".equals(type) || "utility".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalUtility();
                                potteryValues.setPreClassicalUtility(original+increment);
                            } else if ("ceramic production".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalCeramicProduction();
                                potteryValues.setPreClassicalCeramicProduction(original+increment);
                            } else if ("cook".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalCooking();
                                potteryValues.setPreClassicalCooking(original+increment);
                            } else if ("figurine".equals(type.toLowerCase()) || "votive".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalRitual();
                                potteryValues.setPreClassicalRitual(original+increment);
                            } else if ("lamp".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalLamp();
                                potteryValues.setPreClassicalLamp(original+increment);
                            } else if ("storage".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalStorage();
                                potteryValues.setPreClassicalStorage(original+increment);
                            } else if ("table drink".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalTableDrink();
                                potteryValues.setPreClassicalTableDrink(original+increment);
                            } else if ("table eat".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalTableEat();
                                potteryValues.setPreClassicalTableEat(original+increment);
                            } else if ("table service".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalTableService();
                                potteryValues.setPreClassicalTableService(original+increment);
                            } else if ("table unknown".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalTableUnknown();
                                potteryValues.setPreClassicalTableUnknown(original+increment);
                            } else if ("toilet".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalToilet();
                                potteryValues.setPreClassicalToilet(original+increment);
                            } else if ("transport".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalTransport();
                                potteryValues.setPreClassicalTransport(original+increment);
                            } else if ("weaving".equals(type.toLowerCase())) {
                                original = potteryValues.getPreClassicalWeaving();
                                potteryValues.setPreClassicalWeaving(original+increment);
                            }
                        } else if ("Rom".equals(date)) {
                            if ("bowl/lekane/krater".equals(type) || "utility".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanUtility();
                                potteryValues.setRomanUtility(original+increment);
                            } else if ("ceramic production".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanCeramicProduction();
                                potteryValues.setRomanCeramicProduction(original+increment);
                            } else if ("cook".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanCooking();
                                potteryValues.setRomanCooking(original+increment);
                            } else if ("figurine".equals(type.toLowerCase()) || "votive".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanRitual();
                                potteryValues.setRomanRitual(original+increment);
                            } else if ("lamp".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanLamp();
                                potteryValues.setRomanLamp(original+increment);
                            } else if ("storage".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanStorage();
                                potteryValues.setRomanStorage(original+increment);
                            } else if ("table drink".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanTableDrink();
                                potteryValues.setRomanTableDrink(original+increment);
                            } else if ("table eat".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanTableEat();
                                potteryValues.setRomanTableEat(original+increment);
                            } else if ("table service".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanTableService();
                                potteryValues.setRomanTableService(original+increment);
                            } else if ("table unknown".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanTableUnknown();
                                potteryValues.setRomanTableUnknown(original+increment);
                            } else if ("toilet".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanToilet();
                                potteryValues.setRomanToilet(original+increment);
                            } else if ("transport".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanTransport();
                                potteryValues.setRomanTransport(original+increment);
                            } else if ("weaving".equals(type.toLowerCase())) {
                                original = potteryValues.getRomanWeaving();
                                potteryValues.setRomanWeaving(original+increment);
                            }
                        } 
                    }
                }
                // roman
                getRomanPotteryPS.clearParameters();
                getRomanPotteryPS.setInt(1, squareId);
                romanPotteryRS = getRomanPotteryPS.executeQuery();
                while (romanPotteryRS.next()) {
                    int periodIndex = 0;
                    int functionIndex = 0;
                    int quantity = romanPotteryRS.getInt("QUANTITY");
                    String dates = romanPotteryRS.getString("DATES");
                    readPottery += quantity;
                    
                    boolean earlyRoman = romanPotteryRS.getBoolean("P_EROM");
                    boolean middleRoman = romanPotteryRS.getBoolean("P_MIDROM");
                    boolean lateRoman = romanPotteryRS.getBoolean("P_LROM");
                    boolean earlyByzantine = romanPotteryRS.getBoolean("P_EBYZ");
                    boolean middleByzantineI = romanPotteryRS.getBoolean("P_MIDBYZI");
                    boolean middleByzantineII = romanPotteryRS.getBoolean("P_MIDBYZII");
                    boolean lateByzantine = romanPotteryRS.getBoolean("P_LBYZ");
                    boolean earlyOttoman = romanPotteryRS.getBoolean("P_EOTT");
                    boolean modern = romanPotteryRS.getBoolean("P_MOD");
                    boolean unknown = romanPotteryRS.getBoolean("P_UNKNOWN");
                    boolean roman = romanPotteryRS.getBoolean("P_ROM");
                    boolean byzantine = romanPotteryRS.getBoolean("P_BYZ");
                    boolean lateHellenisticEarlyRoman = romanPotteryRS.getBoolean("P_HELLEROM");
                    boolean classical = romanPotteryRS.getBoolean("P_CLASS");
                    boolean archaic = romanPotteryRS.getBoolean("P_ARCHAIC");
                    boolean hellenistic = romanPotteryRS.getBoolean("P_HELL");
                    boolean cooking = romanPotteryRS.getBoolean("F_COOKING");
                    boolean storage = romanPotteryRS.getBoolean("F_STORAGE");
                    boolean transport = romanPotteryRS.getBoolean("F_TRANSPORT");
                    boolean tableEat = romanPotteryRS.getBoolean("F_TBLEAT");
                    boolean tableDrink = romanPotteryRS.getBoolean("F_TBLDRINK");
                    boolean tableServe = romanPotteryRS.getBoolean("F_TBLSERVE");
                    boolean ritual = romanPotteryRS.getBoolean("F_RITUAL");
                    boolean lamp = romanPotteryRS.getBoolean("F_LAMP");
                    boolean toilet = romanPotteryRS.getBoolean("F_TOILET");
                    boolean weaving = romanPotteryRS.getBoolean("F_WEAVING");
                    boolean ceramicProduction = romanPotteryRS.getBoolean("F_CPRODUCT");
                    boolean utility = romanPotteryRS.getBoolean("F_UTILITY");
                    boolean other = romanPotteryRS.getBoolean("F_OTHER");
                    if (earlyRoman) { periodIndex++; }
                    if (middleRoman) { periodIndex++; }
                    if (lateRoman) { periodIndex++; }
                    if (earlyByzantine) { periodIndex++; }
                    if (middleByzantineI) { periodIndex++; }
                    if (middleByzantineII) { periodIndex++; }
                    if (lateByzantine) { periodIndex++; }
                    if (earlyOttoman) { periodIndex++; }
                    if (modern) { periodIndex++; }
                    if (unknown) { periodIndex++; }
                    if (roman) { periodIndex++; }
                    if (byzantine) { periodIndex++; }
                    if (lateHellenisticEarlyRoman) { periodIndex++; }
                    if (classical) { periodIndex++; }
                    if (archaic) { periodIndex++; }
                    if (hellenistic) { periodIndex++; }
                    if (cooking) { functionIndex++; }
                    if (storage) { functionIndex++; }
                    if (transport) { functionIndex++; }
                    if (tableEat) { functionIndex++; }
                    if (tableDrink) { functionIndex++; }
                    if (tableServe) { functionIndex++; }
                    if (ritual) { functionIndex++; }
                    if (lamp) { functionIndex++; }
                    if (toilet) { functionIndex++; }
                    if (weaving) { functionIndex++; }
                    if (ceramicProduction) { functionIndex++; }
                    if (utility) { functionIndex++; }
                    if (other) { functionIndex++; }
                    
                    int total = functionIndex * periodIndex;
                    if (total == 0) {
                        if (functionIndex == 0) {
                            other = true;
                            functionIndex = 1;
                        } else if (periodIndex == 0) {
                            unknown = true;
                            periodIndex = 1;
                        } else {
                            other = true;
                            unknown = true;
                            functionIndex = 1;
                            periodIndex = 1;
                        }
                        total = functionIndex * periodIndex;
                    }
                    
                    double percentage = (double)quantity/(double)total;
                    
                    if (cooking) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanCooking();
                            potteryValues.setEarlyRomanCooking(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanCooking();
                            potteryValues.setMiddleRomanCooking(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanCooking();
                            potteryValues.setLateRomanCooking(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineCooking();
                            potteryValues.setEarlyByzantineCooking(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineCooking();
                            potteryValues.setMiddleByzantineCooking(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineCooking();
                            potteryValues.setLateByzantineCooking(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanCooking();
                            potteryValues.setEarlyOttomanCooking(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernCooking();
                            potteryValues.setModernCooking(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownCooking();
                            potteryValues.setUnknownCooking(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanCooking();
                            potteryValues.setRomanCooking(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineCooking();
                            potteryValues.setByzantineCooking(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanCooking();
                            potteryValues.setLateHellenisticEarlyRomanCooking(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalCooking();
                            potteryValues.setClassicalCooking(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicCooking();
                            potteryValues.setArchaicCooking(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticCooking();
                            potteryValues.setHellenisticCooking(original+percentage);
                         }
                        }
                    if (storage) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanStorage();
                            potteryValues.setEarlyRomanStorage(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanStorage();
                            potteryValues.setMiddleRomanStorage(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanStorage();
                            potteryValues.setLateRomanStorage(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineStorage();
                            potteryValues.setEarlyByzantineStorage(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineStorage();
                            potteryValues.setMiddleByzantineStorage(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineStorage();
                            potteryValues.setLateByzantineStorage(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanStorage();
                            potteryValues.setEarlyOttomanStorage(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernStorage();
                            potteryValues.setModernStorage(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownStorage();
                            potteryValues.setUnknownStorage(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanStorage();
                            potteryValues.setRomanStorage(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineStorage();
                            potteryValues.setByzantineStorage(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanStorage();
                            potteryValues.setLateHellenisticEarlyRomanStorage(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalStorage();
                            potteryValues.setClassicalStorage(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicStorage();
                            potteryValues.setArchaicStorage(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticStorage();
                            potteryValues.setHellenisticStorage(original+percentage);
                         }
                    }
                    if (transport) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanTransport();
                            potteryValues.setEarlyRomanTransport(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanTransport();
                            potteryValues.setMiddleRomanTransport(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanTransport();
                            potteryValues.setLateRomanTransport(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineTransport();
                            potteryValues.setEarlyByzantineTransport(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineTransport();
                            potteryValues.setMiddleByzantineTransport(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineTransport();
                            potteryValues.setLateByzantineTransport(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanTransport();
                            potteryValues.setEarlyOttomanTransport(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernTransport();
                            potteryValues.setModernTransport(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownTransport();
                            potteryValues.setUnknownTransport(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanTransport();
                            potteryValues.setRomanTransport(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineTransport();
                            potteryValues.setByzantineTransport(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanTransport();
                            potteryValues.setLateHellenisticEarlyRomanTransport(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalTransport();
                            potteryValues.setClassicalTransport(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicTransport();
                            potteryValues.setArchaicTransport(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticTransport();
                            potteryValues.setHellenisticTransport(original+percentage);
                         }
                    }
                    if (tableEat) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanTableEat();
                            potteryValues.setEarlyRomanTableEat(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanTableEat();
                            potteryValues.setMiddleRomanTableEat(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanTableEat();
                            potteryValues.setLateRomanTableEat(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineTableEat();
                            potteryValues.setEarlyByzantineTableEat(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineTableEat();
                            potteryValues.setMiddleByzantineTableEat(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineTableEat();
                            potteryValues.setLateByzantineTableEat(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanTableEat();
                            potteryValues.setEarlyOttomanTableEat(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernTableEat();
                            potteryValues.setModernTableEat(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownTableEat();
                            potteryValues.setUnknownTableEat(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanTableEat();
                            potteryValues.setRomanTableEat(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineTableEat();
                            potteryValues.setByzantineTableEat(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanTableEat();
                            potteryValues.setLateHellenisticEarlyRomanTableEat(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalTableEat();
                            potteryValues.setClassicalTableEat(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicTableEat();
                            potteryValues.setArchaicTableEat(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticTableEat();
                            potteryValues.setHellenisticTableEat(original+percentage);
                         }
                    }
                    if (tableDrink) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanTableDrink();
                            potteryValues.setEarlyRomanTableDrink(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanTableDrink();
                            potteryValues.setMiddleRomanTableDrink(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanTableDrink();
                            potteryValues.setLateRomanTableDrink(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineTableDrink();
                            potteryValues.setEarlyByzantineTableDrink(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineTableDrink();
                            potteryValues.setMiddleByzantineTableDrink(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineTableDrink();
                            potteryValues.setLateByzantineTableDrink(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanTableDrink();
                            potteryValues.setEarlyOttomanTableDrink(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernTableDrink();
                            potteryValues.setModernTableDrink(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownTableDrink();
                            potteryValues.setUnknownTableDrink(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanTableDrink();
                            potteryValues.setRomanTableDrink(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineTableDrink();
                            potteryValues.setByzantineTableDrink(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanTableDrink();
                            potteryValues.setLateHellenisticEarlyRomanTableDrink(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalTableDrink();
                            potteryValues.setClassicalTableDrink(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicTableDrink();
                            potteryValues.setArchaicTableDrink(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticTableDrink();
                            potteryValues.setHellenisticTableDrink(original+percentage);
                         }
                    }
                    if (tableServe) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanTableService();
                            potteryValues.setEarlyRomanTableService(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanTableService();
                            potteryValues.setMiddleRomanTableService(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanTableService();
                            potteryValues.setLateRomanTableService(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineTableService();
                            potteryValues.setEarlyByzantineTableService(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineTableService();
                            potteryValues.setMiddleByzantineTableService(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineTableService();
                            potteryValues.setLateByzantineTableService(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanTableService();
                            potteryValues.setEarlyOttomanTableService(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernTableService();
                            potteryValues.setModernTableService(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownTableService();
                            potteryValues.setUnknownTableService(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanTableService();
                            potteryValues.setRomanTableService(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineTableService();
                            potteryValues.setByzantineTableService(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanTableService();
                            potteryValues.setLateHellenisticEarlyRomanTableService(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalTableService();
                            potteryValues.setClassicalTableService(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicTableService();
                            potteryValues.setArchaicTableService(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticTableService();
                            potteryValues.setHellenisticTableService(original+percentage);
                         }
                    }
                    if (ritual) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanRitual();
                            potteryValues.setEarlyRomanRitual(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanRitual();
                            potteryValues.setMiddleRomanRitual(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanRitual();
                            potteryValues.setLateRomanRitual(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineRitual();
                            potteryValues.setEarlyByzantineRitual(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineRitual();
                            potteryValues.setMiddleByzantineRitual(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineRitual();
                            potteryValues.setLateByzantineRitual(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanRitual();
                            potteryValues.setEarlyOttomanRitual(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernRitual();
                            potteryValues.setModernRitual(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownRitual();
                            potteryValues.setUnknownRitual(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanRitual();
                            potteryValues.setRomanRitual(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineRitual();
                            potteryValues.setByzantineRitual(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanRitual();
                            potteryValues.setLateHellenisticEarlyRomanRitual(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalRitual();
                            potteryValues.setClassicalRitual(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicRitual();
                            potteryValues.setArchaicRitual(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticRitual();
                            potteryValues.setHellenisticRitual(original+percentage);
                         }
                    }
                    if (lamp) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanLamp();
                            potteryValues.setEarlyRomanLamp(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanLamp();
                            potteryValues.setMiddleRomanLamp(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanLamp();
                            potteryValues.setLateRomanLamp(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineLamp();
                            potteryValues.setEarlyByzantineLamp(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineLamp();
                            potteryValues.setMiddleByzantineLamp(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineLamp();
                            potteryValues.setLateByzantineLamp(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanLamp();
                            potteryValues.setEarlyOttomanLamp(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernLamp();
                            potteryValues.setModernLamp(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownLamp();
                            potteryValues.setUnknownLamp(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanLamp();
                            potteryValues.setRomanLamp(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineLamp();
                            potteryValues.setByzantineLamp(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanLamp();
                            potteryValues.setLateHellenisticEarlyRomanLamp(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalLamp();
                            potteryValues.setClassicalLamp(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicLamp();
                            potteryValues.setArchaicLamp(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticLamp();
                            potteryValues.setHellenisticLamp(original+percentage);
                         }
                    }
                    if (toilet) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanToilet();
                            potteryValues.setEarlyRomanToilet(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanToilet();
                            potteryValues.setMiddleRomanToilet(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanToilet();
                            potteryValues.setLateRomanToilet(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineToilet();
                            potteryValues.setEarlyByzantineToilet(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineToilet();
                            potteryValues.setMiddleByzantineToilet(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineToilet();
                            potteryValues.setLateByzantineToilet(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanToilet();
                            potteryValues.setEarlyOttomanToilet(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernToilet();
                            potteryValues.setModernToilet(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownToilet();
                            potteryValues.setUnknownToilet(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanToilet();
                            potteryValues.setRomanToilet(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineToilet();
                            potteryValues.setByzantineToilet(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanToilet();
                            potteryValues.setLateHellenisticEarlyRomanToilet(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalToilet();
                            potteryValues.setClassicalToilet(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicToilet();
                            potteryValues.setArchaicToilet(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticToilet();
                            potteryValues.setHellenisticToilet(original+percentage);
                         }
                    }
                    if (weaving) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanWeaving();
                            potteryValues.setEarlyRomanWeaving(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanWeaving();
                            potteryValues.setMiddleRomanWeaving(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanWeaving();
                            potteryValues.setLateRomanWeaving(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineWeaving();
                            potteryValues.setEarlyByzantineWeaving(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineWeaving();
                            potteryValues.setMiddleByzantineWeaving(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineWeaving();
                            potteryValues.setLateByzantineWeaving(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanWeaving();
                            potteryValues.setEarlyOttomanWeaving(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernWeaving();
                            potteryValues.setModernWeaving(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownWeaving();
                            potteryValues.setUnknownWeaving(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanWeaving();
                            potteryValues.setRomanWeaving(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineWeaving();
                            potteryValues.setByzantineWeaving(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanWeaving();
                            potteryValues.setLateHellenisticEarlyRomanWeaving(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalWeaving();
                            potteryValues.setClassicalWeaving(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicWeaving();
                            potteryValues.setArchaicWeaving(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticWeaving();
                            potteryValues.setHellenisticWeaving(original+percentage);
                         }
                    }
                    if (ceramicProduction) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanCeramicProduction();
                            potteryValues.setEarlyRomanCeramicProduction(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanCeramicProduction();
                            potteryValues.setMiddleRomanCeramicProduction(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanCeramicProduction();
                            potteryValues.setLateRomanCeramicProduction(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineCeramicProduction();
                            potteryValues.setEarlyByzantineCeramicProduction(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineCeramicProduction();
                            potteryValues.setMiddleByzantineCeramicProduction(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineCeramicProduction();
                            potteryValues.setLateByzantineCeramicProduction(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanCeramicProduction();
                            potteryValues.setEarlyOttomanCeramicProduction(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernCeramicProduction();
                            potteryValues.setModernCeramicProduction(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownCeramicProduction();
                            potteryValues.setUnknownCeramicProduction(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanCeramicProduction();
                            potteryValues.setRomanCeramicProduction(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineCeramicProduction();
                            potteryValues.setByzantineCeramicProduction(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanCeramicProduction();
                            potteryValues.setLateHellenisticEarlyRomanCeramicProduction(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalCeramicProduction();
                            potteryValues.setClassicalCeramicProduction(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicCeramicProduction();
                            potteryValues.setArchaicCeramicProduction(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticCeramicProduction();
                            potteryValues.setHellenisticCeramicProduction(original+percentage);
                         }
                    }
                    if (utility) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanUtility();
                            potteryValues.setEarlyRomanUtility(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanUtility();
                            potteryValues.setMiddleRomanUtility(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanUtility();
                            potteryValues.setLateRomanUtility(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineUtility();
                            potteryValues.setEarlyByzantineUtility(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineUtility();
                            potteryValues.setMiddleByzantineUtility(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineUtility();
                            potteryValues.setLateByzantineUtility(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanUtility();
                            potteryValues.setEarlyOttomanUtility(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernUtility();
                            potteryValues.setModernUtility(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownUtility();
                            potteryValues.setUnknownUtility(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanUtility();
                            potteryValues.setRomanUtility(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineUtility();
                            potteryValues.setByzantineUtility(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanUtility();
                            potteryValues.setLateHellenisticEarlyRomanUtility(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalUtility();
                            potteryValues.setClassicalUtility(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicUtility();
                            potteryValues.setArchaicUtility(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticUtility();
                            potteryValues.setHellenisticUtility(original+percentage);
//                            System.out.println(romanPotteryRS.getInt("ID") + ": " + original + " + " + percentage);
                         }
                    }
                    if (other) { 
                        if (earlyRoman) { 
                            original = potteryValues.getEarlyRomanOther();
                            potteryValues.setEarlyRomanOther(original+percentage);
                        }
                        if (middleRoman) {  
                            original = potteryValues.getMiddleRomanOther();
                            potteryValues.setMiddleRomanOther(original+percentage);
                         }
                        if (lateRoman) {  
                            original = potteryValues.getLateRomanOther();
                            potteryValues.setLateRomanOther(original+percentage);
                         }
                        if (earlyByzantine) {  
                            original = potteryValues.getEarlyByzantineOther();
                            potteryValues.setEarlyByzantineOther(original+percentage);
                         }
                        if (middleByzantineI || middleByzantineII) {  
                            original = potteryValues.getMiddleByzantineOther();
                            potteryValues.setMiddleByzantineOther(original+percentage);
                         }
                        if (lateByzantine) {  
                            original = potteryValues.getLateByzantineOther();
                            potteryValues.setLateByzantineOther(original+percentage);
                         }
                        if (earlyOttoman) {  
                            original = potteryValues.getEarlyOttomanOther();
                            potteryValues.setEarlyOttomanOther(original+percentage);
                         }
                        if (modern) {  
                            original = potteryValues.getModernOther();
                            potteryValues.setModernOther(original+percentage);
                         }
                        if (unknown) {  
                            original = potteryValues.getUnknownOther();
                            potteryValues.setUnknownOther(original+percentage);
                         }
                        if (roman) {  
                            original = potteryValues.getRomanOther();
                            potteryValues.setRomanOther(original+percentage);
                         }
                        if (byzantine) {  
                            original = potteryValues.getByzantineOther();
                            potteryValues.setByzantineOther(original+percentage);
                         }
                        if (lateHellenisticEarlyRoman) {  
                            original = potteryValues.getLateHellenisticEarlyRomanOther();
                            potteryValues.setLateHellenisticEarlyRomanOther(original+percentage);
                         }
                        if (classical) {  
                            original = potteryValues.getClassicalOther();
                            potteryValues.setClassicalOther(original+percentage);
                         }
                        if (archaic) {  
                            original = potteryValues.getArchaicOther();
                            potteryValues.setArchaicOther(original+percentage);
                         }
                        if (hellenistic) {  
                            original = potteryValues.getHellenisticOther();
                            potteryValues.setHellenisticOther(original+percentage);
                         }
                    }
                }
                potteryValues.setId(potteryCounter);
                potteryValues.setSquareId(squareId);
                potteryValues.setLegacyId(legacyId);
                potteryValues.setPotteryReadTotal(readPottery);
                potteryList.add(potteryValues);
                potteryCounter++;
                // debugging
//                if (potteryCounter == 20) break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {}
            try {
                hellenisticPotteryDetailsRS.close();
            } catch (Exception ex) {}
            try {
                getHellenisticPotteryCountsPS.close();
            } catch (Exception ex) {}
            try {
                hellenisticPotteryRS.close();
            } catch (Exception ex) {}
            try {
                romanPotteryRS.close();
            } catch (Exception ex) {}
            try {
                prehistoricPotteryRS.close();
            } catch (Exception ex) {}
            try {
                getHellenisticPotteryDetailsPS.close();
            } catch (Exception ex) {}
            try {
                getHellenisticPotteryCountsPS.close();
            } catch (Exception ex) {}
            try {
                getHellenisticPotteryPS.close();
            } catch (Exception ex) {}
            try {
                getPrehistoricPotteryPS.close();
            } catch (Exception ex) {}
            try {
                getRomanPotteryPS.close();
            } catch (Exception ex) {}
            try {
                stmt.close();
            } catch (Exception ex) {}
        }
    }
    
    public void loadRomanModel() {
        PotteryTableRoman romanTable = new PotteryTableRoman();
        try {
            romanTable.connect();
            romanTable.setAuto(true);
            romanTable.process();
            romanPotteryList = romanTable.getRomanPotteryList();
        } finally {
            romanTable.disconnect();
        }
    }
    
    public void loadHellenisticModel() {
        PotteryTableHellenistic hellenisticTable = new PotteryTableHellenistic();
        try {
            hellenisticTable.connect();
            hellenisticTable.process();
            hellenisticPotteryList = hellenisticTable.getHellenisticPotteryList();
        } finally {
            hellenisticTable.disconnect();
        }
    }
    
    public void writeRomanCSV() {
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter("/home/mdc502/projects/sikyon/db/roman-export.csv"));
            for (RomanPottery romanPottery : romanPotteryList) {
                csvWriter.writeNext(romanPottery.getArray());
            }
            csvWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MainDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeHellenisticCSV() {
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter("/home/mdc502/projects/sikyon/db/hell-export.csv"));
            for (HellenisticPottery hellenisticPottery : hellenisticPotteryList) {
                csvWriter.writeNext(hellenisticPottery.getArray());
            }
            csvWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MainDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writePotteryCSV() {
        try {CSVWriter csvWriter = new CSVWriter(new FileWriter("/home/mdc502/projects/sikyon/db/pottery-export.csv"));
        csvWriter.writeNext(Pottery.getHeaders());    
        for (Pottery pottery : potteryList) {
                csvWriter.writeNext(pottery.getArray());
            }
            csvWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MainDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateHellenisticLegacyIds() {
        for (HellenisticPottery hellenisticPottery : hellenisticPotteryList) {
            String petersId = hellenisticPottery.getSquareName();
            if (petersId != null) {
                String[] qualifierArray = petersId.split(" ", 3);

                if (qualifierArray.length > 2) {
                    String qualifier = qualifierArray[2];
                    hellenisticPottery.setSquareQualifier(qualifier);
                    petersId = qualifierArray[0] + qualifierArray[1];
                } else if (qualifierArray.length == 2 && qualifierArray[1].endsWith("?")) {
                    hellenisticPottery.setSquareQualifier("?");
                    petersId = petersId.substring(0, petersId.length()-1);
                }
                
                petersId = petersId.toUpperCase().replace(" ", "");
                petersId = petersId.replace("RE", "R");
                String[] petersArray = petersId.split("\\.");
                if (petersArray.length == 2) {
                    if (petersArray[0].length() == 3) {
                        petersArray[0] = petersArray[0].replace("P", "P0");
                    }
                    if (petersArray[1].length() == 1 || (petersArray[1].endsWith("R") && petersArray[1].length() == 2)) {
                        petersArray[1] = "0" + petersArray[1];
                    }
                    petersId = petersArray[0] + "." +  petersArray[1];
//                    System.out.println(petersId);
                    hellenisticPottery.setLegacyId(petersId);
                }
            } else {
                System.out.println("NULL");
            }
        }
    }
    
    public void updateHellenisticSquareIds() {
        for (HellenisticPottery hellenisticPottery : hellenisticPotteryList) {
            hellenisticPottery.setSquareId(getSquareIds(hellenisticPottery.getLegacyId()));
        }
    }
    
    public void updateRomanSquareIds() {
        for (RomanPottery romanPottery : romanPotteryList) {
            romanPottery.setSquareID(getSquareIds(romanPottery.getLegacyID()));
        }
    }
    
    public int getSquareIds(String legacyID) {
        int squareID = -1;
        
        try {
            getSquareIds = getConnection().prepareStatement(SELECT_LEGACY_ID);
            
            getSquareIds.clearParameters();
            getSquareIds.setString(1, legacyID);
            rs = getSquareIds.executeQuery();
            
            if (rs.next()) {
                squareID = rs.getInt("ID");
            } else {
                System.out.println(legacyID);
                System.out.println("\tNO CORRESPONDING ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {System.out.println("rs is null or already closed");}
            try {
                getSquareIds.close();
            } catch (Exception ex) {System.out.println("getRomSquares is null or already closed");}
            
        }
        return squareID;
    }

    private void updateSquareNames() {
        try {
            getLegacyIDs = getConnection().createStatement();
            rs = getLegacyIDs.executeQuery(selectLegacyIDs);

            updateLegacyIDs = getConnection().prepareStatement(updateRomPottery1);
            while (rs.next()) {
                String name = rs.getString(2);
                System.out.println(name);
                updateLegacyIDs.clearParameters();
                updateLegacyIDs.setString(1, name);
                updateLegacyIDs.setInt(2, rs.getInt(1));
                updateLegacyIDs.executeUpdate();
            }
        } catch (SQLException sqlEx) {

        } finally {
            try {
                rs.close();
            } catch (Exception ex) {}
            try {
                getLegacyIDs.close();
            } catch (Exception ex) {}
            try {
                updateLegacyIDs.close();
            } catch (Exception ex) {}
        }
    }

    private void loadRomanRecords() {
        try {
            getSquareIds = getConnection().prepareStatement(findSquareId);
            insertRomanRecordsPS = getConnection().prepareStatement(insertRomanRecord);
            
            // Loop through the romanPotteryList and insert values into db
            for (RomanPottery romanPottery : romanPotteryList){
                String squareName = romanPottery.getLegacyID();
                System.out.println(squareName);

                // construct tract, square, and type for query
                squareIdRS = getNewSquareIds(romanPottery.getLegacyID(), romanPottery.getType());

                int foreignKey = -1;
                if (squareIdRS.next()) {
                    foreignKey = squareIdRS.getInt("id");
//                    System.out.println(foreignKey);
                    romanPottery.setSquareID(foreignKey);
                } else {
                    System.out.println(romanPottery.getLegacyID() + "   **** NO RESULTS!!! ****");
                }

                insertRomanRecordsPS.clearParameters();
                insertRomanRecordsPS.setInt(1, romanPottery.getId());
                insertRomanRecordsPS.setInt(2, romanPottery.getSquareID());
                insertRomanRecordsPS.setString(3, squareName);
                insertRomanRecordsPS.setString(4, romanPottery.getIndex());
                insertRomanRecordsPS.setString(5, romanPottery.getCatalogue());
                insertRomanRecordsPS.setString(6, romanPottery.getDate());
                insertRomanRecordsPS.setString(7, romanPottery.getShape());
                insertRomanRecordsPS.setString(8, romanPottery.getForm());
                insertRomanRecordsPS.setInt(9, romanPottery.getQuantity());
                insertRomanRecordsPS.setString(10, romanPottery.getFabric());
                insertRomanRecordsPS.setString(11, romanPottery.getNotes());
                insertRomanRecordsPS.setString(12, romanPottery.getBiblio());
                insertRomanRecordsPS.setString(13, romanPottery.getNoRom());
                insertRomanRecordsPS.setString(14, romanPottery.getP_ERom());
                insertRomanRecordsPS.setString(15, romanPottery.getP_MidRom());
                insertRomanRecordsPS.setString(16, romanPottery.getP_LRom());
                insertRomanRecordsPS.setString(17, romanPottery.getP_EByz());
                insertRomanRecordsPS.setString(18, romanPottery.getP_MidByzI());
                insertRomanRecordsPS.setString(19, romanPottery.getP_MidByzII());
                insertRomanRecordsPS.setString(20, romanPottery.getP_LByz());
                insertRomanRecordsPS.setString(21, romanPottery.getP_EOtt());
                insertRomanRecordsPS.setString(22, romanPottery.getP_LOtt());
                insertRomanRecordsPS.setString(23, romanPottery.getP_Mod());
                insertRomanRecordsPS.setString(24, romanPottery.getP_Unknown());
                insertRomanRecordsPS.setString(25, romanPottery.getP_Rom());
                insertRomanRecordsPS.setString(26, romanPottery.getP_Byz());
                insertRomanRecordsPS.setString(27, romanPottery.getP_HellERom());
                insertRomanRecordsPS.setString(28, romanPottery.getP_Class());
                insertRomanRecordsPS.setString(29, romanPottery.getP_Archaic());
                insertRomanRecordsPS.setString(30, romanPottery.getP_Hell());
                insertRomanRecordsPS.setString(31, romanPottery.getV_Rim());
                insertRomanRecordsPS.setString(32, romanPottery.getV_Neck());
                insertRomanRecordsPS.setString(33, romanPottery.getV_Shoulder());
                insertRomanRecordsPS.setString(34, romanPottery.getV_Handle());
                insertRomanRecordsPS.setString(35, romanPottery.getV_Body());
                insertRomanRecordsPS.setString(36, romanPottery.getV_Base());
                insertRomanRecordsPS.setString(37, romanPottery.getV_Foot());
                insertRomanRecordsPS.setString(38, romanPottery.getV_Other());
                insertRomanRecordsPS.setString(39, romanPottery.getV_Nozzle());
                insertRomanRecordsPS.setString(40, romanPottery.getV_Disc());
                insertRomanRecordsPS.setString(41, romanPottery.getV_Lower());
                insertRomanRecordsPS.setString(42, romanPottery.getV_Upper());
                insertRomanRecordsPS.setString(43, romanPottery.getV_Spout());
                insertRomanRecordsPS.setString(44, romanPottery.getF_Cooking());
                insertRomanRecordsPS.setString(45, romanPottery.getF_Storage());
                insertRomanRecordsPS.setString(46, romanPottery.getF_Transport());
                insertRomanRecordsPS.setString(47, romanPottery.getF_TblEat());
                insertRomanRecordsPS.setString(48, romanPottery.getF_tblDrink());
                insertRomanRecordsPS.setString(49, romanPottery.getF_tblServ());
                insertRomanRecordsPS.setString(50, romanPottery.getF_Ritual());
                insertRomanRecordsPS.setString(51, romanPottery.getF_Lamp());
                insertRomanRecordsPS.setString(52, romanPottery.getF_Toilet());
                insertRomanRecordsPS.setString(53, romanPottery.getF_Weaving());
                insertRomanRecordsPS.setString(54, romanPottery.getF_CProduct());
                insertRomanRecordsPS.setString(55, romanPottery.getF_Utility());
                insertRomanRecordsPS.setString(56, romanPottery.getF_Other());
                insertRomanRecordsPS.setString(57, romanPottery.getD_notes());
                insertRomanRecordsPS.setString(58, romanPottery.getD_slip());
                insertRomanRecordsPS.setString(59, romanPottery.getD_Glaze());
                insertRomanRecordsPS.setString(60, romanPottery.getD_Paint());
                insertRomanRecordsPS.setString(61, romanPottery.getD_Incised());
                insertRomanRecordsPS.setString(62, romanPottery.getD_Stamped());
                insertRomanRecordsPS.setString(63, romanPottery.getD_Moulded());
                insertRomanRecordsPS.setString(64, romanPottery.getImport());
                insertRomanRecordsPS.setString(65, romanPottery.getFw());
                insertRomanRecordsPS.setString(66, romanPottery.getImitation());
                insertRomanRecordsPS.executeUpdate();
                romanCounter++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("****************************************");
            SQLException sqlEx = ex.getNextException();
            sqlEx.printStackTrace();
        } finally {
            try {
                squareIdRS.close();
            } catch (Exception ex) {}
            try {
                insertRomanRecordsPS.close();
            } catch (Exception ex) {}
        }
    }

    private void processHellenisticRecords() {
        try {
            getHellenisitcRecords = getConnection().createStatement();
            rs = getHellenisitcRecords.executeQuery(selectHellRecords);

            // do some cleaning/prep work
            // set all shape elements to lowercase via SQL
//            setHellShapes = getConnection().createStatement();
//            setHellShapes.executeUpdate(setHellShapeToLowerCase);
//            System.out.println("SHAPE changed to lowercase!!!");

//            deleteSquares = getConnection().createStatement();
//            deleteSquares.executeUpdate(deleteBadSquares);
//            System.out.println("Deleted Peter's bogus squares!!!");

            getSquareIds = getConnection().prepareStatement(findSquareId);
            insertClasses = getConnection().prepareStatement(insertClassValues);
            insertDates = getConnection().prepareStatement(insertDateValues);
            insertVessels = getConnection().prepareStatement(insertVesselValues);
            updateHellSquareIDs = getConnection().prepareStatement(updateHellSquareIDsSQL);

            while (rs.next()) {
                String squareName = rs.getString("squareName");
                if (squareName == null) {
                    continue;
                } else {
                    squareName = squareName.replace(" ", "").toUpperCase();
                }
                int potID = rs.getInt("id");
                String type = null;

                // clean out any peter funniness
                if (squareName.endsWith("RE")) {
                    squareName = squareName.substring(0, squareName.length()-2);
                    type = "R";
                }
                if (squareName.endsWith("TWOWALKERS")) {
                    squareName = squareName.substring(0, squareName.length()-10);
                }
                if (squareName.endsWith("EASTENDOFKNOLL")) {
                    // stupid hack for treasure hunting
                    squareName = squareName.substring(0, squareName.length()-14) + ".23";
                }
                if (squareName.contains("EASTENDOFKNOLL.")) {
                    squareName = squareName.replace("EASTENDOFKNOLL.", ".");
                }
                if (squareName.contains("SOSL")) {
                    squareName = squareName.replace("SOSL1", "");
                    if (squareName.endsWith("11")) {
                        squareName = squareName.replace("11", "8");
                    } else if (squareName.endsWith("12")) {
                        squareName = squareName.replace("12", "9");
                    } else if (squareName.endsWith("13")) {
                        squareName = squareName.replace("13", "10");
                    }
                }
                if (squareName.endsWith("CLEANING")) {
                    squareName = squareName.substring(0, squareName.length()-8);
                }
                while (squareName.contains(".0")) {
                    squareName = squareName.replace(".0", ".");
                }
// PETER: what are the ?'s for
                if (squareName.endsWith("?")) {
                    squareName = squareName.substring(0, squareName.length()-1);
                }
// PETER: what the hell is ext?
                if (squareName.endsWith("EXT")) {
//                    System.out.println("SP76 EXT!!!");
                    continue;
                }
// PETER: what is geophys?
                if (squareName.endsWith("GEOPHYS")) {
                    // don't know where this one goes so am adding it to 10
                    squareName = squareName.substring(0, squareName.length()-7) + ".10";
                }
                if (squareName.endsWith("JULY")) {
                    // don't know where this one goes so am adding it to 10
                    squareName = squareName.substring(0, squareName.length()-4);
                }
                System.out.print(squareName);

                squareIdRS = getNewSquareIds(squareName, type);

                if (squareIdRS.next()) {
                    int primaryKey = squareIdRS.getInt("id");
                    System.out.println("      " + primaryKey);
                    updateHellSquareIDs.clearParameters();
                    updateHellSquareIDs.setInt(1, primaryKey);
                    updateHellSquareIDs.setInt(2, potID);
                    updateHellSquareIDs.executeUpdate();

                    hellenisticCounter++;
                } else {
                    System.out.println(" **** NO RESULTS!!! ****");
                }

//                if ("UP61.4".equals(squareName)) {
//                    System.out.println("");
//                }

                // split out the classes
//                String hellClass = rs.getString("Class");
//                if (hellClass != null) {
//                    String[] classList = hellClass.split("[|]");
//
//                    for (String s : classList) {
//                        insertClasses.clearParameters();
//                        insertClasses.setInt(1, potID);
//                        insertClasses.setString(2, s);
//                        insertClasses.executeUpdate();
////                        System.out.println("\tCLASS:" + s);
//                    }
//                }

                // split out the date ranges
//                String dateRange = rs.getString("DateRanges");
//                if (dateRange != null) {
//                    String[] dateList = dateRange.split("[|]");
//
//                    for (String s : dateList) {
//                        insertDates.clearParameters();
//                        insertDates.setInt(1, potID);
//                        insertDates.setString(2, s);
//                        insertDates.executeUpdate();
////                        System.out.println("\tDATE:" + s);
//                    }
//                }

                // split out the Vessel Portion
//                String vesselPortion = rs.getString("VesselPortion");
//                if (vesselPortion != null) {
//                    String[] vesselList = vesselPortion.split("[|]");
//
//                    for (String s : vesselList) {
//                        insertVessels.clearParameters();
//                        insertVessels.setInt(1, potID);
//                        insertVessels.setString(2, s);
//                        insertVessels.executeUpdate();
////                        System.out.println("\tVESSEL PORTION:" + s);
//                    }
//                }
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
                System.out.println("rs is already closed or null!");
            }
            try {
                squareIdRS.close();
            } catch (Exception ex) {
                System.out.println("squareIdRS is already closed or null!");
            }
            try {
                getHellenisitcRecords.close();
            } catch (Exception ex) {
                System.out.println("getHellenisitcRecords is already closed or null!");
            }
            try {
                getSquareIds.close();
            } catch (Exception ex) {
                System.out.println("getSquareIds is already closed or null!");
            }
            try {
                deleteSquares.close();
            } catch (Exception ex) {
                System.out.println("deleteSquares is already closed or null!");
            }
            try {
                setHellShapes.close();
            } catch (Exception ex) {
                System.out.println("setHellShapes is already closed or null!");
            }
            try {
                insertClasses.close();
            } catch (Exception ex) {
                System.out.println("insertClasses is already closed or null!");
            }
            try {
                insertDates.close();
            } catch (Exception ex) {
                System.out.println("insertDates is already closed or null!");
            }
            try {
                insertVessels.close();
            } catch (Exception ex) {
                System.out.println("insertVessels is already closed or null!");
            }
        }
    }

    private ResultSet getNewSquareIds(String squareName, String type) throws SQLException {
        String[] tractSquare = squareName.split("\\.");
        getSquareIds.clearParameters();
        getSquareIds.setString(1, tractSquare[0]);
        if (tractSquare.length == 1) {
            getSquareIds.setInt(2, 0);
        } else {
            getSquareIds.setInt(2, Integer.parseInt(tractSquare[1].trim()));
        }
        if (type != null) {
            getSquareIds.setString(3, type);
        } else {
            getSquareIds.setNull(3, Types.NULL);
        }
        return getSquareIds.executeQuery();
    }
}
