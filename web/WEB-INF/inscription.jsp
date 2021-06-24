<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>


<html>
<head>
    <title>Inscription</title>
</head>
<body>
<h1>ENI-Enchères</h1>

<h2>Inscription</h2>

<form action="${pageContext.request.contextPath}/inscription" method="post">
    <label for="pseudo">Pseudo :</label>
        <input type="text" name="pseudo" id="pseudo" placeholder="pseudo" value="${pseudo}" required>
    <label for="prenom">Prénom :</label>
        <input type="text" name="prenom" id="prenom" placeholder="prenom" value="${prenom}" required>
    <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" placeholder="nom" value="${nom}" required>
    <label for="email">Email :</label>
        <input type="email" name="email" id="email" placeholder="email" value="${email}" required>
    <label for="telephone">Téléphone :</label>
        <input type="tel" name="telephone" id="telephone" placeholder="telephone" value="${telephone}">
    <label for="rue">Rue :</label>
        <input type="text" name="rue" id="rue" placeholder="rue" value="${rue}" required>
    <label for="codepostal">Code postal :</label>
        <input type="text" name="cp" id="codepostal" placeholder="code postal" value="${cp}" required>
    <label for="ville">Ville :</label>
        <input type="text" name="ville" id="ville" placeholder="ville" value="${ville}" required>
    <label for="pass">Mot de passe :</label>
        <input type="password" name="password" id="pass" placeholder="mot de passe" required title="le mot de passe doit contenir au moins 1 minuscule, 1 majuscule, 1 chiffre">
    <label for="confpass">Confirmation  :</label>
        <input type="password" name="passwordConf" id="confpass" placeholder="confirmez le mot de passe" required>

    <input type="submit" value="Créer">
</form>

<a href="${pageContext.request.contextPath}/accueil"><button>Annuler</button></a>

<c:if test="${!empty listeCodesErreur}">
    <div class="alert alert-danger" role="alert">
        <strong>Erreur !</strong>
        <ul>
            <c:forEach var="code" items="${listeCodesErreur}">
                <li>${LecteurErreur.getMessageErreur(code)}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

</body>
</html>
