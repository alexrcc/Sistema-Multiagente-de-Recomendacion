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
        <link rel="stylesheet" type="text/css" href="assets/css/ayuda.css"/>

        <%if(variable!=null)
                out.print("<script>$(document).ready(function(){$(\"#registroModal\").modal(\"show\");});</script>");
          if(e_sesion!=null)
                out.print("<script>$(document).ready(function(){$(\"#iniciarSesion\").modal(\"show\");});</script>");
        %>
    </head>

    <jsp:include page="vistas/nav.jsp"/>
    <body>
        <div class='ayuda'>
            <h6>Smaweb le permite buscar objetos de aprendizaje, de manera simple o personalizada;
            seguidamente se detallan algunas pautas para realizar la búsqueda de esos objetos.</h6>
            <br>
            <div class="tip">
                <div class="numero"><span class="circle">1</span></div>
                <div class="detalle">
                    <span>Puede realizar búsquedas simples, ingresando la palabra clave 
                    en el buscador de la <a href="/" target="_blank">pagina principal</a>, simplemente debe escribir la plabra clave y presionar el boton de buscar.</span>
                </div>
            </div>
            <div class="tip">
                <div class="numero"><span class="circle">2</span></div>
                <div class="detalle">
                    <span>Para acceder a funcionalizades de búsqueda avanzada y 
                        cálculo de su perfil inteligente, debe 
                        <%if(user==null){%>
                            <a href="#" data-toggle="modal" data-target="#registroModal">registrarse</a> y/o <a href="#" data-toggle="modal" data-target="#iniciarSesion">iniciar sesión</a>
                            <%}else{%> registrarse o inicar sesión<%}%>.</span>
                </div>
            </div>
           <div class="tip">
                <div class="numero"><span class="circle">3</span></div>
                <div class="detalle">
                    <span> Una vez que se ha registrado e iniciado sesión debe contestar el
                        <%if(user!=null){%>
                            <a href="testea.jsp" target="_blank">test de VARK</a> y <a href="testim.jsp" target="_blank">test de Gardner</a>
                            <%}else{%> test de VARK y test de Gardner<%}%>. Puede ver el resultado de los tests en su pagina de
                        <%if(user!=null){%>
                            <a href="perfil.jsp" target="_blank">perfil</a>
                            <%}else{%>perfil<%}%>.</span>
                </div>
            </div>
            <div class="tip">
                <div class="numero"><span class="circle">4</span></div>
                <div class="detalle">
                    <span>Una vez completado este proceso, puede ingresar a la pestaña de
                            <a href="busqueda_avanzada.jsp" target="_blank">búsqueda avanzada</a>, en donde podra filtrar objetos de aprendizaje
                    acorede a su estilo de aprendizaje u otro estilo seleccionado.</span>
                </div>
            </div>
            <div class="tip">
                <div class="numero"><span class="circle">5</span></div>
                <div class="detalle">
                    <span>Si se encuentran resultados en los repositorios, podra
                        visualizar una imgen como esta: <img class="imagen" src="assets/img/tip5.png">
                    </span>
                </div>
                
            </div>
            <div class="tip">
                <div class="numero"><span class="circle">6</span></div>
                <div class="detalle">
                    <span>
                    En el caso de no encontrar resultados podra seleccionar algunas de nuestras palabras clave:
                    <img class="imagen" src="assets/img/palabras.png"></span>
                </div>
                
            </div>
            <div class="tip">
                <div class="numero"><span class="circle">7</span></div>
                <div class="detalle">
                    <span>
                    Para que todas las búsquedas se realicen basado en sus estilos de aprendizaje, debe
                    dejar marcado(check) la opción "Filtrar según mi estilo de aprendizaje", en la barra de búsqueda de resultados (Nota: los objetos buscados desde la pagina principal no se filtran según su estilo):
                    <img class="imagen" src="assets/img/tip7.png" style="border-radius: 5px;"></span>
                </div>
                
            </div>
                <div class="tip">
                <div class="numero"><span class="circle">8</span></div>
                <div class="detalle">
                    <span>
                    Al hacer clic en el boton "Ver" de cada objeto, podrá visualizar su información, e interactuar con él.
                    Existen algunos objetos descargables o que contienen link a otros sitios, por lo que
                    debera ingresar en dichos enlaces para su interacción.</span>
                </div>
                
            </div>
            <div class="tip">
                <div class="numero"><span class="circle"></span></div>
                <div class="detalle">
                    <span>
                    Debajo de cada objeto de aprendizaje se encuentran algunos objetos, sujeridos para potenciar sus inteligencias múltiples
                    estos objetos se presentarán unicamente si ha iniciado sesión y respondidio el test de Gardner.</span>
                </div>
                
            </div>
        </div>
    </body>
       <jsp:include page="vistas/footer.jsp"/>
</html>
