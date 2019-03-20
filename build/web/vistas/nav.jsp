
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String user = (String)session.getAttribute("user");%>
<!DOCTYPE html>
    <head>
        <title>NAV</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="assets/css/nav.css"/>
    </head>
    <header>
        <div class="menu_bar">
            <a href="#" class="bt-menu"><span class="icon-list2"></span>Menú</a>
	</div>
 	<nav>
            <ul>
		<li><a href="/sma_web">Inicio</a></li>
		<li><a href="#">Ayuda</a></li>
		<li class="submenu">
                	<a href="#">Repositorios<span class="caret icon-arrow-down6"></span></a>
			<ul class="children">
                        	<li><a href="#">ROA Cedia</a></li>
				<li><a href="#">ViSH</a></li>
			</ul>
		</li>
		<li><a href="#">Acerca de</a></li>
		<li><a href="#">Contacto</a></li>
                <li><a href="#">Administrador</a></li>
                <% if(user==null){
                    out.print("<li class=\"ingreso\"><a href=\"#\" id=\"sesion\" >Iniciar sesi&oacute;n</a></li>");
                    out.print("<li class=\"ingreso\"><a href=\"#\" id=\"registro\" data-toggle=\"modal\" data-target=\"#registroModal\">Regístrate</a></li>");
                    }
                %>
                
                
            </ul>
	</nav>
    </header>
   <jsp:include page="registro.jsp"/>