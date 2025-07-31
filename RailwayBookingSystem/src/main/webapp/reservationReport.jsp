<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservation Report</title>
    <style> table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; } </style>
</head>
<body>
    <h2>Reservation Lookup Report</h2>
    <a href="manager_dashboard.jsp">Back to Dashboard</a>
    <hr/>

    <form action="reservationReport" method="get">
        <label for="lineName">By Transit Line:</label>
        <select name="lineName" id="lineName">
            <option value="">--All Lines--</option>
            <c:forEach var="line" items="${linesList}">
                <option value="${line.lineName}" ${param.lineName == line.lineName ? 'selected' : ''}>
                    ${line.lineName}
                </option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;
        <label for="customerName">By Customer Name:</label>
        <input type="text" id="customerName" name="customerName" value="${param.customerName}">
        
        <input type="submit" name="search" value="Search Reservations">
    </form>
    <hr/>
	<c:if test="${totalRevenue != null}">
    	<div style="padding: 10px; background-color: #e8f4e8; border: 1px solid green; margin: 10px 0;">
        	<strong>Total Revenue for this Search: 
            	<fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="$"/>
        	</strong>
    	</div>
	</c:if>

    <h3>Search Results</h3>
    <c:if test="${not empty reservationsList}">
        <table>
            <thead>
                <tr>
                    <th>Res. ID</th>
                    <th>Booking Date</th>
                    <th>Passenger Name</th>
                    <th>Trip Details</th>
                    <th>Fare</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="res" items="${reservationsList}">
                    <tr>
                        <td>${res.reservationID}</td>
                        <td>${res.bookingDate}</td>
                        <td>${res.firstName} ${res.lastName}</td>
                        <td>From: ${res.originStationName}<br/>To: ${res.destinationStationName}</td>
                        <td>$${String.format("%.2f", res.totalFare)}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>