<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  String variable = (String)session.getAttribute("error");
    String user = (String)session.getAttribute("user");
%>
<!DOCTYPE html>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
 <link rel="stylesheet" type="text/css" href="assets/css/search.css"/>
 <!-- VENTANA MODAL -->  
    <div class="modal fade" id="registroModal" role="dialog">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document" id="_divRegistroModal">      
      <div class="modal-content">      
        <form action="Registrar" method="POST" class="needs-validation" novalidate="">
          <div class="modal-header">
            <h5 class="modal-title" id="_registroModalLongTitle">Regístrate</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="_btnCerrarModal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body" id="_divRegistroModalBody"> 
              <div id="sesion_google">
              <center><span>Regístrate con google</span>
                  <div class="g-signin2" data-onsuccess="onSignIn"></div>
              <span>o</span></center>
                  <% if(variable!=null){out.print("<span class=\"error\">"+"*Error: "+variable+"</span>");session.removeAttribute("error");}%>
              </div>
            <div class="form-group">
             <div class="sub-group">
                 <div class="icon-log"><span class="fas fa-user"></span></div>
                <input type="text" name="_iptNombres" id="_iptNombres" placeholder="Ingrese su nombre" class="form-control" required id="validationCustom01">
                <div class="invalid-feedback">
                  Debe ingresar su nombre.
                </div>
              </div>
              <div class="sub-group" >
                <div class="icon-log"><span class="fas fa-at"></span></div>
                <input type="email" name="_iptEmail" id="_iptEmail" placeholder="Ingrese su correo" class="form-control" required>
                <div class="invalid-feedback">
                  Debe ingresar su correo.
                </div>
              </div>
              <div class="sub-group" >
                <div class="icon-log"><span class="fas fa-unlock"></span></div>
                <input type="password" name="_iptPassword" id="_iptPassword" placeholder="Contraseña" class="form-control" ng-model="Password1" required>
                <div class="invalid-feedback">
                  Debe ingresar su contraseña.
                </div>
              </div>
             <label id="_lblTerminos" style="font-size: 80%;padding-top: 5%;">
                  Al hacer click en registrarse, aceptas las <a target="_blank" href="vistas/politicas.jsp  ">politicas y condiciones de uso.</a>
                </label>
            </div>

          </div>
          <div class="modal-footer">          
            <input type="submit" id="_btnRegistro" class="btn btn-primary" value="REGISTRARSE">
          </div>
        </form> 
      </div>
             
    </div>
  </div>
  <% if(user==null)
        out.print("<script src=\"assets/js/oauth2.js\"></script>");
  
  %>
  
<!-- FIN VENTANA MODAL -->  
         