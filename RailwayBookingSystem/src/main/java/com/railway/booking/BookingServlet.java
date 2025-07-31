package com.railway.booking;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ScheduleDAO scheduleDAO;
    private StationDAO stationDAO;
    private ReservationDAO reservationDAO;
    private FareService fareService;
    private StopDAO stopDAO;

    public void init() {
        scheduleDAO = new ScheduleDAO();
        stationDAO = new StationDAO();
        reservationDAO = new ReservationDAO();
        fareService = new FareService();
        stopDAO = new StopDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("viewSchedules");
            return;
        }
        
        try {
            switch (action) {
                case "confirm":
                    confirmOneWay(request, response);
                    break;
                case "confirm_round_trip":
                    confirmRoundTrip(request, response);
                    break;
                default:
                    response.sendRedirect("viewSchedules");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("viewSchedules");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !"customer".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String tripType = request.getParameter("tripType");
        if ("round-trip".equals(tripType)) {
            bookRoundTrip(request, response);
        } else {
            bookOneWay(request, response);
        }
    }


    private void confirmOneWay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
        int originId = Integer.parseInt(request.getParameter("originId"));
        int destId = Integer.parseInt(request.getParameter("destId"));
        
        AvailableTrip oneWayTrip = scheduleDAO.getAvailableTripDetails(scheduleId, originId, destId);
        
        List<StopDetail> stopsList = stopDAO.getStopsForSchedule(scheduleId);
        request.setAttribute("stopsList", stopsList);
        
        request.setAttribute("oneWayTrip", oneWayTrip);
        request.getRequestDispatcher("confirmBooking.jsp").forward(request, response);
    }

    private void confirmRoundTrip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int outboundScheduleId = Integer.parseInt(request.getParameter("outboundScheduleId"));
        int outboundOriginId = Integer.parseInt(request.getParameter("outboundOriginId"));
        int outboundDestId = Integer.parseInt(request.getParameter("outboundDestId"));
        
        int returnScheduleId = Integer.parseInt(request.getParameter("returnScheduleId"));
        int returnOriginId = Integer.parseInt(request.getParameter("returnOriginId"));
        int returnDestId = Integer.parseInt(request.getParameter("returnDestId"));
        
        AvailableTrip outboundTrip = scheduleDAO.getAvailableTripDetails(outboundScheduleId, outboundOriginId, outboundDestId);
        AvailableTrip returnTrip = scheduleDAO.getAvailableTripDetails(returnScheduleId, returnOriginId, returnDestId);

        List<StopDetail> outboundStops = stopDAO.getStopsForSchedule(outboundScheduleId);
        List<StopDetail> returnStops = stopDAO.getStopsForSchedule(returnScheduleId);
        
        request.setAttribute("outboundStops", outboundStops);
        request.setAttribute("returnStops", returnStops);
        
        request.setAttribute("outboundTrip", outboundTrip);
        request.setAttribute("returnTrip", returnTrip);
        request.getRequestDispatcher("confirmBooking.jsp").forward(request, response);
    }

    private void bookOneWay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) { response.sendRedirect("login.jsp"); return; }

        try {
            String passengerType = request.getParameter("passengerType");
            double baseFare = Double.parseDouble(request.getParameter("baseFare"));
            double finalFare = fareService.calculateDiscountedFare(baseFare, passengerType);

            Reservation reservation = new Reservation();
            reservation.setCustomerID(customer.getCustomerID());
            reservation.setScheduleID(Integer.parseInt(request.getParameter("scheduleId")));
            reservation.setOriginStationID(Integer.parseInt(request.getParameter("originId")));
            reservation.setDestinationStationID(Integer.parseInt(request.getParameter("destId")));
            reservation.setBookingDate(new Date(System.currentTimeMillis()));
            reservation.setPassengerType(passengerType);
            reservation.setTotalFare(finalFare);

            if (reservationDAO.createReservation(reservation)) {
                response.sendRedirect("bookingSuccess.jsp");
            } else {
                response.sendRedirect("bookingFail.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("bookingFail.jsp");
        }
    }
    
    private void bookRoundTrip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) { response.sendRedirect("login.jsp"); return; }

        try {
            String passengerType = request.getParameter("passengerType");
            
            int outboundScheduleId = Integer.parseInt(request.getParameter("outboundScheduleId"));
            int outboundOriginId = Integer.parseInt(request.getParameter("outboundOriginId"));
            int outboundDestId = Integer.parseInt(request.getParameter("outboundDestId"));
            AvailableTrip outboundTrip = scheduleDAO.getAvailableTripDetails(outboundScheduleId, outboundOriginId, outboundDestId);

            int returnScheduleId = Integer.parseInt(request.getParameter("returnScheduleId"));
            int returnOriginId = Integer.parseInt(request.getParameter("returnOriginId"));
            int returnDestId = Integer.parseInt(request.getParameter("returnDestId"));
            AvailableTrip returnTrip = scheduleDAO.getAvailableTripDetails(returnScheduleId, returnOriginId, returnDestId);
            
            Reservation outboundRes = new Reservation();
            outboundRes.setCustomerID(customer.getCustomerID());
            outboundRes.setScheduleID(outboundTrip.getScheduleID());
            outboundRes.setOriginStationID(outboundTrip.getOriginStationID());
            outboundRes.setDestinationStationID(outboundTrip.getDestinationStationID());
            outboundRes.setBookingDate(new Date(System.currentTimeMillis()));
            outboundRes.setPassengerType(passengerType);
            outboundRes.setTotalFare(fareService.calculateDiscountedFare(outboundTrip.getCalculatedFare(), passengerType));

            Reservation returnRes = new Reservation();
            returnRes.setCustomerID(customer.getCustomerID());
            returnRes.setScheduleID(returnTrip.getScheduleID());
            returnRes.setOriginStationID(returnTrip.getOriginStationID());
            returnRes.setDestinationStationID(returnTrip.getDestinationStationID());
            returnRes.setBookingDate(new Date(System.currentTimeMillis()));
            returnRes.setPassengerType(passengerType);
            returnRes.setTotalFare(fareService.calculateDiscountedFare(returnTrip.getCalculatedFare(), passengerType));

            boolean outboundSuccess = reservationDAO.createReservation(outboundRes);
            boolean returnSuccess = reservationDAO.createReservation(returnRes);

            if (outboundSuccess && returnSuccess) {
                response.sendRedirect("bookingSuccess.jsp");
            } else {
                response.sendRedirect("bookingFail.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("bookingFail.jsp");
        }
    }
}