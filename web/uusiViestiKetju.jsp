<%-- 
    Document   : uusiViestiKetju
    Created on : 06-Feb-2015, 22:03:43
    Author     : sariraut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pohja pageTitle="Uusi viestiketju">
    <form action="TallennaViesti" method="POST">
        <div class="panel panel-info">
            <div class="panel-heading">Valitse aiheryhmä </div>
            <div class="panel-body">
                <select name="ryhma">
                    <c:forEach var="ryhma" items="${ryhmat}">
                        <option value="${ryhma.tunnus}" >${ryhma.nimi}</option>
                        </c:forEach>
                </select>
            </div>
        </div>

        <div class="panel panel-info">
            <div class="panel-heading">Kirjoita viesti</div>
            <div class="panel-body">
                <div class="row">
                    <input hidden="true" name="paaviesti" value="0">
                    Otsikko: <input type="text" name="otsikko" value="${viesti.otsikko}">
                </div>
                <p></p>
                <div class="row">
                    <textarea cols="50" rows="5" name="teksti">${viesti.teksti}</textarea>
                </div>
                <div >
                    <button type="submit" class="btn btn-default">Lähetä</button>
                </div>
            </div>
        </div>
    </form>
</t:pohja>
