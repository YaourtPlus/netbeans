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
        <link rel="stylesheet" href="bootstrap/bootstrap-3.3.6-dist/css/bootstrap.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="tuto.css" type="text/css" media="screen" />
    </head>
    <body>

        <div class="jumbotron" style="text-align:center">Bienvenue sur le premier réseau social allégé!</div>
        <div>${inscriptionMessage}</div>
        <div class="col-lg-offset-4 col-lg-4">
            <form Method="POST" action="connexion.htm">
                <label for="login"> login : </label>
                <input type="text" placeholder="Entrez votre login" onfocus="this.placeholder='' " onblur="this.placeholder='Entrez votre login' " name="login" id ="login" />
                <br />
                <br />

                <label for="password"> mot de passe : </label>
                <input type="password" name="password" id="password"/>
                <br />
                <br />

                <div id="connectButton">
                    <input type="submit" value="connexion" name="submit" />
                </div>
            </form>
            <br/>
            <p> Pas encore inscrit? Inscrivez-vous <a href="${pageContext.servletContext.contextPath}/inscription.htm">par ici!</a> C'est gratuit!</p>
        </div>


    </body>
</html>
