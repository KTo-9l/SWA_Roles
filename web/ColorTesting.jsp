<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Color Testing</title>
</head>
<%
    String bgColor = request.getParameter("bgColor");
    Boolean isChosen;
    if (bgColor!=null) {
        isChosen = true;
    } else {
        isChosen = false; bgColor = "GRAY";
    }
%>
<body bgcolor="<%=bgColor%>">
<h1>Color testing</h1>
<%
    if (isChosen) {
        out.println("Chosen background color is " + bgColor + ".");
    } else {
        out.println("Default background color is white ");
    }
%>
</body>
</html>
