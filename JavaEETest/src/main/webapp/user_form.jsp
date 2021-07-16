<%--
  Created by IntelliJ IDEA.
  User: Дима
  Date: 16.07.2021
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Form</title>
</head>
<body>
<form action="profile-servlet" method="POST">

    <%
        String name = (String) session.getAttribute("user_name");
        String name_value = name == null ? "" : "value="+name;
    %>
    Name: <input name="username" <%=name_value%> />
    <br><br>

    <%
        String age = (String) session.getAttribute("user_phone_number");
        String age_value = age == null ? "" : "value="+age;
    %>
    Age: <input name="userage" <%=age_value%> />
    <br><br>
    Gender: <input type="radio" name="gender" value="female" checked />Female
            <input type="radio" name="gender" value="male" />Male
    <br><br>

    <%
        String phone_number = (String) session.getAttribute("user_phone_number");
        String phone_value = phone_number == null ? "" : "value="+phone_number;
    %>
    Phone Number: <input name="phoneNumber" <%=phone_value%> />
    <br><br>

    Country: <select name="country">
                <option>Russia</option>
                <option>Canada</option>
                <option>Spain</option>
                <option>France</option>
                <option>Germany</option>
            </select>
    <br><br>

    Courses:
    <input type="checkbox" name="courses" value="JavaSE" />Java SE
    <input type="checkbox" name="courses" value="JavaFX" />Java FX
    <input type="checkbox" name="courses" value="JavaEE" />Java EE
    <br><br>

    <input type="submit" value="Submit" />
</form>
</body>
</html>
