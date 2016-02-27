<%-- 
    Document   : Mur
    Created on : 3 déc. 2015, 08:41:22
    Author     : Olivier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <c:if test="${not empty listFilous}"> 
                    <div class="col-lg-2">
                        <c:forEach items="${listFilous}" var="filou">
                            <ul class="list-unstyled">
                                <li>
                                    <a href="${pageContext.request.contextPath}/statuts.htm?idPersonne=${filou.id}"> ${filou.prenom} ${filou.nom}</a>
                                    <a href="${pageContext.request.contextPath}/suppression.htm?idPersonne=${filou.id}"> Supprimer </a>
                                </li>
                            </ul>
                        </c:forEach>
                    </div>
                </c:if>
                <c:set var="className" value=""/>
                <c:if test="${empty listFilous}">
                    <c:set var="className" value="col-lg-offset-2"/>
                </c:if>
                <div class="post col-lg-6 ${className}">   
                    <form Method="POST" action="${pageContext.request.contextPath}/mur/ajoutStatut.htm?idPersonne=${idPersonne}" enctype="multipart/form-data">
                        <textarea rows="5" cols="150" name='statut' id='statut' class="form-control"
                                  placeholder="Ajouter un ptit statut" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Ajouter un ptit statut'"></textarea>
                        <div id="connectButton">
                            <input type="file" name="file"/>
                            <button type="submit" value="Publier" name="submit" class="btn btn-primary"/>Publier</button>
                        </div>
                    </form>
                    <div>
                        <c:forEach items="${listStatuts}" var="statut" >
                            <div class="statuts">
                                <div class="row">
                                    <div class="pull-left statut-left">
                                        ${statut.auteur.prenom} ${statut.auteur.nom}
                                    </div>

                                    <div class="pull-right statut-right">
                                        ${statut.date}
                                    </div>

                                </div>

                                <div class="statuts-texte">
                                    ${statut.texte}
                                </div>
                                <div class="vote">
                                    <c:forEach items="${statut.listeFichiers}" var="fichier" >
                                        <a href="${pageContext.request.contextPath}/files/${fichier.nom}"> ${fichier.contenu} </a>
                                    </c:forEach>


                                    <c:choose>
                                        <c:when test="${empty statut.destinataire}">
                                            <c:set var="idDestinataire" value="${statut.auteur.id}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="idDestinataire" value="${statut.destinataire.id}"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class=row>
                                        <c:choose>
                                            <c:when test="${statut.nbLeger > 1}">
                                                ${statut.nbLeger} legers
                                            </c:when>
                                            <c:when test="${statut.nbLeger <= 1}">
                                                ${statut.nbLeger} leger
                                            </c:when>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${statut.nbLourd > 1}">
                                                ${statut.nbLourd} lourds
                                            </c:when>
                                            <c:when test="${statut.nbLourd <= 1}">
                                                ${statut.nbLourd} lourds
                                            </c:when>
                                            <c:otherwise>

                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <c:choose>
                                        <c:when test="${statut.statutsActeurs.size() > 0}">   
                                            <c:forEach items="${statut.statutsActeurs}" var="ps" >
                                                <c:choose>
                                                    <c:when test="${ps.personne.id == idPersonne && ps.statut.id == statut.id}">
                                                        <c:choose>
                                                            <c:when test="${ps.typeAction.id == 0}">
                                                                <a href='${pageContext.request.contextPath}/path/leger.htm?id=${statut.id}&idPersonne=${idDestinataire}'> Léger! </a>
                                                                <a href='${pageContext.request.contextPath}/path/lourd.htm?id=${statut.id}&idPersonne=${idDestinataire}'> T'es lourd!</a>
                                                            </c:when>
                                                            <c:when test="${ps.typeAction.id == 1}">
                                                                <a href='${pageContext.request.contextPath}/path/removeAction.htm?id=${statut.id}&idPersonne=${idDestinataire}'> Vous avez allégé le statut. </a>
                                                            </c:when>
                                                            <c:when test="${ps.typeAction.id == 2}">
                                                                <a href='${pageContext.request.contextPath}/path/removeAction.htm?id=${statut.id}&idPersonne=${idDestinataire}'> Vous avez allourdi le statut. </a>
                                                            </c:when>
                                                            <c:otherwise>
                                                            </c:otherwise>

                                                        </c:choose>
                                                    </c:when>
                                                   
                                                </c:choose>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <a href='${pageContext.request.contextPath}/path/leger.htm?id=${statut.id}&idPersonne=${idDestinataire}'> Léger! </a>
                                            <a href='${pageContext.request.contextPath}/path/lourd.htm?id=${statut.id}&idPersonne=${idDestinataire}'> T'es lourd!</a> 
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <c:forEach items="${statut.commentaires}" var="commentaire" >
                                <div class="commentaires">
                                    ${commentaire.auteur.prenom} ${commentaire.auteur.nom}
                                    <div class="commentaire-texte">
                                        ${commentaire.texte}
                                    </div>
                                    <div>
                                        ${commentaire.date}
                                    </div>
                                </div>

                            </c:forEach>
                            <form Method='POST' action='${pageContext.request.contextPath}/mur/ajoutCommentaire.htm?idStatut=${statuts.id}&idPersonne=${idDestinataire}'>
                                <textarea rows='3' cols='75' name='commentaire'
                                          id='commentaire' class='form-control pull-left'
                                          placeholder='Ajouter un commentaire'
                                          onfocus="this.placeholder = ''"
                                          onblur="this.placeholder = 'Ajouter un ptit statut'">
                                </textarea>
                                <div id='connectButton'>
                                    <input type='submit' value='Publier' name='submit'/>
                                </div>
                            </form>
                        </c:forEach>
                    </div>
                </div>
                <c:if test="${not empty listFilous}">
                    <div class="col-lg-4">
                        <form Method="POST" action="${pageContext.request.contextPath}/mur/ajoutMessage.htm">
                            <select name="idDestinataire" id="idDestinataire" class="form-control">
                                ${selectUserList}
                            </select>
                            <textarea rows="2" cols="150" name='message' id='message' class="form-control"
                                      placeholder="Envoyer un message" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Envoyer un message'"></textarea>
                            <div id="connectButton">
                                <button type="submit" value="Envoyer" name="submit" class="btn btn-primary"/>Envoyer</button>
                            </div>
                        </form>
                        <div>
                            ${listMessages}
                        </div>
                    </div>
                </c:if>
            </div> <!-- Fin div row -->
        </div> <!-- Fin div fluid -->
    </body>
</html>
