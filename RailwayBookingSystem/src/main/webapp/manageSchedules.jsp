<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Schedules</title>
    <style> table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; } </style>
</head>
<body>
    <h2>Manage Train Schedules</h2>
    <a href="rep_dashboard.jsp">Back to Dashboard</a>
    <hr/>

    <a href="manageSchedules?action=new"><button>Add New Schedule</button></a>
    <br/><br/>

    <table>
        <thead>
            <tr>
                <th>Schedule ID</th>
                <th>Train ID</th>
                <th>Line Name</th>
                <th>Departure Time</th>
                <th>Arrival Time</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="schedule" items="${schedulesList}">
                <tr>
                    <td>${schedule.scheduleID}</td>
                    <td>${schedule.trainID}</td>
                    <td>${schedule.lineName}</td>
                    <td>${schedule.departureDateTime}</td>
                    <td>${schedule.arrivalDateTime}</td>
                    <td>
                        <a href="manageSchedules?action=edit&id=${schedule.scheduleID}">Edit</a>
                        &nbsp;|&nbsp;
                        <a href="manageSchedules?action=delete&id=${schedule.scheduleID}" onclick="return confirm('Are you sure you want to delete this schedule?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>