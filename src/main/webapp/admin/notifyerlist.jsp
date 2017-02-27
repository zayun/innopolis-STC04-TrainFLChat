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
<form action="/chat/admin/edittnotifylist" method="post">
    <%--<input type="text" name="notId" id="notId" value="0" hidden>--%>
        <input type="text" name="userID" id="userID" value="${userID}"} hidden>
        <input type="text" name="editType" id="editType" value="add"} hidden>
    <input type="text" name="notType" id="notType" value=""}>
    <input type="submit" value="Add" formmethod="post">
</form>
    <table border="1" cellpadding="1">
        <tr>
            <th>NotifyType</th>
        </tr>

        <c:forEach items="${notifyers}" var="notify">
            <tr>
                <td><c:out value="${notify.getNotType()}"></c:out></td>
                <td> <form action="/chat/admin/edittnotifylist" method="post">
                    <input type="text" name="editType" id="editType" value="edit"} hidden>
                    <input type="text" name="notId" id="notId" value="${notify.getId()}" hidden>
                    <input type="text" name="notType" id="notType" value="${notify.getNotType()}"}>
                    <input type="submit" value="edit" formmethod="post">
                </form></td>

                <td> <form action="/chat/admin/edittnotifylist" method="post">
                    <input type="text" name="editType" id="editType" value="del"} hidden>
                    <input type="text" name="notId" id="notId" value="${notify.getId()}" hidden>
                    <input type="submit" value="del" formmethod="post">
                </form></td>
            </tr>
        </c:forEach>

    </table>
</body>
</html>
