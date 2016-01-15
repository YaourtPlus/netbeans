<%-- 
    Document   : filous.jsp
    Created on : 3 déc. 2015, 08:49:08
    Author     : Olivier
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/bootstrap-3.3.6-dist/css/bootstrap.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/tuto.css" type="text/css" media="screen" />
        <title>Mes ptits filous !</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/jsp/navbar.jsp" />

        <div class="validMsg"> ${ajoutFilous}</div>   
        <c:forEach items="${listPersonne}" var="filou" >
            <div class="container">
                <div class="row">
                    <div class="col-lg-offset-1 col-lg-10">
                        ${filou.prenom} ${filou.nom}
                        <a href="${pageContext.request.contextPath}/ajout.htm?id=${filou.id}"> Ajouter </a>
                    </div>
                </div>
            </div>
        </c:forEach>    
    </body>
</html>
