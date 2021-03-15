<%-- 
    Document   : checkOut
    Created on : Feb 27, 2021, 5:21:54 PM
    Author     : ngvba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        <c:if test="${not empty user}">
            <font color ="red">
            Welcome, ${user.fullname}<br/>
            </font>
            <form action="logout">
                <input type="submit" value="Logout" />
            </form>
        </c:if>
        <c:if test="${empty user}">
            <c:redirect url="loginPage"/>
        </c:if>


        <a href="/L3_CarRental/"><h1>Car Rental</h1></a>

        <h2>Check Out</h2>

        <h3>Shipping Info</h3>
        <c:set var="user" value="${sessionScope.USER}"/>
        Email: ${user.email}</br>
        Full name: ${user.fullname}</br>
        Phone number: ${user.phone}</br>
        Address: ${user.address}</br>

        <h3>Product</h3>
        <c:set var="cart" value="${sessionScope.CUSTCART}"/>
        <c:if test="${not empty cart}">
            <c:set var="cars" value="${cart.cars}"/>
            <c:if test="${not empty cars}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Car name</th>
                            <th>Car type</th>
                            <th>Amount</th>
                            <th>Rental Date</th>
                            <th>Return Date</th>
                            <th>Price/day</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="car" items="${cars}" varStatus="counter">
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${car.car.carName}
                                </td>
                                <td>
                                    ${car.car.category}
                                </td>
                                <td>
                                    ${car.amount}
                                </td>
                                <td>
                                    <fmt:formatDate value="${car.rentalDate}" pattern="yyyy-MM-dd" />

                                </td>
                                <td>
                                    <fmt:formatDate value="${car.returnDate}" pattern="yyyy-MM-dd" />
                                </td>
                                <td>
                                    ${car.price}
                                </td>
                                <td>
                                    ${car.total}
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${not empty cart.discount}">
                            <tr>
                                <td colspan="6">Discount</td>
                                <td colspan="2">
                                    <fmt:formatNumber var="discountValue" 
                                                      value="${cart.discount.saleOff * 100.00}" 
                                                      maxFractionDigits="0"/>
                                    - ${discountValue}%
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <td colspan="6"></td>
                            <td colspan="2">
                                Total: ${cart.totalPrice}
                            </td>
                        </tr>
                    </tbody>
                </table>
                <c:set var="outOfStock" value="${requestScope.OUTOFSTOCK}"/>
                <c:if test="${not empty OUTOFSTOCK}">
                    <font color="red">
                    ${OUTOFSTOCK}<br/>
                    </font>
                </c:if>
                <c:set var="outOfStock" value="${requestScope.DISCOUNTERR}"/>
                <c:if test="${not empty DISCOUNTERR}">
                    <font color="red">
                    ${DISCOUNTERR}<br/>
                    </font>
                </c:if>

                <h3>Confirm to buy</h3>
                <form action="checkOut" method="POST">
                    <input type="submit" value="Confirm" name="btAction" /></br>
                </form>
                <a href="cartPage">Return to cart</a>
            </c:if>
        </c:if>

    </body>
</html>
