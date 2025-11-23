package net.charno.db.scripts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import net.charno.db.DBConnectionHandler;
import net.charno.db.model.RomanPottery;

public class PotteryTableRoman extends DBConnectionHandler {
    private static final String URL = "jdbc:access:////home/mdc502/projects/sikyon/db/ssp_main.mdb";
    
    private static final String setRomShapeToLowerCase = "update Main set shape = lower(shape)";
    private static final String SELECT_POTTERY_ROMAN = "select * from POTTERY_ROMAN";

    private Statement setRomShapes, getRomanRecords;
    private PreparedStatement getSquareIds;
    private ResultSet rs;

    private List<RomanPottery> romanPotteryList = new ArrayList<RomanPottery>();

    private String squareName;
    private static int id = 1;

    public PotteryTableRoman() {
        super("", "", URL, DBConnectionHandler.ACCESS);
    }

    @Override
    public void process() {

        try {
            // set all shape elements to lowercase via SQL
//            setRomShapes = getConnection().createStatement();
//            setRomShapes.executeUpdate(setRomShapeToLowerCase);
//            System.out.println("SHAPE changed to lowercase!!!");

            // get Roman Pottery records
            getRomanRecords = getConnection().createStatement();
            rs = getRomanRecords.executeQuery(SELECT_POTTERY_ROMAN);

            // loop through the records
            while (rs.next()) {
                String type = null;
                squareName = rs.getString("squareName");
                squareName = squareName.replace("P0", "P").replace("T0", "T").replace("PO", "P").replace("P ", "P");
                squareName = squareName.replace(" R", "R").replace("a", "");
                squareName = squareName.replace("/", ".");
                
                // start cleaning for Elli specific stuff
                
                if (squareName.equals("SP07.04")) {
                    squareName = "SP07.03";
                }
                
                if (squareName.equals("Totl collection")) {
                    squareName = "Total collection";
                }
                
                // date field
                String date = rs.getString("date");
//                if (date != null) {
//                    if ("5th -7th c.".equals(date)) {
////                        System.out.println("\tDATE to '5th - 7th c.'!!!");
//                        date = "5th - 7th c.";
//                    } else if ("Ca. 1500".equals(date)) {
////                        System.out.println("\tDATE to 'ca. 1500'!!!");
//                        date = "ca. 1500";
//                    }
//                }

                // Start populating bean with other shite
                RomanPottery romanPottery = new RomanPottery();
                romanPottery.setId(id);
                romanPottery.setLegacyID(squareName);
                romanPottery.setIndex(rs.getString("index"));
                romanPottery.setCatalogue(rs.getString("catalogue"));
                romanPottery.setDate(date);
                romanPottery.setShape(rs.getString("shape"));
                romanPottery.setForm(rs.getString("form"));
                romanPottery.setQuantity(rs.getInt("quantity"));
                romanPottery.setFabric(rs.getString("fabric"));
                romanPottery.setNotes(rs.getString("notes"));
                romanPottery.setBiblio(rs.getString("biblio"));
                romanPottery.setNoRom(rs.getString("noRom"));
                romanPottery.setP_ERom(rs.getString("p_ERom"));
                romanPottery.setP_MidRom(rs.getString("p_MidRom"));
                romanPottery.setP_LRom(rs.getString("p_LRom"));
                romanPottery.setP_EByz(rs.getString("p_EByz"));
                romanPottery.setP_MidByzI(rs.getString("p_MidByzI"));
                romanPottery.setP_MidByzII(rs.getString("p_MidByzII"));
                romanPottery.setP_LByz(rs.getString("p_LByz"));
                romanPottery.setP_EOtt(rs.getString("p_EOtt"));
                romanPottery.setP_LOtt(rs.getString("p_LOtt"));
                romanPottery.setP_Mod(rs.getString("p_Mod"));
                romanPottery.setP_Unknown(rs.getString("p_Unknown"));
                romanPottery.setP_Rom(rs.getString("p_Rom"));
                romanPottery.setP_Byz(rs.getString("p_Byz"));
                romanPottery.setP_HellERom(rs.getString("p_HellERom"));
                romanPottery.setP_Class(rs.getString("p_Class"));
                romanPottery.setP_Archaic(rs.getString("p_Archaic"));
                romanPottery.setP_Hell(rs.getString("p_Hell"));
                romanPottery.setV_Rim(rs.getString("v_Rim"));
                romanPottery.setV_Neck(rs.getString("v_Neck"));
                romanPottery.setV_Shoulder(rs.getString("v_Shoulder"));
                romanPottery.setV_Handle(rs.getString("v_Handle"));
                romanPottery.setV_Body(rs.getString("v_Body"));
                romanPottery.setV_Base(rs.getString("v_Base"));
                romanPottery.setV_Foot(rs.getString("v_Foot"));
                romanPottery.setV_Other(rs.getString("v_Other"));
                romanPottery.setV_Nozzle(rs.getString("v_Nozzle"));
                romanPottery.setV_Disc(rs.getString("v_Disc"));
                romanPottery.setV_Lower(rs.getString("v_Lower"));
                romanPottery.setV_Upper(rs.getString("v_Upper"));
                romanPottery.setV_Spout(rs.getString("v_Spout"));
                romanPottery.setF_Cooking(rs.getString("f_Cooking"));
                romanPottery.setF_Storage(rs.getString("f_Storage"));
                romanPottery.setF_Transport(rs.getString("f_Transport"));
                romanPottery.setF_TblEat(rs.getString("f_TblEat"));
                romanPottery.setF_tblDrink(rs.getString("f_tblDrink"));
                romanPottery.setF_tblServ(rs.getString("f_tblServ"));
                romanPottery.setF_Ritual(rs.getString("f_Ritual"));
                romanPottery.setF_Lamp(rs.getString("f_Lamp"));
                romanPottery.setF_Toilet(rs.getString("f_Toilet"));
                romanPottery.setF_Weaving(rs.getString("f_Weaving"));
                romanPottery.setF_CProduct(rs.getString("f_CProduct"));
                romanPottery.setF_Utility(rs.getString("f_Utility"));
                romanPottery.setF_Other(rs.getString("f_Other"));
                romanPottery.setD_notes(rs.getString("d_notes"));
                romanPottery.setD_slip(rs.getString("d_Slip"));
                romanPottery.setD_Glaze(rs.getString("d_Glaze"));
                romanPottery.setD_Paint(rs.getString("d_Paint"));
                romanPottery.setD_Incised(rs.getString("d_Incised"));
                romanPottery.setD_Stamped(rs.getString("d_Stamped"));
                romanPottery.setD_Moulded(rs.getString("d_Moulded"));
                romanPottery.setImport(rs.getString("import"));
                romanPottery.setFw(rs.getString("fw"));
                romanPottery.setImitation(rs.getString("imitation"));


                romanPotteryList.add(romanPottery);
                id++;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                getRomanRecords.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
    }

    public List<RomanPottery> getRomanPotteryList() {
        return romanPotteryList;
    }

    public void setRomanPotteryList(List<RomanPottery> romanPotteryList) {
        this.romanPotteryList = romanPotteryList;
    }

}
