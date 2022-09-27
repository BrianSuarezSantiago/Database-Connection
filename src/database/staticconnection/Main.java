package database.staticconnection;

/**
 * Class that initializes a connection to a local
 * database and is responsible for creating a new
 * database, and table, inserting the data, and
 * querying the table by calling the respective
 * methods.
 *
 * @author Brian Suárez Santiago
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        Database database =  new Database("Database.db");    // Extension must be specified

        database.createNewDatabase();
        database.createNewTable();
        database.insertData("address@outlook.com");
        database.insertData("address@gmail.com");
        database.insertData("address@ulpgc.es");
        //database.query1();
        database.query2();
    }
}