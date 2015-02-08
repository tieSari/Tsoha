<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Etusivu" rooli="${sessionScope.kirjautunut.rooli}">
    <h3>Ajankohtaista</h3>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Aihe</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="ryhma" items="${ryhmat}">
                <tr>
                    <td><h3><div class="ryhma"> ${ryhma.nimi}</div></h3></td>
                    <td><a href="./UusiViestiketju">Aloita uusi keskustelu </a> 
                        <span class="glyphicon glyphicon-plus"></span></td>
                </tr> 
                <c:forEach var="viesti" items="${ryhma.viestit}">
                    <tr>
                        <td>
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>
                                        <a href="keskustelu.html"> ${viesti.otsikko}</a>
                                        <c:if test="${(sessionScope.kirjautunut.rooli) == 'yllapitaja'
                                                      && (viesti.paaviesti==0)}">
                                              <td> <a href="./PoistaViestiketju?tunnus=${viesti.tunnus}">poista ketju</a> </td> 
                                        </c:if>
                                        </th> 
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>${viesti.kirjoittajaNimi}&Tab; ${viesti.kirjoituspvm}</td>
                                    </tr>

                                </tbody>
                            </table>
                    </tr>
                </c:forEach>
            </c:forEach>
        </tbody>
    </table>
</t:pohja>