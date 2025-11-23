# Sikyon Data Processing Scripts

A modern, cross-platform system for processing archaeological pottery data from the Sikyon Survey Project.

## Overview

This repository contains data processing scripts for the Sikyon Survey Project, an archaeological survey of ancient Sikyon (northeastern Peloponnese, Greece) conducted between 2004-2009. The scripts automate the cleaning, integration, and analysis of pottery survey data from excavation databases.

**Current Version:** 2.0 (Modernized 2025)

### Technology Stack

- **Java 17+** with Records and modern language features
- **Spring Boot 3.2** for dependency injection and configuration
- **Cross-platform compatible** - Windows, macOS, and Linux
- **UCanAccess 5.0.1** for MS Access databases (no MS Access installation required)
- **Docker support** for consistent execution environments

## Quick Start

### Prerequisites

**Option 1: Local Development**
- Java 17 or higher ([Download](https://adoptium.net/))
- Maven 3.9+ ([Download](https://maven.apache.org/download.cgi))

**Option 2: Docker**
- Docker and Docker Compose ([Download](https://www.docker.com/products/docker-desktop))

### Installation

```bash
git clone <repository-url>
cd sikyon-data-scripts
```

Verify Java installation:
```bash
java -version   # Should show Java 17+
mvn -version    # Should show Maven 3.9+
```

### Running Scripts

**List available scripts:**
```bash
mvn spring-boot:run
```

**Run a specific script:**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--script=db-cleaner
```

**Using Docker:**
```bash
docker-compose run --rm sikyon-scripts --script=density-builder
```

## Available Scripts

| Script | Description | Command |
|--------|-------------|---------|
| **db-cleaner** | Normalize Square and Tract IDs | `--script=db-cleaner` |
| **density-builder** | Calculate pottery/tile densities | `--script=density-builder` |
| **pottery-hellenistic** | Export Hellenistic pottery to CSV | `--script=pottery-hellenistic` |
| **pottery-roman** | Export Roman pottery to CSV | `--script=pottery-roman` |
| **pottery-builder** | Build GIS pottery tables | `--script=pottery-builder` |
| **main-database** | Generate analysis database | `--script=main-database` |

## Configuration

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

See `.env.example` for a complete list of configuration options.

## Architecture

### Project Structure

```
sikyon-data-scripts/
├── src/
│   ├── main/
│   │   ├── java/net/charno/sikyon/
│   │   │   ├── config/              # Configuration classes
│   │   │   ├── model/               # Data models (Records)
│   │   │   ├── repository/          # Data access layer
│   │   │   ├── service/             # Business logic
│   │   │   └── script/              # Script implementations
│   │   └── resources/
│   │       ├── application.yml      # Configuration
│   │       └── logback-spring.xml   # Logging config
│   └── test/                        # Unit tests
├── original/                        # Historical code (2008)
├── Dockerfile                       # Docker build
├── docker-compose.yml               # Docker Compose
└── pom.xml                          # Maven dependencies
```

### Key Features

✅ **Modern Java 17+** - Records, Stream API, modern patterns
✅ **Spring Boot 3.2** - Dependency injection, auto-configuration
✅ **Cross-platform** - Runs on Windows, macOS, Linux
✅ **Externalized Config** - YAML files and environment variables
✅ **Professional Logging** - SLF4J/Logback with file rotation
✅ **Repository Pattern** - Clean separation of concerns
✅ **Open-Source Stack** - No commercial licenses required
✅ **Docker Support** - Containerized execution
✅ **Type Safety** - Immutable Records for data models
✅ **Error Handling** - Comprehensive validation and error reporting

## Building & Testing

**Build the project:**
```bash
mvn clean package
```

**Run tests:**
```bash
mvn test
```

**Create executable JAR:**
```bash
mvn clean package
java -jar target/sikyon-data-scripts-modern-2.0.0.jar --script=db-cleaner
```

## Docker Usage

**Build image:**
```bash
docker build -t sikyon-data-scripts:2.0 .
```

**Run with Docker Compose:**
```bash
# Place your .mdb files in ./data/
mkdir -p data output logs

# Run a script
docker-compose run --rm sikyon-scripts --script=db-cleaner
```

## Original Code (Historical Reference)

The `original/` directory contains the **original 2008 code** exactly as it was used to process the data for the published Sikyon Survey monograph. This code has been preserved without modification to ensure reproducibility of the published results.

**📖 See [original/README.md](original/README.md) for details about the historical code.**

The original version is maintained for:
- **Publication reproducibility** - Ensures published results can be verified
- **Historical reference** - Documents the original data processing approach
- **Academic transparency** - Provides complete methodology disclosure

**For current work, use the modern version in the root directory.**

## The Sikyon Survey Project

The Sikyon Survey Project was a multidisciplinary research program to study human presence and activity on the plateau of ancient Sikyon, a city in northeastern Peloponnese between Corinth and Achaia. The project ran between 2004-2009 and was undertaken by the University of Thessaly in collaboration with the University of York (UK), the Institute of Mediterranean Studies at FORTH, and the 37th Ephoreia of Prehistoric and Classical Antiquities.

The primary activity was an urban survey of the ancient city, which quantified and qualified both pottery and architectural observations from the field over 5 seasons. That data was captured in databases and GIS, which aided the archaeologists in making their interpretation of the ancient city and its use.

### Publications

The monograph this code and data helped shape was published in Meletemata in 2018 by the [Greek National Research Foundation](https://history-bookstore.eie.gr/en/section/greek-roman-antiquity/meletemata/).

Further interpretations and discussions:
- Lolos, Y. 2011 'Land of Sikyon: archaeology and history of a Greek city-state', Hesperia suppl. 39.
- Lolos, Y., Gourley, B. and Stewart, D. 2008 'The Sikyon Survey Project: a blueprint for urban survey?', Journal of Mediterranean Archaeology **20** (2), 267-96.
- Charno, M. 2007 'An Interactive Image Using SVG and Ajax in Archaeology', [Internet Archaeology](http://intarch.ac.uk/) **23**. https://doi.org/10.11141/ia.23.5

**Project website:** http://extras.ha.uth.gr/sikyon/en/

## Cross-Platform Compatibility

This codebase is designed to work seamlessly across platforms:

### Windows
```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--script=db-cleaner"
```

### macOS/Linux
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--script=db-cleaner
```

All file paths use Java's `Path` API for cross-platform compatibility. Use forward slashes `/` in configuration on all platforms.

## Troubleshooting

**"ClassNotFoundException: net.ucanaccess.jdbc.UcanaccessDriver"**
- Run `mvn clean install` to download dependencies

**"FileNotFoundException: data/ssp_main.mdb"**
- Ensure `.mdb` files are in the location specified in `application.yml`

**"SQLException: Database locked"**
- Close MS Access or other applications using the database files

**Docker "Permission denied" errors (Linux/macOS)**
```bash
sudo chown -R $(id -u):$(id -g) data output logs
```

## Contributing

Contributions should:
- Maintain backward compatibility with original data processing logic
- Include unit tests
- Follow Spring Boot best practices
- Support cross-platform execution

## License

See [LICENSE](LICENSE) for details.

## Author

**Matthew Charno**
- Original version: 2008
- Modernization: 2025

## Acknowledgments

- **Sikyon Survey Project** - University of Thessaly, University of York, Institute of Mediterranean Studies at FORTH
- **Spring Boot** - https://spring.io/projects/spring-boot
- **UCanAccess** - https://github.com/ucanaccess/ucanaccess
