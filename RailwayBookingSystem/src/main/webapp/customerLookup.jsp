<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Lookup</title>
    <style> table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; } </style>
</head>
<body>
    <h2>Customer Lookup by Transit Line and Date</h2>
    <a href="rep_dashboard.jsp">Back to Dashboard</a>
    <hr/>

    <form action="customerLookup" method="get">
        <label for="lineName">Transit Line:</label>
        <select name="lineName" id="lineName" required>
            <option value="">--Select a Line--</option>
            <c:forEach var="line" items="${linesList}">
                <option value="${line.lineName}" ${param.lineName == line.lineName ? 'selected' : ''}>
                    ${line.lineName}
                </option>
            </c:forEach>
        </select>

        <label for="date">Date:</label>
        <input type="date" id="date" name="date" value="${param.date}" required>

        <input type="submit" value="Search Passengers">
    </form>
    <p style="color:red;">${requestScope.errorMessage}</p>
    <hr/>

    <h3>Search Results</h3>
    <c:choose>
        <c:when test="${not empty customersList}">
            <table>
                <thead>
                    <tr>
                        <th>Customer ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Username</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="customer" items="${customersList}">
                        <tr>
                            <td>${customer.customerID}</td>
                            <td>${customer.firstName}</td>
                            <td>${customer.lastName}</td>
                            <td>${customer.email}</td>
                            <td>${customer.username}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No customers found for the selected criteria. Please make a search.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>