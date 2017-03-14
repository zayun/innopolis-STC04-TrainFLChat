<%--
  Created by IntelliJ IDEA.
  User: smoldyrev
  Date: 25.02.17
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Private chat room</title>
    <style>
        <%@include file='/resources/bootstrap/css/bootstrap.css'%>
    </style>
    <style>
        <%@include file='/resources/bootstrap/css/common.css'%>
    </style>

</head>
<body>
<div>
    <%@include file='/rooms/navbar.jsp' %>
</div>
<h1>Private chatroom#${chatroom}</h1>


<div class="container">
    <form class="form-inline" action="/sendmessage" method="post">
        <input type="number" name="chatroom" id="chatroom" value="${chatroom}" readonly hidden>
        <input type="number" name="toUserId" id="toUserId" value="${toUserId}" placeholder="userTo">
        <input name="textMessage" id="textMessage" type="text" placeholder="Type your message here...">

        <button formmethod="post" class="btn btn-primary btn-sm" id="btn-chat">
            Send
        </button>

    </form>
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
</body>
</html>
