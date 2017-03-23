<%--
  Created by IntelliJ IDEA.
  User: smoldyrev
  Date: 27.02.17
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <%@include file='/rooms/navbar.jsp' %>
</div>
<table border="1" cellpadding="120">
    <c:forEach items="${conversations}" var="converse">
        <tr>
            <td><c:out value="${converse.getId()}"></c:out></td>
            <td><c:out value="${converse.getChatroom()}"></c:out></td>
            <td><form action="/changeconverse" method="post">
                <input type="text" name="converseId" id="converseId" value="${converse.getId()}" hidden>
                <input type="text" name="version" id="version" value="${converse.version}" hidden>
                <input type="text" name="converseGrade" id="converseGrade" value="${converse.getGradeConverse()}"}>
                <input type="submit" value="chng" formmethod="post">
            </form></td>
            <td><c:out value="${converse.getStartTime()}"></c:out></td>
            <td>
                <form action="/changeconverse" method="post">
                    <input type="text" name="converseId" id="converseId" value="${converse.getId()}" hidden>
                    <input type="text" name="version" id="version" value="${converse.version}" hidden>
                    <input type="datetime" name="date_time" id="date_time" value="${converse.getEndTime()}">
                    <input type="submit" value="chng" formmethod="post">
                </form>
            </td>
            <td>
                <form action="/privatechatroom" method="get">
                    <input type="text" name="chatroom" id="chatroom" value="${converse.getChatroom()}" hidden>
                    <input type="submit" value="shw" formmethod="get">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
