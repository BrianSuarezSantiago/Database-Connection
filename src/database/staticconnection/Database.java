package database.staticconnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
/**
 * Class representing a database. The class has
 * the necessary public methods to obtain all the
 * information needed to create a local database
 * using the JDBC driver provided by SQLite.
 *
 * @author Brian Suárez Santiago
 * @version 1.0.0
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html">Package java.sql</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/">Java JDBC API</a>
 */
public class Database {
    private final String dbPath;

    /**
     * Constructor that initializes a database by taking
     * as data the path of the database .db file.
     *
     * @param dbPath
     */
    public Database(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * Establishes a connection to a local database from
     * a specified URL. If the connection establishment
     * fails, it is reported through an error, otherwise,
     * it displays a message informing about it.
     *
     * @return Connection to the database.
     */
    private Connection connect() {
        Connection connection = null;
        String url = "jdbc:sqlite:" + this.dbPath;

        try {
            connection = DriverManager.getConnection(url);
        } catch(SQLException exception) {
            System.out.println("" + exception.getMessage());
        }
        return connection;
    }

    /**
     * Closes the connection established with the database.
     *
     * @param connection Connection to database.
     */
    private void close(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch(SQLException exception) {
                System.out.println("" + exception.getMessage());
            }
        }
    }

    /**
     * Returns all rows and all columns from the PEOPLE
     * table obtaining thus, all the data contains in it
     * and finally closes the established connection.
     */
    public void query1() {
        Connection connection = null;
        String sql = "SELECT * FROM People;";

        try {
            connection = this.connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                System.out.println(rs.getInt("Id") + "\t" + rs.getString("Name") + "\t");
            }
        } catch(SQLException exception) {
            System.out.println("" + exception.getMessage());
        } finally {
            close(connection);
        }
    }

    /**
     * Returns all rows and all columns from the Emails
     * table obtaining thus, all the data contains in it
     * and finally closes the established connection.
     */
    public void query2() {
        Connection connection = null;
        String sql = "SELECT * FROM Emails;";

        try {
            connection = this.connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                System.out.println(rs.getInt("Id") + "\t" + rs.getString("Address") + "\t");
            }
        } catch(SQLException exception) {
            System.out.println("" + exception.getMessage());
        } finally {
            close(connection);
        }
    }

    /**
     * Creates a database from a specified URL. If the
     * connection establishment fails, it is reported
     * through an error, otherwise, it displays a
     * message informing about it and finally closes
     * the established connection.
     */
    public void createNewDatabase() {
        Connection connection = null;
        String url = "jdbc:sqlite:" + this.dbPath;

        try {
            connection = DriverManager.getConnection(url);
            if(connection != null) {
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println("The database driver is " + metaData.getDriverName());
                System.out.println("A new database has been created!!");
            }
        } catch(SQLException exception) {
            System.out.println("" + exception.getMessage());
        } finally {
            // Regardless of whether the output code is 0 or 1 close the db connection
            close(connection);
        }
    }

    /**
     * Creates a new table into which the data will
     * be inserted, in the database specified by URL.
     *
     * Rename "Emails" to "People" if you want to create
     * the People table and the "Address" column name to
     * "Name" to create the specific column of the People
     * table.
     */
    public void createNewTable() {
        Connection connection = null;
        String sql = "CREATE TABLE IF NOT EXISTS Emails (\n" +
                     "Id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                     "Address TEXT NOT NULL);";

        try {
            connection = this.connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch(SQLException exception) {
            System.out.println("" + exception.getMessage());
        } finally {
            close(connection);
        }
    }

    /**
     * Inserts the e-mail addresses into the specified
     * table. In case the insertion of the data cannot
     * be performed, an informative error message is
     * displayed if not, it inserts the data and closes
     * the connection to the database.
     *
     * @param emailAddress E-mail address to be counted.
     */
    public void insertData(String emailAddress) {
        Connection connection = null;
        String sql = "INSERT INTO Emails(Address) VALUES (?);";

        try {
            connection = this.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, emailAddress);
            pstmt.executeUpdate();
        } catch(SQLException exception) {
            System.out.println("" + exception.getMessage());
        } finally {
            close(connection);
        }
    }
}