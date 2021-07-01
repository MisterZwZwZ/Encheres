<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <link type="text/css" rel="stylesheet" href="./styles/accueilStyles.css"/>
    <title>Accueil</title>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<div class="container py-2">
    <div class="row align-items-center">
        <form class="d-flex justify-content-center row" action="${pageContext.request.contextPath}/recherche"
              method="POST">
            <div class="row m-6 py-4">
                <div class="col-6">
                    <label class="text-white form-check-label py-2" for="recherche-article">Recherche par mots
                        clés</label>
                    <input class="form-control me-2" type="search" id="recherche-article" name="rechercheParMotClef"
                           placeholder="que recherchez vous ?..."
                           value="${motclef}">
                </div>
                <div class="col-6">
                    <label class="text-white form-check-label py-2" for="categorie">Catégories</label>
                    <select class="form-select" name="rechercheParcategorie" id="categorie">
                        <option value="">--"Toutes"--</option>
                        <c:forEach items="${applicationScope.listeDesCategories}" var="categorie">
                            <option value="${categorie.key}" ${categorieChoisie == categorie.value ? "selected" : "" }>${categorie.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="text-white d-flex flex-row-reverse justify-content-between py-4">
                    <div class="">
                        <input class="btn btn-primary" type="submit" value="Rechercher">
                    </div>
                    <div class="col">
                        <div class="d-flex align-items-center">
                            <c:if test="${sessionScope.utilisateur != null}">
                            <!--radio boutons-->
                            <div class="col-4">
                                <div class="form-check form-check-inline">
                                    <input type="radio" class="form-check-input" name="achatOuVente" id="achat"
                                           autocomplete="off"
                                           checked
                                           value="achat">
                                    <label class="form-check-label" for="achat">Achats</label>
                                    <!-- checkboxes-->
                                    <div class="form-check form-switch">
                                        <input type="checkbox" class="form-check-input" id="encheresOuvertes"
                                               autocomplete="off"
                                               name="encheresOuvertes" checked>
                                        <label class="form-check-label" for="encheresOuvertes">Enchères ouvertes</label>
                                    </div>
                                    <div class="form-check form-switch">
                                        <input type="checkbox" class="form-check-input" id="mesEncheresEnCours"
                                               autocomplete="off"
                                               name="mesEncheresEnCours">
                                        <label class="form-check-label" for="mesEncheresEnCours">Mes enchères en
                                            cours</label>
                                    </div>
                                    <div class="form-check form-switch">
                                        <input type="checkbox" class="form-check-input" id="encheresRemportees"
                                               autocomplete="off"
                                               name="encheresRemportees">
                                        <label class="form-check-label" for="encheresRemportees">Mes enchères
                                            remportées</label>
                                    </div>
                                </div>
                            </div>
                            <!--radio boutons-->
                            <div class="col-4">
                                <input type="radio" class="form-check-input" name="achatOuVente" id="vente"
                                       autocomplete="off"
                                       checkedinput value="vente"
                                >
                                <label class="form-check-label" for="vente">Ventes</label>
                                <!-- checkboxes-->
                                <div class="form-check form-switch">
                                    <input type="checkbox" class="form-check-input" id="ventesEnCours"
                                           autocomplete="off"
                                           name="ventesEnCours"
                                    >
                                    <label class="form-check-label" for="ventesEnCours">Mes ventes en cours</label>
                                </div>
                                <div class="form-check form-switch">
                                    <input type="checkbox" class="form-check-input" id="ventesNonDebutees"
                                           autocomplete="off"
                                           name="ventesNonDebutees"
                                    >
                                    <label class="form-check-label" for="ventesNonDebutees">Ventes non débutées</label>
                                </div>
                                <div class="form-check form-switch">
                                    <input type="checkbox" class="form-check-input" id="ventesTerminees"
                                           autocomplete="off"
                                           name="ventesTerminees"
                                    >
                                    <label class="form-check-label" for="ventesTerminees">Ventes terminées</label>
                                </div>
                            </div>
                        </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </form>
        <div class="d-flex justify-content-center row">
            <div class="row m-6">
                <c:choose>
                <c:when test="${listeArticles.size()>0}">
                <c:forEach items="${listeArticles}" var="article">
                <div class="col-3">
                    <div class="card my-4" style="width: 18rem; height: auto">
                    <div class="card-body">
                        <h5 class="card-title text-center"><c:out value="${article.nomArticle}"/></h5>
                        <p class="card-text"><c:out value="Description : ${article.description}"/></p>
                        <p class="card-text"><c:out
                                value="Prix de vente : ${article.prixVente == 0 ? article.prixInitial : article.prixVente} points"/></p>
                        <p class="card-text"><c:out
                                value="Date de fin d'enchère : ${article.dateFinEnchere}"/></p>

                        <c:choose>
                            <c:when test="${sessionScope.utilisateur == null }">
                                <p class="card-text"><c:out value="Vendeur : ${article.vendeur.pseudo}"/></p>
                            </c:when>
                            <c:when test="${sessionScope.utilisateur != null }">
                                <a href="${pageContext.request.contextPath}/profil?pseudo=${article.vendeur.pseudo}">
                                    <p class="card-text"><c:out
                                            value="Vendeur : ${article.vendeur.pseudo}"/></p>
                                </a>
                            </c:when>
                        </c:choose>

                            <div class="text-center py-2">
                                <a href="<%=request.getContextPath()%>/enchere?noarticle=${article.noArticle}"
                                   class="btn btn-primary">Voir l'annonce</a>
                            </div>
                    </div>
                </div>
            </div>
            </c:forEach>
            </c:when>
            <c:when test="${!empty listeCodesErreur}">
                <!-- affichage des messages d'erreur éventuels -->
                <p class="alert alert-danger" role="alert"><strong>Erreur lors de la recherche :</strong></p>
                <ul class="alert alert-danger" role="alert">
                    <c:forEach var="code" items="${listeCodesErreur}">
                        <li>${LecteurErreur.getMessageErreur(code)}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p class="alert alert-info" role="alert">Choisissez les articles à afficher</p>
            </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</div>

<script language="JavaScript" type="text/javascript" src="js\searchButtons.js"></script>
</body>
</html>
