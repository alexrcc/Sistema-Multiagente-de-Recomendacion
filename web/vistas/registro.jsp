<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<div class="modal fade" id="registroModal" role="dialog">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document" id="_divRegistroModal">      
      <div class="modal-content">      
        <form action="model/guardar_registro.php" method="post" class="needs-validation" novalidate="">
          <div class="modal-header">
            <h5 class="modal-title" id="_registroModalLongTitle">Regístrate</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="_btnCerrarModal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body" id="_divRegistroModalBody"> 
              <center><span>Ingresar con google</span>
                  <a href="#" class="google"><span class="fab fa-google"></span></a>
              <span>o</span></center>
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
              <div class="sub-group" >
                  <a id="forgotpass" href="#">¿Has olvidado tu contraseña?</a>
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
