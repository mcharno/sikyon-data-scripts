package net.charno.sikyon.model;

/**
 * Record representing Hellenistic pottery finds.
 * Simplified from the original 13-field HellenisticPottery class.
 *
 * @param squareId Excavation square identifier
 * @param quantity Number of pottery fragments
 * @param classification Pottery classification
 * @param dateRange Date range (e.g., "4th-3rd century BC")
 * @param fabric Fabric description
 * @param shape Vessel shape
 * @param type Pottery type
 * @param vesselPortion Portion of vessel
 * @param century Century classification
 * @param isHellenistic True if classified as Hellenistic
 * @param isRoman True if classified as Roman
 * @param isIndexSherd True if this is an index sherd
 * @param notes Additional notes
 */
public record HellenisticPotteryRecord(
    String squareId,
    int quantity,
    String classification,
    String dateRange,
    String fabric,
    String shape,
    String type,
    String vesselPortion,
    String century,
    boolean isHellenistic,
    boolean isRoman,
    boolean isIndexSherd,
    String notes
) {
    /**
     * Common Hellenistic centuries.
     */
    public enum HellenisticCentury {
        FOURTH_BC("4th BC"),
        THIRD_BC("3rd BC"),
        SECOND_BC("2nd BC"),
        FIRST_BC("1st BC");

        private final String label;

        HellenisticCentury(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
