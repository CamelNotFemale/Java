package com.example.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "postsServlet", value = "/posts")
/**
 * Servlet implementation class Posts
 */
public class Posts extends HttpServlet {
    /**
     * Servlet initialization
     */
    public void init() {
        System.out.println("Init the Posts servlet");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Задание типа кодировки для параметров запроса
        request.setCharacterEncoding("utf-8");
        // Чтение параметра name из запроса
        String username = request.getParameter("username");
        String msg = request.getParameter("msg");
        // Задание типа содержимого для ответа (в том числе кодировки)
        response.setContentType("text/html;charset=UTF-8");
        // Получение потока для вывода ответа
        PrintWriter out = response.getWriter();

        try {
            // Создание HTML-страницы
            out.println("<html>");
            out.println("<head><title>Главная страница</title></head>");
            out.println("<body>");
            out.println("<h1>Все записи сообщества</h1>");
            out.println("<table border='1'>");
            out.println("<tr><td><b>Пользователь</b></td><td><b>Комментарий " +
                    "</b></td><td><b>Дата</b></td></tr>");
            out.println("<tr><td>ДимонЛимон</td><td>Привет всем в этом чатике" +
                    "</td><td>" + new Date() + "</td></tr>");
            out.println("<tr><td>Аноним203</td><td>Жду обновлений" +
                    "</td><td>" + new Date() + "</td></tr>");
            if (username != null && msg != null) {
                out.println("<tr><td>" + username + "</td><td>" + msg +
                        "</td><td>" + new Date() + "</td></tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            // Закрытие потока вывода
            out.close();
        }
    }
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Destroying the servlet
     */
    public void destroy() {
        System.out.println("Destroy the Posts servlet");
    }
}
