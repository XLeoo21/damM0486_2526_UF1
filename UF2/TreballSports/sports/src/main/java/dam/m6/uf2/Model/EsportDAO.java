package dam.m6.uf2.Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;

public class EsportDAO implements DAO<Esport> {
    private Connection conn;

    public EsportDAO(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public void add(Esport item) {
        String sql = "INSERT INTO deportes (nombre) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getNom());
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Esport afegit correctament --> " + item.getNom());

            }
        } catch (SQLException e) {
            System.out.println("Error afegint esport: " + e.getMessage());
        }
    }

    @Override
    public List<Esport> getAll() {
        List<Esport> esports = new ArrayList<>();

        String sql = "SELECT * FROM lista_deportes()";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int cod = rs.getInt("cod");
                String nombre = rs.getString("nombre");
                esports.add(new Esport(cod, nombre));
            }

        } catch (SQLException e) {
            System.out.println("Error llistant esports: " + e.getMessage());
        }

        return esports;
    }

}
