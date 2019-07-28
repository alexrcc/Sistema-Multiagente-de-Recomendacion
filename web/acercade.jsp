<%@ page contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1" %>
<%  String variable = (String)session.getAttribute("error");
    String e_sesion = (String)session.getAttribute("serror");
    String user = (String)session.getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SMA-OA UNL</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="assets/js/busqueda.js"></script>
        <link rel="stylesheet" type="text/css" href="assets/css/search.css"/>
        <link rel="stylesheet" type="text/css" href="assets/css/acercade.css"/>

        <%if(variable!=null)
                out.print("<script>$(document).ready(function(){$(\"#registroModal\").modal(\"show\");});</script>");
          if(e_sesion!=null)
                out.print("<script>$(document).ready(function(){$(\"#iniciarSesion\").modal(\"show\");});</script>");
        %>
    </head>

    <jsp:include page="vistas/nav.jsp"/>
    <body>
        <div class='acerca'>
        <span><b>El Sistema Multiagente Web (smaweb)</b>, es un prototipo 
        desarrollado para la recomendación de objetos de aprendizaje. Este proyecto nace a partir 
        del trabajo de titulación "Agentes inteligentes para recomendación de recursos digitales
        de aprendizaje", el smaweb está desarrollado sobre la plataforma JADE y su interfaz diseñada 
        en páginas jsp.<br><br>
        Los objetos de aprendizaje son recuperados de repositorios basados en la plataforma ViSH, actualmente
        el sitio trabaja con los repositorios ROA-CEDIA, ViSH y EducaInternet.
        
        </span>
        </div>
    </body>
       <jsp:include page="vistas/footer.jsp"/>
</html>
