<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<html>
<head>
    <title>Vente</title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $(function () {
            $("#dateDebutEnchere").datepicker({
                altField: "#datepicker",
                closeText: 'Fermer',
                prevText: 'Précédent',
                nextText: 'Suivant',
                currentText: 'Aujourd\'hui',
                monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
                monthNamesShort: ['Janv.', 'Févr.', 'Mars', 'Avril', 'Mai', 'Juin', 'Juil.', 'Août', 'Sept.', 'Oct.', 'Nov.', 'Déc.'],
                dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
                dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
                dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
                weekHeader: 'Sem.',
                minDate: 0,
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                numberOfMonths: 2,
                changeYear: true,
                onClose: function (selectedDate, inst) {
                    var minDate = new Date(Date.parse(selectedDate));
                    minDate.setDate(minDate.getDate() + 1);
                    $("#dateFinEnchere").datepicker("option", "minDate", minDate);
                }
            });

            $("#dateFinEnchere").datepicker({
                altField: "#datepicker",
                closeText: 'Fermer',
                prevText: 'Précédent',
                nextText: 'Suivant',
                currentText: 'Aujourd\'hui',
                monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
                monthNamesShort: ['Janv.', 'Févr.', 'Mars', 'Avril', 'Mai', 'Juin', 'Juil.', 'Août', 'Sept.', 'Oct.', 'Nov.', 'Déc.'],
                dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
                dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
                dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
                weekHeader: 'Sem.',
                minDate: "+1D",
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                numberOfMonths: 2,
                changeYear: true,
                onClose: function (selectedDate, inst) {
                    var maxDate = new Date(Date.parse(selectedDate));
                    maxDate.setDate(maxDate.getDate() - 1);
                    $("#dateDebutEnchere").datepicker("option", "maxDate", maxDate);
                }
            });
        });
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<h1>Nouvelle vente</h1>
<form action="${pageContext.request.contextPath}/vendre" method="POST">
    <label for="nomArticle">Article</label>
    <input type="text" name="nomArticle" id="nomArticle" value="${nomArticle}">
    <label for="description">Description</label>
    <input type="textarea" name="description" id="description" rows="5" cols="33" value="${description}">

    <label for="categorie">Catégorie</label>
    <select name="rechercheParcategorie" id="categorie">
        <c:forEach items="${applicationScope.listeDesCategories}" var="categorie">
            <option value="${categorie.key}">${categorie.value}</option>
        </c:forEach>
    </select>

    <label for="prixInitial">Mise à prix</label>
    <input type="number" name="prixInitial" id="prixInitial" value="${prixInitial}">
    <label for="dateDebutEnchere">Début de l'enchère</label>
    <input name="dateDebutEnchere" id="dateDebutEnchere">
    <label for="dateFinEnchere">Fin de l'enchère</label>
    <input name="dateFinEnchere" id="dateFinEnchere">

    <fieldset>
        <legend>Retrait</legend>
        <label for="rue">Rue :</label>
        <input type="text" name="rue" id="rue" value="${sessionScope.utilisateur.rue}"><br/>
        <label for="codePostal">Code Postal :</label>
        <input type="text" name="codePostal" id="codePostal" value="${sessionScope.utilisateur.codePostal}"><br/>
        <label for="ville">Ville :</label>
        <input type="text" name="ville" id="ville" value="${sessionScope.utilisateur.ville}">
    </fieldset>
    <input type="submit" value="Enregistrer">
    <a href="<%=request.getContextPath()%>/accueil">Annuler</a>
</form>

<!-- affichage des messages d'erreur éventuels -->
<c:if test="${!empty listeCodesErreur}">
    <strong>Erreur!</strong>
    <ul>
        <c:forEach var="code" items="${listeCodesErreur}">
            <li>${LecteurErreur.getMessageErreur(code)}</li>
        </c:forEach>
    </ul>
</c:if>

</body>
</html>
