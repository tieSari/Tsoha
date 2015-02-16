<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<t:pohja pageTitle="Etusivu" rooli="${sessionScope.kirjautunut.rooli}">
    <h3>Ajankohtaista&MediumSpace;<span class="glyphicon glyphicon-eye-open"></span></h3>
    <span style="float:right;">
        <a href="./Listaus?filter=filter.value"><span class="glyphicon glyphicon-eye-open"></span></a>
        <input type="text" name="filter">
    </span>
    <table class="table table-striped table-condensed">
        <thead>
            <tr>
                <th>Aihe</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="ryhma" items="${ryhmat}">
                <tr>
                    <td><h4><div class="ryhma"> ${ryhma.nimi}</div></h4></td>
                    <td><a href="./UusiViestiketju">Aloita uusi keskustelu  
                            <span class="glyphicon glyphicon-comment"></span></a></td>
                </tr> 
                <c:forEach var="viesti" items="${ryhma.viestit}">
                    <c:if test="${viesti.paaviesti==0}">
                        <tr>
                            <td>
                                <table class="table table-striped table-condensed">
                                    <thead>
                                        <tr class="success" >
                                            <th>
                                                <a href="./Keskustelu?tunnus=${viesti.tunnus}"> ${viesti.otsikko}
                                                    <c:if test="${not viesti.luettu}">&MediumSpace;
                                                        <span class="glyphicon glyphicon-exclamation-sign"></span>
                                                    </c:if>
                                                </a>
                                            <td></td><td></td>
                                    <span style="float:right;">
                                        <c:if test="${(sessionScope.kirjautunut.rooli) == 'yllapitaja'}">
                                            <td> <a href="./PoistaViestiketju?tunnus=${viesti.tunnus}">poista ketju &MediumSpace;<span class="glyphicon glyphicon-trash"></span></a> </td>

                                        </c:if>
                                    </span>
                                    </th> 
                        </tr>
                        </thead>
                    <tbody>
                        <tr class="success pieni">
                            <td>Ketjun aloittaja: ${viesti.kirjoittajaNimi}&MediumSpace; ${viesti.kirjoituspvm}&MediumSpace; ${viesti.kirjoitusaika}</td>
                            <td></td>

                            <td>
                                <c:if test="${fn:length(ryhma.viestit)>1}">
                                    Viimeisin vastaus: 
                                    <c:out value="${ryhma.viestit[fn:length(ryhma.viestit) - 1].kirjoittajaNimi}"/>&MediumSpace; 
                                    <c:out value="${ryhma.viestit[fn:length(ryhma.viestit) - 1].kirjoituspvm}"/>&MediumSpace;
                                    <c:out value="${ryhma.viestit[fn:length(ryhma.viestit) - 1].kirjoitusaika}"/>
                                </c:if>
                            </td>
                            <td></td>

                        </tr>

                    </tbody>
                </table>
            </tr>
        </c:if>
    </c:forEach>
</c:forEach>
</tbody>
</table>
</t:pohja>