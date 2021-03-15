<%-- 
    Document   : verifyEmail
    Created on : Feb 27, 2021, 9:40:30 AM
    Author     : ngvba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rental Car</title>
    </head>
    <body>

        <c:set var="user" value="${sessionScope.USER}"/>
        <c:if test="${not empty user}">
            <font color ="red">
            Welcome, ${user.fullname}<br/>
            </font>
            <form action="logout">
                <input type="submit" value="Logout" />
            </form>
            <c:if test="${user.status eq 'active'}">
                <c:redirect url="/"/>
            </c:if>
        </c:if>
        <c:if test="${empty user}">
            <c:redirect url="loginPage"/>
        </c:if>

        <a href="/L3_CarRental/"><h1>Car Rental</h1></a>

        <h2>Verify your email</h2>

        An OTP verification code has been sent to your email.<br/>
        Please input to the field to verify your email.
        <form action="verifyEmail" method="POST">
            OTP: <input type="text" name="txtOTP" value="" maxlength="4"/>
            <input type="submit" value="Submit" />
        </form>
        <c:if test="${not empty OTPERR}">
            <font color="red">
            ${OTPERR}<br/>
            </font>
        </c:if>

    </body>
</html>
