package org.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/getUsers")
public class UserList extends HttpServlet {
    private DataSource ds;
    private void getDataSource() throws NamingException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        ds = (DataSource) envCtx.lookup("jdbc/simplewebapp");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            getDataSource();
            Connection con = ds.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT id, login, password, email, role, deleted FROM users");

            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            out.write("<html>");
            out.write("<body>");

            out.write("<h3>Database Records</h3>");
            while(rs.next())  {
                out.write("id: " + rs.getInt("id") + " ");
                out.write("login: " + rs.getString("login") + " ");
                out.write("password: " + rs.getString("password") + " ");
                out.write("mail: " + rs.getString("email") + " ");
                out.write("role: " + rs.getString("role") + " ");
                out.write("deleted: " + rs.getString("deleted") + " ");
                out.write("</br>");
            }

            //lets print some DB information
            out.write("<h3>Database Meta Data</h3>");
            DatabaseMetaData metaData = con.getMetaData();
            out.write("Database Product: " + metaData.getDatabaseProductName() + "<br/>");
            out.write("Database Version: " + metaData.getDatabaseMajorVersion() + "."
                    + metaData.getDatabaseMinorVersion() + "<br/>");
            out.write("Database Driver: " + metaData.getDriverName() + "<br/>");
            out.write("Database Driver version: " + metaData.getDriverMajorVersion() + "."
                    + metaData.getDriverMinorVersion() + "<br/>");
            out.write("Database user: " + metaData.getUserName());
            out.write("<div class=\"tenor-gif-embed\" data-postid=\"2233379259902584639\" data-share-method=\"host\" data-aspect-ratio=\"0.843373\" data-width=\"25%\"><a href=\"https://tenor.com/view/no-chic-fil-a-sauce-chick-fil-a-no-chick-fil-a-sauce-really-what-gif-2233379259902584639\">No Chic Fil A Sauce Chick Fil A GIF</a>from <a href=\"https://tenor.com/search/no+chic+fil+a+sauce-gifs\">No Chic Fil A Sauce GIFs</a></div> <script type=\"text/javascript\" async src=\"https://tenor.com/embed.js\"></script>");
            out.write("</html>");

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Exception in closing DB resources");
            }
        }
    }
}