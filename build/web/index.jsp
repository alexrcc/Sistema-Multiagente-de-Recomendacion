<%-- 
    Document   : index
    Created on : 04/01/2019, 08:32:12 AM
    Author     : alexr
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1" %>

<f:view>
    <html lang="es">
        <head>
            <title>SMA-OA UNL</title>
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
            <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
            <script src="assets/js/busqueda.js"></script>
            <link rel="stylesheet" type="text/css" href="assets/css/search.css"/>
        </head>
        <jsp:include page="vistas/nav.html"/>  
        <body>
            <div id="fondo">
                <div id="encabezado">
                    <div id="izq">
                        <span><bold>El sistema de recomendación de Objetos de Aprendizaje</bold>
                            brinda una búsqueda según su estilo de aprendizaje, para ello se recomienda que registre su Perfil</span>
                    </div>
                    <div id="der">
                        <a href="#" >Registrar perfil</a>
                    </div>
                </div>
                <div id="asearch_header">
                    <center><h1>Buscar</h1></center>
                  <div class="search">
                    <form action="Servlet" method="POST">
                    <input class="asearch_box search_input" type="text" placeholder="Buscar recursos educativos ..." autocomplete="off">
                    </form>
                    <button class="toolbar_button search_button">
                      <img class="search_button_img" src="assets/img/search.png">
                    </button>
                  </div>
                    <div id="div_ba"> <center><a id ="busqueda_avanzada" href ="busqueda_avanzada.jsp" id="busqueda_avanzada">Búsqueda avanzada</a></center></div>
                </div>
            </div>
            <br>

</body>
        <div style="display:none">
            <h1>Sistema de Recomendacion de Objetos de Aprendizaje</h1>
            <br/>
          <form action="Servlet2" method="POST">
        Buscar:<br>
        <input type="text" name="nombre2" placeholder="Palabra clave, materia, autor ..." size="80" />
        <input type="submit" value="Enviar" name="enviar2" />
        </form>
        </div>





       <div class="modal fade" id="registroModal" tabindex="-1" role="dialog" aria-labelledby="registroModalTitle" aria-hidden="true" ng-controller="controladorRegistro">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document" id="_divRegistroModal">      
      <div class="modal-content">      
        <form action="model/guardar_registro.php" method="post" class="needs-validation" novalidate="">
          <div class="modal-header">
            <h5 class="modal-title" id="_registroModalLongTitle">Registro TBook</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="_btnCerrarModal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body" id="_divRegistroModalBody">    
            <div class="form-group">
              <div>
                <label for="validationCustom01" id="_lblNombres">Nombres</label>
                <input type="text" name="_iptNombres" id="_iptNombres" placeholder="Nombres" class="form-control" required class="form-control" id="validationCustom01">
                <div class="invalid-feedback">
                  Debe ingresar su nombre.
                </div>
              </div>
              <div>
                <label id="_lblApellidos">Apellidos</label>
                <input type="text" name="_iptApellidos" id="_iptApellidos" placeholder="Apellidos" class="form-control" required>
                <div class="invalid-feedback">
                  Debe ingresar su apellido.
                </div>
              </div>
              <div>
                <label id="_lblEmail">Email</label>
                <input type="email" name="_iptEmail" id="_iptEmail" placeholder="Email" class="form-control" required>
                <div class="invalid-feedback">
                  Debe ingresar su correo.
                </div>
              </div>
              <div>
                <label id="_lblEmail">Contraseña</label>
                <input type="password" name="_iptPassword" id="_iptPassword" placeholder="Contraseña" class="form-control" ng-model="Password1" required>
                <div class="invalid-feedback">
                  Debe ingresar su contraseña.
                </div>
              </div>
              <div>
                <label id="_lblEmail">Repita Contraseña </label><label id="_lblError">{{mensajeError}}</label>
                <input type="password" id="_iptRepeatPassword" placeholder="Contraseña" class="form-control" ng-model="Password2" ng-blur="validarPassword(Password1,Password2)" required>                
                <div class="invalid-feedback">
                  Debe repetir su contraseña.
                </div>
              </div>
              
              <div>
                <label id="_lblGenero">Género</label><br>
                <select class="custom-select mr-sm-2" name="_slctGenero" id="_slctGenero" required>
                  <option value="">Género...</option>
                  <option value="M">Masculino</option>
                  <option value="F">Femenino</option>
                  <option value="O">Otros</option>
                </select>
                <div class="invalid-feedback">
                  Debe ingresar su género.
                </div>
                <label id="_lblTerminos" style="font-size: 80%;padding-top: 5%;">
                  Al hacer click en registrarse, aceptas las <a target="_blank" href="view/politicas.php">politicas y condiciones de uso de tbook.</a>
                </label>
              </div>
            </div>

          </div>
          <div class="modal-footer">          
            <input type="submit" id="_btnRegistro" class="btn btn-primary" value="REGISTRARSE">
          </div>
        </form> 
      </div>
             
    </div>
  </div>
<!-- FIN VENTANA MODAL -->
 

    </html>
</f:view>