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

<table>
    <tbody>
    <tr>Pseudo : ${utilisateur.pseudo} </tr>
    <tr>Nom : ${utilisateur.nom} </tr>
    <tr>Prénom : ${utilisateur.prenom}</tr>
    <tr>Email : ${utilisateur.email} </tr>
    <tr>Telephone : ${utilisateur.telephone} </tr>
    <tr>Rue : ${utilisateur.rue} </tr>
    <tr>Code postal : ${utilisateur.codePostal} </tr>
    <tr>Ville : ${utilisateur.ville} </tr>
    </tbody>
</table>

<a href="accueil">Retour vers l'accueil</a>
<!-- gérer le bouton modifier si on est connecté-->

</body>
</html>
