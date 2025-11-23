package net.charno.sikyon.service;

import com.opencsv.CSVWriter;
import net.charno.sikyon.config.ExportProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Service for exporting data to CSV files.
 * Handles cross-platform file path creation and CSV formatting.
 */
@Service
public class CsvExportService {

    private static final Logger logger = LoggerFactory.getLogger(CsvExportService.class);

    private final ExportProperties exportProperties;

    public CsvExportService(ExportProperties exportProperties) {
        this.exportProperties = exportProperties;
    }

    /**
     * Exports data to a CSV file using the configured delimiter.
     *
     * @param filename Name of the output file
     * @param headers Column headers
     * @param data Data rows to export
     */
    public void exportToCsv(String filename, String[] headers, List<String[]> data) {
        Path outputDir = Paths.get(exportProperties.csv().outputDirectory());
        Path outputFile = outputDir.resolve(filename);

        try {
            // Create output directory if it doesn't exist
            Files.createDirectories(outputDir);

            char delimiter = exportProperties.csv().delimiter().charAt(0);

            try (CSVWriter writer = new CSVWriter(
                new FileWriter(outputFile.toFile()),
                delimiter,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {

                // Write headers
                if (headers != null && headers.length > 0) {
                    writer.writeNext(headers);
                }

                // Write data
                writer.writeAll(data);

                logger.info("Exported {} rows to {}", data.size(), outputFile);
            }

        } catch (IOException e) {
            logger.error("Error exporting to CSV file: {}", outputFile, e);
            throw new RuntimeException("Failed to export CSV file", e);
        }
    }

    /**
     * Exports data to a CSV file with custom delimiter.
     */
    public void exportToCsv(String filename, String[] headers, List<String[]> data, char delimiter) {
        Path outputDir = Paths.get(exportProperties.csv().outputDirectory());
        Path outputFile = outputDir.resolve(filename);

        try {
            Files.createDirectories(outputDir);

            try (CSVWriter writer = new CSVWriter(
                new FileWriter(outputFile.toFile()),
                delimiter,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {

                if (headers != null && headers.length > 0) {
                    writer.writeNext(headers);
                }

                writer.writeAll(data);

                logger.info("Exported {} rows to {}", data.size(), outputFile);
            }

        } catch (IOException e) {
            logger.error("Error exporting to CSV file: {}", outputFile, e);
            throw new RuntimeException("Failed to export CSV file", e);
        }
    }
}
