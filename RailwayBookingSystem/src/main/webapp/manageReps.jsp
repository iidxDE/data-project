<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Representatives</title>
    <style> table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; } </style>
</head>
<body>
    <h2>Manage Customer Representatives</h2>
    <a href="manager_dashboard.jsp">Back to Dashboard</a>
    <hr/>

    <h3>Add New Representative</h3>
    <form action="manageReps" method="post">
        <input type="hidden" name="action" value="add">
        <table>
            <tr>
                <td>Username:</td>
                <td><input type="text" name="username" required></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td><input type="text" name="firstName" required></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><input type="text" name="lastName" required></td>
            </tr>
            <tr>
                <td>SSN:</td>
                <td><input type="text" name="ssn" required></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Add Representative"></td>
            </tr>
        </table>
    </form>
    <hr/>

    <h3>Existing Representatives</h3>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>SSN</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="rep" items="${repsList}">
                <tr>
                    <td>${rep.employeeID}</td>
                    <td>${rep.username}</td>
                    <td>${rep.firstName}</td>
                    <td>${rep.lastName}</td>
                    <td>${rep.ssn}</td>
                    <td>
                        <a href="manageReps?action=edit&id=${rep.employeeID}">Edit</a>
                        &nbsp;|&nbsp;
                        <a href="manageReps?action=delete&id=${rep.employeeID}" onclick="return confirm('Are you sure you want to delete this representative?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>