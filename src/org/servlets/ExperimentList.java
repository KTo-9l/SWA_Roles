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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = "/getExperiments")
public class ExperimentList extends HttpServlet {
    private DataSource ds;
    private void getDataSource() throws NamingException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        ds = (DataSource) envCtx.lookup("jdbc/chemistry");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            getDataSource();
            Connection con = ds.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(
                    "SELECT ex.experiment_id, ex.experiment_name, ex.experiment_status FROM experiment AS ex");

            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            out.write("<html>");
            out.write("<body>");

            out.write("<h3>Experiments</h3>");
            while(rs.next())  {
                out.write("experiment_id: " + rs.getString("ex.experiment_id") + " ");
                out.write("experiment_name: " + rs.getString("ex.experiment_name") + " ");
                out.write("status: " + rs.getString("ex.experiment_status"));
                out.write("</br>");
            }
            out.write("<form action=\"/\" method=\"get\"> " +
                    "<input type=\"submit\" value=\"Logout\"> " +
                    "</form>");
            out.write("</body>");

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
