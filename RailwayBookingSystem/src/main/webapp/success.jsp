<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Success</title>
</head>
<body>

    <h2>Welcome, ${sessionScope.user.firstName}!</h2>
    
    <p>You have successfully logged in.</p>
    <p><a href="viewSchedules">View All Train Schedules</a></p>
    <p><a href="myReservations">View My Reservations</a></p>
    <p><a href="forum?action=myQuestions">My Questions &amp; Answers</a></p>
    <a href="logout.jsp">Logout</a>

</body>
</html>
