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
        <input type="text" name="pseudo" id="pseudo" placeholder="pseudo" value="${pseudo}">
    <label for="prenom">Prénom :</label>
        <input type="text" name="prenom" id="prenom" placeholder="prenom" value="${prenom}">
    <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" placeholder="nom" value="${nom}">
    <label for="email">Email :</label>
        <input type="email" name="email" id="email" placeholder="email" value="${email}">
    <label for="telephone">Téléphone :</label>
        <input type="tel" name="telephone" id="telephone" placeholder="telephone" value="${telephone}">
    <label for="rue">Rue :</label>
        <input type="text" name="rue" id="rue" placeholder="rue" value="${rue}">
    <label for="codepostal">Code postal :</label>
        <input type="text" name="cp" id="codepostal" placeholder="code postal" value="${cp}">
    <label for="ville">Ville :</label>
        <input type="text" name="ville" id="ville" placeholder="ville" value="${ville}">
    <label for="pass">Mot de passe :</label>
        <input type="password" name="password" id="pass" placeholder="mot de passe">
    <label for="confpass">Confirmation  :</label>
        <input type="password" name="passwordConf" id="confpass" placeholder="confirmez le mot de passe">

    <input type="submit" value="Créer">
    <input type="submit" value="Annuler">
</form>


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
