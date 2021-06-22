<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil</title>
</head>
<body>
<h1>ENI-Enchères</h1>

<h2>Mon profil</h2>

<form action="<%=request.getContextPath()%>/inscription" method="POST">
    <label for="pseudo">Pseudo :</label>
        <input type="text" id="pseudo" value="pseudo">
    <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" value="prenom">
    <label for="nom">Nom :</label>
        <input type="text" id="nom" value="nom">
    <label for="email">Email :</label>
        <input type="email" id="email" value="email">
    <label for="telephone">Téléphone :</label>
        <input type="tel" id="telephone" value="telephone">
    <label for="rue">Rue :</label>
        <input type="text" id="rue" value="rue">
    <label for="codepostal">Code postal :</label>
        <input type="text" id="codepostal" value="codepostal">
    <label for="ville">Ville :</label>
        <input type="text" id="ville" value="ville">
    <label for="pass">Mot de passe :</label>
        <input type="password" id="pass" value="pass">
    <label for="confpass">Confirmation  :</label>
        <input type="password" id="confpass" value="confpass">

    <input type="submit" value="Créer">
    <input type="submit" value="Annuler">
</form>
</body>
</html>
