package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by pawel on 14.04.2017.
 */
public class dbConnection {

//    private static final String USERNAME = "dbuser";
//    private static final String PASSWORD = "dbpassword";
//    private static final String CONN = "jdbc:mysql:localhost/login";
    private static final String SQLITECONN = "jdbc:sqlite:school.sqlite";

    public static Connection getConnection() throws SQLException{

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQLITECONN);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
