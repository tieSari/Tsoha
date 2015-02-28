<%-- 
    Document   : jasenenTiedot
    Created on : 07-Feb-2015, 15:05:54
    Author     : sariraut
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Jäsenen tiedot" rooli="${sessionScope.kirjautunut.rooli}">
    <ul class="nav nav-tabs">
        <li><a href="./HaeRyhmanTiedot?lisays=true">Ryhmän lisäys</a></li>
        <li class="active"><a href="#">Ryhmän muokkaus</a></li>
    </ul>
    <div class="panel panel-info">
        <div class="panel-heading">Ryhmän tiedot </div>
        <div class="panel-body">
            <div >
                <form action="PaivitaRyhma" method="POST">
                    <input type="hidden" value="${ryhma.tunnus}" name="tunnus">
                    <table>
                        <tr>

                            <td>Nimi: </td><td><input type="text" value="${ryhma.nimi}" name="nimi"></td>

                        </tr>
                        <tr>
                            <td>Kuvaus: </td><td>
                                <textarea cols="60" rows="8" name="kuvaus">${ryhma.kuvaus}</textarea></td>
                        </tr>

                        <tr>
                            <td>
                                Lisää käyttäjät:
                            </td>
                            <td>                               
                                <select multiple name="kayttajat">
                                    <c:forEach var="kayttaja" items="${kayttajat}">
                                        <option  selected="${kayttaja.selected}" value="${kayttaja.id}">${kayttaja.etunimi}&ThinSpace; ${kayttaja.sukunimi}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <button type="submit" class="btn btn-default">Päivitä tiedot</button>
                            </td>

                        </tr>
                    </table>
                </form>
                <form action="PoistaRyhma" method="POST">
                    <input type="hidden" value="${ryhma.tunnus}" name="tunnus">
                    
                    <button type="submit" class="btn btn-default">Poista ryhmä</button>
                    <b>Huom! Kun poistat ryhmän, poistat samalla myös ryhmän viestit!!!</b>
                </form>
            </div>
        </div>
    </div>
</t:pohja>
