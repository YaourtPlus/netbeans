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
        <link rel="stylesheet" href="bootstrap/bootstrap-3.3.6-dist/css/bootstrap.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="tuto.css" type="text/css" media="screen" />
        <title>JSP Page</title>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top container-fluid">
            <div class="container-fluid">
                <ul class="nav navbar-nav">
                    <li class="active"> 
                        <a href="mur.htm">Mon mur</a>
                    </li>
                    <li> 
                         <a href="filous.htm"> Ptits Filous</a>
                    </li>
                    <li> 
                         <a href="notifications.htm"> Notifications </a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="navbar-right"> 
                        <a href="deconnexion.htm">Déconnexion</a>
                    </li>
                </ul>
            </div>
        </nav>

        <header class="page-header container-fluid">
            <h1>${nomPersonne}</h1>
        </header>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-2">
                    <div> ${listeAmi} </div>
                </div>
                <div class="col-lg-6">   
                    <form Method="POST" action="ajoutStatut.htm">
                    <textarea rows="5" cols="150" name='statut' id='statut' class="form-control"
                        placeholder="Ajouter un ptit statut" onfocus="this.placeholder='' " onblur="this.placeholder='Ajouter un ptit statut'"></textarea>
                    <div id="connectButton">
                        <input type="submit" value="Publier" name="submit" />
                    </div>
                    </form>
                    <div>
                       ${listStatuts}
                    </div>
                </div>
                <div class="col-lg-4">
                    <p>Messages</p>
                </div>
            </div>
        </div>
    </body>
</html>
