<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Accueil</title>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<p>Filtres</p>

<div class="container">

    <form class="d-flex justify-content-center row" action="${pageContext.request.contextPath}/recherche" method="POST">

        <div class="row m-6">
            <div class="row align-items-center">
                <div class="col-6">
                    <input class="form-control me-2" type="search" id="recherche-article" name="rechercheParMotClef"
                           placeholder="rechercher..."
                           value="${motclef}">
                </div>
                <div class="col-6">
                    <select class="form-select" name="rechercheParcategorie" id="categorie">
                        <option value="">--"Toutes"--</option>
                        <c:forEach items="${applicationScope.listeDesCategories}" var="categorie">
                            <option value="${categorie.key}" ${categorieChoisie == categorie.value ? "selected" : "" }>${categorie.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col">
                    <input class="btn btn-info" type="submit" value="Rechercher">
                </div>
            </div>
        </div>

        <div class="row m-6">
            <div class="row align-items-center">
                <c:if test="${sessionScope.utilisateur != null}">
                <!--radio boutons-->
                <div class="col-6">
                    <div class="form-check form-check-inline">
                        <input type="radio" class="form-check-input" name="achatOuVente" id="achat" autocomplete="off"
                               checked
                               value="achat" onclick=
                                       "GestionCheckBoxAchats(achat,'encheresOuvertes','mesEncheresEnCours','encheresRemportees','ventesEnCours','ventesNonDebutees','ventesTerminees')">
                        <label class="form-check-label" for="achat">Achats</label>
                        <!-- checkboxes-->
                        <div class="form-check form-switch">
                            <input type="checkbox" class="form-check-input" id="encheresOuvertes" autocomplete="off"
                                   name="encheresOuvertes">
                            <label class="form-check-label" for="encheresOuvertes">Enchères ouvertes</label>
                        </div>
                        <div class="form-check form-switch">
                            <input type="checkbox" class="form-check-input" id="mesEncheresEnCours" autocomplete="off"
                                   name="mesEncheresEnCours">
                            <label class="form-check-label" for="mesEncheresEnCours">Mes enchères en cours</label>
                        </div>
                        <div class="form-check form-switch">
                            <input type="checkbox" class="form-check-input" id="encheresRemportees" autocomplete="off"
                                   name="encheresRemportees">
                            <label class="form-check-label" for="encheresRemportees">Mes enchères remportées</label>
                        </div>
                    </div>
                </div>
                <!--radio boutons-->
                <div class="col-6">
                    <input type="radio" class="form-check-input" name="achatOuVente" id="vente" autocomplete="off"
                           checkedinput
                           value="vente">
                    <label class="form-check-label" for="vente">Ventes</label>
                    <!-- checkboxes-->
                    <div class="form-check form-switch">
                        <input type="checkbox" class="form-check-input" id="ventesEnCours" autocomplete="off"
                               name="ventesEnCours">
                        <label class="form-check-label" for="ventesEnCours">Mes ventes en cours</label>
                    </div>
                    <div class="form-check form-switch">
                        <input type="checkbox" class="form-check-input" id="ventesNonDebutees" autocomplete="off"
                               name="ventesNonDebutees">
                        <label class="form-check-label" for="ventesNonDebutees">Ventes non débutées</label>
                    </div>
                    <div class="form-check form-switch">
                        <input type="checkbox" class="form-check-input" id="ventesTerminees" autocomplete="off"
                               name="ventesTerminees">
                        <label class="form-check-label" for="ventesTerminees">Mes enchères remportées</label>
                    </div>
                </div>
            </div>

                <%--        <div>--%>
                <%--            <a href="${pageContext.request.contextPath}/recherche?bouton=achat"--%>
                <%--               onclick="GestionCheckBoxAchats(achat,'encheresOuvertes','mesEncheresEnCours','encheresRemportees','ventesEnCours','ventesNonDebutees','ventesTerminees')">--%>
                <%--                <input type="radio" name="achatOuVente" value="achat" checked ${coche=="achat"? "checked" : ""}>--%>
                <%--                Achats</a>--%>
                <%--            <input type="checkbox" id="encheresOuvertes"--%>
                <%--                   name="encheresOuvertes" ${coche=="vente"? "disabled" : ""} ${coche=="achat"? "checked" : ""} >--%>
                <%--            <label for="encheresOuvertes">Enchères ouvertes</label>--%>
                <%--            <input type="checkbox" id="mesEncheresEnCours"--%>
                <%--                   name="mesEncheresEnCours" ${coche=="vente"? "disabled" : ""}>--%>
                <%--            <label for="mesEncheresEnCours">Mes enchères en cours</label>--%>
                <%--            <input type="checkbox" id="encheresRemportees"--%>
                <%--                   name="encheresRemportees" ${coche=="vente"? "disabled" : ""}>--%>
                <%--            <label for="encheresRemportees">Mes enchères remportées</label>--%>
                <%--        </div>--%>

                <%--        <div>--%>
                <%--            <a href="${pageContext.request.contextPath}/recherche?bouton=vente"><input type="radio" id="vente"--%>
                <%--                                                                                       name="achatOuVente"--%>
                <%--                                                                                       value="vente" ${coche=="vente"? "checked" : ""}>--%>
                <%--                Mes ventes</a>--%>
                <%--            <input type="checkbox" id="ventesEnCours"--%>
                <%--                   name="ventesEnCours" ${coche=="achat"? "disabled" : ""} ${coche=="vente"? "checked" : ""}--%>
                <%--                   onclick="GestionCheckBoxVentes(vente,'encheresOuvertes','mesEncheresEnCours','encheresRemportees','ventesEnCours','ventesNonDebutees','ventesTerminees')">--%>
                <%--            <label for="ventesEnCours">Mes ventes en cours</label>--%>
                <%--            <input type="checkbox" id="ventesNonDebut"--%>
                <%--                   name="ventesNonDebutees" ${coche=="achat"? "disabled" : ""}>--%>
                <%--            <label for="ventesNonDebut">Ventes non débutées</label>--%>
                <%--            <input type="checkbox" id="ventesTerminees"--%>
                <%--                   name="ventesTerminees" ${coche=="achat"? "disabled" : ""}>--%>
                <%--            <label for="ventesTerminees">Ventes terminées</label>--%>
                <%--        </div>--%>
            </c:if>
        </div>
    </form>
</div>

<div class="container-fluid">
    <div class="row justify-content-center m-6">
        <c:choose>
            <c:when test="${listeArticles.size()>0}">

                <c:forEach items="${listeArticles}" var="article">

                    <div class="col-3">
                        <div class="card" style="width: 18rem; height: 32rem">
                            <a href="<%=request.getContextPath()%>/enchere?noarticle=${article.noArticle}">
                                <img src="..." class="card-img-top" alt="photo de l'article en vente"></a>
                            <div class="card-body">
                                <h5 class="card-title"><c:out value="${article.nomArticle}"/></h5>
                                <p class="card-text"><c:out value="Description : ${article.description}"/></p>
                                <p class="card-text"><c:out value="Prix de vente : ${article.prixInitial} points"/></p>
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
                                <a href="<%=request.getContextPath()%>/enchere?noarticle=${article.noArticle}"
                                   class="btn btn-info">Voir l'annonce</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <p>Pas d'article correspondant</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script language="JavaScript" type="text/javascript" src="scripts\app.js"></script>
</body>
</html>
