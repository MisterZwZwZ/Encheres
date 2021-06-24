<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<html>
<head>
    <title>Vente</title>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<h1>Nouvelle vente</h1>
<form action="${pageContext.request.contextPath}/vendre" method="POST">
    <label for="nomArticle">Article</label>
    <input type="text" name="nomArticle" id="nomArticle">
    <label for="description">Description</label>
    <input type="textarea" name="description" id="description" rows="5" cols="33">

    <label for="categories">Catégorie</label>
    <select name="categories" id="categories">
        <option value="1">Informatique</option>
        <option value="2">Ameublement</option>
        <option value="3">Vêtements</option>
        <option value="4">Sports & Loisirs</option>
    </select>

    <label for="prixInitial">Mise à prix</label>
    <input type="number" name="prixInitial" id="prixInitial">
    <label for="dateDebutEnchere">Début de l'enchère</label>
    <input type="date" name="dateDebutEnchere" id="dateDebutEnchere" >
    <label for="dateFinEnchere">Fin de l'enchère</label>
    <input type="date" name="dateFinEnchere" id="dateFinEnchere">

    <fieldset>
        <legend>Retrait</legend>
        <label for="rue">Rue :</label>
        <input type="text" name="rue" id="rue"><br/>
        <label for="codePostal">Code Postal :</label>
        <input type="text" name="codePostal" id="codePostal"><br/>
        <label for="ville">Ville :</label>
        <input type="text" name="ville" id="ville">
    </fieldset>
    <input type="submit" value="Enregistrer">
    <a href="<%=request.getContextPath()%>/accueil">Annuler</a>
</form>

<!-- affichage des messages d'erreur éventuels -->
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
