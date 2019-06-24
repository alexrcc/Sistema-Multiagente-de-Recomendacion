<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  String variable_s = (String)session.getAttribute("serror");
%>
<!DOCTYPE html>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
 <link rel="stylesheet" type="text/css" href="assets/css/search.css"/>
 <!-- VENTANA MODAL -->  
    <div class="modal fade" id="iniciarSesion" role="dialog">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document" id="_divRegistroModal">      
      <div class="modal-content">      
        <form action="Sesion" method="POST" class="needs-validation" novalidate="">
          <div class="modal-header">
            <h5 class="modal-title" id="_registroModalLongTitle">Iniciar Sesión</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="_btnCerrarModal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body" id="_divRegistroModalBody"> 
              <div id="sesion_google">
              <center><span>Ingresar con google</span>
                  <div class="g-signin2" data-onsuccess="onSignIn"></div>
              <span>o</span></center>
                  
              </div>
              <% if(variable_s!=null){out.print("<span class='error'>"+"*Error: "+variable_s+"</span>");session.removeAttribute("serror");}%>
            <div class="form-group">
              <div class="sub-group" >
                <div class="icon-log"><span class="fas fa-at"></span></div>
                <input type="email" name="_isEmail" id="_isEmail" placeholder="Ingrese su correo" class="form-control" required>
                <div class="invalid-feedback">
                  Debe ingresar su correo.
                </div>
              </div>
              <div class="sub-group" >
                <div class="icon-log"><span class="fas fa-unlock"></span></div>
                <input type="password" name="_isPassword" id="_isPassword" placeholder="Contraseña" class="form-control" ng-model="Password1" required>
                <div class="invalid-feedback">
                  Debe ingresar su contraseña.
                </div>
              </div>
              <div class="sub-group" >
                  <a id="forgotpass" href="#">¿Has olvidado tu contraseña?</a>
              </div>
            </div>

          </div>
          <div class="modal-footer">          
            <input type="submit" id="_btnRegistro" class="btn btn-primary" value="Iniciar sesión">
          </div>
        </form> 
      </div>
             
    </div>
  </div>
<!-- FIN VENTANA MODAL -->  
         