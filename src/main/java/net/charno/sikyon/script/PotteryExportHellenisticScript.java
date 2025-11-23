package net.charno.sikyon.script;

import net.charno.sikyon.model.HellenisticPotteryRecord;
import net.charno.sikyon.repository.PotteryRepository;
import net.charno.sikyon.service.CsvExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Hellenistic Pottery Export Script
 *
 * Modernized version of the original PotteryTableHellenistic.
 * Exports Hellenistic pottery data to pipe-delimited CSV format.
 *
 * To run: mvn spring-boot:run -Dspring-boot.run.arguments=--script=pottery-hellenistic
 */
@Component
@ConditionalOnProperty(name = "script", havingValue = "pottery-hellenistic")
public class PotteryExportHellenisticScript implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PotteryExportHellenisticScript.class);

    private final PotteryRepository potteryRepository;
    private final CsvExportService csvExportService;

    public PotteryExportHellenisticScript(
        PotteryRepository potteryRepository,
        CsvExportService csvExportService
    ) {
        this.potteryRepository = potteryRepository;
        this.csvExportService = csvExportService;
    }

    @Override
    public void run(String... args) {
        logger.info("=".repeat(60));
        logger.info("HELLENISTIC POTTERY EXPORT SCRIPT");
        logger.info("Exporting Hellenistic pottery data to CSV");
        logger.info("=".repeat(60));

        try {
            List<HellenisticPotteryRecord> records = potteryRepository.findAllHellenisticPottery();

            String[] headers = {
                "SquareID", "Quantity", "Class", "Date", "Fabric", "Shape",
                "Type", "VesselPortion", "Century", "Hellenistic", "Roman",
                "IndexSherd", "Notes"
            };

            List<String[]> data = new ArrayList<>();
            for (HellenisticPotteryRecord record : records) {
                data.add(new String[] {
                    record.squareId(),
                    String.valueOf(record.quantity()),
                    record.classification(),
                    record.dateRange(),
                    record.fabric(),
                    record.shape(),
                    record.type(),
                    record.vesselPortion(),
                    record.century(),
                    String.valueOf(record.isHellenistic()),
                    String.valueOf(record.isRoman()),
                    String.valueOf(record.isIndexSherd()),
                    record.notes()
                });
            }

            csvExportService.exportToCsv("hellenistic_pottery.csv", headers, data, '|');

            logger.info("Exported {} Hellenistic pottery records", records.size());
            logger.info("Export completed successfully");

        } catch (Exception e) {
            logger.error("Error during Hellenistic pottery export", e);
            System.exit(1);
        }
    }
}
