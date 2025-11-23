# Sikyon Data Processing Scripts - Modern Version 2.0

## Overview

This is a **complete modernization** of the archaeological data processing scripts originally written in 2008 for the Sikyon Survey Project. This version maintains the same functionality while incorporating modern Java practices, frameworks, and cross-platform compatibility.

### Key Improvements

✅ **Modern Java 17+** with Records, Stream API, and modern language features
✅ **Spring Boot 3.2** for dependency injection and configuration management
✅ **Cross-platform compatibility** - runs on Windows, macOS, and Linux
✅ **Externalized configuration** via YAML and environment variables
✅ **Proper logging** with SLF4J and Logback (console + file)
✅ **Repository pattern** for clean data access separation
✅ **Open-source dependencies** - no commercial JDBC drivers required
✅ **Docker support** for consistent execution environments
✅ **Type safety** with immutable Records
✅ **Modern error handling** and validation

## Architecture

### Technology Stack

- **Java**: 17 (LTS)
- **Framework**: Spring Boot 3.2
- **Database Drivers**:
  - UCanAccess 5.0.1 (MS Access - cross-platform, no MS Access installation required)
  - Oracle JDBC 21.9 (Oracle databases)
- **CSV Processing**: OpenCSV 5.9
- **Build Tool**: Maven 3.9+
- **Containerization**: Docker

### Project Structure

```
modernized/
├── src/
│   ├── main/
│   │   ├── java/net/charno/sikyon/
│   │   │   ├── config/              # Configuration classes
│   │   │   ├── model/               # Data models (Records)
│   │   │   ├── repository/          # Data access layer
│   │   │   ├── service/             # Business logic
│   │   │   ├── script/              # Script implementations
│   │   │   └── SikyonDataScriptsApplication.java
│   │   └── resources/
│   │       ├── application.yml      # Configuration
│   │       └── logback-spring.xml   # Logging config
│   └── test/                        # Unit tests
├── Dockerfile                       # Docker build configuration
├── docker-compose.yml               # Docker Compose setup
└── pom.xml                          # Maven dependencies
```

## Getting Started

### Prerequisites

**Option 1: Local Development**
- Java 17 or higher ([Download](https://adoptium.net/))
- Maven 3.9+ ([Download](https://maven.apache.org/download.cgi))

**Option 2: Docker**
- Docker and Docker Compose ([Download](https://www.docker.com/products/docker-desktop))

### Installation

#### Clone the Repository
```bash
git clone <repository-url>
cd sikyon-data-scripts/modernized
```

#### Verify Java Installation
```bash
java -version   # Should show Java 17+
mvn -version    # Should show Maven 3.9+
```

## Configuration

### Database Paths

Configure database paths in `src/main/resources/application.yml`:

```yaml
database:
  access:
    main: "./data/ssp_main.mdb"
    working: "./data/ssp_working.mdb"
    reference: "./data/sik_db-working.mdb"
  oracle:
    primary:
      url: "jdbc:oracle:thin:@localhost:1521:xe"
      username: ""
      password: ""
```

### Environment Variables

Override configuration using environment variables:

| Variable | Description | Example |
|----------|-------------|---------|
| `DB_ACCESS_MAIN` | Main Access database path | `./data/ssp_main.mdb` |
| `DB_ACCESS_WORKING` | Working Access database path | `./data/ssp_working.mdb` |
| `DB_ACCESS_REF` | Reference Access database path | `./data/sik_db-working.mdb` |
| `DB_ORACLE_URL` | Oracle database URL | `jdbc:oracle:thin:@host:1521:xe` |
| `DB_ORACLE_USER` | Oracle username | `username` |
| `DB_ORACLE_PASS` | Oracle password | `password` |
| `EXPORT_CSV_DIR` | CSV output directory | `./output/csv` |
| `LOG_FILE` | Log file path | `./logs/sikyon.log` |

## Running the Scripts

### List Available Scripts

```bash
mvn spring-boot:run
```

This displays all available scripts and usage instructions.

### Run Individual Scripts

#### 1. Database Cleaner
Normalizes Square and Tract IDs across all tables.

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--script=db-cleaner
```

#### 2. Density Builder
Calculates pottery and roof tile density values for each excavation square.

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--script=density-builder
```

#### 3. Hellenistic Pottery Export
Exports Hellenistic pottery data to pipe-delimited CSV.

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--script=pottery-hellenistic
```

#### 4. Roman Pottery Export
Cleans and exports Roman pottery data to CSV.

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--script=pottery-roman
```

#### 5. Pottery Table Builder
Builds pottery aggregation tables for GIS integration.

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--script=pottery-builder
```

#### 6. Main Database Generator
Creates the main database with pottery percentages by period and type.

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--script=main-database
```

## Docker Usage

### Build Docker Image

```bash
docker build -t sikyon-data-scripts:2.0 .
```

### Run with Docker Compose

```bash
# Place your .mdb files in the ./data directory
mkdir -p data output logs

# Run a specific script
docker-compose run --rm sikyon-scripts --script=db-cleaner

# Or edit docker-compose.yml to set the default script
```

### Run Individual Container

```bash
docker run --rm \
  -v $(pwd)/data:/app/data:ro \
  -v $(pwd)/output:/app/output \
  -v $(pwd)/logs:/app/logs \
  sikyon-data-scripts:2.0 \
  --script=density-builder
```

## Cross-Platform Compatibility

### Windows
```powershell
# PowerShell
mvn spring-boot:run "-Dspring-boot.run.arguments=--script=db-cleaner"
```

### macOS/Linux
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--script=db-cleaner
```

### Path Handling
All file paths are handled using Java's `Path` API, ensuring cross-platform compatibility. Use forward slashes `/` in configuration even on Windows.

## Logging

Logs are written to:
- **Console**: Color-coded, human-readable format
- **File**: `./logs/sikyon-data-scripts.log` (configurable)

Log files are automatically rotated daily with a 30-day retention policy.

### Log Levels

- `DEBUG`: Detailed information for developers (SQL queries, connection details)
- `INFO`: General progress and summary information
- `WARN`: Warning messages (non-fatal issues)
- `ERROR`: Error messages (operation failures)

## Development

### Build the Project

```bash
mvn clean package
```

### Run Tests

```bash
mvn test
```

### Generate Executable JAR

```bash
mvn clean package
java -jar target/sikyon-data-scripts-modern-2.0.0.jar --script=db-cleaner
```

## Differences from Original Version

| Aspect | Original (2008) | Modern (2025) |
|--------|----------------|---------------|
| **Language** | Java 6 | Java 17+ |
| **Framework** | None | Spring Boot 3.2 |
| **Configuration** | Hardcoded paths | Externalized YAML/env vars |
| **Database Access** | Commercial HXTT driver | Open-source UCanAccess |
| **Data Models** | Verbose POJOs (449 fields) | Immutable Records |
| **Logging** | System.out.println | SLF4J + Logback |
| **Error Handling** | Basic try-catch | Comprehensive error handling |
| **Testing** | None | Unit test support |
| **Cross-platform** | Linux-specific paths | Fully cross-platform |
| **Containerization** | None | Docker support |

## UCanAccess vs HXTT

The original version required the commercial **HXTT JDBC Driver** for MS Access. The modernized version uses **UCanAccess**, which is:

- ✅ **Open-source** and free
- ✅ **Cross-platform** (no MS Access installation required)
- ✅ **Pure Java** implementation
- ✅ **Actively maintained**
- ✅ **Maven Central** distribution

## Troubleshooting

### "ClassNotFoundException: net.ucanaccess.jdbc.UcanaccessDriver"

**Solution**: Run `mvn clean install` to download dependencies.

### "FileNotFoundException: data/ssp_main.mdb"

**Solution**: Ensure your `.mdb` files are in the correct location specified in `application.yml` or environment variables.

### "SQLException: Database locked"

**Solution**: Close any MS Access or other applications that may have the database file open.

### Docker: "Permission denied" on volume mounts

**Solution** (Linux/macOS):
```bash
sudo chown -R $(id -u):$(id -g) data output logs
```

## Contributing

This modernized version maintains the exact same business logic as the original to ensure data consistency. Any contributions should:

1. Maintain backward compatibility with original data processing logic
2. Include unit tests
3. Follow Spring Boot best practices
4. Support cross-platform execution

## License

Same license as the original Sikyon Data Scripts project. See [LICENSE](../LICENSE) in the root directory.

## Acknowledgments

- **Original Author**: Matthew Charno (2008)
- **Modernization**: 2025
- **Sikyon Survey Project**: University of Thessaly, University of York, Institute of Mediterranean Studies at FORTH

## References

- Original code: `../original/`
- Sikyon Survey Project: http://extras.ha.uth.gr/sikyon/en/
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- UCanAccess: https://github.com/ucanaccess/ucanaccess

---

**For the original 2008 code, see [../original/README.md](../original/README.md)**
