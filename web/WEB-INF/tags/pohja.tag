<%@tag description="Generic template for SporttiFoorumi pages" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle"%>
<%@attribute name="rooli"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <script src="http://code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <title>${pageTitle}</title>
    </head>
    <body>
        <div class="panel panel-default otsikko">
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-6">
                        <a> <h1>SporttiFoorumi</h1></a>
                    </div>
                    <c:if test="${rooli == 'yllapitaja'}">

                        <div class="col-md-2">
                            (Ylläpito)
                            <ul> 
                                <li><a class="pieni" href="jasenasiat.html">Jäsenasiat</a></li>
                                <li><a class="pieni" href="yllapito.html">Aiheiden muokkaus</a></li>
                                <li><a class="pieni" href="etusivu.html">Kirjoitusten poisto</a></li>
                            </ul>
                        </div>
                    </c:if>
                    <div class="col-md-2">
                        <a> Kirjaudu ulos</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <c:if test="${virheViesti != null}">
                <div class="alert alert-danger">${virheViesti}</div>
            </c:if>
            <jsp:doBody/>
        </div>
    </body>
</html>