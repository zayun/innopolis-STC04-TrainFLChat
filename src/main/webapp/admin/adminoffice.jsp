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
    <style>
        .userlist {
            overflow: scroll;
            width: 800px; /* Ширина блока */
            height: 800px; /* Высота блока */
            background-color: #c8dfff; /* Цвет фона слоя */
            padding: 5px; /* Поля вокруг текста */
            float: left; /* Обтекание по правому краю */
        }

        .notifyerList {
            overflow: scroll;
            background-color: #c0c0c0; /* Цвет фона слоя */
            padding: 5px;
            height: 300px; /* Поля вокруг текста */
            width: 400px; /* Ширина слоя */
            float: left; /* Обтекание по правому краю */
        }
        .gen {
            clear: left; /* Отмена обтекания */
        }
    </style>
</head>
<body>
<h1>Adminka</h1>
<div class="userlist">
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
                <form action="/adm/edituserblock" method="post">
                    <input type="text" name="userId" id="userId" value="${user.getUserID()}" hidden>
                    <input type="text" name="block" id="block" value="${user.isBlocked()}"} hidden>
                    <input type="submit" value="change" formmethod="post">
                </form>
            </td>
            <td>
                <form action="/adm/editusertype" method="post">
                    <input type="text" name="userId" id="userId" value="${user.getUserID()}" hidden>
                    <input type="text" name="usertype" id="usertype" value="${user.getUserType()}"}>
                    <input type="submit" value="change" formmethod="post">
                </form>
            </td>
            <td>
                <form action="/adm/notifylist" method="get">
                    <input type="text" name="userId" id="userId" value="${user.getUserID()}" hidden>
                    <input type="submit" value=">>notify>>" formmethod="get">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>
</div>
<form action="/generalchat" method="GET">
    <input type="submit" value="exit" formmethod="get">
</form>
</body>
</html>
