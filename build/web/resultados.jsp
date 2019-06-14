<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1" %>
<%  String variable = (String)session.getAttribute("error");
    String e_sesion = (String)session.getAttribute("serror");
    ArrayList<String []> resultados = (ArrayList<String []>)session.getAttribute("listado");
    String keywords = (String)session.getAttribute("ky");
    String pagina_string =(String)request.getParameter("page");
    //Sección para controlar la paginación
    int pagina;
    try{
        pagina = Integer.parseInt(pagina_string);
        if(pagina<=0)
            pagina=1;
        }catch(NumberFormatException e){
            pagina=1;
    }
    int limit = pagina *25;
    int base = limit-25;
    int paginacion = resultados.size()/25;
    int res =  resultados.size()%25;
    if(res>0)
        paginacion+=1;
    // FIN-Sección para controlar la paginación
    
    
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
            <link rel="stylesheet" type="text/css" href="assets/css/resultados.css"/>
            
            <%if(variable!=null)
                    out.print("<script>$(document).ready(function(){$(\"#registroModal\").modal(\"show\");});</script>");
              if(e_sesion!=null)
                    out.print("<script>$(document).ready(function(){$(\"#iniciarSesion\").modal(\"show\");});</script>");
            %>
                   </head>
   
        <jsp:include page="vistas/nav.jsp"/>
        <jsp:include page="vistas/searchbar.jsp"/>
       
    <body>
        <div class="encabezado bg-light" >
            <%if(resultados.size()<=0){%>
            <span>Lo sentimos, no se han encontrado resultados ...</span>
            <%}else{%>
            <span><%out.print("Se han encontrado "+resultados.size()+ " resultados para la cadena: "+keywords);%></span>
            <%}%>
        </div>
        <div id="contenedor">
        <%  for (int i = base; i < resultados.size()&&i<limit ; i++) {
            String [] aux = resultados.get(i);
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
                    <a href="detalles.jsp?dir=<%out.print(aux[4]);%>&lo=<%out.print(aux[0].split("#")[1]);%>&url=<%out.print(aux[2]);%>" class="btn btn-success">Ver</a>
                </div>
            </div>
            
        </div>
        
        
        <%}%>
         <div id="paginacion">
        <nav aria-label="Page navigation example" class="paginacion">
           <%int cont = pagina;%>
            <ul class="pagination">
              <li class="page-item">
                  <a class="page-link" href="resultados.jsp?page=<%if(cont!=1) out.print(cont-1); else out.print(1);%>" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                  <span class="sr-only">Previous</span>
                </a>
              </li>
              <%if(cont>3&&paginacion>10)
                    cont=cont-3;
                else
                    cont=1;
                if((pagina-10)>0) 
                    out.print("<li class='page-item mostrar'><a class='page-link' href='resultados.jsp?page="+(pagina-10)+"'>...</a></li>");
                for(int i=0;i<10&&cont<=paginacion;i++){
                    out.print("<li class='page-item mostrar'><a class='page-link' href='resultados.jsp?page="+cont+"'>"+cont+"</a></li>");
                    cont++;
                }
                if(paginacion>cont) 
                    out.print("<li class='page-item mostrar'><a class='page-link' href='resultados.jsp?page="+(cont++)+"'>...</a></li>");
              %>
              <li class="page-item">
                <a class="page-link" href="resultados.jsp?page=<%if(pagina==paginacion) out.print(pagina); else out.print(pagina+1);%>" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                  <span class="sr-only">Next</span>
                </a>
              </li>
            </ul>
        </nav>
         </div>
        </div>
       
    </body>
    <jsp:include page="vistas/footer.jsp"/>
</html>
