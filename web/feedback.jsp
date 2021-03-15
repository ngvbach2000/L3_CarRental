<%-- 
    Document   : feedback
    Created on : Feb 28, 2021, 9:35:05 AM
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
        <c:if test="${user.role eq 'cust' and user.status eq 'new'}">
            <c:redirect url="generateOTP" />
        </c:if>

        <c:set var="user" value="${sessionScope.USER}"/>
        <font color ="red">
        <c:if test="${not empty user}">
            Welcome, ${user.fullname}<br/>
            <form action="logout">
                <input type="submit" value="Logout" />
            </form>
        </c:if>
        <c:if test="${empty user}">
            <c:redirect url="loginPage"/>
        </c:if>
        </font>

        <a href="/L3_CarRental/"><h1>Car Rental</h1></a>

        <h2>Feedback</h2>

        <c:set var="feedback" value="${requestScope.FEEDBACK}" />

        <c:if test="${empty feedback}">
            <form action="feedback" method="POST">
                Order ID: ${requestScope.ORDERID} <br/>
                Rating: <table border="1">
                    <thead>
                        <tr>
                            <th>1</th>
                            <th>2</th>
                            <th>3</th>
                            <th>4</th>
                            <th>5</th>
                            <th>6</th>
                            <th>7</th>
                            <th>8</th>
                            <th>9</th>
                            <th>10</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td> <input type="radio" name="txtRating" value="1" 
                                        ${feedback.rating == 1 ? 'checked':''}/> </td>
                            <td> <input type="radio" name="txtRating" value="2" 
                                        ${feedback.rating == 2 ? 'checked':''}/> </td>
                            <td> <input type="radio" name="txtRating" value="3" 
                                        ${feedback.rating == 3 ? 'checked':''}/> </td>
                            <td> <input type="radio" name="txtRating" value="4" 
                                        ${feedback.rating == 4 ? 'checked':''}/> </td>
                            <td> <input type="radio" name="txtRating" value="5" checked="checked"
                                        ${feedback.rating == 5 ? 'checked':''}/> </td>
                            <td> <input type="radio" name="txtRating" value="6" 
                                        ${feedback.rating == 6 ? 'checked':''}/> </td>
                            <td> <input type="radio" name="txtRating" value="7" 
                                        ${feedback.rating == 7 ? 'checked':''}/> </td>
                            <td> <input type="radio" name="txtRating" value="8" 
                                        ${feedback.rating == 8 ? 'checked':''}/> </td>
                            <td> <input type="radio" name="txtRating" value="9" 
                                        ${feedback.rating == 9 ? 'checked':''}/> </td>
                            <td> <input type="radio" name="txtRating" value="10" 
                                        ${feedback.rating == 10 ? 'checked':''}/> </td>
                        </tr>
                    </tbody>
                </table>

                Commend: <textarea type="textarea" name="txtCommend" maxlength="225" ></textarea><br/>
                <input type="hidden" name="txtOrderID" value="${param.txtOrderID}" />
                <input type="submit" value="Send Feedback" />
            </form>
        </c:if>
        <c:if test="${not empty feedback}">
            Order ID: ${feedback.orderID} <br/>
            Rating: ${feedback.rating}/10 <br/>
            Commend: ${feedback.content} <br/>
        </c:if>

        <a href="viewHistory">Return to Order History</a>

    </body>
</html>
