package com.railway.booking;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        CustomerDAO customerDAO = new CustomerDAO();
        if (customerDAO.checkUsernameExists(username)) {
            request.setAttribute("errorMessage", "Username already exists. Please choose another one.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            Customer newCustomer = new Customer();
            newCustomer.setFirstName(firstName);
            newCustomer.setLastName(lastName);
            newCustomer.setEmail(email);
            newCustomer.setUsername(username);
            newCustomer.setPassword(password);
            boolean success = customerDAO.createCustomer(newCustomer);

            if (success) {
                request.getSession().setAttribute("successMessage", "Registration successful! Please login.");
                response.sendRedirect("login.jsp");
            } else {
                request.setAttribute("errorMessage", "An error occurred during registration. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        }
    }
}
