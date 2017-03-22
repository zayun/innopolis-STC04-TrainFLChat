<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--<c:set var="currUserId" value="<%=((User) org.springframework.security.core.context.SecurityContextHolder.getContext()--%>
        <%--.getAuthentication().getPrincipal()).getUserID()%>"/>--%>
<html>
<head>

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
                stompClient.subscribe('/topic/greetings', function (message) {
                    showGreeting(JSON.parse(message.body).content);
                });
            });
        }

        function disconnect() {
            stompClient.disconnect();
            setConnected(false);
            console.log("Disconnected");
        }

        function sendName() {
            var bodyText = document.getElementById('name').value;
//            stompClient.send("/app/messageListener", {}, JSON.stringify({'bodyText': bodyText}));
            stompClient.send("/app/messageListener", {},
                JSON.stringify({'chatRoom':0, 'fromUser':0, 'toUser':10, 'bodyText': bodyText}));
        }

        function showGreeting(message) {
//            var response = document.getElementById('response');
            var response = document.getElementById('chatbox');
            var childNotify = document.createElement('div');
            childNotify.className = 'childNotify';
            childNotify.appendChild(document.createTextNode(message));
            response.appendChild(childNotify);
        }

        function init() {
            connect();
        }
    </script>
</head>
<body onload="init()">

<div>
    <div>
        <button id="connect" onclick="connect();" hidden>Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();" hidden>Disconnect</button>
    </div>
    <div id="conversationDiv">
        <label>message</label><input type="text" id="name" />
        <button id="sendName" onclick="sendName();">Send</button>
        <div id="response"></div>
    </div>


</div>
<ul id="chatbox" class="chat">

</ul>



</body>
</html>
