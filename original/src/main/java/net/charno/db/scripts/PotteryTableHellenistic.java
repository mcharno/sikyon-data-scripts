package net.charno.db.scripts;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.charno.db.DBConnectionHandler;
import net.charno.db.model.HellenisticPottery;

public class PotteryTableHellenistic extends DBConnectionHandler {
    private static final String URL = "jdbc:access:////home/mdc502/projects/sikyon/db/ssp_main.mdb";
    
    private static final String SELECT_HELLENISTIC_RECORDS = "select * from POTTERY_HELLENISTIC_BU";
    
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    private List<HellenisticPottery> hellenisticPotteryList = new ArrayList<HellenisticPottery>();

    public PotteryTableHellenistic() {
        super("", "", URL, DBConnectionHandler.ACCESS);
    }
    
    @Override
    public void process() {
        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery(SELECT_HELLENISTIC_RECORDS);
            
            CSVWriter csvWriter = new CSVWriter(new FileWriter("/home/mdc502/projects/sikyon/db/century-export.csv"));
            
            while (rs.next()) {
                HellenisticPottery hellenisticPottery = new HellenisticPottery();
                
                int id = rs.getInt("ID");
                
                System.out.println(rs.getString("Quantity"));
                
                hellenisticPottery.setId(id);
                hellenisticPottery.setQuantity(rs.getString("QUANTITY"));
                hellenisticPottery.setHellRom(rs.getString("HELLROM"));
                hellenisticPottery.setIndexSherds(rs.getString("INDEXSHERDS"));
                hellenisticPottery.setNotes(rs.getString("NOTES"));
                hellenisticPottery.setNothingIdentified(rs.getString("NOTHINGIDENTIFIED"));
                hellenisticPottery.setFabric(rs.getString("FABRIC"));
                hellenisticPottery.setShape(rs.getString("SHAPE"));
                hellenisticPottery.setType(rs.getString("TYPE"));
                hellenisticPottery.setSquareName(rs.getString("SQUARENAME"));
                
                hellenisticPotteryList.add(hellenisticPottery);
                
                // create new csv for centuries!!!
                String centuries = rs.getString("CENTURIES");
                if (centuries != null) {
                    String[] centuryList = centuries.split("[|]");
                    for (String century : centuryList) {
                        csvWriter.writeNext(new String[]{String.valueOf(id),century});
                    }
                }
            }
            csvWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(PotteryTableHellenistic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PotteryTableHellenistic.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) { System.out.println("rs not closed..."); }
            try {
                stmt.close();
            } catch (Exception ex) { System.out.println("stmt not closed..."); }
        }
    }

    public List<HellenisticPottery> getHellenisticPotteryList() {
        return hellenisticPotteryList;
    }

    public void setHellenisticPotteryList(List<HellenisticPottery> hellenisticPotteryList) {
        this.hellenisticPotteryList = hellenisticPotteryList;
    }
    
    
}
