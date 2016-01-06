<%-- 
    Document   : statuts
    Created on : 5 janv. 2016, 15:31:46
    Author     : tbenoist
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/bootstrap-3.3.6-dist/css/bootstrap.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/tuto.css" type="text/css" media="screen" />
        <title>Mes statuts</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
        <div>
            ${nomPersonne}
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-offset-2 col-lg-6">
                    <form Method="POST" action="${pageContext.request.contextPath}/statut/ajoutStatut.htm?idPersonne=${idProprietaire}" enctype="multipart/form-data">
                        <textarea rows="5" cols="150" name='statut' id='statut' class="form-control"
                                  placeholder="Ajouter un ptit statut" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Ajouter un ptit statut'"></textarea>
                        <div id="connectButton">
                            <input type="file" name="file"/>
                            <input type="submit" value="Publier" name="submit" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <c:if test="${sizeStatutsEmis > 0}">
            <div class="col-lg-6">
                ${listStatutsEmis}
            </div>
        </c:if>

        <c:if test="${sizeStatutsRecu > 0}">
            <c:choose>
                <c:when test="${sizeStatutsEmis > 0}">
                    <div class="col-lg-6">
                        ${listStatutsRecu}
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-offset-6 col-lg-6">
                        ${listStatutsRecu}
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>

</body>
</html>
