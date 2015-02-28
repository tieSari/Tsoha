<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pohja pageTitle="Aiheryhmien muokkaus" rooli="${sessionScope.kirjautunut.rooli}">
    <ul class="nav nav-tabs">
        <li><a href="./HaeRyhmanTiedot?lisays=true">Ryhmän lisäys</a></li>
        <li class="active"><a href="#">Ryhmän muokkaus</a></li>
    </ul>
    <div class="panel panel-info">
        <div class="panel-heading"><h2>Ryhmän muokkaus</h2> </div>
        <div class="panel-body">
            <form action="HaeRyhmanTiedot" method="POST">

                <p>
                    Valitse muokattava ryhmä:

                    <select  name="ryhma">
                        <c:forEach var="ryhma" items="${ryhmat}">
                            <option value="${ryhma.tunnus}"> ${ryhma.nimi}</option>
                        </c:forEach>
                    </select>
                </p>
                <p></p>
                <button type="submit" class="btn btn-default">Hae ryhman tiedot</button>
            </form>
        </div>
    </div>

</t:pohja>