<%--
  Created by IntelliJ IDEA.
  User: cgrandval2021
  Date: 24/06/2021
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="./styles/404Styles.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Page non trouvée</title>
</head>
<body>
<div class="p-20">
<p class="pt-12 text-center text-white">Oups ! La page demandée n'existe ni dans cet univers ni dans un autre...</p>
</div>
<jsp:include page="/WEB-INF/fragments/footer.jsp"/>
</body>
</html>
