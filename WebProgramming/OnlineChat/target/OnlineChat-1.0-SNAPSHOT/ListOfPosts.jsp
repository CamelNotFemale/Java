<%@ page import="com.example.utils.LocaleManager" %>
<%@ page import="com.example.models.Post" %>
<%@ page import="com.example.storage.Storage" %> <%-- Добавление собственных классов (обязательно должны быть в каком-то пакете --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<table border='1'>
    <tr>
        <td><b> <%=LocaleManager.getString("user")%> </b></td>
        <td><b> <%=LocaleManager.getString("comment")%> </b></td>
        <td><b> <%=LocaleManager.getString("date")%> </b></td>
    </tr>
    <%
        for (Post post: Storage.getPostList()) {
            out.println("<tr>"+
                    "<td>"+post.getUsername()+"</td>"+
                    "<td>"+post.getComment()+"</td>"+
                    "<td>"+post.getDate()+"</td>"+
                    "</tr>");
        }
    %>
</table>
