<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search for Train Schedules</title>
    <style> table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; } </style>
</head>
<body>
    <h2>Search for Train Schedules</h2>
    <a href="success.jsp">Back to Dashboard</a>
    <hr/>

    <form action="viewSchedules" method="get" id="searchForm">
        <div>
            <input type="radio" id="oneWay" name="tripType" value="one-way" ${param.tripType != 'round-trip' && param.tripType != 'return' ? 'checked' : ''} onchange="handleTripTypeChange();">
            <label for="oneWay">One-Way</label>
            <input type="radio" id="roundTrip" name="tripType" value="round-trip" ${param.tripType == 'round-trip' ? 'checked' : ''} onchange="handleTripTypeChange();">
            <label for="roundTrip">Round-Trip</label>
        </div>
        <br>

        <label for="origin">Origin:</label>
        <select name="origin" id="origin">
            <option value="">--Select Origin--</option>
            <c:forEach var="station" items="${stations}"><option value="${station.stationID}" ${param.origin == station.stationID ? 'selected' : ''}>${station.name}</option></c:forEach>
        </select>

        <label for="destination">Destination:</label>
        <select name="destination" id="destination">
            <option value="">--Select Destination--</option>
            <c:forEach var="station" items="${stations}"><option value="${station.stationID}" ${param.destination == station.stationID ? 'selected' : ''}>${station.name}</option></c:forEach>
        </select>

        <label for="date">Date:</label>
        <input type="date" id="date" name="date" value="${param.date}">
        
        <span id="returnDateContainer" style="display: none;">
            <label for="returnDate">Return Date:</label>
            <input type="date" id="returnDate" name="returnDate" value="${returnDate}">
        </span>

        <input type="submit" value="Search">
    </form>

    <hr/>
    
    <h3>
        <c:if test="${isReturnTripSearch}">Searching for Return Trips</c:if>
        <c:if test="${!isReturnTripSearch}">Available Trips</c:if>
    </h3>
    <table>
        <thead>
            <tr>
                <th>Train ID</th>
                <th>Trip</th>
                <th><a href="viewSchedules?origin=${param.origin}&destination=${param.destination}&date=${param.date}&sortBy=departure">Departure Time</a></th>
                <th><a href="viewSchedules?origin=${param.origin}&destination=${param.destination}&date=${param.date}&sortBy=arrival">Arrival Time</a></th>
                <th><a href="viewSchedules?origin=${param.origin}&destination=${param.destination}&date=${param.date}&sortBy=fare">Estimated Fare</a></th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="trip" items="${trips}">
                <tr>
                    <td>${trip.trainID}</td>
                    <td>From: ${trip.originStationName}<br/>To: ${trip.destinationStationName}</td>
                    <td>${trip.departureTime}</td>
                    <td>${trip.arrivalTime}</td>
                    <td><fmt:formatNumber value="${trip.calculatedFare}" type="currency" currencySymbol="$"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${isRoundTrip}">
                                <a href="viewSchedules?origin=${trip.destinationStationID}&destination=${trip.originStationID}&date=${returnDate}&outboundScheduleId=${trip.scheduleID}&outboundOriginId=${trip.originStationID}&outboundDestId=${trip.destinationStationID}&outboundFare=${trip.calculatedFare}&tripType=return">
                                    Select Outbound &amp; Search Return
                                </a>
                            </c:when>
                            <c:when test="${isReturnTripSearch}">
                                <a href="booking?action=confirm_round_trip&outboundScheduleId=${outboundScheduleId}&outboundOriginId=${param.destination}&outboundDestId=${param.origin}&outboundFare=${param.outboundFare}&returnScheduleId=${trip.scheduleID}&returnOriginId=${trip.originStationID}&returnDestId=${trip.destinationStationID}&returnFare=${trip.calculatedFare}">
                                    Select Return &amp; Continue
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="booking?action=confirm&scheduleId=${trip.scheduleID}&originId=${trip.originStationID}&destId=${trip.destinationStationID}&fare=${trip.calculatedFare}">
                                    Book This Trip
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <script>
        function handleTripTypeChange() {

            document.getElementById('searchForm').submit();
        }

        function setInitialReturnDateVisibility() {

            var tripTypeInput = document.querySelector('input[name="tripType"]:checked');
            if (tripTypeInput) {
                var returnDateContainer = document.getElementById('returnDateContainer');
                if (tripTypeInput.value === 'round-trip') {
                    returnDateContainer.style.display = 'inline';
                } else {
                    returnDateContainer.style.display = 'none';
                }
            }
        }

        document.addEventListener('DOMContentLoaded', setInitialReturnDateVisibility);
    </script>
</body>
</html>