<%--
  Created by IntelliJ IDEA.
  User: student
  Date: 24.10.2017
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="login" method="post">
    Enter name: <input type="text" name="login"><br>
    Enter password: <input type="password" name="pass"><br>
    <input type="submit" value="login"><br>

    New in? Please, <a href="register.jsp">register!</a><br>
    Wanna check a colors? <a href="ColorTesting.jsp">Click it</a><br>
</form>
<form action="getUsers" method="get">
    Test our users db: <input type="submit" value="Click me">
</form>
</body>
</html>
