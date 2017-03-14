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

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>
<div class="container">
    <div class="row">
        <%--<div class="col-md-5">--%>
            <div class="panel panel-primary">
                <div class="panel-body">
                    <ul class="chat">
                        <c:forEach items="${messages}" var="message">
                            <form action="/delmessage" method="post">
                                <input type="number" name="chatroom" id="chatroom" value="${message.getChatRoom()}" hidden>
                                <input type="text" name="msgid" id="msgid" value="${message.getId()}" hidden>
                                <input type="submit" value="X" formmethod="post">
                            </form>
                            <c:if test="${pageContext.session.getAttribute('sessionUserId') == message.getFromUser().getUserID()}">
                                <li class="left clearfix"><span class="chat-img pull-left">
                            <img src="http://placehold.it/50/55C1E7/fff&amp;text=U" alt="User Avatar"
                                 class="img-circle">
                        </span>
                                    <div class="chat-body clearfix">
                                        <div class="header">
                                            <strong class="primary-font"><c:out
                                                    value="${message.getFromUser().getLogin()}"></c:out></strong>
                                            <small class="pull-right text-muted"><span
                                                    class="glyphicon glyphicon-time"></span>
                                                <p>
                                                    <c:out value="${message.getStrDate()}"></c:out>
                                                </p>
                                            </small>
                                        </div>
                                        <p>
                                            <c:out value="${message.getBodyText()}"></c:out>
                                        </p>
                                    </div>
                                </li>

                            </c:if>
                            <c:if test="${pageContext.session.getAttribute('sessionUserId') != message.getFromUser().getUserID()}">
                                <li class="right clearfix"><span class="chat-img pull-right">
                            <img src="http://placehold.it/50/FA6F57/fff&amp;text=ME" alt="User Avatar"
                                 class="img-circle">
                        </span>
                                    <div class="chat-body clearfix">
                                        <div class="header">
                                            <small class=" text-muted"><span class="glyphicon glyphicon-time"></span>
                                                <p>
                                                    <c:out value="${message.getStrDate()}"></c:out>
                                                </p>
                                            </small>
                                            <strong class="pull-right primary-font"><c:out
                                                    value="${message.getToUser().getLogin()}"></c:out></strong>
                                        </div>
                                        <p>
                                            <c:out value="${message.getBodyText()}"></c:out>
                                        </p>
                                    </div>
                                </li>
                            </c:if>

                        </c:forEach>
                    </ul>
                </div>
            <%--</div>--%>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>

