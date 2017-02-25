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
<h1>Добро пожаловать в личный кабинет, ${firstName}</h1>

<form action="/chat/privateoffice" method="post">
    <table border="0" width="10%" cellpadding="2">

        <tr>
            <td><label for="login">Login:</label>
                <input type="text" name="login" id="login" value="${login}" placeholder="Login">
        </tr>
        </td>

        <tr>
            <td><label for="password">Password:</label>
                <input type="password" name="password" id="password" value="${password}" placeholder="Password">
        </tr>
        </td>

        <tr>
            <td><label for="firstName">First name:</label>
                <input type="text" name="firstName" id="firstName" value="${firstName}" placeholder="FirstName">
        </tr>
        </td>

        <tr>
            <td><label for="lastName">LastName:</label>
                <input type="text" name="lastName" id="lastName" value="${lastName}" placeholder="LastName">
        </tr>
        </td>

        <tr>
            <td><label for="birthday">Birthday:</label>
                <input type="date" name="birthday" id="birthday" value="${birthday}" placeholder="Birthday">
        </tr>
        </td>

        <tr>
            <td><label for="email">E-MAIL:</label>
                <input type="email" name="email" id="email" value="${email}" placeholder="email">
        </tr>
        </td>

        <tr>
            <td><label for="phoneNumber">Phone:</label>
                <input type="tel" name="phoneNumber" id="phoneNumber" value="${phoneNumber}" placeholder="number">
        </tr>
        </td>

        <tr>
            <td><label for="male">Male?</label><%--надо разобраться не пишется в базу тру, всегда фолс--%>
                <input type="checkbox" name="male" id="male" value="${male}" placeholder="male">
        </tr>
        </td>
    </table>
    <%--<table border="1" width="10%" cellpadding="1">--%>
    <table border="1" cellpadding="1">
        <tr>
            <th>ShortName</th>
            <th>FullName</th>
            <th>Dialekt</th>
            <th>Level</th>
            <th>Operation</th>
        </tr>

        <c:forEach items="${languages}" var="lang">
            <tr>
                <td><c:out value="${lang.getLanguage().getShortName()}"></c:out></td>
                <td><c:out value="${lang.getLanguage().getFullName()}"></c:out></td>
                <td><c:out value="${lang.getLanguage().getDialekt()}"></c:out></td>
                <td><c:out value="${lang.level}"></c:out></td>

                <td><a href="/chat/edit?id=${lang.language}">edit</a>
                    /<a href="/chat/delete?id=${lang.language}">del</a></td>
            </tr>
        </c:forEach>

    </table>

    <input type="submit" value="ОК" formmethod="post">
</form>


</body>
</html>
