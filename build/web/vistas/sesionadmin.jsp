<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  String variable_s = (String)session.getAttribute("admerror");
%>
<!DOCTYPE html>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
 <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
            <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
 <link rel="stylesheet" type="text/css" href="../assets/css/admin.css"/>
 <div class="global-container">
	<div class="card login-form">
	<div class="card-body">
		<h3 class="card-title text-center">Bienvenido Administrador</h3>
		<div class="card-text">
			<!--
			<div class="alert alert-danger alert-dismissible fade show" role="alert">Incorrect username or password.</div> -->
			<form action="../SesionAdmin" method="POST">
				<!-- to error: add class "has-danger" -->
                                <% if(variable_s!=null){out.print("<span class='error'>"+"*Error: "+variable_s+"</span>");session.removeAttribute("admerror");}%>
				<div class="form-group">
					<label for="InputUser">Usuario:</label>
					<input type="text" class="form-control form-control-sm" name="InputUser" aria-describedby="emailHelp" placeholder="Ingresar usuario">
				</div>
				<div class="form-group">
					<label for="InputPassword">Contraseña:</label>
					<input type="password" class="form-control form-control-sm" name="InputPassword" placeholder="Ingresar contraseña">
				</div>
				<button type="submit" class="btn btn-primary btn-block">Ingresar</button>
				
			</form>
		</div>
	</div>
</div>
</div>
