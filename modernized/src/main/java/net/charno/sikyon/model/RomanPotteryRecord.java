package net.charno.sikyon.model;

import java.util.Map;

/**
 * Record representing Roman pottery finds with period percentages.
 * Simplified from the original 66-field RomanPottery class.
 *
 * @param squareId Excavation square identifier
 * @param periodPercentages Map of period names to percentage values
 * @param vesselParts Map of vessel part types to counts
 * @param functions Map of functional types to counts
 * @param decorativeTechniques Map of decorative techniques to presence/counts
 * @param notes General notes
 */
public record RomanPotteryRecord(
    String squareId,
    Map<String, Double> periodPercentages,
    Map<String, Integer> vesselParts,
    Map<String, Integer> functions,
    Map<String, String> decorativeTechniques,
    String notes
) {
    /**
     * Common Roman periods tracked in the survey.
     */
    public enum RomanPeriod {
        EARLY_ROMAN,
        MID_ROMAN,
        LATE_ROMAN,
        EARLY_BYZANTINE,
        MID_BYZANTINE,
        OTTOMAN,
        MODERN
    }

    /**
     * Common vessel parts tracked.
     */
    public enum VesselPart {
        RIM, NECK, SHOULDER, HANDLE, BODY, BASE, FOOT, LUG
    }

    /**
     * Functional classifications.
     */
    public enum Function {
        COOKING,
        STORAGE,
        TRANSPORT,
        TABLE_EATING,
        TABLE_DRINKING,
        TABLE_SERVICE,
        RITUAL,
        LAMP,
        TOILET,
        WEAVING,
        CERAMIC_PRODUCTION,
        UTILITY,
        OTHER
    }
}
