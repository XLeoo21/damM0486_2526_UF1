package dam.m6.uf2.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtletaDAO implements DAO<Atleta>{

    private Connection conn;

    public AtletaDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Afegeix un atleta a la base de dades
     * 
     * @param item L'atleta a afegir
     * @throws SQLException Si no s'ha pogut afegir l'atleta
     */
    @Override
    public void add(Atleta item) {
        String sql = "INSERT INTO deportistas (nombre, cod_deporte) VALUES (?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getNom());
            stmt.setInt(2, item.getCodEsport());
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Atleta afegit correctament --> " + item.getNom());

            }
        } catch (SQLException e) {
            System.out.println("Error afegint atleta: " + e.getMessage());
        }
    }

    @Override
    public List<Atleta> getAll() {
        
        List<Atleta> atletas = new ArrayList<>();

        String sql = "SELECT * FROM lista_atletas()";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int cod = rs.getInt("cod");
                String nombre = rs.getString("nombre");
                String deporte = rs.getString("deporte");
                atletas.add(new Atleta(cod, nombre, deporte));
            }

        } catch (SQLException e) {
            System.out.println("Error llistant atletas: " + e.getMessage());
        }

        return atletas;
    }

    @Override
    public List<Atleta> getAll(String filter) {
        
        List<Atleta> atleta = new ArrayList<>();
        String sql = "SELECT * FROM lista_atletas()";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int cod = rs.getInt("cod");
                String nombre = rs.getString("nombre");
                String deporte = rs.getString("deporte");
                atleta.add(new Atleta(cod, nombre, deporte));
            }

        } catch (SQLException e) {
            System.out.println("Error llistant atletas: " + e.getMessage());
        }

        return atleta;
        
    }

    @Override
    public List<Atleta> getOne(String filter) {
        List<Atleta> atleta = new ArrayList<>();
        String sql = "SELECT * FROM lista_atleta(?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filter);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int cod = rs.getInt("cod");
                String nombre = rs.getString("nombre");
                String deporte = rs.getString("deporte");
                atleta.add(new Atleta(cod, nombre, deporte));
            }

        } catch (SQLException e) {
            System.out.println("Error llistant atletas: " + e.getMessage());
        }

        return atleta;
    }

    @Override
    public List<Atleta> getAll(int filter) {
        
        List<Atleta> atleta = new ArrayList<>();
        String sql = "SELECT * FROM lista_atletas(?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, filter);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int cod = rs.getInt("cod");
                String nombre = rs.getString("nombre");
                String deporte = rs.getString("deporte");
                atleta.add(new Atleta(cod, nombre, deporte));
            }

        } catch (SQLException e) {
            System.out.println("Error llistant atletas: " + e.getMessage());
        }

        return atleta;
        
    }


}
