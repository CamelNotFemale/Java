<%@ page import="com.example.utils.LocaleManager" %> <%-- Добавление собственных классов (обязательно должны быть в каком-то пакете --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<table border='1'>
    <tr>
        <td><b> <%=LocaleManager.getString("user")%> </b></td>
        <td><b> <%=LocaleManager.getString("comment")%> </b></td>
        <td><b> <%=LocaleManager.getString("date")%> </b></td>
    </tr>
    <tr>
        <td>ДимонЛимон</td>
        <td>всем привет в этом чатике</td>
        <td><%=LocaleManager.getTime()%></td>
    </tr>
    <tr>
        <td>Чипибарум</td>
        <td>Жду обновлений приложения с нетерпением!</td>
        <td><%=LocaleManager.getTime()%></td>
    </tr>
    <%
        if(session.getAttribute("username") != null) {
            out.println("<tr>"+
                            "<td>"+session.getAttribute("username")+"</td>"+
                            "<td>"+session.getAttribute("comment")+"</td>"+
                            "<td>"+LocaleManager.getTime()+"</td>"+
                        "</tr>");
        }
    %>
</table>
