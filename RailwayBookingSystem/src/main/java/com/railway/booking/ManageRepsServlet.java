package com.railway.booking;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/manageReps")
public class ManageRepsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeDAO employeeDAO;

    public void init() {
        employeeDAO = new EmployeeDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "delete":
                deleteRep(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                listReps(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("manageReps?action=list");
            return;
        }

        switch (action) {
            case "add":
                addRep(request, response);
                break;
            case "update":
                updateRep(request, response);
                break;
            default:
                response.sendRedirect("manageReps?action=list");
                break;
        }
    }

    private void listReps(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> repsList = employeeDAO.getAllCustomerReps();
        request.setAttribute("repsList", repsList);
        request.getRequestDispatcher("manageReps.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employee existingRep = employeeDAO.getEmployeeById(id);
        request.setAttribute("employee", existingRep);
        request.getRequestDispatcher("addEditRep.jsp").forward(request, response);
    }

    private void addRep(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employee newRep = new Employee();
        newRep.setUsername(request.getParameter("username"));
        newRep.setPassword(request.getParameter("password"));
        newRep.setFirstName(request.getParameter("firstName"));
        newRep.setLastName(request.getParameter("lastName"));
        newRep.setSsn(request.getParameter("ssn"));
        employeeDAO.addEmployee(newRep);
        response.sendRedirect("manageReps?action=list");
    }

    private void updateRep(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employee rep = new Employee();
        rep.setEmployeeID(Integer.parseInt(request.getParameter("employeeId")));
        rep.setUsername(request.getParameter("username"));
        rep.setPassword(request.getParameter("password"));
        rep.setFirstName(request.getParameter("firstName"));
        rep.setLastName(request.getParameter("lastName"));
        rep.setSsn(request.getParameter("ssn"));
        employeeDAO.updateEmployee(rep);
        response.sendRedirect("manageReps?action=list");
    }

    private void deleteRep(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        employeeDAO.deleteEmployee(id);
        response.sendRedirect("manageReps?action=list");
    }
}