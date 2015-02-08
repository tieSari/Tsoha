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
        <li><a href="./Toteuttamatta">Käyttäjän lisäys</a></li>
        <li class="active"><a href="#">Käyttäjätietojen muokkaus</a></li>
    </ul>
    <div class="panel panel-info">
        <div class="panel-heading">Käyttäjän tiedot </div>
        <div class="panel-body">
            <div >
                <form action="PaivitaKayttaja" method="POST">
                    <input type="hidden" value="${kayttaja.id}" name="id">
                    <table>
                        <tr>

                            <td>Etunimi: </td><td><input type="text" value="${kayttaja.etunimi}" name="etunimi"></td>

                        </tr>
                        <tr>
                            <td>Sukunimi: </td><td><input type="text"  value="${kayttaja.sukunimi}" name="sukunimi"></td>
                        </tr>
                        <tr>
                            <td>Käyttäjätunnus:</td><td> <input type="text"  value="${kayttaja.tunnus}" name="tunnus"></td>
                        </tr>
                        <tr>
                            <td>Salasana: </td><td><input type="text"  value="${kayttaja.salasana}" name="salasana"></td>
                        </tr>
                                                <tr>
                            <td>Rooli: </td><td><input type="text"  value="${kayttaja.rooli}" name="rooli"></td>
                        </tr>
                        <tr>
                            <td>
                                Lisää ryhmät:
                                (tämä ei vielä oikeasti lisää ryhmiä)
                            </td>
                            <td>
                                <input list="aiheet" name="ryhma">
                                <datalist id="aiheet">
                                    <c:forEach var="ryhma" items="${ryhmat}">
                                        <option value="${ryhma.tunnus}" >${ryhma.nimi}</option>
                                    </c:forEach>
                                </datalist>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <button type="submit" class="btn btn-default">Päivitä tiedot</button>
                            </td>

                        </tr>
                    </table>
                </form>
 <!--               <form action="PoistaKayttaja?${kayttaja.id}" method="POST">
                    <button type="submit" class="btn btn-default">Poista käyttäjä</button>
                </form> -->
            </div>
        </div>
    </div>
</t:pohja>
