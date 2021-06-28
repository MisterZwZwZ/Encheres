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
    <input type="text" name="nomArticle" id="nomArticle" value="${nomArticle}">
    <label for="description">Description</label>
    <input type="textarea" name="description" id="description" rows="5" cols="33" value="${description}">

    <label for="categorie">Catégorie</label>
    <select name="rechercheParcategorie" id="categorie">
        <option value="">--${categorie != null? categorie:"Toutes"}--</option>
        <c:forEach items="${applicationScope.listeDesCategories}" var="categorie">
            <option value="${categorie.key}">${categorie.value}</option>
        </c:forEach>
    </select>

    <label for="prixInitial">Mise à prix</label>
    <input type="number" name="prixInitial" id="prixInitial" value="${prixInitial}">
    <label for="dateDebutEnchere">Début de l'enchère</label>
    <input type="date" name="dateDebutEnchere" id="dateDebutEnchere">
    <label for="dateFinEnchere">Fin de l'enchère</label>
    <input type="date" name="dateFinEnchere" id="dateFinEnchere">

    <fieldset>
        <legend>Retrait</legend>
        <label for="rue">Rue :</label>
        <input type="text" name="rue" id="rue" value="${sessionScope.utilisateur.rue}"><br/>
        <label for="codePostal">Code Postal :</label>
        <input type="text" name="codePostal" id="codePostal" value="${sessionScope.utilisateur.codePostal}"><br/>
        <label for="ville">Ville :</label>
        <input type="text" name="ville" id="ville"  value="${sessionScope.utilisateur.ville}">
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
</body>
</html>
