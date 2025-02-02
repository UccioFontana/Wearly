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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-end/css/adminPage.css">
</head>
<body>




<h1>Utenti:</h1>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Nome</th>
        <th>Cognome</th>
        <th>email</th>
    </tr>
    </thead>
    <tbody>
    <% List<Utente> users = (List<Utente>) request.getAttribute("users");
        for (Utente u: users) { %>
    <tr>
        <td><%= u.getId() %></td>
        <td><%= u.getNome() %></td>
        <td><%= u.getCognome() %></td>
        <td> <%=u.getEmail()%></td>
        <td class="updateButton"><input type="submit" value="edit" onclick="updateRow(<%=u.getId()%>)"></td>
        <td class="deleteButton"><input type="submit" value="delete" onclick="deleteUser(<%=u.getId()%>)"></td>
    </tr>

    <tr id="update<%=u.getId()%>" class="hiddenRow" >
        <td colspan="1">
        <td><input type="text" id="n<%=u.getId()%>" placeholder="nome"></td>
        <td><input type="text" id="c<%=u.getId()%>" placeholder="cognome"></td>
        <td><input type="email" id="e<%=u.getId()%>" placeholder="email"></td>
        <td><input type="submit" value="Invia" onclick="updateUser(<%=u.getId()%>)"> </td>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
<h2 onclick="showRow(1)"> add</h2>
<table>
    <tbody>
    <tr id="addRow1" class="hiddenRow">
        <td><input type="text" id="nome" placeholder="nome"></td>
        <td><input type="text" id="cognome" placeholder="cognome"></td>
        <td><input type="email" id="email" placeholder="email"></td>
        <td><input type="password" id="password" placeholder="password"></td>
        <td><input type="submit" value="Aggiungi" onclick="addUser()"> </td>
    </tr>
    </tbody>

</table>

<script src="front-end/js/admin.js" defer></script>

</body>
</html>
