
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
