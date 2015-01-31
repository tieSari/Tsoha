<%-- 
    Document   : Kirjautuminen
    Created on : 31-Jan-2015, 06:43:02
    Author     : sariraut
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Kirjaudu">

    <form action="Kirjautuminen" method="POST">
        Käyttäjänimi: <input type="text" name="username" value="${kayttaja}"/>
        <p></p>
        Salasana: <input type="password" name="password" />
        <p></p>
        <button type="submit">Kirjaudu</button>
    </form>
</t:pohja>
