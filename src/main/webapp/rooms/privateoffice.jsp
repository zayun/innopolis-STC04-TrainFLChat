<%--
  Created by IntelliJ IDEA.
  User: smoldyrev
  Date: 24.02.17
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Личный кабинет</title>
</head>
<body>

<div>
    <%@include file='/rooms/navbar.jsp' %>
</div>
<h1>Добро пожаловать в личный кабинет, ${user.person.firstName}</h1>

<form action="/privateoffice" method="post">
    <table border="0" width="10%" cellpadding="2">
        <input type="text" name="id" id="id" value="${user.userID}" placeholder="id" hidden>
        <tr>
            <td><label for="login">Login:</label>
                <input type="text" name="login" id="login" value="${user.login}" placeholder="Login" minlength="3" maxlength="20">
        </tr>
        </td>

        <tr>
            <td><label for="password">Password:</label>
                <%--<input type="password" name="password" id="password" value="${user.password}" placeholder="Password" required>--%>
                <input type="password" name="password" id="password" value="" placeholder="Password" required minlength="6" maxlength="20">
        </tr>
        </td>

        <tr>
            <td><label for="firstName">First name:</label>
                <input type="text" name="firstName" id="firstName" value="${user.person.firstName}" placeholder="FirstName"maxlength="50">
        </tr>
        </td>

        <tr>
            <td><label for="lastName">LastName:</label>
                <input type="text" name="lastName" id="lastName" value="${user.person.lastName}" placeholder="LastName"maxlength="50">
        </tr>
        </td>

        <tr>
            <td><label for="birthday">Birthday:</label>
                <input type="date" name="birthday" id="birthday" value="${user.person.birthday}" placeholder="Birthday">
        </tr>
        </td>

        <tr>
            <td><label for="email">E-MAIL:</label>
                <input type="email" name="email" id="email" value="${user.person.email}" placeholder="email">
        </tr>
        </td>

        <tr>
            <td><label for="phoneNumber">Phone:</label>
                <input type="tel" name="phoneNumber" id="phoneNumber" value="${user.person.phoneNumber}" placeholder="number"maxlength="10">
        </tr>
        </td>

        <tr>
            <td><label for="male">Male?</label>
                <input type="text" name="male" id="male" value="${user.person.male}" placeholder="male">
        </tr>
        </td>
    </table>
    <%--<table border="1" width="10%" cellpadding="1">--%>


    <table border="1" cellpadding="1">
        <tr>
            <th>ShortName</th>
            <th>FullName</th>
            <th>Dialekt</th>
            <th>Operation</th>
        </tr>

        <c:forEach items="${languages}" var="lang">
            <tr>
                <td><c:out value="${lang.getShortName()}"></c:out></td>
                <td><c:out value="${lang.getFullName()}"></c:out></td>
                <td><c:out value="${lang.getDialekt()}"></c:out></td>

                <%--<td><a href="/chat/edit?id=${lang.language}">edit</a>--%>
                    <%--/<a href="/chat/delete?id=${lang.language}">del</a></td>--%>
            </tr>
        </c:forEach>

    </table>

    <input type="submit" value="ОК" formmethod="post">
</form>
<form action="/addlanguage" method="post">
    <input type="text" name="userId" id="userId" value="${user.userID}"} hidden>
    <input type="text" name="langId" id="langId" value=""}>
    <input type="submit" value="Add" formmethod="post">
</form>
<form action="/generalchat" method="get">
    <input type="submit" value="exit" formmethod="get">
</form>


</body>
</html>
