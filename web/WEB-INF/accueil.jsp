<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accueil</title>
</head>
<body>
<header>
    <h1>ENI-Encheres</h1>
    <a href="<%=request.getContextPath()%>/login">S'inscrire - Se connecter</a>
</header>

<p>Filtres</p>
<form action="" method="">
    <input type="search" id="recherche-article" name="recherche" placeholder="Vous cherchez ?">
    <button>Rechercher</button>
    <select name="categories" id="categories">
        <option value="Informatique">Informatique</option>
        <option value="Ameublement">Ameublement</option>
        <option value="Vetements">Vêtements</option>
        <option value="Sports&Loisirs">Sports & Loisirs</option>
    </select>
</form>

</body>
</html>
