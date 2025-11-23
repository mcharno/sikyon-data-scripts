package net.charno.sikyon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Configuration properties for data export settings.
 */
@ConfigurationProperties(prefix = "export")
@Validated
public record ExportProperties(
    CsvConfig csv
) {
    public record CsvConfig(
        @NotBlank String outputDirectory,
        @NotBlank String delimiter
    ) {}
}
