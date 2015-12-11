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
        <link rel="stylesheet" href="cssSite.css" type="text/css" media="screen" /> 
    </head>
    <body>
        <div>${inscriptionMessage}</div>
        <div class="divCentre">
            <form method="POST" action="validation.htm">
                <label for="nom"> nom : </label>
                <input type="text" value="nom" name="nom" />
                <br />
                <label for="prenom"> prenom : </label>
                <input type="text" value="prenom" name="prenom" />
                <br />
                <label for="ddn"> Date de naissance : </label>
                <input type="text" value="01/01/1990" name="ddn"/>
                <br />
                <label for="login"> login : </label>
                <input type="text" value="login" name="login" />
                <br />
                <label for="password"> mot de passe : </label>
                <input type="password" value="" name="password" />
                <br />
                <label for="mail"> mail : </label>
                <input type="text" value="mail" name="mail" />
                <br />
                <input type="submit" value="s'inscrire" />
            </form>
        </div>
    </body>
</html>
