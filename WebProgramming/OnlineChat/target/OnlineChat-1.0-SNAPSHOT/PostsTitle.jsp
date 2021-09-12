<%@ page import="com.example.utils.LocaleManager" %> <%-- Добавление собственных классов (обязательно должны быть в каком-то пакете --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="/ErrorManager.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    request.setCharacterEncoding("UTF-8");
    // Чтение параметров из строки
    String lang = request.getParameter("lang");
    // установка локализации через LocaleManager
    if (!LocaleManager.setup(lang)) {
        response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                "Приложение не поддерживает запрашиваемый язык");
        return;
    }
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%=LocaleManager.getString("title")%></title>
</head>
<body>
<%
    String isAnonymous = request.getParameter("isAnonymous");
    String username = request.getParameter("username");
    String comment = request.getParameter("comment");
    if (username != null && comment != null) {
        System.out.println(username+" "+comment);
        if (isAnonymous.equals("Yes")) {
            if (comment.length() > 30)
                throw new IllegalArgumentException("Anonymous posts cannot exceed 30 characters");
            username = "Anonimous";
        }
        Storage.save(username, comment);
    }
%>
<h1><%=LocaleManager.getString("header")%></h1>
<%@include file="ListOfPosts.jsp"%>

<br>
<%-- добавление нового поста --%>
<a href='/new_post'><%= LocaleManager.getString("btn.newPost")%><a>
<br>

<%-- поиск всех оставленных пользователем комментариев --%>
<a href='/search'>Поиск сообщений пользователя<a>
<br>

<%-- ссылки на возможные локализации --%>
<span><%= LocaleManager.getString("msg.localeList") + ": " %></span>
<a href='/posts?lang=ru'>ru</a>
<a href='/posts?lang=en'>en</a>
<a href='/posts?lang=de'>de</a>
</body>
</html>

