<%@ page import="com.example.utils.LocaleManager" %>
<%@ page import="com.example.storage.Storage" %>
<%@ page import="com.example.models.Post" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Список сообщений пользователя <%=(String)session.getAttribute("username")%></title>
</head>
<body>
<h1>Список сообщений</h1>
<table border='1'>
    <tr>
        <td><b> <%=LocaleManager.getString("user")%> </b></td>
        <td><b> <%=LocaleManager.getString("comment")%> </b></td>
        <td><b> <%=LocaleManager.getString("date")%> </b></td>
    </tr>
    <%
        for (Post post: Storage.findByName((String)session.getAttribute("username"))) {
            out.println("<tr>"+
                    "<td>"+post.getUsername()+"</td>"+
                    "<td>"+post.getComment()+"</td>"+
                    "<td>"+post.getDate()+"</td>"+
                    "</tr>");
        }
    %>
</table>
<%-- вернуться на главную страницу --%>
<br>
<a href='/posts'>На главную страницу</a>

</body>
</html>