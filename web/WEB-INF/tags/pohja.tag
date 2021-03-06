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
                        <a href="./Listaus"> <h1><span class="glyphicon glyphicon-queen">&MediumSpace;SporttiFoorumi&MediumSpace;<span class="glyphicon glyphicon-queen"></h1></a>
                                        </div>
                                        <c:if test="${sessionScope.kirjautunut.rooli == 'yllapitaja'}">

                                            <div class="col-md-2">
                                                Ylläpito
                                                <ul> 
                                                    <li><a class="pieni" href="./HaeKayttajat">Jäsenasiat</a></li>
                                                    <li><a class="pieni" href="./HaeRyhmat">Aiheiden muokkaus</a></li>
                                                </ul>
                                            </div>
                                        </c:if>
                                        <div class="col-md-2">
                                            <c:if test="${sessionScope.kirjautunut!=null}">
                                                <table>
                                                    <tr><td>${sessionScope.kirjautunut.etunimi}</td></tr>
                                                    <tr><td><a href="./KirjauduUlos"> Kirjaudu ulos</a></td></tr>
                                                </table>
                                            </c:if>
                                        </div>
                                        </div>
                                        </div>
                                        </div>

                                        <div class="container">
                                            <c:if test="${virheViesti != null}">
                                                <div class="alert alert-danger">${virheViesti}</div>
                                            </c:if>
                                            <c:if test="${infoViesti != null}">
                                                <div class="alert alert-info">${infoViesti}</div>
                                            </c:if>
                                            <jsp:doBody/>
                                        </div>
                                        </body>
                                        </html>