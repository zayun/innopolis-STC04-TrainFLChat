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
            <td><c:out value="${converse.getChatrooom()}"></c:out></td>
            <td><c:out value="${converse.getGradeConverse()}"></c:out></td>
            <td><c:out value="${converse.getStartTime()}"></c:out></td>
            <td><c:out value="${converse.getEndTime()}"></c:out></td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
