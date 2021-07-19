package selrvlets;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "profileServlet", value = "/profile-servlet")
public class ProfileServlet extends HttpServlet {

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        HttpSession session = request.getSession();

        writer.println("<p>" + "Your phone number: " + session.getAttribute("user_phone_number") + "</p>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        String name = request.getParameter("username");
        String age = request.getParameter("userage");
        String gender = request.getParameter("gender");
        String phoneNumber = request.getParameter("phoneNumber");
        String country = request.getParameter("country");
        String[] courses = request.getParameterValues("courses");

        try {
            HttpSession session = request.getSession();
            session.setAttribute("user_name", name);
            session.setAttribute("user_age", age);
            session.setAttribute("user_gender", gender);
            session.setAttribute("user_phone_number", phoneNumber);
            session.setAttribute("user_country", country);
            session.setAttribute("user_courses", courses);

            writer.println("<p>Name: " + name + "</p>");
            writer.println("<p>Age: " + age + "</p>");
            writer.println("<p>Gender: " + gender + "</p>");
            writer.println("<p>Phone Number: " + phoneNumber + "</p>");
            writer.println("<p>Country: " + country + "</p>");
            writer.println("<h4>Courses</h4>");
            for(String course: courses)
                writer.println("<li>" + course + "</li>");
            writer.println("<br><br>");
            writer.println("<button onclick=\"window.location.href = '/hello';\">All right!</button>");
        } finally {
            writer.close();
        }
    }
}