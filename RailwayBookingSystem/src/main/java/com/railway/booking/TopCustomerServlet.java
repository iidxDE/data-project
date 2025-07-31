package com.railway.booking;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/topCustomerReport")
public class TopCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"Manager".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        ReservationDAO reservationDAO = new ReservationDAO();
        Map<String, Object> topCustomerData = reservationDAO.getTopRevenueCustomer();

        request.setAttribute("topCustomerData", topCustomerData);
        request.getRequestDispatcher("topCustomerReport.jsp").forward(request, response);
    }
}