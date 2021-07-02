<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="styles/initialize.min.css">
    <link type="text/css" rel="stylesheet" href="./styles/styles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Chewy&display=swap" rel="stylesheet">

    <title>Profil utilisateur</title>
</head>
<body>

<!-- cette page est accessible par 2 voies : via la navbar "mon profil" : afficher le profil de l'utilisateur connecté
via un lien sur le pseudo d'un vendeur : afficher le profil d'un autre utilisateur-->

<jsp:include page="/WEB-INF/fragments/header.jsp"/>

<h1 class="text-center text-white" style="font-family:'Chewy', cursive;">Profil utilisateur</h1>

<div class="d-flex justify-content-center">
    <div class="row m-6">
          <div class="row">
              <div class="card my-4 bg-light" style="width: 30rem; height: auto;">
                  <div class="card-body">
                      <ul class="list-group list-group-flush">
                          <li class="list-group-item">
                              <p class="card-text">Pseudo<p>
                              ${empty vendeur ? utilisateur.pseudo : vendeur.pseudo}
                          </li>
                          <li class="list-group-item">
                              <p class="card-text">Nom<p>
                              ${empty vendeur ? utilisateur.nom : vendeur.nom}
                          </li>
                          <li class="list-group-item">
                              <p class="card-text">Prénom<p>
                              ${empty vendeur ? utilisateur.prenom : vendeur.prenom}
                          </li>
                          <li class="list-group-item">
                              <p class="card-text">Email<p>
                              ${empty vendeur ? utilisateur.email : vendeur.email}
                          </li>
                          <li class="list-group-item">
                              <p class="card-text">Téléphone<p>
                              ${empty vendeur ? utilisateur.telephone : vendeur.telephone}
                          </li>
                          <li class="list-group-item">
                              <p class="card-text">Rue<p>
                              ${empty vendeur ? utilisateur.rue : vendeur.rue}
                          </li>
                          <li class="list-group-item">
                              <p class="card-text">Code Postal<p>
                              ${empty vendeur ? utilisateur.codePostal : vendeur.codePostal}
                          </li>
                          <li class="list-group-item">
                              <p class="card-text">Ville<p>
                              ${empty vendeur ? utilisateur.ville : vendeur.ville}
                          </li>
                          <li class="list-group-item">
                              <c:if test="${(vendeur.pseudo).equals(utilisateur.pseudo) || empty vendeur}">
                                  <p class="card-text">Crédit : </p>
                                  <p>${utilisateur.credit}</p>
                              </c:if>
                          </li>
                      </ul>
                  </div>
                  <div class="d-flex justify-content-center">
                      <div class="row">
                          <a href="accueil"><button class="btn btn-secondary">Retour vers l'accueil</button></a>
                      </div>
                      <!-- gérer le bouton modifier si on est connecté / problème lors de l'affichage du profil de quelqu'un d'autre-->
                      <div class="row">
                          <c:if test="${sessionScope.utilisateur.pseudo == vendeur.pseudo }">
                              <a href="<%=request.getContextPath()%>/monprofil"><button class="btn btn-primary">Modifier mon profil</button></a>
                          </c:if>
                          <c:if test="${empty vendeur}">
                              <a href="<%=request.getContextPath()%>/monprofil"><button class="btn btn-primary">Modifier mon profil</button></a>
                          </c:if>
                      </div>
                  </div>
              </div>
          </div>
        </div>
    </div>
</div>
</body>
</html>
