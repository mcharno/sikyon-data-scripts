package net.charno.db.model;

public class RomanPottery {
    private String legacyID, type, index, catalogue, date, shape, form, fabric, notes, biblio, noRom, _import, fw, imitation;
    private String p_ERom, p_MidRom, p_LRom, p_EByz, p_MidByzI, p_MidByzII, p_LByz, p_EOtt, p_LOtt, p_Mod, p_Unknown, p_Rom, p_Byz, p_HellERom, p_Class, p_Archaic, p_Hell;
    private String v_Rim, v_Neck, v_Shoulder, v_Handle, v_Body, v_Base, v_Foot, v_Other, v_Nozzle, v_Disc, v_Lower, v_Upper, v_Spout;
    private String f_Cooking, f_Storage, f_Transport, f_TblEat, f_tblDrink, f_tblServ, f_Ritual, f_Lamp, f_Toilet, f_Weaving, f_CProduct, f_Utility, f_Other;
    private String d_notes, d_slip, d_Glaze, d_Paint, d_Incised, d_Stamped, d_Moulded;
    private int id, squareID, quantity;

    public String[] getArray() {
        String[] row = new String[66];
        row[0] = String.valueOf(id);
        row[1] = String.valueOf(squareID);
        row[2] = legacyID;
        row[3] = index;
        row[4] = catalogue;
        row[5] = date;
        row[6] = shape;
        row[7] = form;
        row[8] = String.valueOf(quantity);
        row[9] = fabric;
        row[10] = notes;
        row[11] = biblio;
        row[12] = noRom;
        row[13] = p_ERom;
        row[14] = p_MidRom;
        row[15] = p_LRom;
        row[16] = p_EByz;
        row[17] = p_MidByzI;
        row[18] = p_MidByzII;
        row[19] = p_LByz;
        row[20] = p_EOtt;
        row[21] = p_LOtt;
        row[22] = p_Mod;
        row[23] = p_Unknown;
        row[24] = p_Rom;
        row[25] = p_Byz;
        row[26] = p_HellERom;
        row[27] = p_Class;
        row[28] = p_Archaic;
        row[29] = p_Hell;
        row[30] = v_Rim;
        row[31] = v_Neck;
        row[32] = v_Shoulder;
        row[33] = v_Handle;
        row[34] = v_Body;
        row[35] = v_Base;
        row[36] = v_Foot;
        row[37] = v_Other;
        row[38] = v_Nozzle;
        row[39] = v_Disc;
        row[40] = v_Lower;
        row[41] = v_Upper;
        row[42] = v_Spout;
        row[43] = f_Cooking;
        row[44] = f_Storage;
        row[45] = f_Transport;
        row[46] = f_TblEat;
        row[47] = f_tblDrink;
        row[48] = f_tblServ;
        row[49] = f_Ritual;
        row[50] = f_Lamp;
        row[51] = f_Toilet;
        row[52] = f_Weaving;
        row[53] = f_CProduct;
        row[54] = f_Utility;
        row[55] = f_Other;
        row[56] = d_notes;
        row[57] = d_slip;
        row[58] = d_Glaze;
        row[59] = d_Paint;
        row[60] = d_Incised;
        row[61] = d_Stamped;
        row[62] = d_Moulded;
        row[63] = _import;
        row[64] = fw;
        row[65] = imitation;
        
        return row;
    }
    
    
    public int getSquareID() {
        return squareID;
    }

    public void setSquareID(int squareID) {
        this.squareID = squareID;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBiblio() {
        return biblio;
    }

    public void setBiblio(String biblio) {
        this.biblio = biblio;
    }

    public String getNoRom() {
        return noRom;
    }

    public void setNoRom(String noRom) {
        this.noRom = noRom;
    }

    public String getImport() {
        return _import;
    }

    public void setImport(String _import) {
        this._import = (_import);
    }

    public String getFw() {
        return fw;
    }

    public void setFw(String fw) {
        this.fw = fw;
    }

    public String getImitation() {
        return imitation;
    }

    public void setImitation(String imitation) {
        this.imitation = imitation;
    }

    public String getP_ERom() {
        return p_ERom;
    }

    public void setP_ERom(String p_ERom) {
        this.p_ERom = p_ERom;
    }

    public String getP_MidRom() {
        return p_MidRom;
    }

    public void setP_MidRom(String p_MidRom) {
        this.p_MidRom = p_MidRom;
    }

    public String getP_LRom() {
        return p_LRom;
    }

    public void setP_LRom(String p_LRom) {
        this.p_LRom = p_LRom;
    }

    public String getP_EByz() {
        return p_EByz;
    }

    public void setP_EByz(String p_EByz) {
        this.p_EByz = p_EByz;
    }

    public String getP_MidByzI() {
        return p_MidByzI;
    }

    public void setP_MidByzI(String p_MidByzI) {
        this.p_MidByzI = p_MidByzI;
    }

    public String getP_MidByzII() {
        return p_MidByzII;
    }

    public void setP_MidByzII(String p_MidByzII) {
        this.p_MidByzII = p_MidByzII;
    }

    public String getP_LByz() {
        return p_LByz;
    }

    public void setP_LByz(String p_LByz) {
        this.p_LByz = p_LByz;
    }

    public String getP_EOtt() {
        return p_EOtt;
    }

    public void setP_EOtt(String p_EOtt) {
        this.p_EOtt = p_EOtt;
    }

    public String getP_LOtt() {
        return p_LOtt;
    }

    public void setP_LOtt(String p_Ott) {
        this.p_LOtt = p_Ott;
    }

    public String getP_Mod() {
        return p_Mod;
    }

    public void setP_Mod(String p_Mod) {
        this.p_Mod = p_Mod;
    }

    public String getP_Unknown() {
        return p_Unknown;
    }

    public void setP_Unknown(String p_Unknown) {
        this.p_Unknown = p_Unknown;
    }

    public String getP_Rom() {
        return p_Rom;
    }

    public void setP_Rom(String p_Rom) {
        this.p_Rom = p_Rom;
    }

    public String getP_Byz() {
        return p_Byz;
    }

    public void setP_Byz(String p_Byz) {
        this.p_Byz = p_Byz;
    }

    public String getP_HellERom() {
        return p_HellERom;
    }

    public void setP_HellERom(String p_HellERom) {
        this.p_HellERom = p_HellERom;
    }

    public String getP_Class() {
        return p_Class;
    }

    public void setP_Class(String p_Class) {
        this.p_Class = p_Class;
    }

    public String getP_Archaic() {
        return p_Archaic;
    }

    public void setP_Archaic(String p_Archaic) {
        this.p_Archaic = p_Archaic;
    }

    public String getP_Hell() {
        return p_Hell;
    }

    public void setP_Hell(String p_Hell) {
        this.p_Hell = p_Hell;
    }

    public String getV_Rim() {
        return v_Rim;
    }

    public void setV_Rim(String v_Rim) {
        this.v_Rim = v_Rim;
    }

    public String getV_Neck() {
        return v_Neck;
    }

    public void setV_Neck(String v_Neck) {
        this.v_Neck = v_Neck;
    }

    public String getV_Shoulder() {
        return v_Shoulder;
    }

    public void setV_Shoulder(String v_Shoulder) {
        this.v_Shoulder = v_Shoulder;
    }

    public String getV_Handle() {
        return v_Handle;
    }

    public void setV_Handle(String v_Handle) {
        this.v_Handle = v_Handle;
    }

    public String getV_Body() {
        return v_Body;
    }

    public void setV_Body(String v_Body) {
        this.v_Body = v_Body;
    }

    public String getV_Base() {
        return v_Base;
    }

    public void setV_Base(String v_Base) {
        this.v_Base = v_Base;
    }

    public String getV_Foot() {
        return v_Foot;
    }

    public void setV_Foot(String v_Foot) {
        this.v_Foot = v_Foot;
    }

    public String getV_Other() {
        return v_Other;
    }

    public void setV_Other(String v_Other) {
        this.v_Other = v_Other;
    }

    public String getV_Nozzle() {
        return v_Nozzle;
    }

    public void setV_Nozzle(String v_Nozzle) {
        this.v_Nozzle = v_Nozzle;
    }

    public String getV_Disc() {
        return v_Disc;
    }

    public void setV_Disc(String v_Disc) {
        this.v_Disc = v_Disc;
    }

    public String getV_Lower() {
        return v_Lower;
    }

    public void setV_Lower(String v_Lower) {
        this.v_Lower = v_Lower;
    }

    public String getV_Upper() {
        return v_Upper;
    }

    public void setV_Upper(String v_Upper) {
        this.v_Upper = v_Upper;
    }

    public String getV_Spout() {
        return v_Spout;
    }

    public void setV_Spout(String v_Spout) {
        this.v_Spout = v_Spout;
    }

    public String getF_Cooking() {
        return f_Cooking;
    }

    public void setF_Cooking(String f_Cooking) {
        this.f_Cooking = f_Cooking;
    }

    public String getF_Storage() {
        return f_Storage;
    }

    public void setF_Storage(String f_Storage) {
        this.f_Storage = f_Storage;
    }

    public String getF_Transport() {
        return f_Transport;
    }

    public void setF_Transport(String f_Transport) {
        this.f_Transport = f_Transport;
    }

    public String getF_TblEat() {
        return f_TblEat;
    }

    public void setF_TblEat(String f_TblEat) {
        this.f_TblEat = f_TblEat;
    }

    public String getF_tblDrink() {
        return f_tblDrink;
    }

    public void setF_tblDrink(String f_tblDrink) {
        this.f_tblDrink = f_tblDrink;
    }

    public String getF_tblServ() {
        return f_tblServ;
    }

    public void setF_tblServ(String f_tblServ) {
        this.f_tblServ = f_tblServ;
    }

    public String getF_Ritual() {
        return f_Ritual;
    }

    public void setF_Ritual(String f_Ritual) {
        this.f_Ritual = f_Ritual;
    }

    public String getF_Lamp() {
        return f_Lamp;
    }

    public void setF_Lamp(String f_Lamp) {
        this.f_Lamp = f_Lamp;
    }

    public String getF_Toilet() {
        return f_Toilet;
    }

    public void setF_Toilet(String f_Toilet) {
        this.f_Toilet = f_Toilet;
    }

    public String getF_Weaving() {
        return f_Weaving;
    }

    public void setF_Weaving(String f_Weaving) {
        this.f_Weaving = f_Weaving;
    }

    public String getF_CProduct() {
        return f_CProduct;
    }

    public void setF_CProduct(String f_CProduct) {
        this.f_CProduct = f_CProduct;
    }

    public String getF_Utility() {
        return f_Utility;
    }

    public void setF_Utility(String f_Utility) {
        this.f_Utility = f_Utility;
    }

    public String getF_Other() {
        return f_Other;
    }

    public void setF_Other(String f_Other) {
        this.f_Other = f_Other;
    }

    public String getD_notes() {
        return d_notes;
    }

    public void setD_notes(String d_notes) {
        this.d_notes = d_notes;
    }

    public String getD_slip() {
        return d_slip;
    }

    public void setD_slip(String d_slip) {
        this.d_slip = d_slip;
    }

    public String getD_Glaze() {
        return d_Glaze;
    }

    public void setD_Glaze(String d_Glaze) {
        this.d_Glaze = d_Glaze;
    }

    public String getD_Paint() {
        return d_Paint;
    }

    public void setD_Paint(String d_Paint) {
        this.d_Paint = d_Paint;
    }

    public String getD_Incised() {
        return d_Incised;
    }

    public void setD_Incised(String d_Incised) {
        this.d_Incised = d_Incised;
    }

    public String getD_Stamped() {
        return d_Stamped;
    }

    public void setD_Stamped(String d_Stamped) {
        this.d_Stamped = d_Stamped;
    }

    public String getD_Moulded() {
        return d_Moulded;
    }

    public void setD_Moulded(String d_Moulded) {
        this.d_Moulded = d_Moulded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLegacyID() {
        return legacyID;
    }

    public void setLegacyID(String legacyID) {
        this.legacyID = legacyID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
