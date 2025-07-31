<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Travel Itinerary</title>
    <style>
        body { font-family: sans-serif; }
        .container { border: 1px solid #ccc; padding: 15px; width: 500px; }
        table { border-collapse: collapse; width: 100%; margin-top: 15px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h2>Travel Itinerary</h2>
    <a href="myReservations">Back to My Reservations</a>
    <hr/>
    
    <div class="container">
        <h3>Reservation Details</h3>
        <p><strong>Reservation Number:</strong> ${reservation.reservationID}</p>
        <p><strong>Booked On:</strong> ${reservation.bookingDate}</p>
        <p><strong>Passenger:</strong> ${reservation.firstName} ${reservation.lastName}</p>
        <p><strong>Passenger Type:</strong> ${reservation.passengerType}</p>
        <p><strong>Total Fare:</strong> 
            <fmt:formatNumber value="${reservation.totalFare}" type="currency" currencySymbol="$"/>
        </p>
        
        <hr/>
        
        <h3>Trip Information</h3>
        <p><strong>Train ID:</strong> ${reservation.trainID}</p>
        <p><strong>From:</strong> ${reservation.originStationName}</p>
        <p><strong>To:</strong> ${reservation.destinationStationName}</p>
        <p><strong>Departure:</strong> ${reservation.departureDateTime}</p>
        <p><strong>Arrival:</strong> ${reservation.arrivalDateTime}</p>
    </div>

    <div class="container" style="margin-top: 20px;">
        <h3>All Stops on This Route</h3>
        <table>
            <thead>
                <tr>
                    <th>Station</th>
                    <th>Arrival Time</th>
                    <th>Departure Time</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="stop" items="${stopsList}">
                    <tr>
                        <td>${stop.stationName}</td>
                        <td><c:out value="${stop.arrivalTime}"/></td>
                        <td><c:out value="${stop.departureTime}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>