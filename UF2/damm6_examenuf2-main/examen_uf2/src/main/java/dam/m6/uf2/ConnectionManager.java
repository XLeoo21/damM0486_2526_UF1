package dam.m6.uf2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection getConnection() throws SQLException {

        Connection conn = null;
        // Configuración directa de la base de datos
        String host = "localhost";
        String port = "5432";
        String user = "postgres";
        String password = "1234";
        String database = "llibres";

        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conn;
    }

}
