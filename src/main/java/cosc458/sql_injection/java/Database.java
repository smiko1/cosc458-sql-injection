
package cosc458.sql_injection.java;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * THESE METHODS ARE NOT CURRENTLY BEING USED - I PUT THEM HERE TO TAKE SCREENSHOTS
 * AND INSERT THEM INTO OUR PAPER, AND FOR TESTING PURPOSES. THEREFORE, THIS WHOLE
 * CLASS CAN BE DISREGARDED FOR NOW..
 * 
 * @author Shannon Miko
 */
public class Database {

    final Pattern userIdPattern = Pattern.compile("^[a-zA-Z0-9]*$");
    
    //WHITELIST INPUT VALIDATION
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String userId = request.getParameter("username");
            if (!userIdPattern.matcher(userId).matches()) {
                throw new Exception("Improper username format.");
            }
            //if all characters are legal, validate the user
        } catch (Exception e) {
            response.sendError(response.SC_BAD_REQUEST, e.getMessage());
        }
    }
    
     public static void storedProcedures(int candidateId) {
        String user = "smiko";
            String pass = "yo";
            Connection conn = null;
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "users";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "YOUR DB PASSWORD";
            
        String query = "{ call get_user_name(?) }";
        ResultSet res;
 
        try {
            conn = DriverManager.getConnection(url + dbName, userName, password);
                
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setString(1, user);
            res = stmt.executeQuery();
            
            while (res.next()) {
                System.out.println(String.format("%s - %s",
                        res.getString("id") + " "
                        + res.getString("fName"),
                        res.getString("lName")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
   }
}