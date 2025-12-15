
 

package castellet.dam.m6.uf2;

//import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        System.out.println( "Hello World!" );

        try {
            Connection conn = ConnectionManager.getConnection();
          // Connection conn = DriverManager.getConnection("jdbc:mysql://mysql-rfam-public.ebi.ac.uk:4497/Rfam","rfamro","");
            // Close the connection
            System.out.println("OLE!");     

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clan ");       
            
            //System.out.println("Column Value: " + columnValue);

            while (resultSet.next()) {
                String columnValue = resultSet.getString("description");
                String column2Value = resultSet.getString("id");
                System.out.println(column2Value+" Column Value: " + columnValue);
            }        



            conn.close();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());


        }            // Register the PostgreSQL driver


            // Connect to the database



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

      
            // Perform desired database operations

 }
}
