<%-- 
    Document   : messages
    Created on : 6 janv. 2016, 22:29:44
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
        <title>Mes messages</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
        <c:if test="${not empty listFilous}">
            <div class="col-lg-2">
                <form Method="POST" action="${pageContext.request.contextPath}/message.htm">
                    <select name="idDestinataire" id="idDestinataire" class="form-control">
                        ${listFilous}
                    </select>
                    <div id="connectButton">
                        <button type="submit" value="Selectionner" name="submit" class="btn btn-primary"/>Selectionner</button>
                    </div>
                </form>
            </div>
        </c:if>
        <c:if test="${not empty filou}">
            <div class="col-lg-8 noDisplay">
                ${listMessages}
            </div>
        </c:if>
        <c:if test="${not empty filou}">
            <div class="col-lg-2">
                <form Method="POST" action="${pageContext.request.contextPath}/messages/ajoutMessage.htm">
                    <input type="hidden" name="idDestinataire" value="${idDestinataire}">
                    <textarea rows="2" cols="150" name='message' id='message' class="form-control"
                              placeholder="Envoyer un message" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Envoyer un message'"></textarea>
                    <div id="connectButton">
                        <button type="submit" value="Envoyer" name="submit" class="btn btn-primary"/>Envoyer</button>
                    </div>
                </form>
            </div>
        </c:if>
</html>
