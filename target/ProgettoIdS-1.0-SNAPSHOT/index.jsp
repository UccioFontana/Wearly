<%--
  Created by IntelliJ IDEA.
  User: ucciofontana
  Date: 19/01/25
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div style="width: 100vw; height: 100%; display: flex; justify-content: center; align-items: center">
  <div style="transform: scale(1.5)">
    <script src="https://unpkg.com/@dotlottie/player-component@2.7.12/dist/dotlottie-player.mjs" type="module"></script>
    <dotlottie-player src="https://lottie.host/99d1f0db-5d87-4028-a908-7f13a049de22/ULXZKleQeC.lottie" background="transparent" speed="1" style="width: 300px; height: 300px" loop autoplay></dotlottie-player>
  </div>
  </div>
<script src="${pageContext.request.contextPath}/front-end/js/index.js"></script>
</body>
</html>
