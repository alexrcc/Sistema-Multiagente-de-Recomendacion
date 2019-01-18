<%-- 
    Document   : index
    Created on : 04/01/2019, 08:32:12 AM
    Author     : alexr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
            <link rel="stylesheet" href="assets/styles.css"/>
            <title>JSP Page</title>
        </head>
        <body>
            <h1><h:outputText value="Hello World!"/></h1>
            <br/><br/>
        <form action="Servlet" method="POST">
        Ingrese la palabra clave:
        <input type="text" name="nombre" value="…" size="30" />
        <input type="submit" value="Enviar" name="enviar" />
        </form>
        </body>
    </html>
</f:view>
