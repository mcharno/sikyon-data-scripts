package net.charno.sikyon.model;

/**
 * Record representing pottery density calculations for an excavation square.
 *
 * @param squareId Unique identifier for the square
 * @param areaSquareMeters Area of the square in square meters
 * @param potteryCount Total pottery count
 * @param roofTileCount Total roof tile count
 * @param potteryDensity Pottery density (count per square meter)
 * @param roofTileDensity Roof tile density (count per square meter)
 * @param visibilityAdjustedPotteryDensity Pottery density adjusted for visibility
 * @param visibilityAdjustedRoofTileDensity Roof tile density adjusted for visibility
 * @param visibilityFactor Visibility correction factor
 */
public record SquareDensity(
    String squareId,
    double areaSquareMeters,
    int potteryCount,
    int roofTileCount,
    double potteryDensity,
    double roofTileDensity,
    double visibilityAdjustedPotteryDensity,
    double visibilityAdjustedRoofTileDensity,
    double visibilityFactor
) {
    /**
     * Creates a SquareDensity with calculated density values.
     */
    public static SquareDensity calculate(
        String squareId,
        double areaSquareMeters,
        int potteryCount,
        int roofTileCount,
        double visibilityFactor
    ) {
        double potteryDensity = areaSquareMeters > 0 ? potteryCount / areaSquareMeters : 0;
        double roofTileDensity = areaSquareMeters > 0 ? roofTileCount / areaSquareMeters : 0;
        double visibilityAdjustedPotteryDensity = potteryDensity * visibilityFactor;
        double visibilityAdjustedRoofTileDensity = roofTileDensity * visibilityFactor;

        return new SquareDensity(
            squareId,
            areaSquareMeters,
            potteryCount,
            roofTileCount,
            potteryDensity,
            roofTileDensity,
            visibilityAdjustedPotteryDensity,
            visibilityAdjustedRoofTileDensity,
            visibilityFactor
        );
    }
}
