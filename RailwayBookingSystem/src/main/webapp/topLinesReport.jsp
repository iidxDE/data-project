<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Top 5 Active Transit Lines</title>
    <style> table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; } </style>
</head>
<body>
    <h2>Top 5 Most Active Transit Lines Report</h2>
    <a href="manager_dashboard.jsp">Back to Dashboard</a>
    <hr/>

    <c:choose>
        <c:when test="${not empty topLinesData}">
            <table>
                <thead>
                    <tr>
                        <th>Transit Line Name</th>
                        <th>Total Number of Reservations</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${topLinesData}">
                        <tr>
                            <td>${entry.key}</td>
                            <td>${entry.value}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No reservation data available.</p>
        </c:otherwise>
    </c:choose>

</body>
</html>