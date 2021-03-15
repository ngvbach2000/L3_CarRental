<%-- 
    Document   : register
    Created on : Feb 26, 2021, 2:07:43 PM
    Author     : ngvba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Car Rental</title>
    </head>
    <body>

        <c:set var="user" value="${sessionScope.USER}"/>
        <c:if test="${user.role eq 'cust' and user.status eq 'active'}">
            <c:redirect url="" />
        </c:if>
        <c:if test="${user.role eq 'cust' and user.status eq 'new'}">
            <c:redirect url="generateOTP" />
        </c:if>

        <a href="/L3_CarRental/"><h1>Car Rental</h1></a>

        <h2>Register</h2>

        <c:set var="error" value="${requestScope.REGISTERERR}"/>

        <form action="registerUser" method="POST">
            Email*: <input type="email" name="txtEmail" value="${param.txtEmail}" /><br/>
            <c:if test="${not empty error.emailLengthErr}">
                <font color="red">
                ${error.emailLengthErr}<br/>
                </font>
            </c:if> 
            <c:if test="${not empty error.emailFormatErr}">
                <font color="red">
                ${error.emailFormatErr}<br/>
                </font>
            </c:if> 

            Password*: <input type="password" name="txtPassword" value="" /><br/>
            <c:if test="${not empty error.passwordLengthErr}">
                <font color="red">
                ${error.passwordLengthErr}<br/>
                </font>
            </c:if> 

            Confirm password*: <input type="password" name="txtConfirmPassword" value="" /><br/>
            <c:if test="${not empty error.confirmNotMatch}">
                <font color="red">
                ${error.confirmNotMatch}<br/>
                </font>
            </c:if> 

            Name*: <input type="text" name="txtName" value="${param.txtName}" /><br/>
            <c:if test="${not empty error.nameLengthErr}">
                <font color="red">
                ${error.nameLengthErr}<br/>
                </font>
            </c:if> 

            Address*: <input type="text" name="txtAddress" value="${param.txtAddress}" /><br/>
            <c:if test="${not empty error.addressLengthErr}">
                <font color="red">
                ${error.addressLengthErr}<br/>
                </font>
            </c:if>

            Phone*: <input type="text" name="txtPhone" value="${param.txtPhone}" /><br/>
            <c:if test="${not empty error.phoneFormatErr}">
                <font color="red">
                ${error.phoneFormatErr}<br/>
                </font>
            </c:if>
            <c:if test="${not empty error.phoneLengthErr}">
                <font color="red">
                ${error.phoneLengthErr}<br/>
                </font>
            </c:if>

            <input type="submit" value="Register" />
        </form>

        <c:if test="${not empty error.emailIsExisted}">
            <font color="red">
            ${error.emailIsExisted}<br/>
            </font>
        </c:if> 

        <a href="loginPage">Click here to login</a>
    </body>
</html>
