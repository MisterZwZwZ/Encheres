<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<% request.setCharacterEncoding("UTF-8"); %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="styles/initialize.min.css">
    <link type="text/css" rel="stylesheet" href="./styles/styles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<h1 class="text-white text-center">Connexion</h1>

<div class="d-flex justify-content-center text-white py-4">
    <div class="row w-25">
        <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="form-outline mb-4 w-100">
                <label class="form-label" for="identifiant">Identifiant :</label>
                <input class="form-control" type="email" id="identifiant" name="email" value="${email}"
                       placeholder="email" required>
            </div>
            <div class="form-outline mb-4 w-100">
                <label class="form-label" for="password">Mot de passe :</label>
                <input class="form-control" type="password" id="password" name="motDePasse" placeholder="mot de passe"
                       required>
            </div>
            <div class="form-outline mb-4 w-100">
                <div class="col d-flex justify-content-center">
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="rememberMe" disabled/>
                    </div>
                    <label class="form-check-label" for="rememberMe">Se souvenir de moi</label>
                </div>
            </div>
            <div>
                <button class="btn btn-primary w-100" type="submit">Se connecter</button>
            </div>
        </form>
        <div>
            <!-- affichage des messages d'erreur éventuels -->
            <c:if test="${!empty listeCodesErreur}">
                <p class="alert alert-danger" role="alert"><strong>Erreur(s) lors de la tentative de connexion :</strong></p>
                <ul class="alert alert-danger text-center" role="alert">
                    <c:forEach var="code" items="${listeCodesErreur}">
                        <li>${LecteurErreur.getMessageErreur(code)}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </div>
</div>

<div class="d-flex justify-content-center">
    <a href="${pageContext.request.contextPath}/accueil">
        <button class="btn btn-secondary w-100">Retour</button>
    </a>
</div>

<div class="d-flex justify-content-center">
    <a href="${pageContext.request.contextPath}/inscription">
        <button class="btn btn-link">Pas encore de compte ? M'inscrire</button>
    </a>
</div>

</body>
</html>
