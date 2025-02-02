<%@ page import="model.Utente" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: pietro
  Date: 02/02/25
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>testttttt</p>

<% List<Utente> list= (List<Utente>) request.getAttribute("users");
for(Utente u : list){
%>
    <p><%= u.getId()%></p>

<%}%>

</body>
</html>
