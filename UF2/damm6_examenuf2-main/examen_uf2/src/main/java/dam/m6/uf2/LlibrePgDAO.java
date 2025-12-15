package dam.m6.uf2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LlibrePgDAO implements DAO<Llibre> {

    private Connection conn;

    public LlibrePgDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void add(Llibre item) {
        String sql = "INSERT INTO llibres (title,author) VALUES (?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getTitle());
            stmt.setString(2, item.getAuthor());
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Llibre afegit correctament --> " + item.getTitle());
            }

        } catch (SQLException e) {
            System.out.println("Error afegint llibre: " + e.getMessage());
        }
    }

    @Override
    public List<Llibre> getAll() {
        List<Llibre> llibre = new ArrayList<>();
        String sql = "SELECT * FROM llibres";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");

                llibre.add(new Llibre(id, title, author));
            }
        } catch (SQLException e) {
            System.out.println("Error llistant llibres: " + e.getMessage());
        }

        return llibre;
    }

}
