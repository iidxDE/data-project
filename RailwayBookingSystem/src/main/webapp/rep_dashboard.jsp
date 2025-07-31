<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Representative Dashboard</title>
</head>
<body>
    <h2>Customer Representative Dashboard</h2>

    <c:if test="${sessionScope.role == 'CustomerRep'}">
        <h3>Welcome, ${sessionScope.user.firstName}!</h3>
    </c:if>

    <hr/>
    
    <h3>Operational Functions</h3>
    <ul>
        <li><a href="manageSchedules?action=list">Manage Train Schedules</a></li>
        <li><a href="customerLookup">Customer Lookup by Line/Date</a></li>
        <li><a href="stationSchedules">Schedule Lookup by Station</a></li>
        <li><a href="forum?action=cr_view">Answer Customer Questions</a></li>
    </ul>

    <hr/>
    <a href="logout.jsp">Logout</a>
</body>
</html>