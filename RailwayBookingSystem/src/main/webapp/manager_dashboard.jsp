<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Dashboard</title>
</head>
<body>
    
    <h2>Manager Dashboard</h2>
    <c:if test="${sessionScope.role == 'Manager'}">
        <h3>Welcome, ${sessionScope.user.firstName}!</h3>
    </c:if>

    <hr/>
    
    <h3>Management Functions</h3>
    <ul>
        <li><a href="manageReps?action=list">Manage Customer Representatives</a></li>
        <li><a href="reservationReport">Reservation Lookup Report</a></li>
        <li><a href="salesReport">View Monthly Sales Report</a></li>
        <li><a href="topCustomerReport">View Top Revenue Customer</a></li>
        <li><a href="topLinesReport">View Top 5 Active Transit Lines</a></li>
    </ul>

    <hr/>
    <a href="logout.jsp">Logout</a>

</body>
</html>