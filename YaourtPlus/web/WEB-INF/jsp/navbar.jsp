<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <c:set var="active" value='${fn:endsWith(pageContext.request.requestURI, "mur.jsp")}' />
            <li class="${active ? 'active' : 'none'}">
                <a href="${pageContext.servletContext.contextPath}/mur.htm">Accueil</a>
            </li>
            <c:set var="active" value='${fn:endsWith(pageContext.request.requestURI, "filous.jsp")}' /> 
            <li class="${active ? 'active' : 'none'}"> 
                <a href="${pageContext.servletContext.contextPath}/filous.htm"> Ptits Filous</a>
            </li>
            <c:set var="active" value='${fn:endsWith(pageContext.request.requestURI, "notifications.jsp")}' />
            <li class="${active ? 'active' : 'none'}"> 
                <a href="${pageContext.servletContext.contextPath}/notifications.htm"> Notifications ${nbNotif}</a>
            </li>
            <c:set var="active" value='${fn:endsWith(pageContext.request.requestURI, "statuts.jsp")}' />
            <li class="${active ? 'active' : 'none'}"> 
                <a href="${pageContext.servletContext.contextPath}/statuts.htm?idPersonne=${idPersonne}"> Mon mur </a>
            </li>
            
            <c:set var="active" value='${fn:endsWith(pageContext.request.requestURI, "messages.jsp")}' />
            <li class="${active ? 'active' : 'none'}"> 
                <a href="${pageContext.servletContext.contextPath}/message.htm?idDestinataire=${idDestinataire}"> Messages</a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class=""> 
                <a href="${pageContext.servletContext.contextPath}/deconnexion.htm">Déconnexion</a>
            </li>
        </ul> 
    </div>
</nav>