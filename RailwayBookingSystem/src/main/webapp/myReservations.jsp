<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Reservations</title>
    <style> table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; } </style>
</head>
<body>
    <h2>My Reservations</h2>
    <a href="success.jsp">Back to Dashboard</a>
    <c:if test="${not empty sessionScope.message}">
        <p style="color: green; font-weight: bold;">${sessionScope.message}</p>
        <c:remove var="message" scope="session"/>
    </c:if>
    <hr/>

    <h3>Upcoming Trips</h3>
    <c:choose>
        <c:when test="${not empty currentReservations}">
            <table>
                <thead>
                    <tr>
                        <th>Reservation ID</th>
                        <th>Booking Date</th>
                        <th>Trip Details</th>
                        <th>Departure Time</th>
                        <th>Fare</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="res" items="${currentReservations}">
                        <tr>
                            <td>${res.reservationID}</td>
                            <td>${res.bookingDate}</td>
                            <td>From: ${res.originStationName}<br/>To: ${res.destinationStationName}</td>
                            <td>${res.departureDateTime}</td>
                            <td>$${String.format("%.2f", res.totalFare)}</td>
                            <td>
                                <a href="itinerary?reservationId=${res.reservationID}">View Itinerary</a>
                                &nbsp;|&nbsp;
                                <a href="cancelReservation?id=${res.reservationID}" onclick="return confirm('Are you sure you want to cancel this reservation?');">Cancel</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>You have no upcoming trips.</p>
        </c:otherwise>
    </c:choose>

    <hr style="margin-top: 30px;"/>

    <h3>Past Trips</h3>
    <c:choose>
        <c:when test="${not empty pastReservations}">
            <table>
                <thead>
                    <tr>
                        <th>Reservation ID</th>
                        <th>Booking Date</th>
                        <th>Trip Details</th>
                        <th>Departure Time</th>
                        <th>Fare</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="res" items="${pastReservations}">
                        <tr>
                            <td>${res.reservationID}</td>
                            <td>${res.bookingDate}</td>
                            <td>From: ${res.originStationName}<br/>To: ${res.destinationStationName}</td>
                            <td>${res.departureDateTime}</td>
                            <td>$${String.format("%.2f", res.totalFare)}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>You have no past trips.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>