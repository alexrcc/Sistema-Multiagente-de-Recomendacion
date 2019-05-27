<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  String user = (String)session.getAttribute("user");
    String errors = (String)session.getAttribute("errors");
    String msg = (String)session.getAttribute("msgAP");
    if(user==null)
        response.sendRedirect("index.jsp");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="assets/js/busqueda.js"></script>
        <link rel="stylesheet" href="assets/css/est_testim.css"/>
        <link rel="stylesheet" type="text/css" href="assets/css/error_modal.css"/>
        <link rel="stylesheet" type="text/css" href="assets/css/successfull_modal.css"/> 
        <script>
                 
		function Enviar() {
                    var radio = 0;
                    for (var i = 0; i < 35; i++) {
                        if(!$("#formulario input[name='p"+(i+1)+"']:radio").is(':checked')) {  
				radio = 1;
				} 
                                
                    }
                    if(radio===0)
                        $("#formulario").submit();
                    else
                        alert("Error: Hay preguntas sin contestar!");
			
		}</script>
        <title>SMA-WEB</title>
    </head>
    <%  if(errors!=null)
            out.print("<script>$(document).ready(function(){$(\"#myModal\").modal(\"show\");});</script>");
    else if(msg!=null)
            out.print("<script>$(document).ready(function(){$(\"#mensaje_modal\").modal(\"show\");});</script>");%>
    <jsp:include page="vistas/nav.jsp"/>
    <body>
         <div class='alert alert-warning' role='alert'>
            <div id="encabezado">
            <h1>Test de inteligencias múltiples de Gardner</h1>
            <span> Instrucciones: Lea cuidadosamente cada una de las afirmaciones siguientes.<br>

                1.-Si cree que refleja una característica suya y le parece que la afirmación es verdadera, seleccione “V”.<br>

                2.- Si cree que no refleja una característica suya y le parece que la afirmación es falsa, seleccione “F”.
                <br><strong>Si está dudoso porque a veces es verdadera y a veces falsa, elija la que más frecuentemente se presenta.</strong><br>
                <br>
            </span>
            </div>
        </div>
        <div id="cuestionario">
            <form action="GardnerTest" method="POST" id="formulario">
                <div id="header"><p>Pregunta</p><span>V</span><span>F</span></div>
            <div class="seccion_preg">
                <span class="item">1. Prefiero hacer un mapa que explicarle a alguien como tiene que llegar a un lugar determinado.</span>
                <div class="opciones">
                    <input type="radio" name="p1" value="V">
                    <input type="radio" name="p1" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">2. Si estoy enfadado o contento generalmente sé la razón exacta de por qué es así.</span>
                <div class="opciones">
                    <input type="radio" name="p2" value="V">
                    <input type="radio" name="p2" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">3. Sé tocar, o antes sabía, un instrumento musical.</span>
                <div class="opciones">
                    <input type="radio" name="p3" value="V">
                    <input type="radio" name="p3" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">4. Asocio la música con mis estados de ánimo.</span>
                <div class="opciones">
                    <input type="radio" name="p4" value="V">
                    <input type="radio" name="p4" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">5. Puedo sumar o multiplicar mentalmente con mucha rapidez.</span>
                <div class="opciones">
                    <input type="radio" name="p5" value="V">
                    <input type="radio" name="p5" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">6. Puedo ayudar a un amigo(a) a manejar y controlar sus sentimientos, porque yo lo pude hacer antes en relación a sentimientos parecidos.</span>
                <div class="opciones">
                    <input type="radio" name="p6" value="V">
                    <input type="radio" name="p6" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">7. Me gusta trabajar con calculadora y computadoras.</span>
                <div class="opciones">
                    <input type="radio" name="p7" value="V">
                    <input type="radio" name="p7" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">8. Aprendo rápidamente a bailar un baile nuevo.</span>
                <div class="opciones">
                    <input type="radio" name="p8" value="V">
                    <input type="radio" name="p8" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">9. No me es difícil decir lo que pienso durante una discusión o debate.</span>
                <div class="opciones">
                    <input type="radio" name="p9" value="V">
                    <input type="radio" name="p9" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">10. ¿Disfruto de una buena charla, prédica o sermón?</span>
                <div class="opciones">
                    <input type="radio" name="p10" value="V">
                    <input type="radio" name="p10" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">11. Siempre distingo el Norte del Sur, esté donde esté.</span>
                <div class="opciones">
                    <input type="radio" name="p11" value="V">
                    <input type="radio" name="p11" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">12. Me gusta reunir grupos de personas en una fiesta o evento especial.</span>
                <div class="opciones">
                    <input type="radio" name="p12" value="V">
                    <input type="radio" name="p12" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">13. Realmente la vida me parece vacía sin música.</span>
                <div class="opciones">
                    <input type="radio" name="p13" value="V">
                    <input type="radio" name="p13" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">14. Siempre entiendo los gráficos que vienen en las instrucciones de equipos o instrumentos.</span>
                <div class="opciones">
                    <input type="radio" name="p14" value="V">
                    <input type="radio" name="p14" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">15. Me gusta resolver puzles y entretenerme con juegos electrónicos.</span>
                <div class="opciones">
                    <input type="radio" name="p15" value="V">
                    <input type="radio" name="p15" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">16. Me fue fácil aprender a andar en bicicleta o patines.</span>
                <div class="opciones">
                    <input type="radio" name="p16" value="V">
                    <input type="radio" name="p16" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">17. Me enojo cuando escucho una discusión o una afirmación que me parece ilógica o absurda.</span>
                <div class="opciones">
                    <input type="radio" name="p17" value="V">
                    <input type="radio" name="p17" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">18. Soy capaz de convencer a otros que sigan mis planes o ideas.</span>
                <div class="opciones">
                    <input type="radio" name="p18" value="V">
                    <input type="radio" name="p18" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">19. Tengo buen sentido del equilibrio y de coordinación.</span>
                <div class="opciones">
                    <input type="radio" name="p19" value="V">
                    <input type="radio" name="p19" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">20. A menudo puedo captar relaciones entre números con mayor rapidez y facilidad que algunos de mis compañeros.</span>
                <div class="opciones">
                    <input type="radio" name="p20" value="V">
                    <input type="radio" name="p20" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">21. Me gusta construir modelos, maquetas o hacer esculturas.</span>
                <div class="opciones">
                    <input type="radio" name="p21" value="V">
                    <input type="radio" name="p21" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">22. Soy bueno para encontrar el significado preciso de las palabras.</span>
                <div class="opciones">
                    <input type="radio" name="p22" value="V">
                    <input type="radio" name="p22" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">23. Puedo mirar un objeto de una manera y con la misma facilidad verlo dado vuelta o al revés.</span>
                <div class="opciones">
                    <input type="radio" name="p23" value="V">
                    <input type="radio" name="p23" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">24. Con frecuencia establezco la relación que puede haber entre una música o canción y algo que haya ocurrido en mi vida.</span>
                <div class="opciones">
                    <input type="radio" name="p24" value="V">
                    <input type="radio" name="p24" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">25. Me gusta trabajar con números y figuras</span>
                <div class="opciones">
                    <input type="radio" name="p25" value="V">
                    <input type="radio" name="p25" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">26. Me gusta sentarme muy callado y pensar, reflexionar sobre mis sentimientos más íntimos.</span>
                <div class="opciones">
                    <input type="radio" name="p26" value="V">
                    <input type="radio" name="p26" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">27. Solamente con mirar las formas de las construcciones y estructuras me siento a gusto.</span>
                <div class="opciones">
                    <input type="radio" name="p27" value="V">
                    <input type="radio" name="p27" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">28. Cuando estoy en la ducha, o cuando estoy solo me gusta tararear, cantar o silbar.</span>
                <div class="opciones">
                    <input type="radio" name="p28" value="V">
                    <input type="radio" name="p28" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">29. Soy bueno para el atletismo.</span>
                <div class="opciones">
                    <input type="radio" name="p29" value="V">
                    <input type="radio" name="p29" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">30. Me gusta escribir cartas largas a mis amigos.</span>
                <div class="opciones">
                    <input type="radio" name="p30" value="V">
                    <input type="radio" name="p30" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">31. Generalmente me doy cuenta de la expresión o gestos que tengo en la cara.</span>
                <div class="opciones">
                    <input type="radio" name="p31" value="V">
                    <input type="radio" name="p31" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">32. Muchas veces me doy cuenta de las expresiones o gestos en la cara de las otras personas.</span>
                <div class="opciones">
                    <input type="radio" name="p32" value="V">
                    <input type="radio" name="p32" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">33. Reconozco mis estados de ánimo, no me cuesta identificarlos.</span>
                <div class="opciones">
                    <input type="radio" name="p33" value="V">
                    <input type="radio" name="p33" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">34. Me doy cuenta de los estados de ánimo de las personas con quienes me encuentro.</span>
                <div class="opciones">
                    <input type="radio" name="p34" value="V">
                    <input type="radio" name="p34" value="F">
                </div>
            </div>
            <div class="seccion_preg">
                <span class="item">35. Me doy cuenta bastante bien de lo que los otros piensan de mí.</span>
                <div class="opciones">
                    <input type="radio" name="p35" value="V">
                    <input type="radio" name="p35" value="F">
                </div>
            </div>
         
                      <div id="footer_btn">
                <center><button type=button id="enviar" class="btn btn-primary" onclick="Enviar()">Enviar</button></center>
                </div>
            </form>      
         
         </div>
    </body>
    <jsp:include page="vistas/footer.jsp"/>
            <!-- Modal Error -->
<div id="myModal" class="modal fade">
    <div class="modal-dialog modal-error">
	<div class="modal-content">
            <div class="modal-header">
                <div class="icon-box">
                    <i class="fas fa-exclamation"></i>
                </div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body text-center">
                    <h4>Ooops!</h4>	
                    <p>${errors}.<%if(errors!=null)request.getSession().removeAttribute("errors");%></p>
                    <button class="btn btn-success" data-dismiss="modal">Volver a intentar</button>
                </div>
	</div>
    </div>
</div>
<!-- Modal HTML -->
<div id="mensaje_modal" class="modal fade">
	<div class="modal-dialog modal-confirm">
		<div class="modal-content">
			<div class="modal-header">
				<div class="icon-box">
                                <i class="fas fa-check"></i>

				</div>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body text-center">
				<h4>Excelente!</h4>	
				<p>Su estilo de aprendizaje se guardó correctamente.</p><%if(msg!=null)request.getSession().removeAttribute("msgAP");%>
				<button class="btn btn-success" data-dismiss="modal"><span>Continuar test de Gardner </span> </button>
                                
			</div>
		</div>
	</div>
</div>     
</body>
</html>
