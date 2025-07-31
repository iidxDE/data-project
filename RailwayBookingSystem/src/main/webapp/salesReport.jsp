<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Monthly Sales Report</title>
    <style> table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; } </style>
</head>
<body>
    <h2>Monthly Sales Report</h2>
    <a href="manager_dashboard.jsp">Back to Dashboard</a>
    <hr/>

    <table>
        <thead>
            <tr>
                <th>Month</th>
                <th>Total Revenue</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entry" items="${salesData}">
                <tr>
                    <td>${entry.key}</td>
                    <td>
                        <fmt:formatNumber value="${entry.value}" type="currency" currencySymbol="$"/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>