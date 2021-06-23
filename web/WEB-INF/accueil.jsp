<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Accueil</title>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<p>Filtres</p>
<form action="${pageContext.request.contextPath}/accueil" method="POST">
    <input type="search" id="recherche-article" name="recherche" placeholder="Vous cherchez ?">
    <select name="categories" id="categories">
        <option value="Informatique">Informatique</option>
        <option value="Ameublement">Ameublement</option>
        <option value="Vetements">Vêtements</option>
        <option value="Sports&Loisirs">Sports & Loisirs</option>
    </select>
    <input type="submit" value="Rechercher">
</form>
<div>
    <c:choose>
        <c:when test="${ArticlesEncherissables.size()>0}">
            <table>
                <tbody>
                <c:forEach items="${ArticlesEncherissables}" var="article">
                    <tr><c:out value="${article.nomArticle}"/></tr>
                    <tr><c:out value="${article.description}"/></tr>
                    <tr><c:out value="Prix de vente : ${article.prixInitial} points"/></tr>
                    <tr><c:out value="Date de fin d'enchère : ${article.dateFinEnchere}"/></tr>
                    <a href="${pageContext.request.contextPath}/profil?pseudo=${article.vendeur.pseudo}">
                        <tr><c:out value="Vendeur : ${article.vendeur.pseudo}"/></tr>
                    </a>
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
