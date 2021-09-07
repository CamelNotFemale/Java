<%@ page import="com.example.utils.LocaleManager" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%=LocaleManager.getString("error")%></title>
</head>
<body>
<h3><%=LocaleManager.getString("error")%></h3>
<%=LocaleManager.getString("msg.error.anonim")%>
<br>
<%-- добавление нового поста --%>
<a href='/new_post'><%= LocaleManager.getString("btn.newPost")%></a>
</body>
</html>
