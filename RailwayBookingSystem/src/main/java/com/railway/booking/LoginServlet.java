package com.railway.booking;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        
        HttpSession session = request.getSession();

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.validateLogin(user, pass);

        if (customer != null) {
            session.setAttribute("user", customer);
            session.setAttribute("role", "customer");
            response.sendRedirect("success.jsp");
            return;
        }

        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.validateLogin(user, pass);

        if (employee != null) {
            session.setAttribute("user", employee);
            session.setAttribute("role", employee.getRole());

            if ("Manager".equalsIgnoreCase(employee.getRole())) {
                response.sendRedirect("manager_dashboard.jsp");
            } else {
                response.sendRedirect("rep_dashboard.jsp");
            }
            return;
        }
        request.setAttribute("errorMessage", "Invalid username or password!");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}