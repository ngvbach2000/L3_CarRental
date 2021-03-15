<%-- 
    Document   : cart
    Created on : Feb 27, 2021, 1:34:11 PM
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

        <h2>Your Cart</h2>

        <c:set var="cart" value="${sessionScope.CUSTCART}"/>
        <c:if test="${not empty cart}">
            <c:set var="cars" value="${cart.cars}"/>
            <c:if test="${not empty cars}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Car name</th>
                            <th>Year</th>
                            <th>Car type</th>
                            <th>Amount</th>
                            <th>Rental Date</th>
                            <th>Return Date</th>
                            <th>Price/day</th>
                            <th>Total</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="car" items="${cars}" varStatus="counter">
                        <form action="modifyCart" method="POST">
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${car.car.carName}
                                </td>
                                <td>
                                    ${car.car.year}
                                </td>
                                <td>
                                    ${car.car.category}
                                </td>
                                <td>
                                    <input type="number" min="1" max="999999" size="1" name="txtAmount" value="${car.amount}" required="required"/> 
                                    <input type="hidden" name="txtCarID" value="${car.car.carID}" />
                                    <input type="submit" value="Update Amount" name="btAction"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${car.rentalDate}" pattern="yyyy-MM-dd" />

                                </td>
                                <td>
                                    <fmt:formatDate value="${car.returnDate}" pattern="yyyy-MM-dd" />
                                </td>
                                <td>
                                    ${car.price}$
                                </td>
                                <td>
                                    ${car.total}$
                                </td>
                                <td>
                                    <input type="hidden" name="txtCarID" value="${car.car.carID}" />
                                    <input type="submit" value="Delete Car" onclick="return confirm('Are you sure you want to delete?')" name="btAction"/>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                    <c:if test="${not empty cart.discount}">
                        <tr>
                            <td colspan="7">Discount</td>
                            <td colspan="3">
                                <fmt:formatNumber var="discountValue" 
                                                  value="${cart.discount.saleOff * 100.00}" 
                                                  maxFractionDigits="0"/>
                                - ${discountValue}%
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td colspan="7"></td>
                        <td colspan="3">
                            Total: ${cart.totalPrice}$
                        </td>
                    </tr>
                </tbody>
            </table>
            <form action="applyDiscount" method="POST">
                Discount Code: <input type="text" name="txtDiscountCode" value="${cart.discount.discountCode}" maxlength="10"/>
                <font color="green">
                ${cart.discount.discountName}
                </font>
                <input type="submit" value="Apply" /><br/>
            </form>
            <c:if test="${not empty requestScope.DISCOUNTERR}">
                <font color="red">
                ${requestScope.DISCOUNTERR}
                </font>
            </c:if>
            <form action="viewOrder">
                <input type="submit" value="Check out" />
            </form>
        </c:if>
    </c:if>

    <c:if test="${not empty requestScope.OUTOFSTOCK}">
        <font color="red">
        <c:forEach var="item" items="${requestScope.OUTOFSTOCK}">
            ${item}<br/>
        </c:forEach>
        </font>
    </c:if>

    <c:if test="${empty cart}">
        <font color="red">
        <h4>Cart is currently empty</h4>
        </font>
    </c:if>

    <a href="/L3_CarRental/">Click here to go to shopping</a>

</body>
</html>
