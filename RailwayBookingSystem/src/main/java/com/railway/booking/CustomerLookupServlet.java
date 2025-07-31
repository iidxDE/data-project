package com.railway.booking;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/customerLookup")
public class CustomerLookupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TransitLineDAO transitLineDAO = new TransitLineDAO();
        List<TransitLine> linesList = transitLineDAO.getAllTransitLines();
        request.setAttribute("linesList", linesList);

        String lineName = request.getParameter("lineName");
        String dateStr = request.getParameter("date");
        List<Customer> customersList = new ArrayList<>();

        if (lineName != null && !lineName.isEmpty() && dateStr != null && !dateStr.isEmpty()) {
            try {
                Date date = Date.valueOf(dateStr);
                ReservationDAO reservationDAO = new ReservationDAO();
                customersList = reservationDAO.getCustomersOnLineByDate(lineName, date);
            } catch (IllegalArgumentException e) {
                request.setAttribute("errorMessage", "Invalid date format.");
            }
        }

        request.setAttribute("customersList", customersList);
        request.getRequestDispatcher("customerLookup.jsp").forward(request, response);
    }
}