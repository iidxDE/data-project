<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Schedule Lookup by Station</title>
    <style> table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; } </style>
</head>
<body>
    <h2>Schedule Lookup by Station</h2>
    <a href="rep_dashboard.jsp">Back to Dashboard</a>
    <hr/>

    <form action="stationSchedules" method="get">
        <label for="stationId">Station:</label>
        <select name="stationId" id="stationId" required>
            <option value="">--Select a Station--</option>
            <c:forEach var="station" items="${stationsList}">
                <option value="${station.stationID}" ${param.stationId == station.stationID ? 'selected' : ''}>
                    ${station.name}
                </option>
            </c:forEach>
        </select>
        
        <input type="radio" id="origin" name="type" value="origin" ${param.type == 'origin' ? 'checked' : ''} required>
        <label for="origin">As Origin</label>
        
        <input type="radio" id="destination" name="type" value="destination" ${param.type == 'destination' ? 'checked' : ''} required>
        <label for="destination">As Destination</label>

        <input type="submit" value="Search Schedules">
    </form>
    <p style="color:red;">${requestScope.errorMessage}</p>
    <hr/>

    <h3>Search Results</h3>
    <c:if test="${not empty schedulesList}">
        <table>
            <thead>
                <tr>
                    <th>Schedule ID</th>
                    <th>Train ID</th>
                    <th>Line Name</th>
                    <th>Origin</th>
                    <th>Destination</th>
                    <th>Departure Time</th>
                    <th>Arrival Time</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="schedule" items="${schedulesList}">
                    <tr>
                        <td>${schedule.scheduleID}</td>
                        <td>${schedule.trainID}</td>
                        <td>${schedule.lineName}</td>
                        <td>${schedule.originStationName}</td>
                        <td>${schedule.destinationStationName}</td>
                        <td>${schedule.departureDateTime}</td>
                        <td>${schedule.arrivalDateTime}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>