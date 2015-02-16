<%-- 
    Document   : Kirjautuminen
    Created on : 31-Jan-2015, 06:43:02
    Author     : sariraut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Kirjaudu">

    <form action="Kirjautuminen" method="POST">
        <table>
            <tr>
                <td> Käyttäjänimi: </td>
                <td><input type="text" name="username" value="${kayttaja}"/></td>
            </tr>
            <tr>
                <td> Salasana: </td>
                <td><input type="password" name="password" /></td>
            </tr>
            <tr>
                <td></td> 
                <td> <button type="submit">Kirjaudu</button></td>
            <tr>
        </table>
    </form>
</t:pohja>
