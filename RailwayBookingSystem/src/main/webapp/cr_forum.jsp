<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Questions</title>
    <style>
        .question-summary { border: 1px solid #ccc; margin-bottom: 15px; padding: 10px; }
        .question-meta { font-size: 0.9em; color: #555; }
    </style>
</head>
<body>
    <h2>Unanswered Customer Questions</h2>
    <a href="rep_dashboard.jsp">Back to Dashboard</a>
    <hr/>
    <c:if test="${not empty sessionScope.message}">
        <p style="color: green; font-weight: bold;">${sessionScope.message}</p>
        <c:remove var="message" scope="session"/>
    </c:if>

    <c:choose>
        <c:when test="${not empty questionsList}">
            <p>The following questions are awaiting a reply:</p>
            <c:forEach var="question" items="${questionsList}">
                <div class="question-summary">
                    <h4>${question.title}</h4>
                    <p class="question-meta">
                        Question #${question.questionID} from ${question.firstName} ${question.lastName} 
                        on ${question.questionDate}
                    </p>
                    <a href="forum?action=view_thread&amp;id=${question.questionID}">View &amp; Reply</a>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>There are no unanswered questions at this time. Great job!</p>
        </c:otherwise>
    </c:choose>
</body>
</html>