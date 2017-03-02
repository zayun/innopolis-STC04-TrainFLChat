<%--
  Created by IntelliJ IDEA.
  User: smoldyrev
  Date: 23.02.17
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<h1>Регистрация нового пользователя</h1>
<form action="/chat/registration" method="post">

    <label for="login">Login:</label>
    <input type="text" name="login" id="login" value="" placeholder="Login" required>

    <label for="password">Password:</label>
    <input type="password" name="password" id="password" value="" placeholder="Password" required>

    <label for="firstName">First name:</label>
    <input type="text" name="firstName" id="firstName" value="" placeholder="FirstName">

    <label for="lastName">LastName:</label>
    <input type="text" name="lastName" id="lastName" value="" placeholder="LastName">

    <label for="birthday">Birthday:</label>
    <input type="date" name="birthday" id="birthday" value="" placeholder="Birthday">

    <label for="email">E-MAIL:</label>
    <input type="email" name="email" id="email" value="" placeholder="email">

    <label for="phoneNumber">Phone:</label>
    <input type="tel" name="phoneNumber" id="phoneNumber" value="" placeholder="number">

    <label for="male">Male?</label><%--надо разобраться не пишется в базу тру, всегда фолс--%>
    <input type="checkbox" name="male" id="male" value="true" placeholder="male">

    <input type="submit" value="Зарегистрироваться" formmethod="post">
</form>
</body>
</html>
