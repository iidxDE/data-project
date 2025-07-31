<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Representative</title>
</head>
<body>
    <h2>Edit Representative Information</h2>
    <a href="manageReps?action=list">Back to List</a>
    <hr/>

    <form action="manageReps" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="employeeId" value="${employee.employeeID}">
        
        <table>
            <tr>
                <td>Username:</td>
                <td><input type="text" name="username" value="${employee.username}" required></td>
            </tr>
            <tr>
                <td>New Password:</td>
                <td><input type="password" name="password" placeholder="Leave blank to keep current password"></td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td><input type="text" name="firstName" value="${employee.firstName}" required></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><input type="text" name="lastName" value="${employee.lastName}" required></td>
            </tr>
            <tr>
                <td>SSN:</td>
                <td><input type="text" name="ssn" value="${employee.ssn}" required></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Update Information"></td>
            </tr>
        </table>
    </form>
</body>
</html>