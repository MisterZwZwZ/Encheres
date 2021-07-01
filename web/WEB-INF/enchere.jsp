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

<div class="d-flex justify-content-center py-2">
        <div class="row-6">
                <div>
                    <!-- affichage des messages d'erreur éventuels -->
                    <c:if test="${!empty listeCodesErreur}">
                        <p class="alert alert-danger" role="alert"><strong>Erreur(s)!</strong></p>
                        <ul class="alert alert-danger text-center" role="alert">
                            <c:forEach var="code" items="${listeCodesErreur}">
                                <li>${LecteurErreur.getMessageErreur(code)}</li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </div>
            <div class="card my-4" style="width: 30rem; height: auto;">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/enchere" method="POST">
                        <c:choose>
                            <c:when test="${etatVente == 'en cours'}">
                                <h3 class="text-center">Détail vente</h3>
                            </c:when>
                            <c:when test="${etatVente == 'terminee'}">
                                <c:if test="${statutUtilisateur == 'acquereur'}">
                                    <h3 class="text-center alert alert-success" role="alert">Vous avez remporté l'enchère !</h3>
                                </c:if>
                                <c:if test="${statutUtilisateur == 'vendeur'}">
                                    <h3 class="text-center alert alert-primary" role="alert">${enchere.encherisseur.pseudo} a remporté la vente.</h3>
                                </c:if>
                            </c:when>
                        </c:choose>

                        <h5 class="card-title text-center"><c:out value="${article.nomArticle}"/></h5>
                        <p class="card-text overflow-auto"><c:out value="Description : ${article.description}"/></p>
                        <p class="card-text"><c:out value="Catégorie : ${article.categorie.libelle}"/></p>
                        <p class="card-text"><c:out value="Meilleure offre : ${enchere.montantEnchere} ${enchere.montantEnchere != 0 ? 'par' : ''} ${enchere.encherisseur.pseudo}"/></p>
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
                            <p class="alert alert-info" role="alert"><c:out value="Vous êtes le meilleur encherisseur pour le moment"/></p>
                        </c:if>

                        <c:if test="${statutUtilisateur == 'encherisseur' && etatVente == 'en cours'}">
                            <label class="col-form-label" for="prix">Ma proposition :</label>
                            <input class="form-control" type="number" id="prix" name="prix">
                            <input type="hidden" name="noarticle" value="${article.noArticle}">
                            <button class="btn btn-primary w-100" type="submit">Encherir</button>
                        </c:if>
                        <c:if test="${!empty messageConf}">
                            <div class="alert alert-success" role="alert">
                                <p>Votre enchère a bien été enregistrée !</p>
                            </div>
                        </c:if>

                        <c:if test="${statutUtilisateur == 'vendeur' && etatVente == 'en cours'}">
                            <p class="alert alert-info" role="alert"><c:out value="Votre article est en vente et les enchères sont en cours"/></p>
                        </c:if>

                        <!-- je veux modifier un article -->
                        <c:if test="${statutUtilisateur == 'vendeur' && etatVente == 'pas demarree'}">
                            <input type="hidden" name="noarticle" value="${article.noArticle}">
                            <button value="Modifier l'article" class="btn btn-primary" name="modifier">Modifier l'article</button>
                        </c:if>
                    </form>
                </div>
                <a href="<%=request.getContextPath()%>/accueil"><button class="btn btn-secondary w-100">Retour</button></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
