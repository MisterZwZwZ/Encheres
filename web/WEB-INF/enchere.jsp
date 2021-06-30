<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="./styles/enchereStyles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Enchère</title>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<c:choose>
    <c:when test="${etatVente == 'en cours'}">
        <h1 class="text-white text-center">Détail vente</h1>
    </c:when>
    <c:when test="${etatVente == 'terminee'}">
        <c:if test="${statutUtilisateur == 'acquereur'}">
            <h1>Vous avez remporté l'enchère !</h1>
        </c:if>
        <c:if test="${statutUtilisateur == 'vendeur'}">
            <h1>${enchere.encherisseur.pseudo} a remporté la vente</h1>
        </c:if>
    </c:when>
</c:choose>

<div class="d-flex justify-content-center py-2">
    <div class="row">
        <div class="col-6">
            <div class="card my-4" style="width: 30rem; height: auto;">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/enchere" method="POST">
                        <h5 class="card-title text-center"><c:out value="${article.nomArticle}"/></h5>
                        <p class="card-text"><c:out value="Description : ${article.description}"/></p>
                        <p class="card-text"><c:out value="Catégorie : ${article.categorie.libelle}"/></p>
                        <p class="card-text"><c:out value="Meilleure offre : ${enchere.montantEnchere} par ${enchere.encherisseur.pseudo}"/></p>
                        <p class="card-text"><c:out value="Mise à prix : ${article.prixInitial} points"/></p>
                        <c:if test="${statutUtilisateur == 'vendeur' && etatVente == 'pas demarree'}">
                            <c:out value="l'enchère débutera le : ${article.dateDebutEnchere}"></c:out>
                        </c:if>
                        <p class="card-text"><c:out value="Fin de l'enchère : ${article.dateFinEnchere}"/></p>
                        <p class="card-text"><c:out value="Retrait : ${retrait.rue} "/></p>
                        <p class="card-text"><c:out value="${retrait.codePostal} ${retrait.ville}"/></p>
                        <p class="card-text"><c:out value="Vendeur : ${article.vendeur.pseudo}"/></p>
                        <c:choose>
                            <c:when test="${etatVente == 'terminee'}">
                                <c:if test="${statutUtilisateur == 'acquereur'}">
                                    <p class="card-text"><c:out value="Tel : ${article.vendeur.telephone}"/></p>
                                </c:if>
                            </c:when>
                        </c:choose>

                        <c:if test="${statutUtilisateur == 'meilleurEncherisseur' && etatVente == 'en cours'}">
                            <p><c:out value="Vous êtes le meilleur encherisseur pour le moment"/></p>
                        </c:if>

                        <c:if test="${statutUtilisateur == 'encherisseur' && etatVente == 'en cours'}">
                            <label class="col-form-label" for="prix">Ma proposition :</label>
                            <input class="form-control" type="number" id="prix" name="prix">
                            <input type="hidden" name="noarticle" value="${article.noArticle}">
                            <button class="btn btn-primary w-100" type="submit">Encherir</button>
                        </c:if>

                        <c:if test="${statutUtilisateur == 'vendeur' && etatVente == 'en cours'}">
                            <c:out value="Votre article est en vente et les enchères sont en cours"/>
                        </c:if>

                        <c:if test="${statutUtilisateur == 'vendeur' && etatVente == 'pas demarree'}">
                            <a href="<%=request.getContextPath()%>/accueil"><button disabled>modifier ma vente</button></a>
                        </c:if>
                    </form>
                </div>
                <a href="<%=request.getContextPath()%>/accueil"><button class="btn btn-secondary w-100">Annuler</button></a>
            </div>
        </div>
    </div>
</div>


<!-- affichage des messages d'erreur éventuels -->
<c:if test="${!empty listeCodesErreur}">
    <p class="alert alert-danger" role="alert"><strong>Erreur !</strong></p>
    <ul>
        <c:forEach var="code" items="${listeCodesErreur}">
            <li class="alert alert-danger" role="alert">${LecteurErreur.getMessageErreur(code)}</li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>
