<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<html>
<head>
    <title>Vente</title>
    <link type="text/css" rel="stylesheet" href="./styles/venteStyles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
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
                minDate: 0,
                firstDay: 1,
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                numberOfMonths: 1,
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
                minDate: "+1D",
                firstDay: 1,
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                numberOfMonths: 1,
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

<div class="d-flex justify-content-center text-white py-5">
    <div class="row">
        <div class="col md-10 mx-auto">
            <form action="${pageContext.request.contextPath}/vendre" method="POST">
                <div class="form-group row">
                    <div class="col-sm-6">
                        <label class="col-form-label" for="nomArticle">Article</label>
                        <input class="form-control" type="text" name="nomArticle" id="nomArticle" value="${nomArticle}">
                    </div>
<%--                    TODO TL voir pour agrandir le champ de saisie--%>
                    <div class="col-sm-6">
                        <label class="col-form-label" for="description">Description</label>
                        <input class="form-control" type="textarea" name="description" id="description"
                               value="${description}">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-3">
                        <label class="col-form-label" for="categorie">Catégorie</label>
                        <select class="form-control" name="rechercheParcategorie" id="categorie">
                            <c:forEach items="${applicationScope.listeDesCategories}" var="categorie">
                                <option value="${categorie.key}">${categorie.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <label class="col-form-label" for="prixInitial">Mise à prix</label>
                        <input class="form-control" type="number" name="prixInitial" id="prixInitial"
                               value="${prixInitial}">
                    </div>
                    <div class="col-sm-3">
                        <label class="col-form-label" for="dateDebutEnchere">Début de l'enchère</label>
                        <input class="form-control" name="dateDebutEnchere" id="dateDebutEnchere">
                    </div>
                    <div class="col-sm-3">
                        <label class="col-form-label" for="dateFinEnchere">Fin de l'enchère</label>
                        <input class="form-control" name="dateFinEnchere" id="dateFinEnchere">
                    </div>
                </div>
<%--                TODO TL ajouter div pour encadrer et regrouper le retrait --%>
                <div class="form-group row">
                    <div class="col-sm-6">
                        <fieldset>
                            <legend>Retrait</legend>
                            <label class="col-form-label" for="rue">Rue :</label>
                            <input class="form-control" type="text" name="rue" id="rue"
                                   value="${sessionScope.utilisateur.rue}"><br/>
                            <label class="col-form-label" for="codePostal">Code Postal :</label>
                            <input class="form-control" type="text" name="codePostal" id="codePostal"
                                   value="${sessionScope.utilisateur.codePostal}"><br/>
                            <label class="col-form-label" for="ville">Ville :</label>
                            <input class="form-control" type="text" name="ville" id="ville"
                                   value="${sessionScope.utilisateur.ville}">
                        </fieldset>
                    </div>
<%--                    TODO TL center button --%>
                    <div class="form-group row">
                        <div class="col-sm-6">
                            <button class="btn btn-primary" type="submit" style="width:400px;">Enregistrer</button>
                        </div>
                        <div class="col-sm-6">
                            <a href="<%=request.getContextPath()%>/accueil">
                                <button class="btn btn-secondary" style="width:400px;">Annuler</button>
                            </a>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


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
