<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm Your Booking</title>
    <style>
        .trip-details { border: 1px solid #ccc; padding: 15px; margin-bottom: 20px; }
        table { border-collapse: collapse; width: 100%; margin-top: 15px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <c:choose>
        <c:when test="${not empty outboundTrip}">
            <h2>Please Confirm Your Round-Trip Details</h2>
            <hr/>
            
            <div class="trip-details">
                <h3>Outbound Trip</h3>
                <p><strong>From:</strong> ${outboundTrip.originStationName}</p>
                <p><strong>To:</strong> ${outboundTrip.destinationStationName}</p>
                <p><strong>Fare (Adult):</strong> <fmt:formatNumber value="${outboundTrip.calculatedFare}" type="currency" currencySymbol="$"/></p>
                <h4>Stops on this route:</h4>
                <table>
                    <thead><tr><th>Station</th><th>Arrival</th><th>Departure</th></tr></thead>
                    <tbody>
                        <c:forEach var="stop" items="${outboundStops}">
                            <tr><td>${stop.stationName}</td><td><c:out value="${stop.arrivalTime}"/></td><td><c:out value="${stop.departureTime}"/></td></tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <div class="trip-details">
                <h3>Return Trip</h3>
                <p><strong>From:</strong> ${returnTrip.originStationName}</p>
                <p><strong>To:</strong> ${returnTrip.destinationStationName}</p>
                <p><strong>Fare (Adult):</strong> <fmt:formatNumber value="${returnTrip.calculatedFare}" type="currency" currencySymbol="$"/></p>
                <h4>Stops on this route:</h4>
                <table>
                    <thead><tr><th>Station</th><th>Arrival</th><th>Departure</th></tr></thead>
                    <tbody>
                        <c:forEach var="stop" items="${returnStops}">
                            <tr><td>${stop.stationName}</td><td><c:out value="${stop.arrivalTime}"/></td><td><c:out value="${stop.departureTime}"/></td></tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <hr/>
            <h3>Total Base Fare (Adult): <fmt:formatNumber value="${outboundTrip.calculatedFare + returnTrip.calculatedFare}" type="currency" currencySymbol="$"/></h3>
            
            <form action="booking" method="post">
                <input type="hidden" name="tripType" value="round-trip">
                <input type="hidden" name="outboundScheduleId" value="${outboundTrip.scheduleID}">
                <input type="hidden" name="outboundOriginId" value="${outboundTrip.originStationID}">
                <input type="hidden" name="outboundDestId" value="${outboundTrip.destinationStationID}">
                <input type="hidden" name="returnScheduleId" value="${returnTrip.scheduleID}">
                <input type="hidden" name="returnOriginId" value="${returnTrip.originStationID}">
                <input type="hidden" name="returnDestId" value="${returnTrip.destinationStationID}">
                
                <div>
                    <label for="passengerType"><strong>Select Passenger Type (for all tickets):</strong></label>
                    <select name="passengerType" id="passengerType">
                        <option value="adult">Adult</option>
                        <option value="child">Child (25% off)</option>
                        <option value="senior">Senior (35% off)</option>
                        <option value="disabled">Disabled (50% off)</option>
                    </select>
                </div>
                <br/>
                <input type="submit" value="Confirm and Book Round-Trip">
            </form>
        </c:when>

        <c:when test="${not empty oneWayTrip}">
            <h2>Please Confirm Your Trip Details</h2>
            <hr/>
            <div class="trip-details">
                <h3>Trip Information</h3>
                <p><strong>From:</strong> ${oneWayTrip.originStationName}</p>
                <p><strong>To:</strong> ${oneWayTrip.destinationStationName}</p>
                <p><strong>Base Fare (Adult):</strong> <fmt:formatNumber value="${oneWayTrip.calculatedFare}" type="currency" currencySymbol="$"/></p>
                
                <h4>Stops on this route:</h4>
                <table>
                    <thead><tr><th>Station</th><th>Arrival</th><th>Departure</th></tr></thead>
                    <tbody>
                        <c:forEach var="stop" items="${stopsList}">
                            <tr><td>${stop.stationName}</td><td><c:out value="${stop.arrivalTime}"/></td><td><c:out value="${stop.departureTime}"/></td></tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <form action="booking" method="post">
                <input type="hidden" name="tripType" value="one-way">
                <input type="hidden" name="scheduleId" value="${oneWayTrip.scheduleID}">
                <input type="hidden" name="originId" value="${oneWayTrip.originStationID}">
                <input type="hidden" name="destId" value="${oneWayTrip.destinationStationID}">
                <input type="hidden" name="baseFare" value="${oneWayTrip.calculatedFare}">
                
                <div>
                    <label for="passengerType"><strong>Select Passenger Type:</strong></label>
                    <select name="passengerType" id="passengerType">
                        <option value="adult">Adult</option>
                        <option value="child">Child (25% off)</option>
                        <option value="senior">Senior (35% off)</option>
                        <option value="disabled">Disabled (50% off)</option>
                    </select>
                </div>
                <br/>
                <input type="submit" value="Confirm and Book Now">
            </form>
        </c:when>
    </c:choose>
    
    <p><a href="viewSchedules">Cancel and Go Back</a></p>
</body>
</html>