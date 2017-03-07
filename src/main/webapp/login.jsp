<%--
  Created by IntelliJ IDEA.
  User: smoldyrev
  Date: 23.02.17
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WELCOME</title>
</head>
<body>
<b>Авторизируйтесь для входа в чат</b>
<form action="/chat/login" method="post">
    <label for="login">Login:</label>
    <input type="text" name="login" id="login" value="" placeholder="Login">
    <label for="password">Password:</label>
    <input type="password" name="password" id="password" value="" placeholder="Password">
    <input type="submit" value="OK" formmethod="post">
</form>
<form action="/chat/registration" method="post">
    <input type="submit" value="Регистрация" formmethod="GET">

</form>

<%--<%--%>
    <%--for (int i = 0; i < 10; i++) {--%>
        <%--out.println("<form action=\"/chat/registration\" method=\"post\">\n" +--%>
                <%--"    <input type=\"submit\" value=\"Регистрация\" formmethod=\"GET\">\n" +--%>
                <%--"\n" +--%>
                <%--"</form>");--%>
    <%--}%>--%>
${msg}
</body>
</html>
