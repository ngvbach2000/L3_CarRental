<%-- 
    Document   : orderHistory
    Created on : Feb 28, 2021, 9:34:02 AM
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

        <h2>Order History</h2>

        <h3>Search</h3>
        <form action="searchOrderHistory" method="POST">
            Search name: <input type="text" name="txtSearchNameValue" value="${param.txtSearchNameValue}" /><br/>
            Search date: <input type="date" name="txtSearchDateValue" value="${param.txtSearchDateValue}" /><br/>
            <input type="submit" value="Search" />
        </form>

        <h3>List Order History</h3>

        <c:set var="orderHistory" value="${requestScope.ORDERHISTORY}"/>
        <c:if test="${not empty orderHistory}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Order Code</th>
                        <th>Booking Date</th>
                        <th>Order Detail</th>
                        <th>Discount</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Delete</th>
                        <th>Feedback</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orderHistory}" varStatus="counter">
                    <form action="editFeedback" method="POST">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${order.key.orderID}</td>
                            <td><fmt:formatDate value="${order.key.bookingDate}" pattern="yyyy-MM-dd" /></td>
                            <td>
                                <table border="1">
                                    <thead>
                                        <tr>
                                            <th>Car Name</th>
                                            <th>Year</th>
                                            <th>Rental Date</th>
                                            <th>Return Date</th>
                                            <th>Amount</th>
                                            <th>Total Price</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="orderDetail" items="${orderHistory[order.key]}">
                                            <tr>
                                                <td>${orderDetail.car.carName}</td>
                                                <td>${orderDetail.car.year}</td>
                                                <td>${orderDetail.rentalDate}</td>
                                                <td>${orderDetail.returnDate}</td>
                                                <td>${orderDetail.amount}</td>
                                                <td>${orderDetail.total}$</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                            <td>
                                <c:if test="${not empty order.key.discount}">
                                    <fmt:formatNumber var="discountValue" 
                                                      value="${order.key.discount.saleOff * 100}" 
                                                      maxFractionDigits="0"/>
                                    ${discountValue}%
                                </c:if>
                                <c:if test="${empty order.key.discount}">
                                    0%
                                </c:if>
                            </td>
                            <td>${order.key.totalPrice}$</td>
                            <td>
                                ${order.key.status}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.key.status eq 'Pending'}">
                                        <c:url var="deleteOrder" value="deleteOrder">
                                            <c:param name="pk" value="${order.key.orderID}" />
                                            <c:param name="txtSearchDateValue" value="${param.txtSearchDateValue}" />
                                        </c:url>
                                        <a href="${deleteOrder}">Delete</a>
                                    </c:when>
                                    <c:when test="${order.key.status eq 'Cancelled'}">
                                        Deleted
                                    </c:when>
                                    <c:otherwise>
                                        Cannot Delete!
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.key.status eq 'Finish'}">
                                        <input type="hidden" name="txtOrderID" value="${order.key.orderID}" />
                                        <input type="submit" value="Feedback" />
                                    </c:when>
                                    <c:otherwise>
                                        Cannot Feedback!
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>

    </c:if>
    <c:if test="${empty orderHistory}">
        <font color="red">
        <h4>Order History is currently empty</h4>
        </font>
    </c:if>

    <a href="/L3_CarRental/">Click here to go to shopping</a>

</body>
</html>
