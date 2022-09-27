<a href="https://www.sqlite.org/index.html">
    <img align="right" src ="https://img.shields.io/badge/SQLite-07405e.svg?logo=sqlite&logoColor=white" alt="SQLite">
</a>

<a href="https://www.oracle.com/es/database/technologies/appdev/sql.html">
    <img align="right" src="https://custom-icon-badges.herokuapp.com/badge/SQL-025E8C.svg?logo=database&logoColor=white" alt="SQL">
</a>

<a href="https://www.java.com/">
    <img align="right" src="https://custom-icon-badges.herokuapp.com/badge/Java-E8E8E8.svg?logo=Java" alt="Java">
</a>

<h1 align="center">🗄 Database Connection Demo 📂</h1>


<p align="center">
    <img src="./assets/Execution of Database Connection.gif" alt="Database Connection Execution">
    <sub>· Execution of Database Connection ·</sub>
</p>


# 📖 Table of contents

1. [SQLite JDBC Driver](#SQLite-JDBC-Driver)
2. [Usage](#Usage)
    * [Example usage](#Example-usage)
3. [How does SQLiteJDBC work?](#How-does-SQLiteJDBC-work?)
4. [Supported Operating Systems](#Supported-Operating-Systems)
5. [Download](#Download)
6. [How to Specify Database Files](#How-to-Specify-Database-Files)
7. [How to Use Memory Databases](#How-to-Use-Memory-Databases)
8. [How to use Online Backup and Restore Feature](#How-to-use-Online-Backup-and-Restore-Feature)
9. [Creating BLOB data](#Creating-BLOB-data)
10. [Reading Database Files in classpaths or network (read-only)](#Reading-Database-Files-in-classpaths-or-network-(read-only))


## 📚 SQLite JDBC Driver <a name="SQLite-JDBC-Driver"></a>

SQLite JDBC is a library for accessing and creating [SQLite](http://sqlite.org) database files in Java. SQLiteJDBC library requires no configuration since native libraries for major OSs, including Windows, macOS and GNU/Linux distributions, are assembled into a single JAR (Java Archive) file.

## ⚙️ Usage <a name="Usage"></a>

SQLite JDBC is a library for accessing SQLite databases through the JDBC API. For the general usage of JDBC, see [JDBC Tutorial](http://docs.oracle.com/javase/tutorial/jdbc/index.html) or [Oracle JDBC Documentation](http://www.oracle.com/technetwork/java/javase/tech/index-jsp-136101.html).

1. Download [sqlite-jdbc-(VERSION).jar](https://github.com/BrianSuarezSantiago/Database-Connection/raw/master/sqlite-jdbc-3.39.2.0.jar) then append this JAR file into your classpath.
2. Open a SQLite database connection from your code. (See the example below)

### 🚀 Example usage <a name="Example-usage"></a>

Assuming `sqlite-jdbc-(VERSION).jar` or the `pom.xml file` with the dependency is placed in the current directory.

```shell
> javac Sample.java
> java -classpath ".;sqlite-jdbc-(VERSION).jar" Sample    # in Windows
or
> java -classpath ".:sqlite-jdbc-(VERSION).jar" Sample    # in macOS or Linux
Name = Harry
Id = 1
Name = Tom
Id = 2
```

**Sample.java**

```java
package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sample {

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:Database.db");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);    // Set timeout to 30 sec

            statement.executeUpdate("DROP TABLE IF EXISTS PERSON");
            statement.executeUpdate("CREATE TABLE PERSON(Id INTEGER, Name STRING)");
            statement.executeUpdate("INSERT INTO PERSON VALUES(1, 'Harry')");
            statement.executeUpdate("INSERT INTO PERSON VALUES(2, 'Tom')");

            ResultSet rs = statement.executeQuery("SELECT * FROM PERSON");

            while(rs.next()) {
                // Read the result set
                System.out.println("Name = " + rs.getString("Name"));
                System.out.println("Id = " + rs.getInt("Id"));
            }
        } catch(SQLException e) {
            // If the error message is "out of memory", it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }
    }
}
```

## 👨🏻‍💻 How does SQLite JDBC work? <a name="How-does-SQLiteJDBC-work?"></a>

Our SQLite JDBC driver package (i.e., `sqlite-jdbc-(VERSION).jar`) contains three types of native SQLite libraries (`sqlite-jdbc.dll`, `sqlite-jdbc.jnilib`, `sqlite-jdbc.so`), each of them is compiled for Windows, MacOS and Linux. An appropriate native library file is automatically extracted into your OS's temporary folder, when your program loads `org.sqlite.JDBC` driver.

## 🖥️ Supported Operating Systems <a name="Supported-Operating-Systems"></a>

Since sqlite-jdbc-3.6.19, the natively compiled SQLite engines will be used for the following operating systems:

|              | x86 | x86_64 | armv5 | armv6 | armv7 | arm64 | ppc64 |
|--------------|-----|--------|-------|-------|-------|-------|-------|
| Windows      | ✔   | ✔      |       |       | ✔     | ✔     |       |
| macOS        |     | ✔      |       |       |       | ✔     |       |
| Linux (libc) | ✔   | ✔      | ✔     | ✔     | ✔     | ✔     | ✔     |
| Linux (musl) |     | ✔      |       |       |       | ✔     |       |
| Android      | ✔   | ✔      | ✔     |       |       | ✔     |       |
| FreeBSD      | ✔   | ✔      |       |       |       | ✔     |       |

In the other OSs not listed above, the pure-java SQLite is used. (Applies to versions before 3.7.15)

## ⬇️ Download <a name="Download"></a>

Download from [Maven Central](https://search.maven.org/artifact/org.xerial/sqlite-jdbc) or from the [releases](https://www.sqlite.org/changes.html) page.

```xml
<dependencies>
    <dependency>
      <groupId>org.xerial</groupId>
      <artifactId>sqlite-jdbc</artifactId>
      <version>(version)</version>
    </dependency>
</dependencies>
```

Snapshots of the development version are available in [Sonatype's snapshots repository.](https://oss.sonatype.org/content/repositories/snapshots/org/xerial/sqlite-jdbc/)

You may need to add shade plugin transformer to solve `No suitable driver found for jdbc:sqlite:` issue.

```xml
<transformer
	implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
	<resource>META-INF/services/java.sql.Driver</resource>
</transformer>
```

```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>(version)</version>
</dependency>
```

## How to Specify Database Files <a name="How-to-Specify-Database-Files"></a>

Here is an example to establishing a connection to a database file `C:\work\MyDatabase.db` (in Windows)

```java
Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/work/MyDatabase.db");
```

Opening a UNIX (Linux, MacOS, etc.) file `/home/leo/work/MyDatabase.db`

```java
Connection connection = DriverManager.getConnection("jdbc:sqlite:/home/leo/work/MyDatabase.db");
```

## How to Use Memory Databases <a name="How-to-Use-Memory-Databases"></a>

SQLite supports on-memory database management, which does not create any database files. To use a memory database in your Java code, get the database connection as follows:

```java
Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
```

And also, you can create memory database as follows:

```java
Connection connection = DriverManager.getConnection("jdbc:sqlite:");
```

## How to use Online Backup and Restore Feature <a name="How-to-use-Online-Backup-and-Restore-Feature"></a>

Take a backup of the whole database to `backup.db` file:

```java
// Create a memory database
Connection conn = DriverManager.getConnection("jdbc:sqlite:");
Statement stmt = conn.createStatement();
// Do some updates
stmt.executeUpdate("CREATE TABLE Sample(id, name)");
stmt.executeUpdate("INSERT INTO Sample VALUES(1, \"Harry\")");
stmt.executeUpdate("INSERT INTO Sample VALUES(2, \"Tom\")");
// Dump the database contents to a file
stmt.executeUpdate("BACKUP TO backup.db");
```

Restore the database from a backup file:

```java
// Create a memory database
Connection conn = DriverManager.getConnection("jdbc:sqlite:");
// Restore the database from a backup file
Statement stat = conn.createStatement();
stat.executeUpdate("RESTORE FROM backup.db");
```

## Creating BLOB data <a name="Creating-BLOB-data"></a>

1. Create a table with a column of blob type: `CREATE TABLE T(id INTEGER, data BLOB)`
1. Create a prepared statement with `?` symbol: `INSERT INTO T VALUES(1, ?)`
1. Prepare a blob data in byte array (e.g., `byte[] data = ...`)
1. `preparedStatement.setBytes(1, data)`
1. `preparedStatement.execute()...`

## Reading Database Files in classpaths or network (read-only) <a name="Reading-Database-Files-in-classpaths-or-network-(read-only)"></a>

To load database files that can be found from the class loader (e.g., database files inside a JAR file in the classpath), use `jdbc:sqlite::resource:` prefix. 

For example, here is an example to access an SQLite database file, `Sample.db` in a Java package `org.yourdomain`:

```java
Connection connection = DriverManager.getConnection("jdbc:sqlite::resource:org/yourdomain/sample.db");
```

In addition, external database resources can be used as follows:

```java
Connection connection = DriverManager.getConnection("jdbc:sqlite::resource:http://www.xerial.org/svn/project/XerialJ/trunk/sqlite-jdbc/src/test/java/org/sqlite/sample.db");
```

To access database files inside some specific JAR file (in local or remote), use the [JAR URL](http://java.sun.com/j2se/1.5.0/docs/api/java/net/JarURLConnection.html):

```java
Connection connection = DriverManager.getConnection("jdbc:sqlite::resource:jar:http://www.xerial.org/svn/project/XerialJ/trunk/sqlite-jdbc/src/test/resources/testdb.jar!/sample.db");
```

Database files will be extracted to a temporary folder specified in `System.getProperty("java.io.tmpdir")`

<hr>
<p align="center">
Made with ♥️ in Spain
</p>
