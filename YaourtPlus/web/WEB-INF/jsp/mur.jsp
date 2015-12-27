<%-- 
    Document   : Mur
    Created on : 3 déc. 2015, 08:41:22
    Author     : Olivier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Mur ${nomPersonne}!</h1>
        <div> ${listeAmi} </div>
        <a href="filous.htm"> Ptits Filous</a>
        <p>
            ${murMessage}
        </p>
        <form Method="POST" action="ajoutStatut.htm">
        <textarea rows="5" cols="150" 
            placeholder="Ajouter un ptit statut" onfocus="this.placeholder='' " onblur="this.placeholder='Ajouter un ptit statut'">
        </textarea>
        <div id="connectButton">
            <input type="submit" value="Publier" name="submit" />
        </div>
        </form>
        <p>
            ${listStatus}
        </p>
    </body>
</html>
