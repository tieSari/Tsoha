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
        <li><a href="./HaeKayttajat">Käyttäjän tietojen muokkaus</a></li>
        <li class="active"><a href="#">Käyttäjän lisäys</a></li>
    </ul>
    <div class="panel panel-info">
        <div class="panel-heading">Käyttäjän tiedot </div>
        <div class="panel-body">
            <div >
                <form action="./Toteuttamatta" method="POST">
                    <table>
                        <tr>

                            <td>Etunimi: </td><td><input type="text"  name="etunimi"></td>

                        </tr>
                        <tr>
                            <td>Sukunimi: </td><td><input type="text"  name="sukunimi"></td>
                        </tr>
                        <tr>
                            <td>Käyttäjätunnus:</td><td> <input type="text" name="tunnus"></td>
                        </tr>
                        <tr>
                            <td>Salasana: </td><td><input type="text"  name="salasana"></td>
                        </tr>
                        <tr>
                            <td>Rooli: </td><td><input type="text" name="rooli"></td>
                        </tr>
                        <tr>
                            <td>
                                Lisää ryhmät:
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
                                <button type="submit" class="btn btn-default">Tallenna</button>
                            </td>

                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</t:pohja>
