package dam.m6.uf2.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String host = "";   
    private static String port = "";
    private static String database = "";
    private static String username = "";
    private static String password = "";
    private static Connection con = null;
    private static String urlstring;

    public static Connection getConnection(String config_file) {

        // Llegim dades de l’XML
        ReadConfigXML config = new ReadConfigXML(config_file);

        host = config.getHost();
        port = config.getPort();
        database = config.getDatabase();
        username = config.getUser();
        password = config.getPassword();

        System.out.println("DEBUG: Dades obtingudes del fitxer XML -> "
                + host + " / " + port + " / " + database + " / " + username);

        // Si no s’ha carregat res → atura
        if (host == null || port == null || database == null || username == null) {
            System.out.println("ERROR: No s’ha pogut carregar el fitxer XML.");
            return null;
        }

        // Construïm la URL correcte
        urlstring = "jdbc:postgresql://" + host + ":" + port + "/" + database;

        try {
            con = DriverManager.getConnection(urlstring, username, password);
            System.out.println("Connection established successfully.");
            return con;

        } catch (SQLException ex) {
            System.out.println("Failed to create the database connection.");
            System.out.println("Reason: " + ex.getMessage());
            return null;
        }
    }
}
