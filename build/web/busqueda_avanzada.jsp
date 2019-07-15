<%@page import="model.Virtuoso"%>
<%@page import="org.apache.jena.query.QuerySolution"%>
<%@page import="org.apache.jena.query.ResultSet"%>
<%@page import="org.apache.jena.query.QueryExecutionFactory"%>
<%@page import="org.apache.jena.query.QueryExecution"%>
<%@page import="org.apache.jena.query.QueryFactory"%>
<%@page import="org.apache.jena.query.Query"%>
<%@page import="virtuoso.jena.driver.VirtModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  String user = (String)session.getAttribute("user");
    String name_user = (String)session.getAttribute("name");
    String profile = (String)session.getAttribute("intelligentProfile");
    int [] estilos = new int[4];
    boolean band=false;
    if(user==null){
%>
        <jsp:include page="vistas/nav.jsp"/>  
        <jsp:include page="vistas/modalba.jsp?e=Debe registrarse o iniciar sesión y contestar al menos el cuestionario de VARK, 
                    para realizar una búsqueda avanzada!"/> 
<%  
    }else if(profile==null){
        try{
        Virtuoso bdv = new Virtuoso();
        bdv.conectar("Perfiles");
        ResultSet results=bdv.GetEstilos(user);
        while(results.hasNext()){
               band=true;
               QuerySolution qs = results.next();
           }
            if(band==true){
                session.setAttribute("intelligentProfile","si");
                profile=(String)session.getAttribute("intelligentProfile");
            }else{
               response.sendRedirect("testea.jsp");
            }
        }catch(Exception e){
            out.print("<div class='alert alert-danger' role='alert'><center>Lo sentimos... "
                    + "No se puede conectar con VIRTUSO OPENLIK!</center></div>");
        }
    }
    if(profile!=null){
%>
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
                
                        <div class="[ form-group ]">
                            <input type="checkbox" name="fancy-checkbox-warning" id="fancy-checkbox-warning" autocomplete="off" checked onclick="checkFunct()"/>
                            <div class="[ btn-group ]">
                                <label for="fancy-checkbox-warning" class="[ btn btn-warning ]">
                                    <span class="fas fa-check"></span>
                                    <span class="fas fa-minus"></span>
                                </label>
                                <label for="fancy-checkbox-warning" class="[ btn btn-default active ]">
                                    Filtrar según mi estilo de aprendizaje predominante.
                                </label>
                            </div>
                        </div>

                  
                <div class="grupo">
                <label for="keyword">Palabra clave</label>
                <input type="text" class="input" id="keyword" name="keyword" placeholder="Buscar objetos de aprendizaje ...">
                </div>
                <div class="grupo">
                <label for="keyword">Disciplina</label>
               
                <div id="disciplina" class='input'>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Art" name="disciplina">
                        <label class="form-check-label">Arte</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Biologi" name="disciplina">
                        <label class="form-check-label">Biología</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Chemistry" name="disciplina">
                        <label class="form-check-label">Química</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Citizenship" name="disciplina">
                        <label class="form-check-label">Ciudadanía</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Computerscience" name="disciplina">
                        <label class="form-check-label">Informática</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Economics" name="disciplina">
                        <label class="form-check-label">Economía</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Education" name="disciplina">
                        <label class="form-check-label">Educación</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Engineering" name="disciplina">
                        <label class="form-check-label">Ingeniería</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Foreignlanguages" name="disciplina">
                        <label class="form-check-label">Idiomas</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Generalculture" name="disciplina">
                        <label class="form-check-label">Cultura General</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Geography" name="disciplina">
                        <label class="form-check-label">Geografía</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Geology" name="disciplina">
                        <label class="form-check-label">Geología</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="History" name="disciplina">
                        <label class="form-check-label">Historia</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Humanities" name="disciplina">
                        <label class="form-check-label">Humanidades</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Literature" name="disciplina">
                        <label class="form-check-label">Literatura</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Maths" name="disciplina">
                        <label class="form-check-label">Matemáticas</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Music" name="disciplina">
                        <label class="form-check-label">Música</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Naturalscience" name="disciplina">
                        <label class="form-check-label">Ciencias Naturales</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Physics" name="disciplina">
                        <label class="form-check-label">Física</label>
                    </div>
                    <div class="form-check">
                       <input class="form-check-input" type="checkbox" value="Technology" name="disciplina">
                        <label class="form-check-label">Tecnología</label>
                    </div>
                </div>
                </div>
                <div class="grupo">
                <label for="idioma">Idioma</label>
                <select class="input" name="idioma">
                    <option value="0">Todos...</option>
                    <option value="1">Español</option>
                    <option value="2">Inglés</option>
                </select>
                </div>
                    
                    <div id="b_avanzada">
                        <label id="info">Para buscar por otro estilo de aprendizaje, desmarque la casilla superior ...</label><br>
                    <div class="grupo">
                    <label class="oculto">Estilo de aprendizaje:</label>
                    <select class="input" id="estilo" name="estilo" disabled="true">
                        <option value="-1">Seleccione el estilo</option>
                        <option value="0">Visual</option>
                        <option value="1">Auditivo</option>
                        <option value="2">Lectura-Escritura</option>
                        <option value="3">Kinestésico</option>
                    </select>
                    </div>
            <!--        <div class="grupo">
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
                    </div> -->
                </div>
                <center><button type="submit" class="btn btn-enviar">Buscar</button></center>   
            </form>
        </div>
    </body>
    <jsp:include page="vistas/footer.jsp"/>
</html>
<%}%>