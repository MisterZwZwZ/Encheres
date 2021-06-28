<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<html>
<head>
    <title>Encherir</title>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<h1>Détail vente</h1>
<form action="${pageContext.request.contextPath}/encherir" method="POST">

    <p><c:out value="${article.nomArticle}"/></p>
    <p><c:out value="Description : ${article.description}"/></p>
    <p><c:out value="Catégorie : ${article.categorie}"/></p>
    <p><c:out value="${article.categorie}"/></p>
    <p><c:out value="Meilleure offre : ${enchere.MeilleureOffre} par ${utilisateur.pseudo}"/></p>
    <p><c:out value="Mise à prix : ${article.prixInitial} points"/></p>
    <p><c:out value="Fin de l'enchère : ${article.dateFinEnchere}"/></p>
    <p><c:out value="Retrait : ${retrait.rue} "/></p>
    <p><c:out value="${retrait.codePostal} ${retrait.ville}"/></p>
    <p><c:out value="Vendeur : ${utilisateur.pseudo}"/></p>

    <label for="prix">Ma proposition :</label>
    <input type="number" id="prix">
    <input type="submit" value="Encherir">
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
