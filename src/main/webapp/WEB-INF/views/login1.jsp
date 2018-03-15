<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dingqin
  Date: 2017/11/16
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <link href="<%=request.getContextPath()%>/WEB-INF/css/style.css" rel="stylesheet" type="text/css">
</head>
<body  class="showInCenter">
<c:url var="loginUrl" value="/login" />
<form action="${loginUrl}" method="post">
    <c:if test="${param.error != null}">
    <div class="alert alert-danger">
        <p>Invalid username and password.</p>
    </div>
    </c:if>
    <c:if test="${param.logout != null}">
    <div class="alert alert-success">
        <p>You have been logged out successfully.</p>
    </div>
    </c:if>
    名称：<input type="text" name="name"/>
    密码:<input type="password" name="password"/>
    <%--这一行的目的是防止CSRF攻击--%>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="submit" value="登录">
</form>
</body>
</html>
