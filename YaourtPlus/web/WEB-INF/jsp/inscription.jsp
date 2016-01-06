<%-- 
    Document   : inscription
    Created on : 10 déc. 2015, 12:09:30
    Author     : Olivier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Inscription légère</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/bootstrap-3.3.6-dist/css/bootstrap.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/tuto.css" type="text/css" media="screen" />
    </head>
    <body>
        <div class="jumbotron" style="text-align:center">Bienvenue sur le premier réseau social allégé!</div>
        <div>${inscriptionMessage}</div>
        <div class="col-lg-offset-4 col-lg-4" style="text-align:center">
            <form method="POST" action="validation.htm">
                <label for="nom"> nom : </label>
                <input type="text" placeholder="Nom" onfocus="this.placeholder='' " onblur="this.placeholder='Nom'" name="nom" />
                <br />
                <label for="prenom"> prenom : </label>
                <input type="text" placeholder="Prenom" onfocus="this.placeholder='' " onblur="this.placeholder='Prenom'" name="prenom" />
                <br />
                <label for="ddn"> Date de naissance : </label>
                <input type="text" placeholder="01/01/1990" onfocus="this.placeholder='' " onblur="this.placeholder='01/011990'" name="ddn"/>
                <br />
                <label for="login"> login : </label>
                <input type="text" placeholder="Login" onfocus="this.placeholder='' " onblur="this.placeholder='Login'" name="login" />
                <br />
                <label for="password"> mot de passe : </label>
                <input type="password" value="" name="password" />
                <br />
                <label for="mail"> mail : </label>
                <input type="text" placeholder="Mail" onfocus="this.placeholder='' " onblur="this.placeholder='Mail'" name="mail" />
                <br />
                <br />
                <br />
                <input type="submit" value="s'inscrire" />
            </form>
        </div>
        <div>
            <a href="connexion.htm"> Retour à la page d'accueil </a>
        </div>
    </body>
</html>
