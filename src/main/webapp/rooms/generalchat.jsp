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
        <%@include file='/resources/bootstrap/css/bootstrap.css'%>
    </style>
    <style>
        <%@include file='/resources/bootstrap/css/common.css'%>
    </style>

    <style>
        .chatGroup {
            overflow: scroll;
            /*width: 800px; !* Ширина блока *!*/
            width: 75%;
            height: 400px; /* Высота блока */
            padding: 5px; /* Поля вокруг текста */
            float: left; /* Обтекание по правому краю */
        }

        .userlist {
            overflow: scroll;
            padding: 25px;
            width: 20%; /* Ширина слоя */
            /*height: 420px; !* Поля вокруг текста *!*/

            float: left; /* Обтекание по правому краю */
        }
    </style>
</head>
<body>
<div>
    <%@include file='navbar.jsp' %>
</div>
<h1>Welcome to FLChat, dear <%=request.getSession().getAttribute("sessionLogin")%>
</h1>


<div class="container">
    <form class="form-inline" action="/sendmessage" method="post">
        <input type="number" name="chatroom" id="chatroom" value="0" readonly hidden>
        <input type="number" name="toUserId" id="toUserId" value="${toUserId}" placeholder="userTo">
        <input name="textMessage" id="textMessage" type="text" placeholder="Type your message here...">

        <button formmethod="post" class="btn btn-primary btn-sm" id="btn-chat">
            Send
        </button>

    </form>

    <div class="userlist">
        <div>
            <%@include file='userlist.jsp' %>
        </div>
    </div>
    <div class="row">
        <div class="panel panel-primary">
            <div class="panel-body">
                <div class="chatGroup">
                    <%@include file='messagebox2.jsp' %>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    <%@include file='/resources/bootstrap/js/bootstrap.min.js' %>
</script>
</body>
</html>
