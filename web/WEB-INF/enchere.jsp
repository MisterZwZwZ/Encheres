<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<html>
<head>
    <title>Enchère</title>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<c:choose>
    <c:when test="${etatVente == 'en cours'}">
        <h1>Détail vente</h1>
    </c:when>
    <c:when test="${etatVente == 'terminee'}">
        <c:if test="${statutUtilisateur == 'acquereur'}">
            <h1>Vous avez remporté l'enchère</h1>
        </c:if>
        <c:if test="${statutUtilisateur == 'vendeur'}">
            <h1>${enchere.encherisseur.pseudo} a remporté la vente</h1>
        </c:if>
    </c:when>
</c:choose>
<form action="${pageContext.request.contextPath}/enchere" method="POST">
    <p><c:out value="${article.nomArticle}"/></p>
    <p><c:out value="Description : ${article.description}"/></p>
    <p><c:out value="Catégorie : ${article.categorie.libelle}"/></p>
    <p><c:out value="Meilleure offre : ${enchere.montantEnchere} par ${enchere.encherisseur.pseudo}"/></p>
    <p><c:out value="Mise à prix : ${article.prixInitial} points"/></p>
    <c:if test="${statutUtilisateur == 'vendeur' && etatVente == 'pas demarree'}">
        <c:out value="l'enchère débutera le : ${article.dateDebutEnchere}"></c:out>
    </c:if>
    <p><c:out value="Fin de l'enchère : ${article.dateFinEnchere}"/></p>
    <p><c:out value="Retrait : ${retrait.rue} "/></p>
    <p><c:out value="${retrait.codePostal} ${retrait.ville}"/></p>
    <p><c:out value="Vendeur : ${article.vendeur.pseudo}"/></p>
    <c:choose>
        <c:when test="${etatVente == 'terminee'}">
            <c:if test="${statutUtilisateur == 'acquereur'}">
                <p><c:out value="Tel : ${article.vendeur.telephone}"/></p>
            </c:if>
        </c:when>
    </c:choose>

    <c:if test="${statutUtilisateur == 'meilleurEncherisseur' && etatVente == 'en cours'}">
        <c:out value="Vous êtes le meilleur encherisseur pour le moment"/>
    </c:if>

    <c:if test="${statutUtilisateur == 'encherisseur' && etatVente == 'en cours'}">
        <label for="prix">Ma proposition :</label>
        <input type="number" id="prix" name="prix">
        <input type="hidden" name="noarticle" value="${article.noArticle}">
        <input type="submit" value="Encherir">
    </c:if>
    <c:if test="${!empty messageConf}">
        <p>Votre enchère a bien été enregistrée !</p>
    </c:if>

    <c:if test="${statutUtilisateur == 'vendeur' && etatVente == 'en cours'}">
        <c:out value="Votre article est en vente et les enchères sont en cours"/>
    </c:if>

</form>

<c:if test="${statutUtilisateur == 'vendeur' && etatVente == 'pas demarree'}">
    <a href="<%=request.getContextPath()%>/modifierVente?noArticle=${article.noArticle}">
        <button>Modifier ma vente</button>
    </a>
</c:if>


<a href="<%=request.getContextPath()%>/accueil">
    <button>Retour</button>
</a>

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
