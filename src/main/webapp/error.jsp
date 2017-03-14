<%--
  Created by IntelliJ IDEA.
  User: smoldyrev
  Date: 26.02.17
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8' />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <style>
        <%@include file='/resources/error/style.css'%>
    </style>
    <style>
        <%@include file='/resources/error/ani-red.css'%>
    </style>
    <%--<link rel="stylesheet" type="text/css" media="all" href="http://bootstraptema.ru/templates/404/2016/rmr/style.css">--%>
    <%--<link rel="stylesheet" type="text/css" media="all" href="http://bootstraptema.ru/templates/404/2016/rmr/ani-red.css">--%>
    <title>ERROR</title>
    <script>
        <%@include file='/resources/error/modernizr.js'%>
    </script>
    <%--<script type="text/javascript" src="http://bootstraptema.ru/templates/404/2016/rmr/modernizr.js"></script>--%>
</head>
<body>
<div class="container">
    <div class="ani">
        <div class="a1"><span><b></b></span></div>
        <div class="a2"><span><b></b></span></div>
        <div class="a3"><span><b></b></span></div>
        <div class="a4"><span><b></b></span></div>
        <div class="a5"><span><b></b></span></div>
        <div class="a6"><span><b></b></span></div>
        <div class="a7"><span><b></b></span></div>
        <div class="a8"><span><b></b></span></div>
        <div class="a9"><span><b></b></span></div>
        <div class="a10"><span><b></b></span></div>
    </div>
    <div role="main" class="home">
        <div>
            <h1>ERROR</h1>
            <p class="intro">${msg}</p>
        </div>
    </div>
    <footer role="contentinfo">
        <div>
            <nav id="menu">
                <ul role="navigation" class="nav">
                    <li><a href="/login" class="nav-home"><strong>Login</strong></a></li>
                    <li><a href="/logout">Logout</a></li>
                    <li><a href="/generalchat">General form</a></li>
                </ul>
            </nav>
        </div>
    </footer>
</div>
</body>
</html>
