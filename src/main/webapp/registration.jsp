
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

    <title>Create an account</title>

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

    <form action="/registration" method="post">
        <h2 class="form-signin-heading">Create your account</h2>
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <input name="login" type="text" class="form-control" placeholder="login"
                       autofocus="true" required/>
            </div>

            <div class="form-group ${status.error ? 'has-error' : ''}">
                <input name="password" type="password" class="form-control" placeholder="Password" required/>
            </div>

            <div class="form-group ${status.error ? 'has-error' : ''}">
                <input name="confirmpassword" type="password" class="form-control" placeholder="confirmpassword" required/>
            </div>

        <div class="form-group ${status.error ? 'has-error' : ''}">
            <input name="firstName" type="text" class="form-control" placeholder="firstName"
                   autofocus="true"/>
        </div>

        <div class="form-group ${status.error ? 'has-error' : ''}">
            <input name="lastName" type="text" class="form-control" placeholder="lastName"
                   autofocus="true"/>
        </div>

        <div class="form-group ${status.error ? 'has-error' : ''}">
            <input name="birthday" type="date" class="form-control" placeholder="birthday"
                   autofocus="true" />
        </div>

        <div class="form-group ${status.error ? 'has-error' : ''}">
            <input name="email" type="email" class="form-control" placeholder="E-MAIL"
                   autofocus="true"/>
        </div>

        <div class="form-group ${status.error ? 'has-error' : ''}">
            <input name="phoneNumber" type="number" class="form-control" placeholder="Phone"
                   autofocus="true"/>
        </div>

        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label for="male">male?:</label>
            <input name="male" id="male" type="checkbox" class="form-control" placeholder=""
                   autofocus="true"/>
        </div>


        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script><%@include file='/resources/bootstrap/js/bootstrap.min.js'%></script>
</body>
</html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<%--<html>--%>
<%--<head>--%>
    <%--<title>Регистрация</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Регистрация нового пользователя</h1>--%>
<%--<form action="/registration" method="post">--%>

    <%--<label for="login">Login:</label>--%>
    <%--<input type="text" name="login" id="login" value="" placeholder="Login" required>--%>

    <%--<label for="password">Password:</label>--%>
    <%--<input type="password" name="password" id="password" value="" placeholder="Password" required>--%>

    <%--<label for="firstName">First name:</label>--%>
    <%--<input type="text" name="firstName" id="firstName" value="" placeholder="FirstName">--%>

    <%--<label for="lastName">LastName:</label>--%>
    <%--<input type="text" name="lastName" id="lastName" value="" placeholder="LastName">--%>

    <%--<label for="birthday">Birthday:</label>--%>
    <%--<input type="date" name="birthday" id="birthday" value="" placeholder="Birthday">--%>

    <%--<label for="email">E-MAIL:</label>--%>
    <%--<input type="email" name="email" id="email" value="" placeholder="email">--%>

    <%--<label for="phoneNumber">Phone:</label>--%>
    <%--<input type="tel" name="phoneNumber" id="phoneNumber" value="" placeholder="number">--%>

    <%--<label for="male">Male?</label>&lt;%&ndash;надо разобраться не пишется в базу тру, всегда фолс&ndash;%&gt;--%>
    <%--<input type="checkbox" name="male" id="male" value="true" placeholder="male">--%>

    <%--<input type="submit" value="Зарегистрироваться" formmethod="post">--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>
