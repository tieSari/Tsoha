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
                <input list="aiheet" name="ryhma">
                <datalist id="aiheet">
                    <c:forEach var="ryhma" items="${ryhmat}">
                        <option value="${ryhma.tunnus}" >${ryhma.nimi}</option>
                        </c:forEach>
                </datalist>
            </div>
        </div>

        <div class="panel panel-info">
            <div class="panel-heading">Kirjoita viesti</div>
            <div class="panel-body">
                <div class="row">
                    Otsikko: <input type="text" name="otsikko">
                </div>
                <p></p>
                <div class="row">
                    <textarea cols="60" rows="10" name="teksti"></textarea>
                </div>
                <div >
                    <button type="submit" class="btn btn-default">Lähetä</button>
                </div>
            </div>
        </div>
    </form>
</t:pohja>