Sikyon Data Processing Scripts
==============================

This project was created to automate the cleaning and integration of the Sikyon data into a usable format for the purpose of publication and interpretation.

## Repository Structure

This repository now contains **two versions** of the Sikyon data processing scripts:

### 📁 `original/` - Original Publication Code (2008)
The original code written in 2008, preserved exactly as it was when it processed the data used in the publication. This code is referenced in the Sikyon Survey monograph and is maintained without modification to ensure reproducibility of the published results.

The original code was written with the benefit of hindsight in a rather sloppy way, with hardcoded paths, no tests, and various antipatterns. However, it has been left untouched to ensure there is no chance of inadvertent modification to the logic which would alter the processing of the published data.

**Documentation:** See [original/README.md](original/README.md) for details on running the original code.

### 📁 `modernized/` - Modernized Version (2025)
A complete modernization of the data processing scripts using current best practices and patterns:

- ✅ **Modern Java 17+** with Records, Stream API, and modern language features
- ✅ **Spring Boot** for dependency injection and configuration management
- ✅ **Cross-platform compatibility** (Windows, macOS, Linux)
- ✅ **Externalized configuration** via YAML files
- ✅ **Proper logging** with SLF4J and Logback
- ✅ **Repository pattern** for data access
- ✅ **Open-source dependencies** (no commercial JDBC drivers required)
- ✅ **Docker support** for consistent execution environments
- ✅ **Type safety** and immutability with Records
- ✅ **Modern error handling** and validation

**Documentation:** See [modernized/README.md](modernized/README.md) for details on running the modernized version.

## Quick Start

### Running the Original Version
```bash
cd original
mvn clean compile
# See original/README.md for detailed instructions
```

### Running the Modernized Version
```bash
cd modernized
mvn spring-boot:run
# See modernized/README.md for detailed instructions
```

Publication
-----------
Having said all of that, the code enabled an efficient and repeatable way to process the original survey data in a way that benefited the archaeologists trying to understand the site. The monograph this code and data helped shape will be published in Meletemata in 2018 by the [Greek National Research Foundation](https://history-bookstore.eie.gr/en/section/greek-roman-antiquity/meletemata/). The updated bibliographic reference will be published here when it is available.

The Sikyon Survey Project
-------------------------
The project was a multidisciplinary research program to study the human presence and activity on the plateau of ancient Sikyon, a city in northeastern Peloponnese between Corinth and Achaia. The project ran between the summers of 2004 and 2009 and was undertaken by the University of Thessaly in collaboration with  the University of York (UK), the Institute of Mediterranean Studies at FORTH and the 37th Ephoreia of Prehistoric and Classical Antiquities.

The primary activity of the project was an urban survey of the ancient city, which quantified and qualified both pottery and architectural observations from the field over the 5 seasons. That data was captured in a database and GIS, which aided the archaeologists in making their interpretation of the ancient city and its use.

Further interpretations and discussions about the Sikyon Survey Project can be found in the [project website](http://extras.ha.uth.gr/sikyon/en/), or from the following publications:

* Lolos, Y. 2011 'Land of Sikyon: archaeology and history of a Greek city-state', Hesperia suppl. 39.
* Lolos, Y., Gourley, B. and Stewart, D. 2008 'The Sikyon Survey Project: a blueprint for urban survey?', Journal of Mediterranean Archaeology **20** (2), 267-96.
* Charno, M. 2007 'An Interactive Image Using SVG and Ajax in Archaeology', [Internet Archaeology](http://intarch.ac.uk/) **23**. https://doi.org/10.11141/ia.23.5

Building the Project
--------------------
This project was originally built using Netbeans and Ant, but i've added Maven to the project to make this process (and the overall project structure) more generic.

Dependencies
------------
### OpenCSV ###
This library was used to write CSV after parsing and processing data from the database. This can be pulled in from Maven Central.

### OJDBC6 ###
This library was used to drive JDBC connections to Oracle databases, which was utilised to insert processed data into an Oracle ArcSDE database hosted by the Archaeology Data Service. The loader that does that was used to process the data, associate it with its relative spatial entity, then export the combined data as an ESRI Shapefile for use in desktop GIS.

### Access JDBC ###
A library is necessary to create a JDBC connection to an MS Access database. A example product that is available commercially is the [HXTT JDBC Driver for Access](http://www.hxtt.com/access.html). To add this to your project to re-run the scripts, purchase a license from HXTT and download the JAR. Once downloaded add it to your local maven repository with:

    mvn install:install-file -DgroupId=com.hxtt.sql.access -DartifactId=accessjdbc4 \
     -Dversion=v5.1 -Dpackaging=jar -Dfile=Access_JDBC40.jar -DgeneratePom=true

 Then add the following to your `pom.xml` dependencies entry:

    <dependency>
         <groupId>com.hxtt.sql.access</groupId>
         <artifactId>accessjdbc4</artifactId>
         <version>v5.1</version>
     </dependency>

There are also other libraries available, such as [UCanAccess](http://ucanaccess.sourceforge.net/site.html).

Running the Scripts
-------------------
To run the scripts, you can simply run the main method in any of the classes in the `scripts` package. These scripts were used to get some data out of a database, process/clean it, then reinsert the data back in the database or create an export. The available scripts are:

* `DBCLeaner`: A utility script to correct or update Tract & Square IDs
* `DensityBuilder`: A script to build raw and visibility corrected densities values for each Square
* `MainDatabase`: A script to create percentages for pottery types, shapes and periods based on square counts, taking into account the idiosyncrasies of each pottery specialists recording technique
* `PotteryTableBuilder`: A script to take pottery data and import it into a geospatial database for rendering in a GIS
* `PotteryTableHellenistic`: A script to export the specialist Hellenistic pottery data in the pipe delimited format
* `PotteryTableRoman`: A script to clean the Roman pottery data

Conclusion
----------
This code ain't pretty, but it did its job and is now available for others to investigate and re-process as required. It was also written before I discovered Spring and JPA, so be nice...
