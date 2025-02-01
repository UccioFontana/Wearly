<%@ page import="model.CapoAbbigliamento" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-end/css/userPage.css">
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
            <svg width="50px" height="50px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M12 19.5C16.1421 19.5 19.5 16.1421 19.5 12C19.5 7.85786 16.1421 4.5 12 4.5C7.85786 4.5 4.5 7.85786 4.5 12C4.5 16.1421 7.85786 19.5 12 19.5ZM12 21C16.9706 21 21 16.9706 21 12C21 7.02944 16.9706 3 12 3C7.02944 3 3 7.02944 3 12C3 16.9706 7.02944 21 12 21ZM12.75 15V16.5H11.25V15H12.75ZM10.5 10.4318C10.5 9.66263 11.1497 9 12 9C12.8503 9 13.5 9.66263 13.5 10.4318C13.5 10.739 13.3151 11.1031 12.9076 11.5159C12.5126 11.9161 12.0104 12.2593 11.5928 12.5292L11.25 12.7509V14.25H12.75V13.5623C13.1312 13.303 13.5828 12.9671 13.9752 12.5696C14.4818 12.0564 15 11.3296 15 10.4318C15 8.79103 13.6349 7.5 12 7.5C10.3651 7.5 9 8.79103 9 10.4318H10.5Z" fill="#30503d"></path> </g></svg>
            <svg onclick="window.location.href = ('${pageContext.request.contextPath}/toWardrobe')" width="42px" height="42px" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg" stroke="#333333" stroke-width="0.00016"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M5 4.5C5 2.567 6.567 1 8.5 1C10.433 1 12 2.567 12 4.5V4.55376C12 5.82905 11.3167 7.00657 10.2094 7.63929L10.0156 7.75005L16 11.1697V15.0001H0V11.1697L9.21712 5.9028C9.70123 5.62617 10 5.11134 10 4.55376V4.5C10 3.67157 9.32843 3 8.5 3C7.67157 3 7 3.67157 7 4.5V5H5V4.5ZM8 8.9018L2 12.3304V13.0001H14V12.3304L8 8.9018Z" fill="#333333"></path> </g></svg>
        </div>
    </header>

    <div id="welcomeBackContainer">
        <h1 id="welcomeBackText" style="color: white"> <!--data-username="<%= request.getAttribute("utente") %>"--></h1>
        <div id="lineContainer"></div>
        <h2 id="welcomeBackSubtitle">In this page you can find your profile details.</h2>
    </div>

    <div id="outerContainer">
        <div id="innerContainerLeft">
            <div id="sectionHeadingContainer">
                <div id="sectionHeadingInner">
                    <h1 class="sectionHeading"> My account</h1>
                </div>
            </div>

            <div class="doubleInputContainer">
                <div class="doubleInputInner">
                    <div class="Input">
                        <h1 class="sectionHeading">Nome</h1>
                        <input type="text" placeholder="Uccio">
                    </div>
                    <div class="Input">
                        <h1 class="sectionHeading">Cognome</h1>
                        <input type="text" placeholder="Fontana">
                    </div>
                </div>
            </div>

            <div class="doubleInputContainer">
                <div class="doubleInputInner">
                    <div class="Input">
                        <h1 class="sectionHeading">Email</h1>
                        <input type="email" placeholder="example@example.com">
                    </div>
                    <div class="Input">
                        <h1 class="sectionHeading">Password</h1>
                        <input type="password">
                    </div>
                </div>
            </div>

            <div class="doubleInputContainer">
                <div class="doubleInputInner">
                    <div id="saveButtonContainer">
                        <input type="submit" value="Save">
                    </div>
                </div>
            </div>

            <div class="doubleInputContainer">
                <div class="doubleInputInner">

                </div>
            </div>


        </div>


        <div id="innerContainerRight">
            <div id="profileImage"></div>
            <div id="profileDetails">
                <div class="profileDetailsElement">
                    <h1 class="elementCounter">15</h1>
                    <h2 class="elementName">Clothes</h2>
                </div>
                <div class="profileDetailsElement">
                    <h1 class="elementCounter">3</h1>
                    <h2 class="elementName">Outfits</h2>
                </div>
            </div>
        </div>
    </div>

    <div id="infoCapiAbbigliamento">

        <h3 style="color: #2C2C2C;font-size: 1.3em;">Informazioni Capi d'Abbigliamento</h3>
        <% List<CapoAbbigliamento> capiAbbigliamento = (List<CapoAbbigliamento>) request.getAttribute("listaCapi"); %>
        <% if (capiAbbigliamento == null || capiAbbigliamento.isEmpty()) { %>
        <span style="text-align: center">Non ci sono capi d'abbigliamento disponibili per questo utente</span>
        <% } else { %>

        <table>
            <thead>
            <tr>
                <th>Nome</th>
                <th>Descrizione</th>
                <th>Materiale</th>
                <th>Colore</th>
                <th>Stile</th>
                <th>Stagione</th>
                <th>Immagine</th>
                <th>Categoria</th>
                <th>Parte del Corpo</th>
            </tr>
            </thead>
            <tbody>
            <% for (CapoAbbigliamento capo : capiAbbigliamento) { %>
            <tr>
                <td><%= capo.getNome() %></td>
                <td><%= capo.getDescrizione() %></td>
                <td><%= capo.getMateriale() %></td>
                <td><%= capo.getColore() %></td>
                <td><%= capo.getStile() %></td>
                <td><%= capo.getStagione() %></td>
                <td><img src="<%= capo.getImmagine() %>" alt="<%= capo.getNome() %>" width="50" height="50"/></td>
                <td><%= capo.getCategoria() %></td>
                <td><%= capo.getParteDelCorpo() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>

        <% } %>
    </div>









    <script src="../js/userPage.js"></script>
</body>
</html>
