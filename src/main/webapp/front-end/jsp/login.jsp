<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/front-end/css/login.css">
  <script>
    function toggleRegisterFields() {
      const registerFields = document.getElementById("registerFields");
      const loginForm = document.getElementById("loginForm");
      const registerButton = document.getElementById("registerButton");

      if (registerFields.style.display === "none" || registerFields.style.display === "") {
        registerFields.style.display = "block";
        loginForm.action = "${pageContext.request.contextPath}/registerServlet";
        registerButton.style.display = "none";
      }
    }

    function resetToLogin(event) {
      const registerFields = document.getElementById("registerFields");
      const loginForm = document.getElementById("loginForm");
      const registerButton = document.getElementById("registerButton");

      if (registerFields.style.display === "block") {
        event.preventDefault(); // Evita l'invio del form
        registerFields.style.display = "none";
        loginForm.action = "${pageContext.request.contextPath}/loginServlet";
        registerButton.style.display = "block";
      }
    }
  </script>
</head>
<body>
<div class="container">
  <div class="screen">
    <div class="screen__content">
      <form class="login" id="loginForm" action="${pageContext.request.contextPath}/loginServlet" method="POST">
        <img id="logo" src="${pageContext.request.contextPath}/images/Wearly_Logo.png" onclick="window.location.href = ('${pageContext.request.contextPath}/home')">
        <div class="login__field">
          <i class="login__icon fas fa-user"></i>
          <input type="text" class="login__input" name="email" placeholder="User name / Email">
        </div>
        <div class="login__field">
          <i class="login__icon fas fa-lock"></i>
          <input type="password" class="login__input" name="password" placeholder="Password">
        </div>

        <div id="registerFields" style="display: none;">
          <div class="login__field">
            <i class="login__icon fas fa-user"></i>
            <input type="text" class="login__input" name="fullName" placeholder="Full Name">
          </div>
          <div class="login__field">
            <i class="login__icon fas fa-lock"></i>
            <input type="password" class="login__input" name="confirmPassword" placeholder="Confirm Password">
          </div>
        </div>

        <button class="button login__submit" type="submit" onclick="resetToLogin(event)">
          <span class="button__text">Log In Wearly</span>
          <i class="button__icon fas fa-chevron-right"></i>
        </button>

        <button class="button login__submit" type="button" id="registerButton" onclick="toggleRegisterFields()">
          <span class="button__text">Non hai un account? Registrati</span>
        </button>

        <h2 class="backHomepage" onclick="window.location.href = 'home'">Back Homepage</h2>
      </form>
    </div>
    <div class="screen__background">
      <span class="screen__background__shape screen__background__shape4"></span>
      <span class="screen__background__shape screen__background__shape3"></span>
      <span class="screen__background__shape screen__background__shape2"></span>
      <span class="screen__background__shape screen__background__shape1"></span>
    </div>
  </div>
</div>
</body>
</html>
