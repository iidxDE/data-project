package com.railway.booking;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/myReservations")
public class MyReservationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Customer customer = (Customer) session.getAttribute("user");
        int customerId = customer.getCustomerID();

        ReservationDAO reservationDAO = new ReservationDAO();
        List<Reservation> currentReservations = reservationDAO.getCurrentReservationsByCustomerId(customerId);
        List<Reservation> pastReservations = reservationDAO.getPastReservationsByCustomerId(customerId);
        
        request.setAttribute("currentReservations", currentReservations);
        request.setAttribute("pastReservations", pastReservations);
        request.getRequestDispatcher("myReservations.jsp").forward(request, response);
    }
}