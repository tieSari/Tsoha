<%-- 
    Document   : jasenenTiedot
    Created on : 07-Feb-2015, 15:05:54
    Author     : sariraut
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Ryhmän lisäys" rooli="${sessionScope.kirjautunut.rooli}">
    <ul class="nav nav-tabs">
        <li><a href="./HaeRyhmat">Ryhmän muokkaus</a></li>
        <li class="active"><a href="#">Ryhmän lisäys</a></li>
    </ul>
    <div class="panel panel-info">
        <div class="panel-heading">Ryhmän tiedot </div>
        <div class="panel-body">
            <div >
                <form action="./LisaaRyhma" method="POST">
                    <table>
                        <tr>

                            <td>Aiheryhmän nimi: </td><td><input type="text" value="${ryhma.nimi}" name="nimi"></td>
                        </tr>
                        <tr>
                            <td>Kuvaus: </td><td>
                                <textarea cols="50" rows="5" name="kuvaus">${ryhma.kuvaus}</textarea></td>
                        </tr>
                        <tr>
                            <td>
                                Lisää käyttäjät:
                            </td>
                            <td>
                                <select multiple name="kayttajat">
                                    <c:forEach var="kayttaja" items="${kayttajat}">
                                        <option value="${kayttaja.id}">${kayttaja.etunimi}&ThinSpace; ${kayttaja.sukunimi}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <button type="submit" class="btn btn-default">Tallenna</button>
                            </td>

                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</t:pohja>

