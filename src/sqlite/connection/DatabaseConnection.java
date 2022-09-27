package sqlite.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Class that allows the connection to a local database
 * creating as a result, a database file called "Database
 * Connection.db" with empty tables, indexes, views and
 * triggers.
 *
 * @author Brian Suárez Santiago
 * @version 1.0.0
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html">Package java.sql</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/">Java JDBC API</a>
 */
public class DatabaseConnection {

    /**
     * Establishes a connection to a local database from
     * a specified URL. If the connection establishment
     * fails, it is informed through an error otherwise,
     * it displays a message reporting about it and finally
     * closes the established connection.
     */
    private static void connect() {
        String urlDB = "jdbc:sqlite:Database Connection.db";
        Connection connection = null;

        try {
            // Connection connection = DriverManager.getConnection(urlDB); Connection cannot be referenced in finally
            connection = DriverManager.getConnection(urlDB);
            System.out.println("A connection has been created!");
        } catch(SQLException exception) {
            System.out.println(exception.getMessage());    // Generates error message
        } finally {
            if(connection != null) {    // If the DB creation does not give any error, the connection will not be null and will point to the urlDB
                try {
                    connection.close();    // Closing the connection to the DB may fail (e.g. operations are being performed on the DB)
                    System.out.println("Connection has been closed successfully!");
                } catch(SQLException exception) {
                    System.out.println(exception.getMessage());    // Generates a message in the form of a log if an error has occurred
                }
            }
        }
    }

    /**
     * Establishes a connection to the local database
     * specified in the call to the connect() method.
     */
    public static void main(String[] args) {
        connect();
    }
}