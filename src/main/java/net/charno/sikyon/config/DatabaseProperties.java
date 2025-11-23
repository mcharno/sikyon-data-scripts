package net.charno.sikyon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Configuration properties for database connections.
 * Values are read from application.yml and can be overridden via environment variables.
 */
@ConfigurationProperties(prefix = "database")
@Validated
public record DatabaseProperties(
    AccessConfig access,
    OracleConfig oracle
) {
    public record AccessConfig(
        @NotBlank String main,
        @NotBlank String working,
        @NotBlank String reference
    ) {}

    public record OracleConfig(
        PrimaryConfig primary,
        BackupConfig backup
    ) {
        public record PrimaryConfig(
            @NotBlank String url,
            String username,
            String password
        ) {}

        public record BackupConfig(
            @NotBlank String url,
            String username,
            String password
        ) {}
    }
}
