package net.charno.db.model;

public class HellenisticPottery {
    private String quantity, _class, dateRanges, fabric, hellRom, indexSherds, notes, nothingIdentified, shape, squareName, squareQualifier, legacyId, type, vesselPortion, centuries;
    private int id, squareId;
    
    public String[] getArray() {
        String[] row = new String[13];
        
        row[0] = String.valueOf(id);
        row[1] = String.valueOf(squareId);
        row[2] = legacyId;
        row[3] = squareQualifier;
        row[4] = quantity;
        row[5] = hellRom;
        row[6] = indexSherds;
        row[7] = notes;
        row[8] = nothingIdentified;
        row[9] = fabric;
        row[10] = shape;
        row[11] = type;
        row[12] = squareName;
        
        return row;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
    public String get_class() {
        return _class;
    }
    
    public void set_class(String _class) {
        this._class = _class;
    }

    public String getDateRanges() {
        return dateRanges;
    }

    public void setDateRanges(String dateRanges) {
        this.dateRanges = dateRanges;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getHellRom() {
        return hellRom;
    }

    public void setHellRom(String hellRom) {
        this.hellRom = hellRom;
    }

    public String getIndexSherds() {
        return indexSherds;
    }

    public void setIndexSherds(String indexSherds) {
        this.indexSherds = indexSherds;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNothingIdentified() {
        return nothingIdentified;
    }

    public void setNothingIdentified(String nothingIdentified) {
        this.nothingIdentified = nothingIdentified;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSquareName() {
        return squareName;
    }

    public void setSquareName(String squareName) {
        this.squareName = squareName;
    }

    public String getSquareQualifier() {
        return squareQualifier;
    }

    public void setSquareQualifier(String squareQualifier) {
        this.squareQualifier = squareQualifier;
    }

    public String getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(String legacyId) {
        this.legacyId = legacyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVesselPortion() {
        return vesselPortion;
    }

    public void setVesselPortion(String vesselPortion) {
        this.vesselPortion = vesselPortion;
    }

    public String getCenturies() {
        return centuries;
    }

    public void setCenturies(String centuries) {
        this.centuries = centuries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSquareId() {
        return squareId;
    }

    public void setSquareId(int squareId) {
        this.squareId = squareId;
    }
}
