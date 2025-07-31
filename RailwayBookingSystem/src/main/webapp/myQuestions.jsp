<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Questions</title>
    <style>
        .question { border: 1px solid #000; margin-bottom: 20px; padding: 15px; }
        .answer { border-top: 1px dashed #ccc; margin-top: 15px; padding-top: 15px; margin-left: 30px; }
        .status-answered { color: green; }
        .status-unanswered { color: orange; }
    </style>
</head>
<body>
    <h2>My Questions</h2>
    <form action="forum" method="get">
    	<input type="hidden" name="action" value="myQuestions">
    	<label for="keywords">Search Your Questions:</label>
    	<input type="text" id="keywords" name="keywords" value="${param.keywords}" placeholder="Enter keywords...">
    	<input type="submit" value="Search">
	</form>
    <a href="success.jsp">Back to Dashboard</a> | <a href="askQuestion.jsp">Ask a New Question</a>
    <hr/>

    <c:choose>
        <c:when test="${not empty questionsList}">
            <c:forEach var="question" items="${questionsList}">
                <div class="question">
                    <h4>${question.title}</h4>
                    <p><strong>Status:</strong> 
                        <span class="status-${question.status.toLowerCase()}">${question.status}</span>
                    </p>
                    <p><strong>You asked on ${question.questionDate}:</strong></p>
                    <p>${question.body}</p>

                    <c:forEach var="answer" items="${question.answers}">
                        <div class="answer">
                            <p><strong>Reply from ${answer.employeeFirstName} on ${answer.answerDate}:</strong></p>
                            <p>${answer.body}</p>
                        </div>
                    </c:forEach>
                    
                    <c:if test="${empty question.answers and question.status == 'Unanswered'}">
                         <div class="answer">
                            <p><i>A customer representative will reply to you soon.</i></p>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>You have not asked any questions yet.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>