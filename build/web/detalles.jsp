<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1" %>
<%  String variable = (String)session.getAttribute("error");
    String e_sesion = (String)session.getAttribute("serror");
    String url = request.getParameter("dir");
%>
 <html lang="es">
        <head>
            <title>SMA-OA UNL</title>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
            <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
            <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
            <script src="assets/js/busqueda.js"></script>
            <link rel="stylesheet" type="text/css" href="assets/css/detalles.css"/>
            
            <%if(variable!=null)
                    out.print("<script>$(document).ready(function(){$(\"#registroModal\").modal(\"show\");});</script>");
              if(e_sesion!=null)
                    out.print("<script>$(document).ready(function(){$(\"#iniciarSesion\").modal(\"show\");});</script>");
            %>
        </head>
   
        <jsp:include page="vistas/nav.jsp"/>
        <jsp:include page="vistas/searchbar.jsp"/>
       
    <body>
        <div class="objeto">
            <iframe src="<%out.print(url);%>.full" width="80%" height="600" style="margin: 1% 10%;" webkitAllowFullScreen="true" allowfullscreen="true" mozallowfullscreen="true"></iframe>
        </div>
        <div class="metadatos">
            <div class="m_contenedor">
                <h3></h3>
            </div>
        </div>
    </body>
    <jsp:include page="vistas/footer.jsp"/>
</html>
