<%-- 
    Document   : home
    Created on : Feb 25, 2021, 7:17:57 PM
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
            <a href="loginPage">Click here to login</a>
        </c:if>
        </font>

        <a href="/L3_CarRental/"><h1>Car Rental</h1></a>

        <a href="cartPage">View Your Cart</a>

        <form action="viewHistory" method="POST">
            <input type="submit" value="View Your Order History" />
        </form>

        <h2>Search</h2>
        <c:set var="searchErr" value="${requestScope.SEARCHERR}"/>
        <form action="searchCar" method="POST" >
            Name: <input type="text" name="txtSearchNameValue" value="${param.txtSearchNameValue}" /><br/>
            Category: <select name="txtSearchCategoryValue">
                <option value="">---choose category---</option>
                <c:set var="listCategory" value="${sessionScope.LISTCATEGORY}"/>
                <c:if test="${not empty listCategory}">
                    <c:forEach var="category" items="${listCategory}">
                        <option ${category == param.txtSearchCategoryValue ? 'selected':''}>${category}</option>
                    </c:forEach>
                </c:if>
            </select> <br/>
            Rental Date: <input type="date" name="txtRentalDate" value="${param.txtRentalDate}"/><br/>
            <c:if test="${not empty searchErr.rentalDateIsEmpty}">
                <font color="red">
                ${searchErr.rentalDateIsEmpty}<br/>
                </font>
            </c:if>
            Return Date: <input type="date" name="txtReturnDate" value="${param.txtReturnDate}"/><br/>
            <c:if test="${not empty searchErr.returnDateIsEmpty}">
                <font color="red">
                ${searchErr.returnDateIsEmpty}<br/>
                </font>
            </c:if>
            Amount: <input type="number" name="txtAmount" value="${empty param.txtAmount ? 1 : param.txtAmount}" /><br/>
            <c:if test="${not empty searchErr.amountLengthErr}">
                <font color="red">
                ${searchErr.amountLengthErr}<br/>
                </font>
            </c:if>
            <input type="submit" value="Search" />
            <c:if test="${not empty searchErr.rangeOfDateErr}">
                <font color="red">
                <br/>${searchErr.rangeOfDateErr}<br/>
                </font>
            </c:if>
        </form>

        <c:choose>
            <c:when test="${not empty param.txtRentalDate or not empty param.txtReturnDate}">
                <c:set var="type" value="searchCar"/>
            </c:when>
            <c:otherwise>
                <c:set var="type" value="/"/>
            </c:otherwise>
        </c:choose>

        <c:if test="${not empty requestScope.DATEERR}">
            <font color="red">
            ${requestScope.DATEERR}
            </font>
        </c:if>

        <h2>List Car</h2>

        <c:set var="listCar" value="${requestScope.LISTCAR}"/>
        <c:if test="${not empty listCar}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Car name</th>
                        <th>Seats</th>
                        <th>Transmission</th>
                        <th>Color</th>
                        <th>Year</th>
                        <th>Category</th>
                        <th>Price/day</th>
                        <th>Quantity</th>
                        <th>Add to cart</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="car" items="${listCar}" varStatus="counter">
                    <form action="addCarToCart" method="POST">
                        <tr>
                            <td>
                                ${counter.count}
                            </td>
                            <td>
                                ${car.carName}
                            </td>
                            <td>
                                ${car.seats}
                            </td>
                            <td>
                                ${car.transmission}
                            </td>
                            <td>
                                ${car.color}
                            </td>
                            <td>
                                ${car.year}
                            </td>
                            <td>
                                ${car.category}
                            </td>
                            <td>
                                ${car.price}$
                            </td>
                            <td>
                                ${car.quantity}
                            </td>
                            <td>
                                <input type="submit" value="Add to cart" ${ not empty searchErr.rangeOfDateErr ? 'disabled="disabled"' : ''}/>
                                <input type="hidden" name="txtCarID" value="${car.carID}" />
                                <input type="hidden" name="txtSearchCategoryValue" value="${param.txtSearchCategoryValue}" />
                                <input type="hidden" name="txtSearchNameValue" value="${param.txtSearchNameValue}" />
                                <input type="hidden" name="txtAmount" value="${param.txtAmount}" />
                                <input type="hidden" name="txtRentalDate" value="${param.txtRentalDate}" />
                                <input type="hidden" name="txtReturnDate" value="${param.txtReturnDate}" />
                                <input type="hidden" name="type" value="${type}" />
                                <input type="hidden" name="currentPage" value="${requestScope.CURRENTPAGE}" />
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty listCar}">
        <font color="red">
        <h4>No Records!</h4>
        </font>
    </c:if>

    <!-- paging -->
    <c:set var="noOfPage" value="${requestScope.NOOFPAGE}"/>
    <c:set var="currentPage" value="${requestScope.CURRENTPAGE}"/>

    <c:if test="${noOfPage != 0}">
        <c:url var="previousPage" value="${type}" >
            <c:param name="txtSearchNameValue" value="${param.txtSearchNameValue}" />
            <c:param name="txtSearchCategoryValue" value="${param.txtSearchCategoryValue}" />
            <c:param name="txtAmount" value="${param.txtAmount}" />
            <c:param name="txtRentalDate" value="${param.txtRentalDate}" />
            <c:param name="txtReturnDate" value="${param.txtReturnDate}" />
            <c:param name="page" value="${currentPage - 1}"/>
        </c:url>
        <c:if test="${currentPage != 1}">
            <a href="${previousPage}">Previous</a>
        </c:if>

        <c:url var="nextPage" value="${type}" >
            <c:param name="txtSearchNameValue" value="${param.txtSearchNameValue}" />
            <c:param name="txtSearchCategoryValue" value="${param.txtSearchCategoryValue}" />
            <c:param name="txtAmount" value="${param.txtAmount}" />
            <c:param name="txtRentalDate" value="${param.txtRentalDate}" />
            <c:param name="txtReturnDate" value="${param.txtReturnDate}" />
            <c:param name="page" value="${currentPage + 1}"/>
        </c:url>
        <c:if test="${currentPage < noOfPage}">
            <a href="${nextPage}">Next</a>
        </c:if>

        <table border="0">
            <tr>
                <c:forEach begin="1" end="${noOfPage}" var="i">
                    <c:choose>
                        <c:when test="${currentPage == i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <c:url var="choosePage" value="${type}" >
                                <c:param name="txtSearchNameValue" value="${param.txtSearchNameValue}" />
                                <c:param name="txtSearchCategoryValue" value="${param.txtSearchCategoryValue}" />
                                <c:param name="txtAmount" value="${param.txtAmount}" />
                                <c:param name="txtRentalDate" value="${param.txtRentalDate}" />
                                <c:param name="txtReturnDate" value="${param.txtReturnDate}" />
                                <c:param name="page" value="${i}"/>
                            </c:url>
                            <td> <a href="${choosePage}">${i}</a> </td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </tr>
        </table>
    </c:if>

</body>
</html>
