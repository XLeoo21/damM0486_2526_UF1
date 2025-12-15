package castellet.dam.m6.uf2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String urlstring = "";    
    private static String username = "";   
    private static String password = "";
    private static Connection con;

    public static Connection getConnection() {
            try {
                con = DriverManager.getConnection(urlstring, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection."); 
            }
        return con;
    }
}