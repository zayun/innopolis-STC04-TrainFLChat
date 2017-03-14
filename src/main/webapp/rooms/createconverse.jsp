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
    <title>converse</title>
</head>
<body>

<div>
    <%@include file='/rooms/navbar.jsp' %>
</div>
<h1>create converse</h1>

<form action="/createconverse" method="post">

    <table border="0" width="10%" cellpadding="2">

        <tr>
            <td><label for="converse">chatroom:</label>
                <input type="number" name="converse" id="converse" value="${converse}" placeholder="chatroom">
        </tr>
        <tr>
            <td><label for="chatroom">chatroom:</label>
                <input type="number" name="chatroom" id="chatroom" value="${chatroom}" placeholder="chatroom">
        </tr>
        </td>
        <tr>
                <input type="datetime-local" name="start_date" id="start_date" value="${start_date}" placeholder="start_date" hidden>
        </tr>
        </td>
        <tr>

                <input type="datetime-local" name="end_date" id="end_date" value="${end_date}" placeholder="end_date" hidden>
        </tr>
        </td>
    </table>
    <input type="submit" value="ОК" formmethod="post" hidden>

</form>
<form action="/addconversemember" method="post">
    <label user_add:</label>
    <input type="number" name="converse" id="converse" value="${converse}" hidden>
    <input type="number" name="chatroom" id="chatroom" value="${chatroom}" hidden>
    <input type="number" name="userId" id="userId" value="${userId}">
    <input type="submit" value=">>" formmethod="post">
</form>

<table border="1" cellpadding="1">
    <tr>
        <th>user</th>
    </tr>
<c:forEach items="${users}" var="user">
    <tr>
        <td><c:out value="${user.getLogin()}"></c:out></td>
    </tr>
</c:forEach>
</table>

<form class="navbar-form pull-right" action="/privatechatroom" method="get">

    <input type="number" name="chatroom" id="chatroom" value="${chatroom}" readonly>

    <input type="submit" value="go" formmethod="get">
</form>
</body>
</html>
