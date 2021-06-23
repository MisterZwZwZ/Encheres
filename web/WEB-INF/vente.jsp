<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vente</title>
</head>
<body>
<h1>Nouvelle vente</h1>
<form action="" method="">
    <label for="nomArticle">Article</label>
    <input type="text" name="nomArticle" id="nomArticle" value="${nomArticle}">
    <label for="description">Description</label>
    <input type="textarea" name="description" id="description" value="${description}" >

    <label for="categories">Catégorie</label>
    <select name="categories" id="categories">
        <option value="Informatique">Informatique</option>
        <option value="Ameublement">Ameublement</option>
        <option value="Vetements">Vêtements</option>
        <option value="Sports&Loisirs">Sports & Loisirs</option>
    </select>

    <label for="prixInitial">Mise à prix</label>
    <input type="number" name="prixInitial" id="prixInitial" min="1" value="${prixInitial}">
    <label for="dateDebutEnchere">Début de l'enchère</label>
    <input type="date" name="dateDebutEnchere" id="dateDebutEnchere" value="${debutEnchere}">
    <label for="dateFinEnchere">Fin de l'enchère</label>
    <input type="date" name="dateDebutEnchere" id="dateFinEnchere" value="${finEnchere}">

    <fieldset>
        <legend>Retrait</legend>
        <label for="rueRetrait">Rue :</label>
        <input type="text" name="rueRetrait" id="rueRetrait" value="${rueRetrait}"><br/>
        <label for="codePostalRetrait">Code Postal :</label>
        <input type="text" name="codePostalRetrait" id="codePostalRetrait" value="${codePostalRetrait}"><br/>
        <label for="villeRetrait">Rue :</label>
        <input type="text" name="villeRetrait" id="villeRetrait" value="${villeRetrait}">
    </fieldset>
    <input type="submit" value="Enregistrer">
    <input type="submit" value="Annuler">
</form>
</body>
</html>
