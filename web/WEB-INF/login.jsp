<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur"  %>
<% request.setCharacterEncoding("UTF-8"); %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>ENI-Enchères</h1>

<form action="${pageContext.request.contextPath}/login" method="POST">
    <label for="identifiant">Identifiant :</label>
    <input type="email" id="identifiant" name="email">
    <label for="password">Mot de passe :</label>
    <input type="password" id="password" name="motDePasse">
    <label for="rememberMe">Se souvenir de moi</label>
    <input type="checkbox" id="rememberMe">

    <button>Se connecter</button>
</form>
<c:if test="${!empty listeCodesErreur}">

        <strong>Erreur!</strong>
        <ul>
            <c:forEach var="code" items="${listeCodesErreur}">
                <li>${LecteurErreur.getMessageErreur(code)}</li>
            </c:forEach>
        </ul>

</c:if>
</body>
</html>
