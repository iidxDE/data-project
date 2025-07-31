package com.railway.booking;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewSchedules")
public class SchedulesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StationDAO stationDAO;
    private ScheduleDAO scheduleDAO;

    public void init() {
        stationDAO = new StationDAO();
        scheduleDAO = new ScheduleDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Station> stationList = stationDAO.getAllStations();
        request.setAttribute("stations", stationList);

        String originIdStr = request.getParameter("origin");
        String destinationIdStr = request.getParameter("destination");
        String dateStr = request.getParameter("date");
        String sortBy = request.getParameter("sortBy");
        String tripType = request.getParameter("tripType");
        String returnDateStr = request.getParameter("returnDate");
        String outboundScheduleId = request.getParameter("outboundScheduleId");
        String outboundOriginId = request.getParameter("outboundOriginId");
        String outboundDestId = request.getParameter("outboundDestId");
        String outboundFare = request.getParameter("outboundFare");

        List<AvailableTrip> availableTrips = scheduleDAO.searchAvailableTrips(originIdStr, destinationIdStr, dateStr);

        if (sortBy != null) {
            switch (sortBy) {
                case "departure":
                    availableTrips.sort(Comparator.comparing(AvailableTrip::getDepartureTime));
                    break;
                case "arrival":
                    availableTrips.sort(Comparator.comparing(AvailableTrip::getArrivalTime));
                    break;
                case "fare":
                    availableTrips.sort(Comparator.comparing(AvailableTrip::getCalculatedFare));
                    break;
            }
        }
        
        if ("round-trip".equals(tripType)) {
            request.setAttribute("isRoundTrip", true);
            request.setAttribute("returnDate", returnDateStr);
        } else if ("return".equals(tripType)) {
            request.setAttribute("isReturnTripSearch", true);
            request.setAttribute("outboundScheduleId", outboundScheduleId);
            request.setAttribute("outboundOriginId", outboundOriginId);
            request.setAttribute("outboundDestId", outboundDestId);
            request.setAttribute("outboundFare", outboundFare);
        }

        request.setAttribute("trips", availableTrips);
        request.getRequestDispatcher("schedules.jsp").forward(request, response);
    }
}