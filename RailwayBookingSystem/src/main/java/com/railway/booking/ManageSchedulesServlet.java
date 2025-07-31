package com.railway.booking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;

@WebServlet("/manageSchedules")
public class ManageSchedulesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ScheduleDAO scheduleDAO;
    private TrainDAO trainDAO;
    private TransitLineDAO transitLineDAO;

    public void init() {
        scheduleDAO = new ScheduleDAO();
        trainDAO = new TrainDAO();
        transitLineDAO = new TransitLineDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isEmployee(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteSchedule(request, response);
                    break;
                default:
                    listSchedules(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isEmployee(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("manageSchedules?action=list");
            return;
        }
        
        try {
            switch (action) {
                case "add":
                    addSchedule(request, response);
                    break;
                case "update":
                    updateSchedule(request, response);
                    break;
                default:
                    response.sendRedirect("manageSchedules?action=list");
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private boolean isEmployee(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String role = (String) session.getAttribute("role");
            return "Manager".equalsIgnoreCase(role) || "CustomerRep".equalsIgnoreCase(role);
        }
        return false;
    }

    private void listSchedules(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        List<Schedule> schedulesList = scheduleDAO.getAllSchedules(sortBy);
        request.setAttribute("schedulesList", schedulesList);
        request.getRequestDispatcher("manageSchedules.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("trainsList", trainDAO.getAllTrains());
        request.setAttribute("linesList", transitLineDAO.getAllTransitLines());
        request.getRequestDispatcher("addEditSchedule.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Schedule existingSchedule = scheduleDAO.getScheduleById(id);
        request.setAttribute("schedule", existingSchedule);
        showNewForm(request, response);
    }


	private void addSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException, DateTimeParseException {
		Schedule newSchedule = new Schedule();

		LocalDateTime departureTime = LocalDateTime.parse(request.getParameter("departureTime"));
		LocalDateTime arrivalTime = LocalDateTime.parse(request.getParameter("arrivalTime"));

		long travelTimeInMinutes = Duration.between(departureTime, arrivalTime).toMinutes();

		newSchedule.setTrainID(Integer.parseInt(request.getParameter("trainId")));
		newSchedule.setLineName(request.getParameter("lineName"));
		newSchedule.setDepartureDateTime(departureTime);
		newSchedule.setArrivalDateTime(arrivalTime);
		newSchedule.setTravelTimeMinutes((int) travelTimeInMinutes);
	     
	     scheduleDAO.addSchedule(newSchedule);
	     response.sendRedirect("manageSchedules?action=list");
	}

 	private void updateSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException, DateTimeParseException {
 		Schedule schedule = new Schedule();
 		LocalDateTime departureTime = LocalDateTime.parse(request.getParameter("departureTime"));
 		LocalDateTime arrivalTime = LocalDateTime.parse(request.getParameter("arrivalTime"));
 		long travelTimeInMinutes = Duration.between(departureTime, arrivalTime).toMinutes();

 		schedule.setScheduleID(Integer.parseInt(request.getParameter("scheduleId")));
 		schedule.setTrainID(Integer.parseInt(request.getParameter("trainId")));
 		schedule.setLineName(request.getParameter("lineName"));
 		schedule.setDepartureDateTime(departureTime);
 		schedule.setArrivalDateTime(arrivalTime);
 		schedule.setTravelTimeMinutes((int) travelTimeInMinutes);

 		scheduleDAO.updateSchedule(schedule);
 		response.sendRedirect("manageSchedules?action=list");
	}

    private void deleteSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        scheduleDAO.deleteSchedule(id);
        response.sendRedirect("manageSchedules?action=list");
    }
}