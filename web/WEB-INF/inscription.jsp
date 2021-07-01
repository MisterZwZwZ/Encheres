<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.messages.LecteurErreur" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="./styles/inscriptionStyles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Inscription</title>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<h2 class="text-white text-center py-4">Inscription</h2>


<!--Formulaire d'inscription mis en page avec Boostrap-->
<div class="d-flex justify-content-center text-white py-2">
    <div class="row w-75">
        <div class="d-flex justify-content-center row py-2">
            <c:if test="${!empty listeCodesErreur}">
                <p class="alert alert-danger" role="alert"><strong>Erreur !</strong></p>
                <ul class="alert alert-danger" role="alert">
                    <c:forEach var="code" items="${listeCodesErreur}">
                        <li>${LecteurErreur.getMessageErreur(code)}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
        <div class="col md-10 mx-auto">
            <form action="${pageContext.request.contextPath}/inscription" method="post" class="needs-validation"
                  novalidate>
                <div class="form-group row">
                    <div class="col-sm-4">
                        <label for="pseudo" class="form-label">Pseudo</label>
                        <input type="text" name="pseudo" id="pseudo" placeholder="pseudo" value="${pseudo}" required
                               class="form-control" required>
                        <div class="valid-feedback">
                            Choisissez un pseudo.
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <label for="prenom" class="form-label">Prénom</label>
                        <input type="text" name="prenom" id="prenom" placeholder="prenom" value="${prenom}" required
                               class="form-control">
                        <div class="valid-feedback">
                            Looks good !
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <label for="nom" class="form-label">Nom</label>
                        <input type="text" name="nom" id="nom" placeholder="nom" value="${nom}" required class="form-control">
                        <div class="invalid-feedback">
                            Looks good !
                        </div>
                    </div>
                </div>
                <div class="form-group row py-2">
                    <div class="col-sm-4">
                        <label for="rue" class="form-label">Rue</label>
                        <input type="text" name="rue" id="rue" placeholder="rue" value="${rue}" required class="form-control">
                        <div class="invalid-feedback">
                            Veuillez renseigner une rue valide.
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <label for="codepostal" class="form-label">Code postal </label>
                        <input type="text" name="cp" id="codepostal" placeholder="code postal" value="${cp}" required
                               class="form-control">
                        <div class="invalid-feedback">
                            Choisissez un code postal valide.
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <label for="ville" class="form-label">Ville</label>
                        <input type="text" name="ville" id="ville" placeholder="ville" value="${ville}" required
                               class="form-control">
                        <div class="invalid-feedback">
                            Choisissez une ville valide.
                        </div>
                    </div>
                </div>
                <div class="form-group row py-2">
                    <div class="col-sm-4">
                        <label for="email" class="form-label">Email</label>
                        <div class="input-group has-validation">
                            <span class="input-group-text" id="inputGroupPrepend">@</span>
                            <input type="email" name="email" id="email" placeholder="email" value="${email}" required
                                   class="form-control">
                            <div class="invalid-feedback">
                                Choisissez un email valide.
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <label for="telephone" class="form-label">Téléphone</label>
                        <input type="tel" name="telephone" id="telephone" placeholder="telephone" value="${telephone}"
                               class="form-control">
                        <div class="invalid-feedback">
                            Choisissez un email valide.
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-4">
                        <label for="pass" class="form-label">Mot de passe</label>
                        <input type="password" name="password" id="pass" placeholder="mot de passe" required title="le mot de passe
        doit contenir au moins 1 minuscule, 1 majuscule, 1 chiffre" class="form-control">
                        <div class="invalid-feedback">
                            Choisissez un mot de passe valide. Le mot de passe
                            doit contenir au moins 1 minuscule, 1 majuscule et 1 chiffre.
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <label for="confpass" class="form-label">Confirmation mot de passe</label>
                        <input type="password" name="passwordConf" id="confpass" placeholder="mot de passe" required title="le mot de passe
        doit contenir au moins 1 minuscule, 1 majuscule, 1 chiffre" class="form-control">
                        <div class="invalid-feedback">
                            Les mots de passe doivent être identiques.
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-center py-4">
                    <button class="btn btn-primary" type="submit">Créer un compte</button>
                </div>
            </form>
            <div class="d-flex justify-content-center">
                <a href="${pageContext.request.contextPath}/accueil"><button class="btn btn-secondary">Annuler</button></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
