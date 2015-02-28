<%-- 
    Document   : keskustelu
    Created on : 11-Feb-2015, 07:27:26
    Author     : sariraut
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Etusivu" rooli="${sessionScope.kirjautunut.rooli}">

    <div class="container">
        <form action="TallennaViesti" method="POST">
            <div class="row">
                <div class="col-md-10">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <td><b>${viestiketju[0].otsikko}</b></td>
                        <input hidden="true" name="ryhma" value="${viestiketju[0].ryhma}">
                        <input hidden="true" name="paaviesti" value="${viestiketju[0].tunnus}">
                        <input hidden="true" name="otsikko" value="${viestiketju[0].otsikko}">
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="viesti" items="${viestiketju}">
                                <tr>
                                    <td>
                                        <div class="panel panel-info">
                                            <div class="panel-heading">
                                                ${viesti.kirjoittajaNimi}&MediumSpace; ${viesti.kirjoituspvm}&MediumSpace; ${viesti.kirjoitusaika}
                                                &MediumSpace;&MediumSpace;
                                                lukijoita &thinsp;${viesti.lukijaLkm} &thinsp;kpl
                                                <span style="float:right;">
                                                    <c:if test="${sessionScope.kirjautunut.rooli == 'yllapitaja' &&  viesti.paaviesti!=0}">
                                                        <a href="./PoistaViesti?tunnus=${viesti.tunnus}&paaviesti=${viesti.paaviesti}">poista viesti &MediumSpace;
                                                            <span class="glyphicon glyphicon-trash"></span>
                                                        </a>
                                                    </c:if>
                                                </span>
                                            </div>
                                            <div class="panel-body">
                                                 ${viesti.teksti}

                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
</div>
<div class="panel panel-default">
    <div class="panel-heading">Lisää viesti ketjuun</div>
    <div class="panel-body">
        <textarea cols="50" rows="5" name="teksti">${sessionScope.teksti}</textarea>
    </div>
    <div >
        <button type="submit" class="btn btn-default">Lähetä</button>
    </div>
</form>
</div>
</t:pohja>