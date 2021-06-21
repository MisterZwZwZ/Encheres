<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>ENI-Enchères</h1>

<form action="${pageContext.request.contextPath}/login" method="POST">
    <label for="identifiant">Identifiant :</label>
    <input type="text" id="identifiant" name="identifiant" value="<c:out value="${utilisateur.email}"/>">
    <label for="password">Mot de passe :</label>
    <input type="password" id="password" name="motDePasse" value="<c:out value="${utilisateur.motDePasse}"/>">
    <input type="checkbox" id="rememberMe">
    <label for="rememberMe">Se souvenir de moi</label>
    <input type="submit" value="Login">
</form>

</body>
</html>
