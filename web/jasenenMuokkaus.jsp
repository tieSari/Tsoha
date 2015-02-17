<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pohja pageTitle="Jäsentietojen muokkaus" rooli="${sessionScope.kirjautunut.rooli}">
    <ul class="nav nav-tabs">
        <li><a href="jasenenLisays.jsp">Käyttäjän lisäys</a></li>
        <li class="active"><a href="#">Käyttäjätietojen muokkaus</a></li>
    </ul>
    <div class="panel panel-info">
        <div class="panel-heading"><h2>Käyttäjätietojen muokkaus</h2> </div>
        <div class="panel-body">
            <form action="HaeKayttajanTiedot" method="POST">

                <p>
                    Valitse muokattava käyttäjä:

                    <input list="kayttajat" name="jasen">
                    <datalist id="kayttajat">
                        <c:forEach var="jasen" items="${kayttajat}">
                            <option value="${jasen.id}"> ${jasen.etunimi}  ${jasen.sukunimi}</option>
                        </c:forEach>
                    </datalist>
                </p>
                <p></p>
                <button type="submit" class="btn btn-default">Hae käyttäjän tiedot</button>
            </form>
        </div>
    </div>

</t:pohja>