<%@ page import="java.util.Date" %> <%-- Добавление директивы - в нашем случае импорт класса Date --%>
<%@ page import="logic.Cart" %> <%-- Добавление собственных классов (обязательно должны быть в каком-то пакете --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Example</title>
</head>
<body>
<%
    // Авторизация по номеру телефона
    String userPhoneNumber = (String) session.getAttribute("user_phone_number");
    if (userPhoneNumber == null) { // редирект на страницу регистрации (новый Профиль)
        response.sendRedirect("/profile");
    }
%>  <%-- Не выдаёт на экран что-либо, просто Java code --%>
<h1>
    <%=
        "Your phone number: " + session.getAttribute("user_phone_number")
    %> <%-- Обязаны вернуть String, который вставится на это место в HTML код --%>
</h1>
<p>
    <%
        // получаем атрибут сессии "cart"
        Cart cart = (Cart) session.getAttribute("cart");
        // если пользователь заходит на сайт впервые, то cart==null
        if (cart == null) {
            cart = new Cart();
        }
        // пример обработки атрибутов запроса для добавления товара в корзину
        String name = "---";
        int quantity = 0;
        if (request.getParameter("name") != null && request.getParameter("quantity") != null) {
            name = request.getParameter("name");
            quantity = Integer.parseInt(request.getParameter("quantity"));
        }
        cart.setName(name);
        cart.setQuantity(quantity);
        session.setAttribute("cart", cart);
    %>
    <%
        // Конструкция для вывода строки в HTML без тегов "%="
        out.println("There is " + cart.getQuantity() + " item in your cart: " + cart.getName());
    %>
</p>
<p>
    <%
        // получаем атрибут сессии "count"
        Integer count = (Integer) session.getAttribute("count");
        // если пользователь заходит на сайт впервые, то count==null
        if (count == null) {
            count = 1;
            session.setAttribute("count", count);
        }
        else {
            session.setAttribute("count", ++count);
        }
    %>
    <%= "Your session-counter: " + count  %>
</p>
<br/>
<a href="profile-servlet">My profile</a>
<br/>
<a href="redirect">Redirect to Google!</a>
<br/>
<a href="forward">Forward to Edit profile!</a>
<br/>
<a href="profile">Edit profile!</a>
</body>
</html>