<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<% request.setCharacterEncoding("UTF-8"); %>

<html>
<head>
    <title>Login</title>
    <link type="text/css" rel="stylesheet" href="./styles/loginStyles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<h1>ENI-Enchères</h1>
<div class="d-flex justify-content-center text-white">
    <form action="${pageContext.request.contextPath}/login" method="POST">
        <div class="form-outline mb-4" style="width:400px;">
            <label class="form-label" for="identifiant">Identifiant :</label>
            <input class="form-control" type="email" id="identifiant" name="email" value="${email}"
                   placeholder="email"
                   required>
        </div>
        <div class="form-outline mb-4" style="width:400px;">
            <label class="form-label" for="password">Mot de passe :</label>
            <input class="form-control" type="password" id="password" name="motDePasse" placeholder="mot de passe"
                   required>
        </div>
        <div class="form-outline mb-4" style="width:400px;">
            <div class="col d-flex justify-content-center">
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="rememberMe"/>
                </div>
                <label class="form-check-label" for="rememberMe">Se souvenir de moi</label>
            </div>
        </div>
        <div style="width:400px;">
            <button type="submit" class="btn btn-primary" style="width:400px;">Se connecter</button>
        </div>
    </form>
</div>
<div class="d-flex justify-content-center">
    <a href="${pageContext.request.contextPath}/accueil">
        <button class="btn btn-secondary" style="width:400px;">Retour
        </button>
    </a>
</div>


<!-- affichage des messages d'erreur éventuels -->
<c:if test="${!empty listeCodesErreur}">
    <div>
    <strong class="alert alert-danger" role="alert">Erreur lors de la tentative de connexion :</strong>
    </div>
    <div>
    <ul class="alert alert-danger" role="alert">
        <c:forEach var="code" items="${listeCodesErreur}">
            <li>${LecteurErreur.getMessageErreur(code)}</li>
        </c:forEach>
    </ul>
    </div>
</c:if>
</body>
</html>
