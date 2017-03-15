
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>

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

    <form action="/validregistration" th:action="@{/validregistration}" th:object="${user}" method="post">
        <h2 class="form-signin-heading">Create your account</h2>

        <tr>
            <td>Name:</td>
            <td><input type="text" th:field="*{login}" /></td>
            <td th:if="${fields.hasErrors('login')}" th:errors="*{login}">Name Error</td>
        </tr>

        <tr>
            <td>pwd:</td>
            <td><input type="text" th:field="*{password}" /></td>
            <td th:if="${fields.hasErrors('password')}" th:errors="*{password}">Name Error</td>
        </tr>
            <%--<div class="form-group ${status.error ? 'has-error' : ''}">--%>
                <%--<input name="login" th:field="*{login}" type="text" class="form-control" placeholder="login"--%>
                       <%--autofocus="true" required/>--%>
            <%--</div>--%>

            <%--<div class="form-group ${status.error ? 'has-error' : ''}">--%>
                <%--<input name="password" th:field="*{password}" type="password" class="form-control" placeholder="Password" required/>--%>
            <%--</div>--%>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script><%@include file='/resources/bootstrap/js/bootstrap.min.js'%></script>
</body>
</html>
