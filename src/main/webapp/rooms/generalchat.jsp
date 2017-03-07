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
            width: 800px; /* Ширина блока */
            height: 300px; /* Высота блока */
            background-color: #fbefbc; /* Цвет фона слоя */
            padding: 5px; /* Поля вокруг текста */
            float: left; /* Обтекание по правому краю */
        }

        .userlist {
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
<h1>Welcome to FLChat, dear <%=request.getSession().getAttribute("sessionLogin")%>
</h1>
<div class="gen">
    <a href="/privateoffice?userId=<%=request.getSession().getAttribute("sessionUserId")%>">Личный кабинет</a>
    <%if ("admin".equals(request.getSession().getAttribute("sessionUserType"))) {
        out.print("<a href=\"/adm/adminoffice\">Админка</a>");
    }%>

</div>
<div class="userlist">
    <table border="0" cellpadding="1">
        <tr>
            <form action="/privatechatroom" target="_self" method="get">

                <label for="chatroom">Go to room #:</label>
                <input type="number" name="chatroom" id="chatroom" value="" placeholder="#">

                <input type="submit" value="privat" formmethod="get">
            </form>

        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.getLogin()}"></c:out></td>

                <td><a href="/chat/rooms/generalchat?userTo=${user.getUserID()}">></a>
                </td>
                <td><a href="/chat/rooms/privatechatroom?userTo=${user.getUserID()}">>pr></a>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>
<div class="chat">
    <table border="0" cellpadding="1">

        <form action="/sendmessage" method="post">

            <%--<label for="userFrom">From:</label>--%>
            <input type="text" name="userFrom" id="userFrom" value="<%=request.getSession().getAttribute("sessionUserId")%>"
                   readonly placeholder="userFrom" hidden>

            <label for="toUserId">To:</label>
            <input type="number" name="toUserId" id="toUserId" value="${toUserId}" placeholder="userTo">

            <label for="textMessage">Text:</label>
            <input type="text" name="textMessage" id="textMessage" value="${textMessage}" placeholder="textMessage">

            <input type="number" name="chatroom" id="chatroom" value="0" readonly hidden>

            <input type="submit" value="send" formmethod="post">
        </form>

        <c:forEach items="${messages}" var="message">
            <tr>
                <td><c:out value="${message.getStrDate()}"></c:out></td>
                <td><c:out value="${message.getFromUser().getLogin()}"></c:out></td>
                <td><c:out value="${message.getToUser().getLogin()}"></c:out></td>
                <td><c:out value="${message.getBodyText()}"></c:out></td>
                <td>
                    <form action="/delmessage" method="post">
                        <input type="number" name="chatroom" id="chatroom" value="${message.getChatRoom()}" hidden>
                    <input type="text" name="msgid" id="msgid" value="${message.getId()}" hidden>
                    <input type="submit" value="X" formmethod="post">
                    </form>

                </td>

            <%--<td>--%>
                    <%--<%if ("admin".equals(request.getSession().getAttribute("sessionUserType"))) {--%>
                        <%--out.print("<form action=\"/chat/deletemsg\" method=\"post\">\n" +--%>
                                <%--"<input type=\"text\" name=\"msgid\" id=\"msgid\" value=\"${message.getId()}\" hidden>\n" +--%>
                                <%--"<input type=\"submit\" value=\"X\" formmethod=\"post\">\n" +--%>
                                <%--"</form>");--%>
                    <%--}%>--%>
                <%--</td>--%>
            </tr>
        </c:forEach>

    </table>
</div>

</body>
</html>
