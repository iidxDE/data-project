<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ask a Question</title>
</head>
<body>
    <h2>Ask Customer Service</h2>
    <a href="success.jsp">Back to Dashboard</a>
    <hr/>

    <form action="forum" method="post">
        <input type="hidden" name="action" value="ask">
        
        <strong>Title:</strong><br/>
        <input type="text" name="title" size="50" required><br/><br/>
        
        <strong>Your Question:</strong><br/>
        <textarea name="body" rows="10" cols="50" required></textarea><br/><br/>
        
        <input type="submit" value="Submit Question">
    </form>
</body>
</html>