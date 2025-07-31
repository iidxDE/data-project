<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

    <h2>Login</h2>
    <p style="color:red;">${requestScope.errorMessage}</p>
    <form method="post" action="LoginServlet">
        Username: <input type="text" name="username" required /><br/>
        Password: <input type="password" name="password" required /><br/>
        <input type="submit" value="Login" />
    </form>
    
    <p>Don't have an account? <a href="register.jsp">Register here</a></p>

</body>
</html>
