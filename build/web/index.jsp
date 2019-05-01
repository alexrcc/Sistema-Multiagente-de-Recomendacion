<%-- 
    Document   : index
    Created on : 04/01/2019, 08:32:12 AM
    Author     : alexr
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1" %>
<%  String variable = (String)session.getAttribute("error");
    String e_sesion = (String)session.getAttribute("serror");
    String user = (String)session.getAttribute("user");
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
            <link rel="stylesheet" type="text/css" href="assets/css/search.css"/>
            
            <%if(variable!=null)
                    out.print("<script>$(document).ready(function(){$(\"#registroModal\").modal(\"show\");});</script>");
              if(e_sesion!=null)
                    out.print("<script>$(document).ready(function(){$(\"#iniciarSesion\").modal(\"show\");});</script>");
            %>
        </head>
<f:view>
   
        <jsp:include page="vistas/nav.jsp"/>
       
        <body>
            <div id="fondo">
                
                    <div id="izq">
                        <span><bold>El sistema de recomendación de Objetos de Aprendizaje</bold>
                            brinda una búsqueda según su estilo de aprendizaje, para ello se recomienda que registre su Perfil</span>
                    </div>

                    <!--<div id="der">
                        <a href="#" >Registrar perfil</a>
                    </div>-->
                
                <div id="asearch_header">
                    <center><h1>Buscar</h1></center>
                  <div class="search">
                    <form action="Servlet" method="POST">
                    <input class="asearch_box search_input" name="keywords" type="text" placeholder="Buscar recursos educativos ..." autocomplete="off">
                    
                    <button class="toolbar_button search_button">
                      <img class="search_button_img" src="assets/img/search.png">
                    </button>
                    </form>
                  </div>
                    <div id="div_ba"> <center><a id ="busqueda_avanzada" href ="busqueda_avanzada.jsp" id="busqueda_avanzada">Búsqueda avanzada</a></center></div>
                </div>
            </div>
            <br>

</body>
</f:view>
    </html>
