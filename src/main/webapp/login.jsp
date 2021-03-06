
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Log in with your account</title>

    <style>
        <%@include file='/resources/bootstrap/css/bootstrap.min.css'%>
    </style>
    <style>
        <%@include file='/resources/bootstrap/css/common.css'%>
    </style>

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>
<div class="container">
    <c:url value="/j_spring_security_check" var="loginUrl"/>
    <form method="POST" action="${loginUrl}" class="form-signin">
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">

            <span>${msg}</span>
            <input name="j_username" type="text" class="form-control" placeholder="login"
                   autofocus="true"/>
            <input name="j_password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
            <h4 class="text-center"><a href="/registration">Registration</a></h4>
        </div>
    </form>

</div>
<%--<div class="container">--%>
<%--<div>--%>
    <%--<c:url value="/j_spring_security_check" var="loginUrl"/>--%>
    <%--<form action="${loginUrl}" method="post">--%>
        <%--<input type="text" name="j_username" placeholder="Login" value="">--%>
        <%--<input type="password"name="j_password" placeholder="Password" required value="">--%>
        <%--<button type="submit">Войти</button>--%>
    <%--</form>--%>
<%--</div>--%>
<%--</div>--%>


<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script><%@include file='/resources/bootstrap/js/bootstrap.min.js'%></script>
</body>
</html>

