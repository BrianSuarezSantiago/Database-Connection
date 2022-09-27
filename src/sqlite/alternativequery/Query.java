package sqlite.alternativequery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 * Class responsible for establishing a connection to
 * the database named Kata5 through the file Kata5.db
 * as well as performing a query to the database table.
 *
 * @author Brian Suárez Santiago
 * @version 1.0.0
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html">Package java.sql</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/">Java JDBC API</a>
 */
public class Query {

    /**
     * Establishes a connection to a local database from
     * a specified URL. If the connection establishment
     * fails, it is reported through an error, otherwise,
     * it displays a message informing about it.
     *
     * @return Connection established with the database.
     */
    private Connection connect() {
        String urlDB = "jdbc:sqlite:";
        String dbPath = urlDB + "Database.db";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbPath);
            System.out.println("A connection to " + dbPath + " has been created successfully!");
        } catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return connection;
    }

    /**
     * Returns all rows and all columns from the People
     * table obtaining thus, all the data contains in it.
     */
    public void query() {
        Connection connection = this.connect();
        String sql = "SELECT * FROM PEOPLE;";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {    // It is convenient that it is in the try because an error may occur if the fields do not exist
                System.out.println(result.getInt("Id") + "\t" +
                        result.getString("Name") + "\t" +
                        result.getString("Surname") + "\t" +
                        result.getString("Role"));
            }
        } catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}