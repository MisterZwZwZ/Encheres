<%--
  Created by IntelliJ IDEA.
  User: lzwierzewicz2021
  Date: 22/06/2021
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>

<header>

    <c:choose>
        <c:when test="${!empty(sessionScope.utilisateur)}">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <a class="navbar-brand" href="<%=request.getContextPath()%>/accueil">
                        <img src="https://img2.freepng.fr/20180420/xrq/kisspng-cat-spacecraft-drawing-outer-space-clip-art-spaceship-5adaadfe72c5c1.7562180915242808304701.jpg"
                             height="100px" width="100px" alt="Logo du site encheres" width="30" height="24"></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page"
                                   href="<%=request.getContextPath()%>/accueil">Enchères</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="<%=request.getContextPath()%>/vendre">Vendre un
                                    article</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="<%=request.getContextPath()%>/profil">Voir mon
                                    profil</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="<%=request.getContextPath()%>/monprofil">Modifier mon
                                    profil</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=request.getContextPath()%>/deconnexion">Se déconnecter</a>
                            </li>
                        </ul>
                        <form class="d-flex">
                            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                            <button class="btn btn-outline-success" type="submit">Rechercher</button>
                        </form>
                    </div>
                </div>
            </nav>
        </c:when>

        <c:when test="${empty(sessionScope.utilisateur)}">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <a class="navbar-brand" href="<%=request.getContextPath()%>/accueil">
                        <img src="https://img2.freepng.fr/20180420/xrq/kisspng-cat-spacecraft-drawing-outer-space-clip-art-spaceship-5adaadfe72c5c1.7562180915242808304701.jpg"
                             height="100px" width="100px" alt="Logo du site encheres" width="30" height="24"></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarBasic">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page"
                                   href="<%=request.getContextPath()%>/inscription">S'inscrire</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=request.getContextPath()%>/login">Se connecter</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </c:when>
    </c:choose>

</header>

</html>