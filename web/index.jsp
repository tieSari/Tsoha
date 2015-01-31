<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja pageTitle="Etusivu" rooli="${session.kirjautunut.rooli}">
        <h1>Ajankohtaista</h1>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Aihe</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><h3> Juoksu</h3></td><td><a href="uusiViestiketju.html">Aloita uusi keskustelu </a> <span class="glyphicon glyphicon-plus"></span></td>
                </tr>
                <tr>
                    <td>
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>(poista ketju <input type="checkbox">) <a href="keskustelu.html"> Onko yhteislenkille lähtijöitä?</a></th> 
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Saku 20.01.2015 10:25</td>
                                </tr>

                            </tbody>
                        </table>
                </tr>
                <tr>
                    <td> <h3>Luistelu</h3></td><td><a href="uusiViestiketju.html">Aloita uusi keskustelu </a> <span class="glyphicon glyphicon-plus"></span></td>
                </tr>
                <tr>
                    <td>
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>(poista ketju <input type="checkbox">) <a>Retki Espoonlahden jäille</a></th> 
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Kalle Piirainen 19.01.2015 15:22</td>
                                </tr>

                            </tbody>
                        </table>
                </tr>
                <tr>
                    <td> <h3>Suunnistus</h3></td><td><a href="uusiViestiketju.html">Aloita uusi keskustelu </a><span class="glyphicon glyphicon-plus"></span></td>
                </tr>
                <tr>
                    <td>
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>(poista ketju <input type="checkbox">) <a> Jukolaan ensi kesänä</a></th> 
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Mirva 25.01.2015 19:25</td>
                                </tr>

                            </tbody>
                        </table>
                </tr>
            </tbody>
        </table>
</t:pohja>