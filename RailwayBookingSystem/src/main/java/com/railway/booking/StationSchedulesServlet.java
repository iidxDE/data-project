package com.railway.booking;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stationSchedules")
public class StationSchedulesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StationDAO stationDAO = new StationDAO();
        List<Station> stationsList = stationDAO.getAllStations();
        request.setAttribute("stationsList", stationsList);

        String stationIdStr = request.getParameter("stationId");
        String type = request.getParameter("type");

        if (stationIdStr != null && !stationIdStr.isEmpty() && type != null && !type.isEmpty()) {
            try {
                int stationId = Integer.parseInt(stationIdStr);
                ScheduleDAO scheduleDAO = new ScheduleDAO();
                List<Schedule> schedulesList = scheduleDAO.getSchedulesByStation(stationId, type);
                request.setAttribute("schedulesList", schedulesList);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid station selected.");
            }
        }

        request.getRequestDispatcher("stationSchedules.jsp").forward(request, response);
    }
}