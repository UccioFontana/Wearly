<%@ page import="model.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Ticket" %><%--
  Created by IntelliJ IDEA.
  User: pietro
  Date: 02/02/25
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-end/css/adminPage.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

    <header>
        <div id="logoContainer">
            <img id="logo" src="${pageContext.request.contextPath}/images/Wearly_Logo.png" onclick="window.location.href = ('${pageContext.request.contextPath}/home')">
        </div>
        <div id="spaceFiller">

        </div>
        <div id="buttonsContainer">
            <button class="defaultButtons" onclick="window.location.href = ('${pageContext.request.contextPath}/authservlet')">Sign In/Up</button>
            <svg onclick="window.location.href = ('toSupportPage')" width="50px" height="50px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M12 19.5C16.1421 19.5 19.5 16.1421 19.5 12C19.5 7.85786 16.1421 4.5 12 4.5C7.85786 4.5 4.5 7.85786 4.5 12C4.5 16.1421 7.85786 19.5 12 19.5ZM12 21C16.9706 21 21 16.9706 21 12C21 7.02944 16.9706 3 12 3C7.02944 3 3 7.02944 3 12C3 16.9706 7.02944 21 12 21ZM12.75 15V16.5H11.25V15H12.75ZM10.5 10.4318C10.5 9.66263 11.1497 9 12 9C12.8503 9 13.5 9.66263 13.5 10.4318C13.5 10.739 13.3151 11.1031 12.9076 11.5159C12.5126 11.9161 12.0104 12.2593 11.5928 12.5292L11.25 12.7509V14.25H12.75V13.5623C13.1312 13.303 13.5828 12.9671 13.9752 12.5696C14.4818 12.0564 15 11.3296 15 10.4318C15 8.79103 13.6349 7.5 12 7.5C10.3651 7.5 9 8.79103 9 10.4318H10.5Z" fill="#30503d"></path> </g></svg>
            <svg onclick="window.location.href = ('${pageContext.request.contextPath}/toWardrobe')" width="42px" height="42px" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg" stroke="#333333" stroke-width="0.00016"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M5 4.5C5 2.567 6.567 1 8.5 1C10.433 1 12 2.567 12 4.5V4.55376C12 5.82905 11.3167 7.00657 10.2094 7.63929L10.0156 7.75005L16 11.1697V15.0001H0V11.1697L9.21712 5.9028C9.70123 5.62617 10 5.11134 10 4.55376V4.5C10 3.67157 9.32843 3 8.5 3C7.67157 3 7 3.67157 7 4.5V5H5V4.5ZM8 8.9018L2 12.3304V13.0001H14V12.3304L8 8.9018Z" fill="#333333"></path> </g></svg>
        </div>
    </header>


    <h1>Welcome <%Utente administrator = (Utente) request.getSession(false).getAttribute("admin");%> <%=administrator.getNome()%> !</h1>
    <button class="defaultButtons" onclick="window.location.href = ('logoutServlet')">Logout</button>




    <h1>Users:</h1>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
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
            <td><input class="defaultButtons" type="submit" value="Invia" onclick="updateUser(<%=u.getId()%>)"> </td>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <button class="defaultButtons" style="margin-top: 20px; margin-bottom: 20px" onclick="showRow(1)"> Add New User</button>
    <table style="margin-bottom: 30px">
        <tbody>
        <tr id="addRow1" class="hiddenRow" >
            <td><input type="text" id="nome" placeholder="nome"></td>
            <td><input type="text" id="cognome" placeholder="cognome"></td>
            <td><input type="email" id="email" placeholder="email"></td>
            <td><input type="password" id="password" placeholder="password"></td>
            <td><input type="submit" value="Aggiungi" onclick="addUser()" class="defaultButtons"> </td>
        </tr>
        </tbody>

    </table>

    <h1>All Tickets:</h1>
    <%
        List<Ticket> tickets = (List<Ticket>) request.getAttribute("ticket");// Lista di tutti i ticket
        if(!tickets.isEmpty())  {%>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>State</th>
            <th>Creation Date</th>
            <th>User ID</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Ticket t : tickets) {
        %>
        <tr>
            <td><%= t.getId() %></td>
            <td><%= t.getTitolo() %></td>
            <td><%= t.getDescrizione() %></td>
            <td><%= t.getStato() %></td>
            <td><%= t.getDataCreazione() %></td>
            <td><%= t.getIdUtente() %></td>
            <form action="adminTicketServlet" method="post">
                <input type="hidden" name="idT" value="<%= t.getId() %>">
                <input type="hidden" name="type" value="claim">
                <td class="deleteButton"><input type="submit" value="Claim Ticket" ></td>
            </form>
        </tr>
        <% }}else{ %>
        <p>No open tickets.</p>
        <% }%>
        </tbody>
    </table>

    <br>

    <h1>Your Managed Tickets:</h1>
    <%
        List<Ticket> adminTickets = (List<Ticket>) request.getAttribute("ticketAdmin"); // Lista dei ticket gestiti dall'amministratore
        if(!adminTickets.isEmpty()){
    %>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>State</th>
            <th>Creation Date</th>
            <th>Admin ID</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Ticket t : adminTickets) {
        %>
        <tr>
            <td><%= t.getId() %></td>
            <td><%= t.getTitolo() %></td>
            <td><%= t.getDescrizione() %></td>
            <td><%= t.getStato() %></td>
            <td><%= t.getDataCreazione() %></td>
            <td><%= t.getIdAmministratore() %></td>
            <form action="adminTicketServlet" method="post">
                <input type="hidden" name="idT" value="<%= t.getId() %>">
                <input type="hidden" name="type" value="close">
                <td class="deleteButton"><input type="submit" value="Close Ticket" onclick=""></td>
            </form>


        </tr>
        <% } }else{%>
        <p>No managed tickets.</p>
        <% }%>
        </tbody>
    </table>


<br><br><br>

    <script src="front-end/js/admin.js" defer></script>

</body>
</html>
