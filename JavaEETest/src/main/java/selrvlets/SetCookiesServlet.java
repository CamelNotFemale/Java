package selrvlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
/** Создание cookies */
public class SetCookiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookieID = new Cookie("id", "123"),
               cookieName = new Cookie("name", "Viktor");

        cookieID.setMaxAge(24 * 60 * 60);
        cookieName.setMaxAge(24 * 60 * 60);

        response.addCookie(cookieID);
        response.addCookie(cookieName);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
