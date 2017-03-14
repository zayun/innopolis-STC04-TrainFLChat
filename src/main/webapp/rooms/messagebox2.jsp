<%--
  Created by IntelliJ IDEA.
  User: smoldyrev
  Date: 11.03.17
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="currUserId" value="<%=((User) org.springframework.security.core.context.SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal()).getUserID()%>"/>
<html>
<head>

</head>
<body>

<ul class="chat">

    <c:forEach items="${messages}" var="message">

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

                            <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin">
                                <form action="/delmessage" method="post">
                                    <input type="number" name="chatroom" id="chatroom" value="${message.getChatRoom()}"
                                           hidden>
                                    <input type="text" name="msgid" id="msgid" value="${message.getId()}" hidden>
                                    <input type="submit" class="btn btn-danger btn-xs" value="del" formmethod="post">
                                </form>
                            </sec:authorize>


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

                            <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin">
                                <form action="/delmessage" method="post">
                                    <input type="number" name="chatroom" id="chatroom" value="${message.getChatRoom()}"
                                           hidden>
                                    <input type="text" name="msgid" id="msgid" value="${message.getId()}" hidden>
                                    <input type="submit" class="btn btn-danger btn-xs" value="del" formmethod="post">
                                </form>
                            </sec:authorize>
                        </small>
                        <strong class="pull-right primary-font">
                            <c:out
                                    value="${message.getToUser().getLogin()}"></c:out></strong>
                    </div>
                    <p>
                        <c:if test="${currUserId == message.getToUser().getUserID()}">
                            <b><c:out value="${message.getBodyText()}"></c:out>
                            </b>
                        </c:if>
                        <c:if test="${currUserId != message.getToUser().getUserID()}">
                            <c:out value="${message.getBodyText()}"></c:out>
                        </c:if>
                    </p>
                </div>
            </li>
        </c:if>
    </c:forEach>
</ul>
</div>
</div>


</body>
</html>
