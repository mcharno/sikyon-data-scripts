# Original Sikyon Data Processing Scripts (2008)

## About This Version

This is the **original code** written in 2008 that was used to process the data for the Sikyon Survey monograph. It has been preserved exactly as it was to ensure reproducibility of the published results.

**⚠️ This code is maintained for historical and reproducibility purposes only.** For new work, please use the modernized version in the `../modernized/` directory.

## Original Description

This project was created to automate the cleaning and integration of the Sikyon data into a usable format for the purpose of publication and interpretation. It was primarily written in 2008 and with the benefit of hindsight, in a rather sloppy and unfortunate way. The point of this code being "published" on GitHub is not for ridicule or self-deprecation, but rather to provide the original code to anyone wishing to challenge or reinterpret the conclusions from the Sikyon Survey monograph.

Revisiting this code in preparation for "publication" has brought on feelings of bemusement and minor revulsion. And while it would be very easy to refactor the majority of the bad practice, antipatterns, and general ugliness out of the code, I felt it was important to leave the code as it was when it originally processed the data used in the analysis and interpretation for the publication. In this way, there is no chance of an inadvertent modification to the logic which would in turn alter the processing of the data.

Lots of stuff was just hardcoded, such as paths and configuration details. The database paths which referenced the original locations on my old Linux workstation have been left in for reference, although they are obviously totally irrelevant. The original project was created in Netbeans, but all traces of Netbeans specific stuff has been removed. There is also not a single test that graces this project, it was tested and validated cowboy style on copies of the database.

## Building the Project

This project was originally built using Netbeans and Ant, but Maven has been added to make this process (and the overall project structure) more generic.

```bash
mvn clean compile
```

## Dependencies

### OpenCSV
This library was used to write CSV after parsing and processing data from the database. This can be pulled in from Maven Central.

### OJDBC6
This library was used to drive JDBC connections to Oracle databases, which was utilised to insert processed data into an Oracle ArcSDE database hosted by the Archaeology Data Service. The loader that does that was used to process the data, associate it with its relative spatial entity, then export the combined data as an ESRI Shapefile for use in desktop GIS.

### Access JDBC
A library is necessary to create a JDBC connection to an MS Access database. A example product that is available commercially is the [HXTT JDBC Driver for Access](http://www.hxtt.com/access.html). To add this to your project to re-run the scripts, purchase a license from HXTT and download the JAR. Once downloaded add it to your local maven repository with:

```bash
mvn install:install-file -DgroupId=com.hxtt.sql.access -DartifactId=accessjdbc4 \
 -Dversion=v5.1 -Dpackaging=jar -Dfile=Access_JDBC40.jar -DgeneratePom=true
```

Then add the following to your `pom.xml` dependencies entry:

```xml
<dependency>
     <groupId>com.hxtt.sql.access</groupId>
     <artifactId>accessjdbc4</artifactId>
     <version>v5.1</version>
 </dependency>
```

There are also other libraries available, such as [UCanAccess](http://ucanaccess.sourceforge.net/site.html).

## Running the Scripts

To run the scripts, you can simply run the main method in any of the classes in the `scripts` package. These scripts were used to get some data out of a database, process/clean it, then reinsert the data back in the database or create an export. The available scripts are:

* `DBCLeaner`: A utility script to correct or update Tract & Square IDs
* `DensityBuilder`: A script to build raw and visibility corrected densities values for each Square
* `MainDatabase`: A script to create percentages for pottery types, shapes and periods based on square counts, taking into account the idiosyncrasies of each pottery specialists recording technique
* `PotteryTableBuilder`: A script to take pottery data and import it into a geospatial database for rendering in a GIS
* `PotteryTableHellenistic`: A script to export the specialist Hellenistic pottery data in the pipe delimited format
* `PotteryTableRoman`: A script to clean the Roman pottery data

## Conclusion

This code ain't pretty, but it did its job and is now available for others to investigate and re-process as required. It was also written before I discovered Spring and JPA, so be nice...

---

**For a modern, maintainable version of this code, see the [../modernized/](../modernized/) directory.**
