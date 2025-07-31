<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>

    <h2>Create a New Account</h2>

    <p style="color:red;">${requestScope.errorMessage}</p>
    <p style="color:green;">${requestScope.successMessage}</p>

    <form method="post" action="RegisterServlet">
        First Name: <input type="text" name="firstName" required /><br/>
        Last Name: <input type="text" name="lastName" required /><br/>
        Email: <input type="email" name="email" required /><br/>
        Username: <input type="text" name="username" required /><br/>
        Password: <input type="password" name="password" required /><br/>
        <input type="submit" value="Register" />
    </form>
    
    <p>Already have an account? <a href="login.jsp">Login here</a></p>

</body>
</html>