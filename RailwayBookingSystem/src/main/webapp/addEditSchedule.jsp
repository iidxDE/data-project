<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${not empty schedule ? 'Edit' : 'Add'} Schedule</title>
</head>
<body>
    <h2>${not empty schedule ? 'Edit' : 'Add'} Schedule</h2>
	<c:if test="${not empty errorMessage}">
    	<p style="color: red; font-weight: bold; border: 1px solid red; padding: 10px;">
        	${errorMessage}
    	</p>
	</c:if>
    <a href="manageSchedules?action=list">Back to List</a>
    <hr/>

    <form action="manageSchedules" method="post">
        
        <c:if test="${not empty schedule}">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="scheduleId" value="${schedule.scheduleID}">
        </c:if>
        <c:if test="${empty schedule}">
            <input type="hidden" name="action" value="add">
        </c:if>

        <table>
            <tr>
                <td>Train ID:</td>
                <td>
                    <select name="trainId" required>
                        <c:forEach var="train" items="${trainsList}">
                            <option value="${train.trainID}" ${train.trainID == schedule.trainID ? 'selected' : ''}>
                                ${train.trainID}
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Transit Line:</td>
                <td>
                    <select name="lineName" required>
                        <c:forEach var="line" items="${linesList}">
                            <option value="${line.lineName}" ${line.lineName == schedule.lineName ? 'selected' : ''}>
                                ${line.lineName}
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Departure Time:</td>
                <td><input type="datetime-local" name="departureTime" value="${schedule.departureDateTime}" required></td>
            </tr>
            <tr>
                <td>Arrival Time:</td>
                <td><input type="datetime-local" name="arrivalTime" value="${schedule.arrivalDateTime}" required></td>
            </tr>
             <tr>
                <td>Travel Time (Minutes):</td>
                <td><input type="number" name="travelTime" value="${schedule.travelTimeMinutes}" required></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="${not empty schedule ? 'Update' : 'Add'} Schedule"></td>
            </tr>
        </table>
    </form>
	<script>
    	function calculateTravelTime() {
        	var departureInput = document.getElementById('departureTime');
        	var arrivalInput = document.getElementById('arrivalTime');
        	if (departureInput.value && arrivalInput.value) {
            	var departureDate = new Date(departureInput.value);
            	var arrivalDate = new Date(arrivalInput.value); 

            	var diffMs = arrivalDate - departureDate;
            
            	if (diffMs >= 0) {
                	var diffMins = Math.round(diffMs / 60000);
                document.getElementById('travelTime').value = diffMins;
            	} else {
                	document.getElementById('travelTime').value = 'Invalid time';
            	}
        	}
    	}
    	document.addEventListener('DOMContentLoaded', calculateTravelTime);
	</script>
</body>
</html>