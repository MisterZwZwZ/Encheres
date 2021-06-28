<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur"  %>
<% request.setCharacterEncoding("UTF-8"); %>

<html>
<head>
    <title>Login</title>
<%-- FIXME CSS  --%>
    <link type="text/css" rel="stylesheet" href="./styles/loginStyles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<h1>ENI-Enchères</h1>

<form action="${pageContext.request.contextPath}/login" method="POST">
    <div class="mb-3">
        <label class="form-label" for="identifiant">Identifiant :</label>
        <input class="form-control" type="email" id="identifiant" name="email" value="${email}" placeholder="email" required>
        <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
    </div>
    <div class="mb-3">
        <label class="form-label" for="password">Mot de passe :</label>
        <input class="form-control" type="password" id="password" name="motDePasse" placeholder="mot de passe" required>
    </div>
    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="rememberMe">
        <label class="form-check-label" for="rememberMe">Se souvenir de moi</label>
    </div>
    <button type="submit" class="btn btn-primary">Se connecter</button>
</form>


    <a href="${pageContext.request.contextPath}/accueil"><button>Retour</button></a>

<!-- affichage des messages d'erreur éventuels -->
<c:if test="${!empty listeCodesErreur}">
        <strong>Erreur lors de la tentative de connexion :</strong>
        <ul>
            <c:forEach var="code" items="${listeCodesErreur}">
                <li>${LecteurErreur.getMessageErreur(code)}</li>
            </c:forEach>
        </ul>
</c:if>

</body>
</html>
