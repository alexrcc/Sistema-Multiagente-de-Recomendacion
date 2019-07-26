
<%@page import="org.apache.jena.query.QuerySolution"%>
<%@page import="org.apache.jena.query.ResultSet"%>
<%@page import="model.Virtuoso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
  <%  String vadmin = (String)session.getAttribute("admuser");
      if(vadmin==null){
        response.sendRedirect("vistas/sesionadmin.jsp");
      }
        Virtuoso bdv = new Virtuoso();
        bdv.conectar("http://LearningObjects");
        ResultSet results=bdv.GetRepositorio();
      
  %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link rel="stylesheet" type="text/css" href="assets/css/admin.css"/>
            <script>
               function IniciarAgentes(){
                    // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                    $.post('IniciarAgentes', {
                    }, function(responseText) {

                            $('#respuestas').html(responseText);
                    });
                }
                function DetenerPlataforma(uri){
                    //Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                     $.post('DetenerPlataforma', {
                     }, function(responseText) {
                            $('#respuestas').html(responseText);
                     }); 
                }
                function ObtenerEstadisticas(){
                    //Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                     $.post('ObtenerEstadisticas', {
                     }, function(responseText) {
                            $('#respuestas').html(responseText);
                     }); 
                }
        </script>
    </head>
    <body >
        <div class="centrado-porcentual">
        <h1>Panel Administrativo</h1>
        <div class="jade">
            <span>Plataforma JADE</span>
        <button class="toolbar_button search_button btn btn-success" onclick="IniciarAgentes();">
                   Inciar Agentes</button>
        <button class="toolbar_button search_button btn btn-danger" onclick="DetenerPlataforma();">
                Detener JADE</button>
        </div>
        <div id="repositorios">
            <form action="AgregarRepositorio" method="POST">
                <span>Agregar Repositorio: </span><input type="text" name="url" class="input">
                <input type="submit" value="Enviar" class="btn btn-success">
            </form>
            <div class="listado">
                <span class="t_roa">ROAS Guardados</span>
                <% while(results.hasNext()){
                    QuerySolution qs = results.next();
                    out.print("<span><a href='"+qs.getLiteral("url").toString()+"' target='_blank'>"+qs.getLiteral("url").toString()+"</a></span>");
                    }
                %>
            </div>
        </div>
        <div class="stadistics">
            <span>Estadísticas de Perfiles: </span>
        <button class="toolbar_button search_button btn btn-success" onclick="ObtenerEstadisticas();">
                   Obtener Estadísticas</button>
        </div>
            <div class="cont_resp"><span>Respuestas</span><div id ="respuestas"></div></div>
            <div class="sesion"><a href="SalirAdm">Cerrar Sesión</a></div>

        </div>
    </body>
</html>
