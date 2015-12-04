<%-- 
    Document   : connexion
    Created on : 4 déc. 2015, 11:23:14
    Author     : Olivier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Connexion au réseau léger</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="foundation.css" type="text/css" media="screen" /> 
    </head>
    <body>
        <div>
            <form Method="POST" action="mur.htm">
                <label for="login"> login : </label>
                <input type="text" value="Entrez votre login" name="login" />
                <br />
                
                <label for="password"> mot de passe : </label>
                <input type="password" name="password" />
                <br />
                
                <input type="submit" value="connexion" name="submit" />
                <br />
            </form>
            <p> Pas encore inscrit? Inscrivez-vous <a href="inscription.html">par ici!</a> C'est gratuit!</p>
        </div>
    </body>
</html>
