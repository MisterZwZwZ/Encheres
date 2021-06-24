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
    <input type="search" id="recherche-article" name="rechercheParMotClef" placeholder="Le nom de l'article contient">
    <select name="rechercheParcategorie" id="categories">
        <option value="">--Toutes--</option>
        <option value="informatique">Informatique</option>
        <option value="ameublement">Ameublement</option>
        <option value="vetements">Vêtements</option>
        <option value="sportsLoisirs">Sports & Loisirs</option>
    </select>

    <c:if test="${sessionScope.utilisateur != null}">

    <div>
        <div>
            <a href="${pageContext.request.contextPath}/recherche?bouton=achat"><input type="radio" name="achatOuVente" value="achat" ${coche=="achat"? "checked" : ""}>
            Achats</a>
            <input type="checkbox" id="encheresOuvertes" value="enchères ouvertes" name="encheresOuvertes" ${coche=="vente"? "disabled" : ""}>
            <label for="encheresOuvertes">Enchères ouvertes</label>
            <input type="checkbox" id="mesEncheresEnCours" value="mes enchères en cours" name="mesEncheresEnCours" ${coche=="vente"? "disabled" : ""}>
            <label for="mesEncheresEnCours">Mes enchères en cours</label>
            <input type="checkbox" id="encheresRemportees" value="enchères remportées" name="encheresRemportees" ${coche=="vente"? "disabled" : ""}>
            <label for="encheresRemportees">Mes enchères remportées</label>
        </div>

        <div>
            <a href="${pageContext.request.contextPath}/recherche?bouton=vente"><input type="radio" id="vente" name="achatOuVente" value="vente" ${coche=="vente"? "checked" : ""}>
            Mes ventes</a>
            <input type="checkbox" id="ventesEnCours" value="ventes en cours" name="ventesEnCours" ${coche=="achat"? "disabled" : ""}>
            <label for="ventesEnCours">Mes ventes en cours</label>
            <input type="checkbox" id="ventesNonDebut" value="ventes non débutées" name="ventesNonDebutees" ${coche=="achat"? "disabled" : ""}>
            <label for="ventesNonDebut">Ventes non débutées</label>
            <input type="checkbox" id="ventesTerminees" value="ventes terminées" name="ventesTerminees" ${coche=="achat"? "disabled" : ""}>
            <label for="ventesTerminees">Ventes terminées</label>
        </div>
        </c:if>

    <input type="submit" value="Rechercher">
</form>
<div>
    <c:choose>
        <c:when test="${ArticlesEncherissables.size()>0}">
            <table>
                <tbody>
                <c:forEach items="${ArticlesEncherissables}" var="article">
                    <tr>
                        <td><c:out value="${article.nomArticle}"/></td>
                    </tr>
                    <tr>
                        <td><c:out value="${article.description}"/></td>
                    </tr>
                    <tr>
                        <td><c:out value="Prix de vente : ${article.prixInitial} points"/></td>
                    </tr>
                    <tr>
                        <td><c:out value="Date de fin d'enchère : ${article.dateFinEnchere}"/></td>
                    </tr>

                    <tr>
                    <c:choose>
                    <c:when test="${sessionScope.utilisateur == null }">
                        <td><c:out value="Vendeur : ${article.vendeur.pseudo}"/></td>
                    </c:when>

                    <c:when test="${sessionScope.utilisateur != null }">
                        <td><a href="${pageContext.request.contextPath}/profil?pseudo=${article.vendeur.pseudo}">
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
            <p>Pas d'article encherissable actuellement</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
