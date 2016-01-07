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
        <div class="col-lg-2">
            <select name="idDestinataire" id="idDestinataire">
                ${listFilous}
            </select>
        </div>
        <c:if test="${not empty filou}">
            <div class="col-lg-offset-2 col-lg-6">
                ${listMessages}
            </div>
        </c:if>
        <c:choose>
            <c:when test="${not empty filou}">
                <div class="col-lg-2">
                    <form Method="POST" action="messages.htm">
                        <textarea rows="2" cols="150" name='statut' id='message' class="form-control"
                                  placeholder="Envoyer un message" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Envoyer un message'"></textarea>
                        <div id="connectButton">
                            <input type="submit" value="Envoyer" name="submit" />
                        </div>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col-lg-offset-8 col-lg-2">
                    <form Method="POST" action="messages.htm">
                        <textarea rows="2" cols="150" name='statut' id='message' class="form-control"
                                  placeholder="Envoyer un message" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Envoyer un message'"></textarea>
                        <div id="connectButton">
                            <input type="submit" value="Envoyer" name="submit" />
                        </div>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
</html>
