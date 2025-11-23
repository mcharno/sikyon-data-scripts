package net.charno.sikyon.model;

/**
 * Immutable record representing a pottery find from the Sikyon Survey.
 * This is a modernized, simplified version of the original Pottery class.
 *
 * @param squareId Unique identifier for the excavation square
 * @param tractId Tract identifier
 * @param period Archaeological period (e.g., "Hellenistic", "Roman")
 * @param shape Pottery shape/form
 * @param function Functional classification (e.g., "Storage", "Cooking")
 * @param vesselPart Part of vessel (e.g., "Rim", "Base", "Handle")
 * @param quantity Number of sherds/fragments
 * @param fabric Fabric type/description
 * @param decoration Decorative technique or description
 * @param notes Additional notes or observations
 */
public record PotteryRecord(
    String squareId,
    String tractId,
    String period,
    String shape,
    String function,
    String vesselPart,
    int quantity,
    String fabric,
    String decoration,
    String notes
) {
    /**
     * Normalizes the square ID by removing leading zeros and spaces.
     * Example: "P0" -> "P", "SP 07.04" -> "SP07.04"
     */
    public String normalizedSquareId() {
        if (squareId == null) {
            return null;
        }
        return squareId
            .replaceAll("\\s+", "")
            .replaceAll("([A-Z])0+([0-9])", "$1$2")
            .replaceAll("PO", "P")
            .replaceAll("TO", "T");
    }

    /**
     * Normalizes the tract ID similarly to square ID.
     */
    public String normalizedTractId() {
        if (tractId == null) {
            return null;
        }
        return tractId
            .replaceAll("\\s+", "")
            .replaceAll("([A-Z])0+([0-9])", "$1$2")
            .replaceAll("R$", ""); // Remove trailing 'R' for type
    }
}
