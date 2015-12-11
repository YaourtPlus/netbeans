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
        <div class="jumbotron" style="text-align:center">Bienvenue sur le premier réseau social allégé!</div>
        <div class="container">
            <div class="row">
                ${listFilous}
            </div>
            
        </div>
    </body>
</html>
