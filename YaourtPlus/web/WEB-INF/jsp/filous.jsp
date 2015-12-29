<%-- 
    Document   : filous.jsp
    Created on : 3 déc. 2015, 08:49:08
    Author     : Olivier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="bootstrap/bootstrap-3.3.6-dist/css/bootstrap.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="tuto.css" type="text/css" media="screen" />
        <title>Mes ptits filous !</title>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top container-fluid">
            <div class="container-fluid">
                <ul class="nav navbar-nav">
                    <li> 
                        <a href="mur.htm">Mon mur</a>
                    </li>
                    <li class="active"> 
                         <a href="filous.htm"> Ptits Filous</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="navbar-right"> 
                        <a href="deconnexion.htm">Déconnexion</a>
                    </li>
                </ul>
            </div>
        </nav>
        
        <p> ${ajoutFilous}</p>
        <div class="container">
            <div class="row">
                ${listFilous}
            </div>
            
        </div>
            <div>
                <a href="mur.htm">Retour au profil</a>
            </div>
    </body>
</html>
