<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  String user = (String)session.getAttribute("user");
    String errors = (String)session.getAttribute("errors");
    String msg = (String)session.getAttribute("msg_registro");
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
        <script>         
		function Enviar() {
                    var check = 0;
                    for (var i = 0; i < 16; i++) {
                       
                        if($("#formulario input[name='p"+(i+1)+"v']").prop('checked')||$("#formulario input[name='p"+(i+1)+"a']").prop('checked')
                                ||$("#formulario input[name='p"+(i+1)+"r']").prop('checked')||$("#formulario input[name='p"+(i+1)+"k']").prop('checked')) {
                            check ++;
                        } 
                                
                    }
                    if(check>7){
                          $("#loadMe").modal({
                            backdrop: "static", //remove ability to close modal with click
                            keyboard: false, //remove option to close with keyboard
                            show: true //Display loader!
                          });
                          setTimeout(function() {
                                $("#loadMe").modal("hide");
                              }, 6000);
                          
                        $("#formulario").submit();}
                    else
                        alert("Error: Al menos debe contestar el 50% de las preguntas");
			
		}</script>
        <link rel="stylesheet" href="assets/css/est_cuestionario.css"/>
        <link rel="stylesheet" type="text/css" href="assets/css/error_modal.css"/>
        <title>SMA-WEB</title>
    </head>
    <%  if(errors!=null)
            out.print("<script>$(document).ready(function(){$(\"#myModal\").modal(\"show\");});</script>");
        else
            out.print("<script>$(document).ready(function(){$(\"#mensaje_modal\").modal(\"show\");});</script>");%>
    <jsp:include page="vistas/nav.jsp"/>
    <jsp:include page="vistas/loading.jsp"/>
    <body>
        <div class='alert alert-warning' role='alert'>
        <div id="encabezado">
        <h1>Test de estilos de aprendizaje de VARK</h1>
        <span > Con este cuestionario se tiene el propósito de saber acerca de sus 
            preferencias sensoriales para trabajar con información; seguramente tiene un estilo de aprendizaje preferido, y en base
            al siguiente cuestionario lo determinaremos.
            El test de VARK consta de 4 posibles estilos, estos son: Visual, Auditivo,Lectura- Escritura y Kinestésico. 
            <br><br><strong>Elija las respuestas que mejor expliquen su preferencia
           y marque la casilla de su elección. Puede seleccionar más de una respuesta 
           a una pregunta si una sola no encaja con su percepción. Deje en blanco toda pregunta 
            que no se apliqué a sus preferencias, pero trate que sea el menor número posible</strong><br>
            <br>
        </span>
        </div>
            </div>
        <div id="cuestionario">
            <form action="VarkTest" method="POST" id="formulario">
            <div class="seccion_preg">
                <div class="numero"><span class="circle">1</span></div>
            <div class="pregunta">
                <span class="item">Va a cocinar algún platillo especial para su familia. Usted.:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p1a">
                    <label class="form-check-label" for="p1r1">
                      Pediría sugerencias a sus amigos.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p1v">
                    <label class="form-check-label" for="p1r2">
                      Hojearía un libro de cocina para tomar ideas de las fotografías.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p1r">
                    <label class="form-check-label" for="p1r3">
                      Utilizaría un libro de cocina donde sabe que hay una buena receta.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p1k">
                    <label class="form-check-label" for="p1r4">
                      Cocinaría algo que conoce sin la necesidad de instrucciones.
                    </label>
                </div>
            </div>
         </div>
            
         <div class="seccion_preg">
                <div class="numero"><span class="circle">2</span></div>
            <div class="pregunta">
                <span class="item">Va a elegir sus alimentos en un restaurante o café. Usted.:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p2a">
                    <label class="form-check-label" for="p2r1">
                      Escucharía al mesero o pediría recomendaciones a sus amigos.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p2v">
                    <label class="form-check-label" for="p2r2">
                      Observaría lo que otros están comiendo o las fotografías de cada platillo.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p2r">
                    <label class="form-check-label" for="p2r3">
                      Elegiría a partir de las descripciones del menú.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p2k">
                    <label class="form-check-label" for="p2r4">
                      Elegiría algo que ya ha probado en ese lugar.
                    </label>
                </div>
            </div>
         </div>
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">3</span></div>
            <div class="pregunta">
                <span class="item">Además del precio, ¿qué más influiría en su decisión de comprar un nuevo libro de ciencia ficción?</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p3a">
                    <label class="form-check-label" for="p3r1">
                      Un amigo le habla del libro y se lo recomienda.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p3k">
                    <label class="form-check-label" for="p3r2">
                      Tiene historias, experiencias y ejemplos de la vida real.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p3r">
                    <label class="form-check-label" for="p3r3">
                      Una lectura rápida de algunas partes del libro.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p3v">
                    <label class="form-check-label" for="p3r4">
                      La apariencia le resulta atractiva.
                    </label>
                </div>
            </div>
         </div>
            
        
        <div class="seccion_preg">
                <div class="numero"><span class="circle">4</span></div>
            <div class="pregunta">
                <span class="item">Ha acabado una competencia o una prueba y quisiera una retroalimentación. ¿Cómo le gustaría recibirla?:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p4r">
                    <label class="form-check-label" for="p4r1">
                      Utilizando una descripción escrita de sus resultados.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p4k">
                    <label class="form-check-label" for="p4r2">
                      Utilizando ejemplos de lo que ha hecho.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p4v">
                    <label class="form-check-label" for="p4r3">
                      Utilizando gráficas que muestren lo que ha conseguido.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p4a">
                    <label class="form-check-label" for="p4r4">
                      Escuchando a alguien haciendo una revisión detallada de su desempeño.
                    </label>
                </div>
            </div>
         </div>
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">5</span></div>
            <div class="pregunta">
                <span class="item">Usted tiene un problema con su rodilla. Preferiría que el doctor:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p5k">
                    <label class="form-check-label" for="p5r1">
                      Utilizara el modelo plástico de una rodilla para mostrarle qué está mal.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p5r">
                    <label class="form-check-label" for="p5r2">
                      Le diera una dirección web o algo para leer sobre el asunto.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p5a">
                    <label class="form-check-label" for="p5r3">
                      Le describiera qué está mal.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p5v">
                    <label class="form-check-label" for="p5r4">
                      Le mostrara con un diagrama qué es lo que está mal.
                    </label>
                </div>
            </div>
         </div>
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">6</span></div>
            <div class="pregunta">
                <span class="item">Usted está a punto de comprar una cámara digital o teléfono o móvil. ¿Además del precio, qué más influye en su decisión?.</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p6k">
                    <label class="form-check-label" for="p6r1">
                      Lo utiliza o lo prueba.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p6v">
                    <label class="form-check-label" for="p6r2">
                      El diseño del aparato es moderno y parece bueno.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p6r">
                    <label class="form-check-label" for="p6r3">
                      La lectura de los detalles acerca de las características del aparato.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p6a">
                    <label class="form-check-label" for="p6r4">
                      Los comentarios del vendedor acerca de las características del aparato.
                    </label>
                </div>
            </div>
         </div> 
        
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">7</span></div>
            <div class="pregunta">
                <span class="item">No está seguro como se deletrea "trascendente" o "tracendente", ¿Usted qué haría?.</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p7k">
                    <label class="form-check-label" for="p7r1">
                      Escribiría ambas palabras y elegiría una.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p7a">
                    <label class="form-check-label" for="p7r2">
                      Pensaría en cómo suena cada palabra y elegiría una.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p7r">
                    <label class="form-check-label" for="p7r3">
                      Las buscaría en un diccionario.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p7v">
                    <label class="form-check-label" for="p7r4">
                      Vería las palabras en su mente y elegiría la que mejor luce.
                    </label>
                </div>
            </div>
         </div>
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">8</span></div>
            <div class="pregunta">
                <span class="item">Le gustan los sitios web que tienen:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p8r">
                    <label class="form-check-label" for="p8r1">
                      Descripciones escritas interesantes, características  y explicaciones.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p8v">
                    <label class="form-check-label" for="p8r2">
                      Un diseño interesante y características visuales.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p8k">
                    <label class="form-check-label" for="p8r3">
                      Cosas que se pueden picar, mover o probar.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p8a">
                    <label class="form-check-label" for="p8r4">
                      Canales de audio para oír música, programas o entrevistas.
                    </label>
                </div>
            </div>
         </div>    
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">9</span></div>
            <div class="pregunta">
                <span class="item">Está planeando unas vacaciones para un grupo, y quiere alguna observación de ellos acerca del plan. Usted qué haría:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p9v">
                    <label class="form-check-label" for="p9r1">
                      Utilizaría un mapa o un sitio web para mostrar los lugares. 
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p9a">
                    <label class="form-check-label" for="p9r2">
                      Describiría algunos de los atractivos del viaje.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p9r">
                    <label class="form-check-label" for="p9r3">
                      Les daría una copia del itinerario impreso.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p9k">
                    <label class="form-check-label" for="p9r4">
                      Les llamaría por teléfono, les escribiría o les enviaría un e-mail.
                    </label>
                </div>
            </div>
         </div>  
         
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">10</span></div>
            <div class="pregunta">
                <span class="item">Está utilizando un libro, CD o sitio web para aprender cómo tomar fotografías con su nueva cámara digital. Le gustaría tener:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value=A name="p10a">
                    <label class="form-check-label" for="p10r1">
                      La oportunidad de hacer preguntas y que le hablen sobre la cámara y sus características.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p10v">
                    <label class="form-check-label" for="p10r2">
                      Diagramas que muestren la cámara y qué hace cada una de sus partes.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p10k">
                    <label class="form-check-label" for="p10r3">
                      Muchos ejemplos de fotografías buenas y malas y cómo mejorar éstas.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p10r">
                    <label class="form-check-label" for="p10r4">
                      Instrucciones escritas con claridad, con características y puntos sobre qué hacer.
                    </label>
                </div>
            </div>
         </div> 
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">11</span></div>
            <div class="pregunta">
                <span class="item">Quiere aprender un programa nuevo, habilidad o juego en una computadora. Usted qué haría:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p11a">
                    <label class="form-check-label" for="p11r1">
                      Platicar con personas que conocen el programa.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p11r">
                    <label class="form-check-label" for="p11r2">
                      Leer las instrucciones escritas que vienen con el programa.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p11v">
                    <label class="form-check-label" for="p11r3">
                      Seguir los diagramas del libro que vienen con el programa.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p11k">
                    <label class="form-check-label" for="p11r4">
                      Utilizar los controles o el teclado.
                    </label>
                </div>
            </div>
         </div>
            
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">12</span></div>
            <div class="pregunta">
                <span class="item">Estás ayudando a alguien que quiere a ir al aeropuerto, al centro del pueblo o la estación del ferrocarril. Usted.:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p12k">
                    <label class="form-check-label" for="p12r1">
                      Iría con la persona.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p12r">
                    <label class="form-check-label" for="p12r2">
                      Le daría las indicaciones por escrito (sin un mapa).
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p12a">
                    <label class="form-check-label" for="p12r3">
                      Le diría cómo llegar.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p12v">
                    <label class="form-check-label" for="p12r4">
                      Le dibuja un croquis o le da un mapa.
                    </label>
                </div>
            </div>
         </div>
        
        <div class="seccion_preg">
                <div class="numero"><span class="circle">13</span></div>
            <div class="pregunta">
                <span class="item">Recuerde la vez cuando aprendió cómo hacer algo nuevo. Evite elegir una destreza física, como montar bicicleta. ¿Cómo aprendió mejor?:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p13k">
                    <label class="form-check-label" for="p13r1">
                      Viendo una demostración.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p13r">
                    <label class="form-check-label" for="p13r2">
                      Siguiendo instrucciones escritas en un manual o libro de texto.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p13a">
                    <label class="form-check-label" for="p13r3">
                      Escuchando la explicación de alguien y haciendo preguntas.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p13v">
                    <label class="form-check-label" for="p13r4">
                      Siguiendo pistas visuales en diagramas y gráficas.
                    </label>
                </div>
            </div>
         </div>    
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">14</span></div>
            <div class="pregunta">
                <span class="item">Usted prefiere a un profesor o un expositor que utiliza:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p14k">
                    <label class="form-check-label" for="p14r1">
                      Demostraciones, modelos o sesiones prácticas.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p14r">
                    <label class="form-check-label" for="p14r2">
                      Folletos, libros o lecturas.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p14v">
                    <label class="form-check-label" for="p14r3">
                      Diagramas, esquemas o gráficos.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p14a">
                    <label class="form-check-label" for="p14r4">
                      Preguntas y respuestas, charlas, grupos de discusión u oradores invitados.
                    </label>
                </div>
            </div>
         </div>   
        
            
        <div class="seccion_preg">
                <div class="numero"><span class="circle">15</span></div>
            <div class="pregunta">
                <span class="item">Un grupo de turistas quiere aprender acerca de parques o reservas naturales en su área. Usted:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p15k">
                    <label class="form-check-label" for="p15r1">
                      Los llevaría a un parque o reserva y daría una caminata con ellos.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p15r">
                    <label class="form-check-label" for="p15r2">
                      Les daría libros o folletos sobre parques o reservas de vida salvaje.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p15a">
                    <label class="form-check-label" for="p15r3">
                      Les daría una plática acerca de parques o reservas de vida salvaje.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p15v">
                    <label class="form-check-label" for="p15r4">
                      Les mostraría figuras de Internet, fotografías o libros con imágenes.
                    </label>
                </div>
            </div>
         </div> 
            
          
        <div class="seccion_preg">
                <div class="numero"><span class="circle">16</span></div>
            <div class="pregunta">
                <span class="item">Tiene que hacer un discurso para una conferencia u ocasión especial. Usted.:</span>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="R" name="p16r">
                    <label class="form-check-label" for="p16r1">
                      Escribiría su discurso y se lo aprendería leyéndolo varias veces.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="K" name="p16k">
                    <label class="form-check-label" for="p16r2">
                      Conseguiría muchos ejemplos e historias para hacer la charla real y práctica.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="A" name="p16a">
                    <label class="form-check-label" for="p16r3">
                      Escribiría algunas palabras clave y práctica su discurso repetidamente.
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="V" name="p16v">
                    <label class="form-check-label" for="p16r4">
                      Elaboraría diagramas o conseguiría gráficos que le ayuden a explicar las ideas.
                    </label>
                </div>
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
<div class="modal" id="mensaje_modal" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header bg-warning">
          <div class="icon-box">
            <i class="fas fa-exclamation"></i>
            </div>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <%if(msg==null)msg="Aviso";%>
          <h5 class="modal-title"><%out.print(msg);request.getSession().removeAttribute("msg_registro");%></h5>
          <br>
          <p>Para realizar búsquedas avanzadas debe contestar el test de estilos de 
            aprendizaje de VARK!</p>
        <center><button class="btn btn-success" data-dismiss="modal">Continuar</button></center>
      </div> 
    </div>
  </div>
</div>
          

</html>
