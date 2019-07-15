
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String user = (String)session.getAttribute("user");%>
<% String name_user = (String)session.getAttribute("name");%>
<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="stylesheet" href="assets/css/nav.css"/>
        <script src="assets/js/avatar.js"></script>
         <script src="https://apis.google.com/js/platform.js" async defer></script>
         <script src="assets/js/salir_google.js"></script>
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id"
            content="922782693652-cl698h5u82ekr0dvfh8jcl21m9k18ns1.apps.googleusercontent.com">
        
    </head>
    <header>
        <div class="menu_bar">
            <a href="#" class="bt-menu"><span class="icon-list2"></span>Menú</a>
	</div>
 	<nav>
            <ul>
		<li><a href="/sma_web">Inicio</a></li>
		
		<li class="submenu">
                    <a href="#">Repositorios<span class="fas fa-sort-down" style="float:inline-end; margin-left: 5px; font-size: 1rem;"></span></a>
			<ul class="children">
                            <li><a href="https://roa.cedia.edu.ec/" target="_blank">ROA Cedia</a></li>
				<li><a href="http://vishub.org/" target="_blank">ViSH</a></li>
                                <li><a href="http://vishub.org/" target="_blank">EducaInternet</a></li>
			</ul>
		</li>
                <li><a href="busqueda_avanzada.jsp">Búsqueda avanzada</a></li>  
		<li><a href="#">Acerca de</a></li>  
                <li><a href="#">Ayuda</a></li>
                <% if(user==null){
                    out.print("<li class=\"ingreso\"><a href=\"#\" id=\"sesion\" data-toggle=\"modal\" data-target=\"#iniciarSesion\" >Iniciar sesi&oacute;n</a></li>");
                    out.print("<li class=\"ingreso\"><a href=\"#\" id=\"registro\" data-toggle=\"modal\" data-target=\"#registroModal\">Regístrate</a></li>");
                    }else{
                        out.print("<li class=\"avatar\"><a href=\"javascript:opcAvatar();\" ><span class=\"fas fa-user-circle\"></span><span class=\"text_user\">"+name_user.split(" ")[0]+"<i class=\"fas fa-caret-down\"></i></span></a></li>");
                        out.print("<div class='opc_avatar'>");
                        out.print("<div class='contenedor'><span class=\"triangle\"></span>");
                        out.print("<div class='img'><span class=\"fas fa-user-circle\"></span>");
                        
                        out.print("</div>");
                        out.print("<div class='datos'>");
                        out.print("<span class='nombre'>"+name_user+"</span>");
                        out.print("<span class='correo'>"+user+"</span>");
                        out.print("<a class='perfil'  href='perfil.jsp'>Ver Perfil</a>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='exit'>");
                        out.print("<a href='#' onClick='signOut();'>Salir<span class='fas fa-power-off'></span></a>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                %>
                
                
            </ul>
	</nav>
    </header>
   <jsp:include page="registro.jsp"/>
   <jsp:include page="sesion.jsp"/>