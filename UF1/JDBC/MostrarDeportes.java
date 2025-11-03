package UF1.JDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class MostrarDeportes {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/sports";
        String user = "postgres";
        String password = "1234";

        String sql = "SELECT cod, nombre FROM deportes";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Llista d'esports:");
            System.out.println("------------------");

            while (rs.next()) {
                int cod = rs.getInt("cod");
                String nombre = rs.getString("nombre");
                System.out.println(cod + " - " + nombre);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar o consultar la base de datos:");
            e.printStackTrace();
        }
    }
}
