package com.example.servlets;

import com.example.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "userProcessorServlet", value = "/usernameProcessor")
public class UsernameProcessor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        // Получение параметра из строки запроса
        String parameter = request.getParameter("username");
        if(parameter == null) {
            // Сообщение об ошибке, если сервлет вызван без параметра
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не задано имя пользователя");
            return;
        }
        // Проверка, что такой пользователь есть
        boolean haveComments = Storage.findByName(parameter) != null;
        if(!haveComments) { // Сообщение об ошибке, если пользователь не найден
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Пользователь " + parameter + " не найден");
            return;
        }
        // Сохранение автора в сессии
        request.getSession().setAttribute("username", parameter);
        // Сохранение автора в Cookie
        Cookie c = new Cookie("post.author", URLEncoder.encode(parameter, "UTF-8"));
        // Установка времени жизни Cookie в секундах
        c.setMaxAge(100);
        response.addCookie(c);
        // Перенаправление на страницу книг
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() +
                "/search_result"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
