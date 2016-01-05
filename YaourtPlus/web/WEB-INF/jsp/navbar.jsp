<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar navbar-inverse navbar-static-top container-fluid">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <c:set var="active" value='${fn:endsWith(pageContext.request.requestURI, "mur.jsp")}' />
            <li class="${active ? 'active' : 'none'}">
                <a href="mur.htm">Mon mur</a>
            </li>
            <c:set var="active" value='${fn:endsWith(pageContext.request.requestURI, "filous.jsp")}' /> 
            <li class="${active ? 'active' : 'none'}"> 
                <a href="filous.htm"> Ptits Filous</a>
            </li>
            <c:set var="active" value='${fn:endsWith(pageContext.request.requestURI, "notifications.jsp")}' />
            <li class="${active ? 'active' : 'none'}"> 
                <a href="notifications.htm"> Notifications ${nbNotif}</a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="navbar-right"> 
                <a href="deconnexion.htm">D�connexion</a>
            </li>
        </ul> 
</nav>