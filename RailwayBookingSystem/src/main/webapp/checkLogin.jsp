<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/login_database?serverTimezone=UTC",
            "root", "tnt1234567"
        );

        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM users WHERE username=? AND password=?"
        );
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            session.setAttribute("user", username);
            response.sendRedirect("success.jsp");
        } else {
            response.sendRedirect("fail.jsp");
        }

        conn.close();
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>
