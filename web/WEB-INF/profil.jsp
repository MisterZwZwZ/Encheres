<%--
  Created by IntelliJ IDEA.
  User: cgrandval2021
  Date: 23/06/2021
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Profil utilisateur</title>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<table>
    <tbody>
    <tr>
        <td>Pseudo : </td>
        <td>${utilisateur.pseudo} </td>
    </tr>
    <tr>
        <td>Nom : </td>
        <td>${utilisateur.nom} </td>
    </tr>
    <tr>
        <td>Prénom : </td>
        <td>${utilisateur.prenom}</td>
    </tr>
    <tr>
        <td>Email : </td>
        <td>${utilisateur.email} </td>
    </tr>
    <tr>
        <td>Telephone : </td>
        <td>${utilisateur.telephone} </td>
    </tr>
    <tr>
        <td>Rue : </td>
        <td>${utilisateur.rue} </td>
    </tr>
    <tr>
        <td>Code postal : </td>
        <td>${utilisateur.codePostal} </td>
    </tr>
    <tr>
        <td>Ville : </td>
        <td>${utilisateur.ville} </td>
    </tr>
    </tbody>
</table>

<a href="accueil">Retour vers l'accueil</a>
<!-- gérer le bouton modifier si on est connecté-->
<c:if test="${sessionScope.utilisateur.pseudo == utilisateur.pseudo }">
    <a href="<%=request.getContextPath()%>/monprofil">Modifier</a>
</c:if>

</body>
</html>
