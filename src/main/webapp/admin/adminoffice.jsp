<%--
  Created by IntelliJ IDEA.
  User: smoldyrev
  Date: 26.02.17
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Adminka</title>
</head>
<body>
<h1>Adminka</h1>

<table border="1" cellpadding="1">
    <tr>
        <th>user</th>
        <th>usertype</th>
        <th>blocked</th>
        <th>Operation</th>
    </tr>

    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.getLogin()}"></c:out></td>
            <td><c:out value="${user.getUserType()}"></c:out></td>
            <td><c:out value="${user.isBlocked()}"></c:out>
                <form action="/chat/admin/edituser" method="post">
                    <input type="text" name="userID" id="userID" value="${user.getUserID()}" hidden>
                    <input type="text" name="block" id="block" value="${user.isBlocked()}"} hidden>
                    <input type="submit" value="change" formmethod="post">
                </form>
            </td>
            <td>
                <form action="/chat/admin/edituser" method="post">
                    <input type="text" name="userID" id="userID" value="${user.getUserID()}" hidden>
                    <input type="text" name="usertype" id="usertype" value="${user.getUserType()}"}>
                    <input type="submit" value="change" formmethod="post">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>
<form action="/chat/rooms/generalchat" method="post">
    <input type="submit" value="exit" formmethod="post">
</form>
</body>
</html>
