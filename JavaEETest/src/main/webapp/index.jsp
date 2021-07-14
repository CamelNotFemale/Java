<%@ page import="java.util.Date" %>
<%@ page import="logic.TestClass" %> <%-- Добавление директивы - в нашем случае импорт класса Date --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Math example</title>
</head>
<body>
<h1>
    <%
        int a = 2;
        int b = 5;
        if (request.getParameter("a") != null && request.getParameter("a") != null) {
            a = Integer.parseInt(request.getParameter("a"));
            b = Integer.parseInt(request.getParameter("b"));
        }

        String res = a < b ? a + " < " + b : a + " >= " + b;
        // Конструкция для вывода строки в HTML
        out.println("I think that " + res);
        // Использование собственных классов
        TestClass testClass = new TestClass();
    %>  <%-- Не выдаёт на экран что-либо, просто Java code --%>

    <%=
        " from " + new Date()
    %> <%-- Обязаны вернуть String, который вставится на это место в HTML код --%>
</h1>
<p>
    <%= testClass.getInfo() %>
</p>
<br/>
<a href="hello-servlet">Go to Hello Servlet</a>
<br/>
<a href="redirect">Redirect to Google!</a>
<br/>
<a href="forward">Forward to Check in!</a>
<br/>
<a href="user_form.html">Check in!</a>
</body>
</html>