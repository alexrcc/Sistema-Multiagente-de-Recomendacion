<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  String user = (String)session.getAttribute("user");
    String variable = (String)session.getAttribute("error");
    String e_sesion = (String)session.getAttribute("serror");
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
        <link rel="stylesheet" type="text/css" href="assets/css/search.css"/>
        <link rel="stylesheet" href="assets/css/est_cuestionario.css"/>
        <title>JSP Page</title>
    </head>
    <%  out.print("<script>$(document).ready(function(){$(\"#mensaje_modal\").modal(\"show\");});</script>");%>
    <jsp:include page="vistas/nav.jsp"/>
    <body>
        <div id="encabezado">
        <h1>Test de estilos de aprendizaje de VARK</h1>
        <span> El siguiente cuestionario permite determinar su estilo de aprendizaje. Su contestación es boligatoria
            si desea realizar una búsqueda avanzada de Objetos de Aprendizaje. <br>
            Sus respuestas serán guardadas bajo la mas estricta confidencialidad. Usted podra visualizar sus resultados unicamente iniciando sesión<br>
            <br>
           
            
            Por favor, selecciona la respuesta que más se acerque a tus preferencias, puedes seleccionar más de una respuesta si una sola no encaja con tu percepción. Deja en blanco aquellas preguntas que no sepas qué contestar, pero intenta que sean las menos posibles. </span>
        </div>
        <div id="cuestionario">
            <form action="VarkTest" method="POST">
            <div class="seccion_preg">
                <div class="numero"><span class="circle">1</span></div>
            <div class="pregunta">
                <span class="item">Usted cocinará algo especial para su familia. Usted haría:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p1">
                    <label class="form-check-label" for="p1r1">
                      Preguntar a amigos por sugerencias.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p1">
                    <label class="form-check-label" for="p1r2">
                      Dar una vista al recetario por ideas de las fotos.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p1">
                    <label class="form-check-label" for="p1r3">
                      Usar un libro de cocina donde usted sabe hay una buena receta.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p1">
                    <label class="form-check-label" for="p1r4">
                      Cocinar algo que usted sabe sin la necesidad de instrucciones.
                    </label>
                </div>
            </div>
         </div>
            
         <div class="seccion_preg">
                <div class="numero"><span class="circle">2</span></div>
            <div class="pregunta">
                <span class="item">Usted escogerá alimento en un restaurante o un café. Usted haría:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p2">
                    <label class="form-check-label" for="p2r1">
                      Escuchar al mesero o pedir que amigos recomienden opciones.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p2">
                    <label class="form-check-label" for="p2r2">
                      Mirar lo qué otros comen o mirar dibujos de cada platillo.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p2">
                    <label class="form-check-label" for="p2r3">
                      Escoger de las descripciones en el menú.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p2">
                    <label class="form-check-label" for="p2r4">
                      Escoger algo que tienes o has tenido antes.
                    </label>
                </div>
            </div>
         </div>
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">3</span></div>
            <div class="pregunta">
                <span class="item">Aparte del precio, qué más te influenciaría para comprar un libro de ciencia ficción</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p3">
                    <label class="form-check-label" for="p3r1">
                      Un amigo habla acerca de él y te lo recomienda.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p3">
                    <label class="form-check-label" for="p3r2">
                      Tienes historias reales, experiencias y ejemplos.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p3">
                    <label class="form-check-label" for="p3r3">
                      Leyendo rápidamente partes de él.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p3">
                    <label class="form-check-label" for="p3r4">
                      El diseño de la pasta es atractivo.
                    </label>
                </div>
            </div>
         </div>
            
        
        <div class="seccion_preg">
                <div class="numero"><span class="circle">4</span></div>
            <div class="pregunta">
                <span class="item">Usted ha terminado una competencia o un examen y le gustaría tener alguna retroalimentación. Te gustaría retroalimentarte:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p4">
                    <label class="form-check-label" for="p4r1">
                      Usando descripciones escritas de los resultados.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p4">
                    <label class="form-check-label" for="p4r2">
                      Usando ejemplos de lo que usted ha hecho.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p4">
                    <label class="form-check-label" for="p4r3">
                      Usando gráficos que muestran lo que usted ha logrado.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p4">
                    <label class="form-check-label" for="p4r4">
                      De alguien que habla por usted.
                    </label>
                </div>
            </div>
         </div>
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">5</span></div>
            <div class="pregunta">
                <span class="item">Usted tiene un problema con la rodilla. Usted preferiría que el doctor:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p5">
                    <label class="form-check-label" for="p5r1">
                      Use un modelo de plástico y te enseñe lo que está mal.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p5">
                    <label class="form-check-label" for="p5r2">
                      Te de una página de internet o algo para leer.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p5">
                    <label class="form-check-label" for="p5r3">
                      Te describa lo qué está mal.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p5">
                    <label class="form-check-label" for="p5r4">
                      Te enseñe un diagrama lo que está mal.
                    </label>
                </div>
            </div>
         </div>
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">6</span></div>
            <div class="pregunta">
                <span class="item">Usted está a punto de comprar una cámara digital o teléfono o móvil. ¿Aparte del precio qué más influirá en tomar tu decisión?.</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p6">
                    <label class="form-check-label" for="p6r1">
                      Probándolo.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p6">
                    <label class="form-check-label" for="p6r2">
                      Es un diseño moderno y se mira bien.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p6">
                    <label class="form-check-label" for="p6r3">
                      Leer los detalles acerca de sus características.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p6">
                    <label class="form-check-label" for="p6r4">
                      El vendedor me informa acerca de sus características.
                    </label>
                </div>
            </div>
         </div> 
        
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">7</span></div>
            <div class="pregunta">
                <span class="item">Usted no está seguro como se deletrea trascendente o tracendente ¿Ud. qué haría?.</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p7">
                    <label class="form-check-label" for="p7r1">
                      Escribir ambas palabras en un papel y escojo una.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p7">
                    <label class="form-check-label" for="p7r2">
                      Pienso cómo suena cada palabra y escojo una.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p7">
                    <label class="form-check-label" for="p7r3">
                      Busco la palabra en un diccionario.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p7">
                    <label class="form-check-label" for="p7r4">
                      Veo la palabra en mi mente y escojo según como la veo.
                    </label>
                </div>
            </div>
         </div>
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">8</span></div>
            <div class="pregunta">
                <span class="item">Me gustan páginas de Internet que tienen:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p8">
                    <label class="form-check-label" for="p8r1">
                      Interesantes descripciones escritas, listas y explicaciones.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p8">
                    <label class="form-check-label" for="p8r2">
                      Diseño interesante y características visuales.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p8">
                    <label class="form-check-label" for="p8r3">
                      Cosas que con un click pueda cambiar o examinar.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p8">
                    <label class="form-check-label" for="p8r4">
                      Canales donde puedo oír música, programas de radio o entrevistas.
                    </label>
                </div>
            </div>
         </div>    
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">9</span></div>
            <div class="pregunta">
                <span class="item">Usted está planeando unas vacaciones para un grupo. Usted quiere alguna observación de ellos acerca del plan. Usted qué haría:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p9">
                    <label class="form-check-label" for="p9r1">
                      Usa un mapa o página de Internet para mostrarles los lugares.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p9">
                    <label class="form-check-label" for="p9r2">
                      Describe algunos de los puntos sobresalientes.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p9">
                    <label class="form-check-label" for="p9r3">
                      Darles una copia del itinerario impreso.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p9">
                    <label class="form-check-label" for="p9r4">
                      Llamarles por teléfono o mandar mensaje por correo electrónico.
                    </label>
                </div>
            </div>
         </div>  
         
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">10</span></div>
            <div class="pregunta">
                <span class="item">Usted está usando un libro, disco compacto o página de Internet para aprender a tomar fotos con su cámara digital nueva. Usted le gustaría tener:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value=A name="p10">
                    <label class="form-check-label" for="p10r1">
                      Una oportunidad de hacer preguntas acerca de la cámara y sus características.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p10">
                    <label class="form-check-label" for="p10r2">
                      Esquemas o diagramas que muestran la cámara y la función de cada parte.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p10">
                    <label class="form-check-label" for="p10r3">
                      Ejemplos de buenas y malas fotos y cómo mejorarlas.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p10">
                    <label class="form-check-label" for="p10r4">
                      Aclarar las instrucciones escritas con listas y puntos sobre qué hacer.
                    </label>
                </div>
            </div>
         </div> 
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">11</span></div>
            <div class="pregunta">
                <span class="item">Usted quiere aprender un programa nuevo, habilidad o juego en una computadora. Usted qué hace:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p11">
                    <label class="form-check-label" for="p11r1">
                      Hablar con gente que sabe acerca del programa.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p11">
                    <label class="form-check-label" for="p11r2">
                      Leer las instrucciones que vienen en el programa.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p11">
                    <label class="form-check-label" for="p11r3">
                      Seguir los esquemas en el libro que acompaña el programa.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p11">
                    <label class="form-check-label" for="p11r4">
                      Use los controles o el teclado.
                    </label>
                </div>
            </div>
         </div>
            
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">12</span></div>
            <div class="pregunta">
                <span class="item">Estás ayudando a alguien que quiere a ir al aeropuerto, al centro del pueblo o la estación del ferrocarril. Usted hace:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p12">
                    <label class="form-check-label" for="p12r1">
                      Va con la persona.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p12">
                    <label class="form-check-label" for="p12r2">
                      Anote las direcciones en un papel (sin mapa).
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p12">
                    <label class="form-check-label" for="p12r3">
                      Les dice las direcciones.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p12">
                    <label class="form-check-label" for="p12r4">
                      Les dibuja un croquis o les da un mapa.
                    </label>
                </div>
            </div>
         </div>
        
        <div class="seccion_preg">
                <div class="numero"><span class="circle">13</span></div>
            <div class="pregunta">
                <span class="item">Recuerde un momento en su vida en que Ud. aprendió a hacer algo nuevo. Trate de evitar escoger una destreza física, como andar en bicicleta. Ud. Aprendió mejor:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p13">
                    <label class="form-check-label" for="p13r1">
                      Viendo una demostración.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p13">
                    <label class="form-check-label" for="p13r2">
                      Con instrucciones escritas, en un manual o libro de texto.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p13">
                    <label class="form-check-label" for="p13r3">
                      Escuchando a alguien explicarlo o haciendo preguntas.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p13">
                    <label class="form-check-label" for="p13r4">
                      Con esquemas y diagramas o pistas visuales.
                    </label>
                </div>
            </div>
         </div>    
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">14</span></div>
            <div class="pregunta">
                <span class="item">Ud. Prefiere un maestro o conferencista que use:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p14">
                    <label class="form-check-label" for="p14r1">
                      Demostraciones, modelos o sesiones prácticas.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p14">
                    <label class="form-check-label" for="p14r2">
                      Folletos, libros o lecturas.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p14">
                    <label class="form-check-label" for="p14r3">
                      Diagramas, esquemas o gráficos.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p14">
                    <label class="form-check-label" for="p14r4">
                      Preguntas y respuestas, pláticas y oradores invitados.
                    </label>
                </div>
            </div>
         </div>   
        
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">15</span></div>
            <div class="pregunta">
                <span class="item">Un grupo de turistas quiere aprender acerca de parques o reservas naturales en su área. Usted:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p15">
                    <label class="form-check-label" for="p15r1">
                      Los acompaña a un parque o reserva natural.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p15">
                    <label class="form-check-label" for="p15r2">
                      Les da un libro o folleto acerca de parques o reservas naturales.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p15">
                    <label class="form-check-label" for="p15r3">
                      Les da una plática acerca de parques o reservas naturales.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p15">
                    <label class="form-check-label" for="p15r4">
                      Les muestra imágenes de Internet, fotos o libros con dibujos.
                    </label>
                </div>
            </div>
         </div> 
            
          
        <div class="seccion_preg">
                <div class="numero"><span class="circle">16</span></div>
            <div class="pregunta">
                <span class="item">Usted tiene que hacer un discurso para una conferencia u ocasión especial. Usted hace:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p16">
                    <label class="form-check-label" for="p16r1">
                      Escribir el discurso y aprendérselo leyéndolo varias veces.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p16">
                    <label class="form-check-label" for="p16r2">
                      Reunir muchos ejemplos e historias para hacer el discurso verdadero y práctico.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p16">
                    <label class="form-check-label" for="p16r3">
                      Escribir algunas palabras claves y practicar el discurso repetidas veces.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p16">
                    <label class="form-check-label" for="p16r4">
                      Hacer diagramas o esquemas que te ayuden a explicar las cosas.
                    </label>
                </div>
            </div>
         </div>
                <div id="footer_btn">
                <center><button type="submit" class="btn btn-primary">Enviar</button></center>
                </div>
            </form>  
        </div>
    </body>
  <div class="modal" id="mensaje_modal" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Para realizar búsquedas avanzadas debe contestar el test de estilos de 
            aprendizaje de VARK.</p>
      </div>
      <div class="modal-footer">
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
        <button type="button" class="btn btn-primary" data-dismiss="modal">Aceptar</button>
      </div>
    </div>
  </div>
</div>
</html>
