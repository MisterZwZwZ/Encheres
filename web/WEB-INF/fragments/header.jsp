<%--
  Created by IntelliJ IDEA.
  User: lzwierzewicz2021
  Date: 22/06/2021
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <h1>ENI-Encheres</h1>

<c:choose>
    <c:when test="${!empty(sessionScope.utilisateur)}">
        <a href="<%=request.getContextPath()%>/encheres">Enchères</a>
        <a href="<%=request.getContextPath()%>/vendre">Vendre un article</a>
        <a href="<%=request.getContextPath()%>/profil">Mon profil</a>
        <a href="<%=request.getContextPath()%>/deconnexion">Se déconnecter</a>
        <p>Bonjour ${sessionScope.utilisateur.pseudo}</p>
        <p>Votre Crédit : ${sessionScope.utilisateur.credit} points</p>
    </c:when>
    <c:when test ="${empty(sessionScope.utilisateur)}">
        <a href="<%=request.getContextPath()%>/inscription">S'inscrire</a>
        <a href="<%=request.getContextPath()%>/login">Se connecter</a>
    </c:when>
</c:choose>
</header>
