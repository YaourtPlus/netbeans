<%-- 
    Document   : vueNotif
    Created on : 5 janv. 2016, 18:29:08
    Author     : tbenoist
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/bootstrap-3.3.6-dist/css/bootstrap.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/tuto.css" type="text/css" media="screen" />
        <title>Notification</title>
    </head>

    <body>
        <jsp:include page="/WEB-INF/jsp/navbar.jsp" />
        ${data}
    </body>
</html>