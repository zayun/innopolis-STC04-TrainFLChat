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
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">WebSiteName</a>
        </div>
        <ul class="nav navbar-nav">

            <c:if test="${pageContext.session.getAttribute('sessionUserType') == 'admin'}">
                <li><a href="/adm/adminoffice">ADMINKA</a></li>
            </c:if>
            <li class="active"><a href="/generalchat">Home</a></li>
            <%--<li><a href="#">Page 1</a></li>--%>
            <li><a href="/privateoffice?userId=<%=request.getSession().getAttribute("sessionUserId")%>">
                <%=request.getSession().getAttribute("sessionLogin")%>
            </a></li>
            <li><a href="/logout">Logout</a></li>

        </ul>
    </div>
</nav>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
    <%@include file='/resources/bootstrap/js/bootstrap.min.js' %>
</script>
</body>
</html>

