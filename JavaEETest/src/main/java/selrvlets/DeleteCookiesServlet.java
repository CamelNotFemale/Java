package selrvlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
/** Удаление Cookies */
public class DeleteCookiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = new Cookie("id", "");

        cookie.setMaxAge(0);
        //cookie.setMaxAge(-1); // - удаление cookies при закрытии Браузера!

        response.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
