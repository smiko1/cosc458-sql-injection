
package cosc458.sql_injection.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DBConnection extends HttpServlet {
    
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
            String password = "rootpw";
            
            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
                
                Statement st = conn.createStatement();
                out.print("<h2>Username entered is: " + user + "</h2><br>");
                String query = "SELECT * FROM  customers where username='" + user + "' AND password='" + pass + "';";
                ResultSet res = st.executeQuery(query);
                
                if (!res.isBeforeFirst() ) {    
                    out.print("<h3>Password is incorrect. Please click the back button and try again.</h3>");
                    return;
                } 

                out.print("<h3>Query : <br>" + query + "</h3><br><br>");
                
                String id, uname, fName, lName, addr;
                out.print("<h3>Results: </h3><br>");
                out.print("<table> <tr> <th>ID</th> <th>Username</th> <th>First Name</th> <th>Last Name</th> <th>Address</th> </tr>");
                while (res.next()) {
                    id = res.getString("id");
                    uname = res.getString("username");
                    fName = res.getString("fName");
                    lName = res.getString("lName");
                    addr = res.getString("address");
                    out.print("<tr> <td>" + id + "</td> <td>" + uname + "</td> <td>" + fName + "</td> <td>" + lName + "</td> <td>" + addr + "</td></tr>");
                }
                out.print("</table><br>");
                conn.close();

            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
                System.out.println(e.getMessage());
            }
        } finally {
            out.close();
        }
    }// end of processRequest method
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }// end of doPost method
    
}// end of DBConnection class