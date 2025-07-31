package com.railway.booking;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cancelReservation")
public class CancelReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int reservationId = Integer.parseInt(request.getParameter("id"));
            ReservationDAO reservationDAO = new ReservationDAO();
            boolean success = reservationDAO.cancelReservation(reservationId);
            if (success) {
                session.setAttribute("message", "Reservation #" + reservationId + " has been successfully cancelled.");
            } else {
                session.setAttribute("message", "Error: Could not cancel reservation #" + reservationId + ".");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("message", "Error: Invalid reservation ID.");
            e.printStackTrace();
        }
        response.sendRedirect("myReservations");
    }
}