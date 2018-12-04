
package cosc458.sql_injection.java;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.ServletException;
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
    public void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
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
     
     
     protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"loginForm.css\">");
        
        try {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            Connection conn = null;
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "users";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "YOUR DB PASSWORD";
            
            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
                
                out.print("<h2>Username entered is: " + user + "</h2><br>");
                
                /* Code that makes the program vulnerable to SQL injection */
                Statement st = conn.createStatement();
                String query = "SELECT * FROM  customers where username='" + 
                        user + "' AND password='" + pass + "';";
                ResultSet res = st.executeQuery(query);
                
                String id, uname, fName, lName, addr;
                out.print("<h3>Results: </h3><br>");
                out.print("<table> <tr> <th>ID</th> <th>Username</th> "
                        + "<th>First Name</th> <th>Last Name</th> "
                        + "<th>Address</th> </tr>");
                while (res.next()) {
                    id = res.getString("id");
                    uname = res.getString("username");
                    fName = res.getString("fName");
                    lName = res.getString("lName");
                    addr = res.getString("address");
                    out.print("<tr> <td>" + id + "</td> <td>" + uname + 
                            "</td> <td>" + fName + "</td> <td>" + lName + 
                            "</td> <td>" + addr + "</td></tr>");
                }
                out.print("</table><br>");
                conn.close();

            } catch (ClassNotFoundException | IllegalAccessException | 
                    InstantiationException | SQLException e) {
                System.out.println(e.getMessage());
            }
        } finally {
            out.close();
        }
    }// end of processRequest method
}