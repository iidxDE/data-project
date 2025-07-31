package com.railway.booking;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/reservationReport")
public class ReservationReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"Manager".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        TransitLineDAO transitLineDAO = new TransitLineDAO();
        request.setAttribute("linesList", transitLineDAO.getAllTransitLines());
        String lineName = request.getParameter("lineName");
        String customerName = request.getParameter("customerName");
        if (request.getParameter("search") != null) {
            ReservationDAO reservationDAO = new ReservationDAO();
            List<Reservation> reservationsList = reservationDAO.searchReservations(lineName, customerName);
            request.setAttribute("reservationsList", reservationsList);
            double totalRevenue = reservationDAO.getTotalRevenue(lineName, customerName);
            request.setAttribute("totalRevenue", totalRevenue);
        }

        request.getRequestDispatcher("reservationReport.jsp").forward(request, response);
    }
}