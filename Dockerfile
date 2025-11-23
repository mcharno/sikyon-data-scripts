# Multi-stage Dockerfile for cross-platform Sikyon Data Scripts

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre

WORKDIR /app

# Create non-root user for security
RUN groupadd -r sikyon && useradd -r -g sikyon sikyon

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Create directories for data and output
RUN mkdir -p /app/data /app/output /app/logs && \
    chown -R sikyon:sikyon /app

# Switch to non-root user
USER sikyon

# Set environment variables
ENV JAVA_OPTS="-Xmx512m" \
    DB_ACCESS_MAIN="/app/data/ssp_main.mdb" \
    DB_ACCESS_WORKING="/app/data/ssp_working.mdb" \
    DB_ACCESS_REF="/app/data/sik_db-working.mdb" \
    EXPORT_CSV_DIR="/app/output/csv" \
    LOG_FILE="/app/logs/sikyon-data-scripts.log"

# Volume mounts for data and output
VOLUME ["/app/data", "/app/output", "/app/logs"]

# Entry point
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar \"$@\"", "--"]

# Default: show available scripts
CMD []
