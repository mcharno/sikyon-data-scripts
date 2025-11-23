package net.charno.sikyon.script;

import net.charno.sikyon.model.RomanPotteryRecord;
import net.charno.sikyon.repository.PotteryRepository;
import net.charno.sikyon.service.CsvExportService;
import net.charno.sikyon.service.DataCleaningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Roman Pottery Export Script
 *
 * Modernized version of the original PotteryTableRoman.
 * Cleans and exports Roman pottery data to CSV format.
 *
 * To run: mvn spring-boot:run -Dspring-boot.run.arguments=--script=pottery-roman
 */
@Component
@ConditionalOnProperty(name = "script", havingValue = "pottery-roman")
public class PotteryExportRomanScript implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PotteryExportRomanScript.class);

    private final PotteryRepository potteryRepository;
    private final CsvExportService csvExportService;
    private final DataCleaningService dataCleaningService;

    public PotteryExportRomanScript(
        PotteryRepository potteryRepository,
        CsvExportService csvExportService,
        DataCleaningService dataCleaningService
    ) {
        this.potteryRepository = potteryRepository;
        this.csvExportService = csvExportService;
        this.dataCleaningService = dataCleaningService;
    }

    @Override
    public void run(String... args) {
        logger.info("=".repeat(60));
        logger.info("ROMAN POTTERY EXPORT SCRIPT");
        logger.info("Cleaning and exporting Roman pottery data");
        logger.info("=".repeat(60));

        try {
            List<RomanPotteryRecord> records = potteryRepository.findAllRomanPottery();

            String[] headers = {
                "SquareID", "EarlyRoman%", "MidRoman%", "LateRoman%",
                "Rim", "Base", "Handle", "Cooking", "Storage", "Transport", "Notes"
            };

            List<String[]> data = new ArrayList<>();
            for (RomanPotteryRecord record : records) {
                String normalizedSquareId = dataCleaningService.normalizeSquareId(record.squareId());

                data.add(new String[] {
                    normalizedSquareId,
                    String.format("%.2f", record.periodPercentages().getOrDefault("EarlyRoman", 0.0)),
                    String.format("%.2f", record.periodPercentages().getOrDefault("MidRoman", 0.0)),
                    String.format("%.2f", record.periodPercentages().getOrDefault("LateRoman", 0.0)),
                    String.valueOf(record.vesselParts().getOrDefault("Rim", 0)),
                    String.valueOf(record.vesselParts().getOrDefault("Base", 0)),
                    String.valueOf(record.vesselParts().getOrDefault("Handle", 0)),
                    String.valueOf(record.functions().getOrDefault("Cooking", 0)),
                    String.valueOf(record.functions().getOrDefault("Storage", 0)),
                    String.valueOf(record.functions().getOrDefault("Transport", 0)),
                    record.notes()
                });
            }

            csvExportService.exportToCsv("roman_pottery.csv", headers, data);

            logger.info("Exported {} Roman pottery records", records.size());
            logger.info("Export completed successfully");

        } catch (Exception e) {
            logger.error("Error during Roman pottery export", e);
            System.exit(1);
        }
    }
}
