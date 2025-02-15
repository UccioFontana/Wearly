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
      const submitLogin = document.getElementById("submitLogin");
      const submitRegister = document.getElementById("submitRegister");
      const loginInsteadButton = document.getElementById("loginInsteadButton");

      if (registerFields.style.display === "none" || registerFields.style.display === "") {
        registerFields.style.display = "block";
        loginForm.action = "${pageContext.request.contextPath}/registerServlet";
        registerButton.style.display = "none";
        submitLogin.style.display = "none";
        submitRegister.style.display = "block";
        loginInsteadButton.style.display = "block";
      }
    }

    function resetToLogin() {
      const registerFields = document.getElementById("registerFields");
      const loginForm = document.getElementById("loginForm");
      const registerButton = document.getElementById("registerButton");
      const submitLogin = document.getElementById("submitLogin");
      const submitRegister = document.getElementById("submitRegister");
      const loginInsteadButton = document.getElementById("loginInsteadButton");

      registerFields.style.display = "none";
      loginForm.action = "${pageContext.request.contextPath}/loginServlet";
      registerButton.style.display = "block";
      submitLogin.style.display = "block";
      submitRegister.style.display = "none";
      loginInsteadButton.style.display = "none";
    }
  </script>
</head>
<body>
<div class="container">
  <div class="screen">
    <div class="screen__content">
      <form class="login" id="loginForm" action="${pageContext.request.contextPath}/loginServlet" method="POST" onsubmit=" return validateR()">
        <img id="logo" src="${pageContext.request.contextPath}/images/Wearly_Logo.png" onclick="window.location.href = ('${pageContext.request.contextPath}/home')">
        <div class="login__field">
          <i class="login__icon fas fa-user"></i>
          <input type="email" class="login__input" name="email" placeholder="Email" required>
        </div>
        <div class="login__field">
          <i class="login__icon fas fa-lock"></i>
          <input type="password" class="login__input" name="password" placeholder="Password" required>
        </div>

        <div id="registerFields" style="display: none;">
          <div class="login__field">
            <i class="login__icon fas fa-user"></i>
            <input type="text" class="login__input" name="name" placeholder="Your Name" id="firstName" >
          </div>
          <div class="login__field">
            <i class="login__icon fas fa-lock"></i>
            <input type="text" class="login__input" name="surname" placeholder="Your Surname" id="lastName">
          </div>
        </div>

        <button class="button login__submit" type="submit" id="submitLogin">
          <span class="button__text">Log In Wearly</span>
          <i class="button__icon fas fa-chevron-right"></i>
        </button>

        <button class="button login__submit" type="submit" id="submitRegister" style="display: none;">
          <span class="button__text">Register</span>
          <i class="button__icon fas fa-user-plus"></i>
        </button>

        <button class="button login__submit" type="button" id="loginInsteadButton" style="display: none;" onclick="resetToLogin()">
          <span class="button__text">Login Instead</span>
        </button>

        <button class="button login__submit" type="button" id="registerButton" onclick="toggleRegisterFields()">
          <span class="button__text">Register Instead</span>
        </button>
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
<script src="front-end/js/register.js" defer></script>
</body>
</html>
