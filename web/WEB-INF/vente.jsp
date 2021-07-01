<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<html>
<head>
    <title>Vente</title>
    <link type="text/css" rel="stylesheet" href="./styles/venteStyles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<h1 class="text-white text-center">Vendre un article</h1>

<div class="d-flex justify-content-center text-white py-2">
    <div class="row">
        <div>
            <!-- affichage du message de confirmation pour l'insertion-->
            <c:if test="${!empty msgConfirmation}">
                <div class="alert alert-success" role="alert">
                    <strong >${msgConfirmation}</strong>
                </div>
            </c:if>

            <!-- affichage des messages d'erreur éventuels -->
            <c:if test="${!empty listeCodesErreur}">
                <div class="alert alert-danger" role="alert">
                    <p><strong>Erreur(s) !</strong></p>
                    <ul>
                        <c:forEach var="code" items="${listeCodesErreur}">
                            <li>${LecteurErreur.getMessageErreur(code)}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </div>
        <div class="col md-10 mx-auto">
            <form action="${pageContext.request.contextPath}/vendre" method="POST">
                <div class="form-group row">
                    <div class="col-sm-6">
                        <label class="col-form-label" for="nomArticle">Article</label>
                        <input class="form-control" type="text" name="nomArticle" id="nomArticle" value="${empty articleAModifier ? nomArticle : articleAModifier.nomArticle}" required>
                    </div>

                    <div class="col-sm-6">
                        <label class="col-form-label overflow-auto" for="description">Description</label>
                        <textarea class="form-control" rows="5" cols="33" name="description" id="description"
                      >${empty articleAModifier ? description : articleAModifier.description}</textarea>
                    </div>
                </div>
                <div class="form-group row py-3">
                    <div class="col-sm-3">
                        <label class="col-form-label" for="categorie">Catégorie</label>
                        <select class="form-control" aria-label="Default select example" name="rechercheParcategorie" id="categorie">
                            <c:choose>
                                <c:when test="${!empty (rechercheParcategorie)}">
                                    <option value="${rechercheParcategorie}" selected>${valeurCatePrecedente}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="0" selected>Choisissez une catégorie</option>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach items="${applicationScope.listeDesCategories}" var="categorie">
                                <option required value="${categorie.key}">${categorie.value} </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <label class="col-form-label" for="prixInitial">Mise à prix</label>
                        <input class="form-control" type="number" name="prixInitial" id="prixInitial"
                               value="${empty articleAModifier ? prixInitial : articleAModifier.prixInitial}" required>
                    </div>
                    <div class="col-sm-3">
                        <label class="col-form-label" for="dateDebutEnchere">Début de l'enchère</label>
                            <span class="input-group-addon"><i class="fas fa-calendar-check"></i></span>
                            <input class="form-control" name="dateDebutEnchere" id="dateDebutEnchere" required value="${dateDebutEnchere}">
                    </div>
                    <div class="col-sm-3">
                        <label class="col-form-label" for="dateFinEnchere">Fin de l'enchère</label>
                        <input class="form-control" name="dateFinEnchere" id="dateFinEnchere" required value="${dateFinEnchere}">
                    </div>
                </div>

                <div class="form-group row py-2">
                    <div class="col-sm-6">
                        <fieldset>
                            <legend>Retrait</legend>
                            <label class="col-form-label" for="rue">Rue :</label>
                            <input class="form-control" type="text" name="rue" id="rue"
                                   value="${empty retraitAModifier ? sessionScope.utilisateur.rue : retraitAModifier.rue }"><br/>
                            <label class="col-form-label" for="codePostal">Code Postal :</label>
                            <input class="form-control" type="text" name="codePostal" id="codePostal"
                                   value="${empty retraitAModifier ? sessionScope.utilisateur.codePostal : retraitAModifier.codePostal }"><br/>
                            <label class="col-form-label" for="ville">Ville :</label>
                            <input class="form-control" type="text" name="ville" id="ville"
                                   value="${empty retraitAModifier ? sessionScope.utilisateur.ville : retraitAModifier.ville }">
                        </fieldset>
                    </div>
                </div>
                <div class="d-flex justify-content-center">
                    <div class="col-sm-6 m-2 w-50">
                        <c:if test="${empty articleAModifier}">
                            <button class="btn btn-primary w-100" type="submit" ${!empty msgConfirmation ? "disabled" : ""}>Valider</button>
                        </c:if>
                        <c:if test="${!empty articleAModifier}">
                            <input type="hidden" value="${articleAModifier.noArticle}" name="noArticle">
                            <button class="btn btn-primary w-100" type="submit">Valider la modification</button>
                        </c:if>
                    </div>
                </div>
            </form>
            <div class="d-flex justify-content-center">
                <div class="col-sm-12 m-2 w-50">
                    <a href="<%=request.getContextPath()%>/accueil">
                        <button class="btn btn-secondary w-100">Retour</button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
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

</body>
</html>
