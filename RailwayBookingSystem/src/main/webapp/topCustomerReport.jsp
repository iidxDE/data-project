<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Top Revenue Customer Report</title>
</head>
<body>
    <h2>Top Revenue Customer Report</h2>
    <a href="manager_dashboard.jsp">Back to Dashboard</a>
    <hr/>

    <c:choose>
        <c:when test="${not empty topCustomerData}">
            <div style="padding: 10px; background-color: #e8f4e8; border: 1px solid green; margin: 10px 0;">
                <h3>Our Most Valuable Customer</h3>
                <p>
                    <strong>Name:</strong> ${topCustomerData.customer.firstName} ${topCustomerData.customer.lastName}
                </p>
                <p>
                    <strong>Customer ID:</strong> ${topCustomerData.customer.customerID}
                </p>
                <p>
                    <strong>Total Revenue Generated:</strong> 
                    <fmt:formatNumber value="${topCustomerData.totalRevenue}" type="currency" currencySymbol="$"/>
                </p>
            </div>
        </c:when>
        <c:otherwise>
            <p>No reservation data available to determine the top customer.</p>
        </c:otherwise>
    </c:choose>

</body>
</html>