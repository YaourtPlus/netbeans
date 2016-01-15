<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body>
        <form Method="POST" action="connexion.htm">
                <label for="login"> login : </label>
                <input type="text" placeholder="Entrez votre login" onfocus="this.placeholder='' " onblur="this.placeholder='Entrez votre login' " name="login" id ="login" />
                <br />
                <br />

                <label for="password"> mot de passe : </label>
                <input type="password" name="password" id="password"/>
                <br />
                <br />

                <div id="connectButton">
                    <input type="submit" value="connexion" name="submit" />
                </div>
            </form>
    </body>
</html>
