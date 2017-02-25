<%--
  Created by IntelliJ IDEA.
  User: smoldyrev
  Date: 23.02.17
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Общий чат</title>

    <style>
        .chat {
            overflow: scroll;
            width: 1000px; /* Ширина блока */
            height: 300px; /* Высота блока */
            background-color: #fc0; /* Цвет фона слоя */
            padding: 5px; /* Поля вокруг текста */
            float: left; /* Обтекание по правому краю */
            width: 1000px; /* Ширина слоя */
        }

        .users {
            background-color: #c0c0c0; /* Цвет фона слоя */
            padding: 5px; /* Поля вокруг текста */
            width: 300px; /* Ширина слоя */
            float: left; /* Обтекание по правому краю */
        }

        .gen {
            clear: left; /* Отмена обтекания */
        }
    </style>
</head>
<body>
<h1><%=request.getSession().getAttribute("wname")%></h1>
<a href="/chat/privateoffice?id=<%=request.getSession().getAttribute("idd")%>">Личный кабинет</a>
<div class="chat">
    <table border="0" cellpadding="1">

        <form action="/chat/sendmessage" method="post">

            <label for="userFrom">From:</label>
            <input type="text" name="userFrom" id="userFrom" value="<%=request.getSession().getAttribute("idd")%>"
                   readonly placeholder="userFrom" hidden>

            <label for="userTo">To:</label>
            <input type="text" name="userTo" id="userTo" value="${userTo}" placeholder="userTo">

            <label for="textMessage">Text:</label>
            <input type="text" name="textMessage" id="textMessage" value="${textMessage}" placeholder="textMessage">

            <input type="text" name="chatroom" id="chatroom" value="0" readonly hidden>

            <input type="submit" value="send" formmethod="post">
        </form>

        <c:forEach items="${messages}" var="message">

            <tr>
                <td><c:out value="${message.getFromUser().getLogin()}"></c:out></td>
                <td><c:out value="${message.getToUser().getLogin()}"></c:out></td>
                <td><c:out value="${message.getBodyText()}"></c:out></td>
            </tr>
        </c:forEach>

    </table>
</div>
<div class="users">
    <table border="0" cellpadding="1">
        <tr>
            <form action="/chat/privatechatroom" method="post">

                <label for="chatroomin">Go to room #:</label>
                <input type="text" name="chatroomin" id="chatroomin" value="" placeholder="chatroomin">

                <input type="submit" value="privat" formmethod="post">
            </form>

        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.getLogin()}"></c:out></td>
                <td><a href="/chat/sendmessage?messageto=${user.getUserID()}">></a>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>
