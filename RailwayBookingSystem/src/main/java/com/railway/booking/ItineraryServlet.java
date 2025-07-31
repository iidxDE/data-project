package com.railway.booking;

import java.io.IOException;
import java.util.List; // 确保引入 List
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/itinerary")
public class ItineraryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationDAO reservationDAO;
    private StopDAO stopDAO;

    public void init() {
        reservationDAO = new ReservationDAO();
        stopDAO = new StopDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Customer customer = (session != null) ? (Customer) session.getAttribute("user") : null;

        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            Reservation reservation = reservationDAO.getReservationById(reservationId);
            
            if (reservation != null && reservation.getCustomerID() == customer.getCustomerID()) {
                List<StopDetail> stopsList = stopDAO.getStopsForSchedule(reservation.getScheduleID());
                request.setAttribute("stopsList", stopsList);
                request.setAttribute("reservation", reservation);
                request.getRequestDispatcher("itinerary.jsp").forward(request, response);
                
            } else {
                session.setAttribute("message", "Could not find the requested reservation.");
                response.sendRedirect("myReservations");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid reservation ID.");
            response.sendRedirect("myReservations");
        }
    }
}