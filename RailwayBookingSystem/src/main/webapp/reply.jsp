<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reply to Question #${question.questionID}</title>
     <style>
        .question { border: 1px solid #000; margin-bottom: 20px; padding: 15px; background-color: #f9f9f9; }
        .answer { border-top: 1px dashed #ccc; margin-top: 15px; padding-top: 15px; margin-left: 30px; }
    </style>
</head>
<body>
    <h2>Reply to Question</h2>
    <a href="forum?action=cr_view">Back to Question List</a>
    <hr/>
    <div class="question">
        <h4>${question.title}</h4>
        <p><strong>Status:</strong> ${question.status}</p>
        <p><strong>From:</strong> ${question.firstName} ${question.lastName}</p>
        <p><strong>Date:</strong> ${question.questionDate}</p>
        <p><strong>Question Body:</strong></p>
        <p>${question.body}</p>
        <c:if test="${not empty answersList}">
            <c:forEach var="answer" items="${answersList}">
                <div class="answer">
                    <p><strong>Reply from ${answer.employeeFirstName} on ${answer.answerDate}:</strong></p>
                    <p>${answer.body}</p>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <form action="forum" method="post">
        <input type="hidden" name="action" value="reply">
        <input type="hidden" name="questionId" value="${question.questionID}">
        
        <h3>Your Reply:</h3>
        <textarea name="body" rows="10" cols="70" required></textarea><br/><br/>
        
        <input type="submit" value="Post Reply">
    </form>
</body>
</html>