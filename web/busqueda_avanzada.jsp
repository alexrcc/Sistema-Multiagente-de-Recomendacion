<%-- 
    Document   : busqueda_avanzada
    Created on : 21/02/2019, 08:05:58 AM
    Author     : alexr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SMA-OA UNL</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="assets/css/busqueda_avanzada.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
        <script src="assets/js/busqueda.js"></script>
    </head>
    <jsp:include page="vistas/nav.jsp"/>  
    <body>
        <div class="titulo"><h1>Búsqueda avanzada de Objetos de Aprendizaje</h1></div>
        <div id="busqueda">
            <form action="Servlet2" method="POST">
                <div class="grupo">
                <label for="keyword">Palabra clave</label>
                <input type="text" class="input" id="keyword" name="keyword" placeholder="Buscar objetos de aprendizaje ...">
                </div>
                <div class="grupo">
                <label for="keyword">Disciplina</label>
                <select class="input" id="disciplina" name="disciplina">
                    <option value="0">Selecciona una disciplina...</option>
                    <option value="1">Arte</option>
                    <option value="2">Biología</option>
                    <option value="3">Química</option>
                    <option value="4">Educación para la Ciudadanía</option>
                    <option value="5">Informática</option>
                    <option value="6">Economía</option>
                    <option value="7">Educación</option>
                    <option value="8">Ingeniería</option>
                    <option value="9">Idiomas</option>
                    <option value="10">Cultura General</option>
                    <option value="11">Geografía</option>
                    <option value="12">Geología</option>
                    <option value="13">Literatura</option>
                    <option value="14">Matemáticas</option>
                    <option value="15">Música</option>
                    <option value="16">Ciencias Naturales</option>
                    <option value="17">Física</option>
                    <option value="18">Tecnología</option>
                </select>
                </div>
                <div class="grupo">
                <label for="idioma">Idioma</label>
                <select class="input" id="disciplina" name="idioma">
                    <option value="0">Selecciona un idioma...</option>
                    <option value="1">Español</option>
                    <option value="2">Inglés</option>
                    <option value="3">Otros</option>
                </select>
                </div>
                    <div class="grupo">
                        <label for="la" id="check">Buscar, basandose en mi estilo de aprendizaje:</label>
                        <input type="checkbox"  id="checkbox" name="checkbox" checked="true" onclick="checkFunct()" >
                    </div>
                    <div id="b_avanzada">
                        <label id="info">Para buscar por otro estilo de aprendizaje, desmarque la casilla anterior ...</label><br>
                    <div class="grupo">
                    <label class="oculto">Estilo de aprendizaje:</label>
                    <select class="input" id="estilo" name="estilo" disabled="true">
                        <option value="0">Seleccione el estilo</option>
                        <option value="1">Estilo1</option>
                        <option value="2">Estilo2</option>
                        <option value="3">Estilo3</option>
                        <option value="3">Estilo4</option>
                    </select>
                    </div>
                    <div class="grupo">
                    <label class="oculto" disabled="true">Inteligencia múltiple:</label>
                    <select class="input" id="inteligencia" name="inteligencia" disabled="true">
                        <option value="0">Seleccione la Inteligencia</option>
                        <option value="1">I1</option>
                        <option value="2">I2</option>
                        <option value="3">I3</option>
                        <option value="4">I4</option>
                        <option value="5">I5</option>
                        <option value="6">I6</option>
                        <option value="7">I7</option>
                        <option value="8">I8</option>
                    </select>
                    </div>
                </div>
                <center><button type="submit" class="btn btn-enviar">Buscar</button></center>   
            </form>
        </div>
    </body>
</html>
