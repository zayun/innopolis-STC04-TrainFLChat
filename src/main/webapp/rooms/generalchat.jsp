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
<c:set var="currUserId" value="<%=((User) org.springframework.security.core.context.SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal()).getUserID()%>"/>
<html>
<head>
    <title>Общий чат</title>

    <style>
        <%@include file='/resources/bootstrap/css/bootstrap.css'%>
    </style>
    <style>
        <%@include file='/resources/bootstrap/css/common.css'%>
    </style>

    <script src="/resources/static/sockjs-0.3.4.js"></script>
    <script src="/resources/static/stomp.js"></script>
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js'></script>
    <script type="text/javascript">
        var stompClient = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';

            document.getElementById('response').innerHTML = '';
        }

        function connect() {
            var socket = new SockJS('/messageListener');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/recieve', function (message) {
                    showMessage(JSON.parse(message.body).fromUser+": "+JSON.parse(message.body).bodyText);
                });
            });
        }

        function disconnect() {
            stompClient.disconnect();
            setConnected(false);
            console.log("Disconnected");
        }

        function sendMessage() {
            var bodyText = document.getElementById('textMessage').value;
            var toUserId = document.getElementById('toUserId').value;
//            stompClient.send("/app/messageListener", {}, JSON.stringify({'bodyText': bodyText}));
            stompClient.send("/app/messageListener", {},
                JSON.stringify({'chatRoom': 0, 'fromUser': ${currUserId}, 'toUser': toUserId, 'bodyText': bodyText}));
            document.getElementsByName("textMessage").value = "";
        }

        function showMessage(message) {
            var response = document.getElementById('chatGroup');
            var childNotify = document.createElement('div');
            childNotify.className = 'childNotify';
            childNotify.appendChild(document.createTextNode(message));
            response.appendChild(childNotify);
        }

        function test() {
            document.write("");
        }

        function init() {
            connect();
        }
    </script>

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
<body onload="init()">
<div>
    <%@include file='navbar.jsp' %>
</div>
<h1>Welcome to FLChat, dear <%=((User) org.springframework.security.core.context.SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal()).getLogin()%>
</h1>


<div class="container">
    <%--<form class="form-inline" action="/sendmessage" method="post">--%>
    <%--<input type="number" name="chatroom" id="chatroom" value="0" readonly hidden>--%>
    <%--<input type="number" name="toUserId" id="toUserId" value="${toUserId}" placeholder="userTo">--%>
    <%--<input name="textMessage" id="textMessage" type="text" placeholder="Type your message here..."maxlength="100">--%>

    <%--<button formmethod="post" class="btn btn-primary btn-sm" id="btn-chat">--%>
    <%--Send--%>
    <%--</button>--%>

    <%--</form>--%>

    <div>
        <button id="connect" onclick="connect();" hidden>Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();" hidden>Disconnect</button>
    </div>
    <div id="conversationDiv" class="form-inline">
        <input type="number" name="toUserId" id="toUserId" value="${toUserId}" placeholder="userTo">
        <input type="text" name="textMessage" id="textMessage" placeholder="Type your message here..." maxlength="100"/>
        <button class="btn btn-primary btn-sm" id="sendMessage" onclick="sendMessage();">Send</button>
        <div id="response"></div>
    </div>
    <div class="userlist">
        <div>
            <%@include file='userlist.jsp' %>
        </div>
    </div>
    <div class="row">
        <div class="panel panel-primary">
            <div class="panel-body">
                <div id="chatGroup" class="chatGroup">
                    <%--<%@include file='messagebox2.jsp' %>--%>
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
