<%@ page import="com.example.utils.LocaleManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>New post</title>
</head>
<body>
<!-- форма отправляет данные на корневой сервер -->
<form action="/" method="POST">

    <%
        // заготовка для удобного редактирования
        String name = (String) session.getAttribute("username");
        String name_value = name == null ? "" : "value="+name;
    %>
    <%=LocaleManager.getString("user")%>: <input name="username" <%=name_value%> />
    <br>
    <%=LocaleManager.getString("msg.anonymously")%> <input type="radio" name="isAnonymous" value="No" checked />No
    <input type="radio" name="isAnonymous" value="Yes" />Yes
    <br><br>

    <%
        String comment = (String) session.getAttribute("comment");
        String comment_value = comment == null ? "" : "value="+comment;
    %>
    <%=LocaleManager.getString("comment")%>: <input name="comment" <%=comment_value%> />
    <br><br>

    <br><br>

    <input type="submit" value=<%=LocaleManager.getString("btn.submit")%> />
</form>
</body>
</html>
