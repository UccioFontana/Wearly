
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-end/css/login.css">
</head>
<body>
<div class="container">
  <div class="screen">
    <div class="screen__content">
      <form class="login">
        <img id="logo" src="${pageContext.request.contextPath}/images/Wearly_Logo.png">
        <div class="login__field">
          <i class="login__icon fas fa-user"></i>
          <input type="text" class="login__input" placeholder="User name / Email">
        </div>
        <div class="login__field">
          <i class="login__icon fas fa-lock"></i>
          <input type="password" class="login__input" placeholder="Password">
        </div>
        <button class="button login__submit">
          <span class="button__text">Log In Wearly</span>
          <i class="button__icon fas fa-chevron-right"></i>
        </button>
        <button class="button login__submit">
          <span class="button__text">Create An Account</span>
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
