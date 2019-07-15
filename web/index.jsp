<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jena.query.QuerySolution"%>
<%@page import="org.apache.jena.query.ResultSet"%>
<%@page import="model.Virtuoso"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1" %>
<%  String variable = (String)session.getAttribute("error");
    String e_sesion = (String)session.getAttribute("serror");
    String user = (String)session.getAttribute("user");
%>
 <html lang="es">
        <head>
            <title>SMA-OA UNL</title>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
            <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
            <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
            <script src="assets/js/busqueda.js"></script>
            <link rel="stylesheet" type="text/css" href="assets/css/search.css"/>
            <link rel="stylesheet" type="text/css" href="assets/css/resultados.css"/>
            
            <%if(variable!=null)
                    out.print("<script>$(document).ready(function(){$(\"#registroModal\").modal(\"show\");});</script>");
              if(e_sesion!=null)
                    out.print("<script>$(document).ready(function(){$(\"#iniciarSesion\").modal(\"show\");});</script>");
            %>
        </head>
   
        <jsp:include page="vistas/nav.jsp"/>
       
        <body>
            <div id="fondo">
                <div id="izq">
                    <span><bold>Busque Objetos de Aprendizaje</bold>
                            de acuerdo a sus preferencias y estilos, para ello registre su perfil </span>
                </div>
                <div id="asearch_header">
                    <center><h1>Buscar</h1></center>
                  <div class="search">
                    <form action="Servlet" method="POST">
                    <input class="asearch_box search_input" name="keywords" type="text" placeholder="Buscar recursos educativos ..." autocomplete="off">
                    
                    <button class="toolbar_button search_button">
                      <img class="search_button_img" src="assets/img/search.png">
                    </button>
                    </form>
                  </div>
                    <div id="div_ba"> <center><a id ="busqueda_avanzada" href ="busqueda_avanzada.jsp" id="busqueda_avanzada">Búsqueda avanzada</a></center></div>
                </div>
            </div>
            <div class="row rejilla">
                <div class="col-md-4">
                    <div class="icono"><i class="fas fa-brain"></i></div>
                    <h5>Recursos Adaptativos</h5>
                    <span>Le recomendamos recursos digitales acorde a sus preferencias, usando tests
                    que determinar sus estilos de aprendizaje e inteligencias.</span>
                </div>
                <div class="col-md-4">
                    <div class="icono"><i class="fas fa-atlas"></i></div>
                    <h5>Repositorios Libres</h5>
                    <span>Trabajamos con repositorios libres, como: <a href="https://roa.cedia.edu.ec" target="_blank">roa.cedia.edu.ec</a>,
                        <a href="http://educainternet.es" target="_blank">educainternet.es</a> y <a href="http://vishub.org" target="_blank">vishub.org</a> </span>
                </div>
                <div class="col-md-4"><div class="icono"><i class="fas fa-robot"></i></div>
                    <h5>Sistema Multiagente</h5>
                <span>El sistema web esta montado sobre una plataforma de agentes inteligentes que se encargan de 
                cumplir con las peticiones del usuario.</span>
                </div>
            </div>
            <div class="ejemplos">
                <div class="t_ejemplos"><h5>Algunos Ejemplos</h5></div>
        <%  try{
            Virtuoso bdv = new Virtuoso();
            bdv.conectar("http://LearningObjects");
            ResultSet results=bdv.BusquedaInicio();
            ArrayList<String[]> al = new ArrayList<String[]>();
           while(results.hasNext()){
                String [] res = new String[4];
                QuerySolution qs = results.next();
                res[0]=qs.getResource("LearningObject").toString();
                res[1]=qs.get("title").toString();
                res[2]=qs.get("entry").toString();
                res[3]=qs.get("avatar").toString();
                al.add(res);
            }
          
         %>
        <div id="contenedor">
        <%  for (int i = 0;i<al.size() ; i++) {
            String [] aux = al.get(i);
        %>
            <div class="resultado">
            
            <%if(aux[3].equals("null")){%>
                <div class="img_avatar" style ="opacity: 0.8;background-image:url('assets/img/img_null.png');">
            <%}else{%>
                <div class="img_avatar" style ="background-image:url(<%out.print(aux[3]);%>);">
            <%}%> 
            <div class="loavatar"><i
            <%String loavatar = aux[0].split("#")[1].split(":")[0];
                if(loavatar.equals("Officedoc"))
                    out.print("class='fas fa-file-alt'");
                else if(loavatar.equals("Excursion"))
                    out.print("class='fas fa-puzzle-piece'");
                else if(loavatar.equals("Audio"))
                    out.print("class='fas fa-headphones'");
                else if(loavatar.equals("Embed"))
                    out.print("class='fas fa-code'");
                else if(loavatar.equals("Swf"))
                    out.print("class='fas fa-puzzle-piece'");
                else if(loavatar.equals("Link"))
                    out.print("class='fas fa-link'");
                else if(loavatar.equals("Picture"))
                    out.print("class='fas fa-image'");
                else if(loavatar.equals("Scormfile"))
                    out.print("class='fas fa-puzzle-piece'");
                else if(loavatar.equals("Video"))
                    out.print("class='fas fa-file-video'");
                else if(loavatar.equals("Webapp"))
                    out.print("class='fas fa-puzzle-piece'");
                else if(loavatar.equals("Workshop"))
                    out.print("class='fas fa-file-archive'");
                else
                    out.print("class='fas fa-puzzle-piece'");
            %>
                ></i>
            </div>
            </div>
           
            <div class="data">
                <div class="titulo">
                <h6 ><%out.print(aux[1]);%></h6>
                </div>
                <div class="boton">
                    <a href="detalles.jsp?lo=<%out.print(aux[0].split("#")[1]);%>&url=<%out.print(aux[2]);%>" class="btn btn-success">Ver</a>
                </div>
            </div>
            
        </div>
                <%}
        }catch(Exception e){
            out.print("Conectando con repositorios ...");
        }%>
            </div>
        
                 
         </div>
            </div>
            <br>
    </body>
    <jsp:include page="vistas/footer.jsp"/>
</html>
