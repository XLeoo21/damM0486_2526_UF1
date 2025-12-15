//import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
    public static void main(String[] args) {

        // String jdbcUrl = "jdbc:postgresql://hh-pgsql-public.ebi.ac.uk:5432/pfmegrnargs";
        // String username = "alex";
        // String password = "Castellet:17";

        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "1234";

            // Register the PostgreSQL driver

            //Class.forName("org.postgresql.Driver");

            // Connect to the database

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                // Close the connection
                System.out.println("OLE!");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM deportes");       
                

                while (resultSet.next()) {
                    String columnValue = resultSet.getString("nombre");
                    System.out.println("Column Value: " + columnValue);
                }


// int foovalue = 500;
// PreparedStatement st = conn.prepareStatement("SELECT * FROM mytable WHERE columnfoo = ?");
// st.setInt(1, foovalue);
// ResultSet rs = st.executeQuery();
// while (rs.next()) {
//     System.out.print("Column 1 returned ");
//     System.out.println(rs.getString(1));
// }




// CallableStatement callStmt = c.prepareCall("{call myFunc(?, ?, ?)}");
// callStmt.setString(1, "1");
// callStmt.setString(2, "2");
// callStmt.setString(3, "3");
// callStmt.execute();
// callStmt.close();

                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Perform desired database operations

        }
}