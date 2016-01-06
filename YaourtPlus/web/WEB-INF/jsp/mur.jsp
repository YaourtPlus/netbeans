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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/bootstrap-3.3.6-dist/css/bootstrap.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/tuto.css" type="text/css" media="screen" />
        <title>Mon Mur</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
        
        <header class="page-header container-fluid">
            <h1>${nomPersonne}</h1>
        </header>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-2">
                    <div> ${listeAmi} </div>
                </div>
                <div class="post col-lg-6">   
                    <form Method="POST" action="${pageContext.request.contextPath}/mur/ajoutStatut.htm?idPersonne=${idPersonne}" enctype="multipart/form-data">
                        <textarea rows="5" cols="150" name='statut' id='statut' class="form-control"
                                  placeholder="Ajouter un ptit statut" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Ajouter un ptit statut'"></textarea>
                        <div id="connectButton">
                            <input type="file" name="file"/>
                            <input type="submit" value="Publier" name="submit" />
                        </div>
                    </form>
                    <div>
                        ${listStatuts}
                    </div>
                </div>
                <div class="col-lg-4">
                    <form Method="POST" action="ajoutMessage.htm">
                        <select name="destinataireMessage" id="destinataireMessage">
                            ${selectUserList}
                        </select>
                        <textarea rows="2" cols="150" name='statut' id='message' class="form-control"
                                  placeholder="Envoyer un message" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Envoyer un message'"></textarea>
                        <div id="connectButton">
                            <input type="submit" value="Envoyer" name="submit" />
                        </div>
                    </form>
                    <div>
                        <hr />
                        ${listMessages}
                    </div>
                </div>
                   
            </div>
        </div>
    </body>
</html>
