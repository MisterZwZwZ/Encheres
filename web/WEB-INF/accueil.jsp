<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Accueil</title>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<p>Filtres</p>

<form action="${pageContext.request.contextPath}/recherche" method="POST">
    <input type="search" id="recherche-article" name="rechercheParMotClef" placeholder="Le nom de l'article contient" value="${motclef}">

    <select name="rechercheParcategorie" id="categorie">
        <option value="">--"Toutes"--</option>
        <c:forEach items="${applicationScope.listeDesCategories}" var="categorie">
        <option value="${categorie.key}">${categorie.value}</option>
        </c:forEach>
    </select>

    <c:if test="${sessionScope.utilisateur != null}">

        <div>
            <a href="${pageContext.request.contextPath}/recherche?bouton=achat">
                <input type="radio" name="achatOuVente" value="achat" checked ${coche=="achat"? "checked" : ""}>
                Achats</a>
            <input type="checkbox" id="encheresOuvertes"
                   name="encheresOuvertes" ${coche=="vente"? "disabled" : ""} ${coche=="achat"? "checked" : ""} >
            <label for="encheresOuvertes">Enchères ouvertes</label>
            <input type="checkbox" id="mesEncheresEnCours"
                   name="mesEncheresEnCours" ${coche=="vente"? "disabled" : ""}>
            <label for="mesEncheresEnCours">Mes enchères en cours</label>
            <input type="checkbox" id="encheresRemportees"
                   name="encheresRemportees" ${coche=="vente"? "disabled" : ""}>
            <label for="encheresRemportees">Mes enchères remportées</label>
        </div>

        <div>
            <a href="${pageContext.request.contextPath}/recherche?bouton=vente"><input type="radio" id="vente"
                                                                                       name="achatOuVente"
                                                                                       value="vente" ${coche=="vente"? "checked" : ""}>
                Mes ventes</a>
            <input type="checkbox" id="ventesEnCours"
                   name="ventesEnCours" ${coche=="achat"? "disabled" : ""} ${coche=="vente"? "checked" : ""} >
            <label for="ventesEnCours">Mes ventes en cours</label>
            <input type="checkbox" id="ventesNonDebut"
                   name="ventesNonDebutees" ${coche=="achat"? "disabled" : ""}>
            <label for="ventesNonDebut">Ventes non débutées</label>
            <input type="checkbox" id="ventesTerminees"
                   name="ventesTerminees" ${coche=="achat"? "disabled" : ""}>
            <label for="ventesTerminees">Ventes terminées</label>
        </div>

    </c:if>

    <input type="submit" value="Rechercher">
</form>

<div>
    <c:choose>
        <c:when test="${listeArticles.size()>0}">
            <table>
                <tbody>
                <c:forEach items="${listeArticles}" var="article">
                    <tr><td><c:out value="${article.nomArticle}"/></td></tr>
                    <tr><td><c:out value="${article.description}"/></td></tr>
                    <tr><td><c:out value="Prix de vente : ${article.prixInitial} points"/></td></tr>
                    <tr><td><c:out value="Date de fin d'enchère : ${article.dateFinEnchere}"/></td></tr>

                    <tr>
                        <c:choose>
                            <c:when test="${sessionScope.utilisateur == null }">
                                <td><c:out value="Vendeur : ${article.vendeur.pseudo}"/></td>
                            </c:when>

                            <c:when test="${sessionScope.utilisateur != null }">
                                <td>
                                    <a href="${pageContext.request.contextPath}/profil?pseudo=${article.vendeur.pseudo}">
                                        <c:out value="Vendeur : ${article.vendeur.pseudo}"/>
                                    </a></td>
                            </c:when>
                        </c:choose>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
        </c:when>

        <c:otherwise>
            <p>Pas d'article correspondant</p>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>
