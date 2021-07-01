<%--
  Created by IntelliJ IDEA.
  User: cgrandval2021
  Date: 01/07/2021
  Time: 09:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success !</title>
    <link type="text/css" rel="stylesheet" href="./styles/loginStyles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<!-- affichage du message de confirmation pour la modification de l'article-->
<c:if test="${!empty msgConfirmationModif}">
    <div class="alert alert-success d-flex justify-content-center" role="alert">
        <p class="mx-auto">${msgConfirmationModif}</p>
    </div>
</c:if>

<div class="col-sm-12 m-2 w-50">
    <a href="<%=request.getContextPath()%>/accueil">
        <button class="btn btn-secondary w-100">Retour</button>
    </a>
</div>

</body>
</html>
