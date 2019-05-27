<%@ page contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1" %>
<%@page import="virtuoso.jena.driver.VirtModel"%>
<%@page import="org.apache.jena.rdf.model.Resource"%>
<%@page import="org.apache.jena.query.QuerySolution"%>
<%@page import="org.apache.jena.graph.Triple"%>
<%@page import="java.io.FileInputStream"%>
<%@ page import="org.apache.jena.query.ResultSet" %>
<%@ page import="org.apache.jena.query.QueryExecutionFactory"%>
<%@ page import="org.apache.jena.query.QueryExecution"%>
<%@ page import="org.apache.jena.query.QueryFactory"%>
<%@ page import="org.apache.jena.query.Query"%>


<%  String user = (String)session.getAttribute("user");
    String name_user = (String)session.getAttribute("name");
    boolean band=false;
    boolean bandim=false;
    if(user==null)
        response.sendRedirect("index.jsp");
    String smas ="http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#";
    int estilos[] = new int[4]; //{v,a,r,k}
    int inteligencias[] = new int[7]; //{v,a,r,k}
    int porcent[] = new int[4];
    int porcentim [] = new int[7];
%>

<%
    String URL = "jdbc:virtuoso://localhost:1111";
    String uid = "dba";
    String pwd = "dba";
    VirtModel model=null;
    try{
        model = VirtModel.openDatabaseModel("Perfiles", URL, uid, pwd);
        String stringQuery = 
        "PREFIX smas: <"+smas+">"
              + "SELECT * WHERE {"
              + "<"+smas+"LE-"+user+"> smas:visual ?visual."
              + "<"+smas+"LE-"+user+"> smas:aural ?aural."
              + "<"+smas+"LE-"+user+"> smas:kinesthetic ?kinesthetic."
              + "<"+smas+"LE-"+user+"> smas:readwrite ?readwrite}";     
        Query query = QueryFactory.create(stringQuery);
        // Ejecutar la consulta y obtener los resultados
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        
        ResultSet results = qe.execSelect();
       while(results.hasNext()){
           band=true;
           QuerySolution qs = results.next();
           estilos[0]=qs.getLiteral("visual").getInt();
           estilos[1]=qs.getLiteral("aural").getInt();
           estilos[3]=qs.getLiteral("kinesthetic").getInt();
           estilos[2]=qs.getLiteral("readwrite").getInt();
       }
       stringQuery = 
        "PREFIX smas: <"+smas+">"
              + "SELECT * WHERE {"
              + "<"+smas+"MI-"+user+"> smas:verbal ?verbal."
              + "<"+smas+"MI-"+user+"> smas:logicalmath ?logical."
              + "<"+smas+"MI-"+user+"> smas:visual ?visual."
              + "<"+smas+"MI-"+user+"> smas:kinesthetic ?kinesthetic."
              + "<"+smas+"MI-"+user+"> smas:musical ?musical."
              + "<"+smas+"MI-"+user+"> smas:intrapersonal ?intrapersonal."
              + "<"+smas+"MI-"+user+"> smas:interpersonal ?interpersonal}";     
        query = QueryFactory.create(stringQuery);
        // Ejecutar la consulta y obtener los resultados
        qe = QueryExecutionFactory.create(query, model);
        
        results = qe.execSelect();
       while(results.hasNext()){
           bandim=true;
           QuerySolution qs = results.next();
           inteligencias[0]=qs.getLiteral("verbal").getInt();
           inteligencias[1]=qs.getLiteral("logical").getInt();
           inteligencias[2]=qs.getLiteral("visual").getInt();
           inteligencias[3]=qs.getLiteral("kinesthetic").getInt();
           inteligencias[4]=qs.getLiteral("musical").getInt();
           inteligencias[5]=qs.getLiteral("intrapersonal").getInt();
           inteligencias[6]=qs.getLiteral("interpersonal").getInt();
       }
       int suma=0;
       for(int v:estilos){
           suma = suma+v;
       }
       double aux []= new double[4];
       for(int i =0;i<estilos.length;i++){
           aux[i]=(double)(estilos[i]*100)/suma;
           porcent[i]=(int)(Math.round(aux[i]));
       }
       double auxim []= new double[7];
       for(int i =0;i<inteligencias.length;i++){
           auxim[i]=(double)(inteligencias[i]*100)/5;
           porcentim[i]=(int)(Math.round(auxim[i]));
       }
       qe.close();
    }catch(Exception e){
        out.print("<div class='alert alert-danger' role='alert'><center>Lo sentimos... "
                + "No se puede conectar con VIRTUSO OPENLIK!</center></div>");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>SMA-OA UNL</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link rel="stylesheet" type="text/css" href="assets/css/perfil.css"/>
    </head>
    <jsp:include page="vistas/nav.jsp"/>
    <body>
        <div id="cprincipal">
            <div class="inf_personal">
                <div>
                    <div class="img"><span class="fas fa-user-circle"></span></div>
                    <div class="datos">
                        <div class="fila"><span class="etiqueta">Nombre: </span><span class="valor"><% out.print(name_user);%></span></div>    
                        <div class="fila"><span class="etiqueta">Correo: </span><span class="valor"><% out.print(user);%></span></div>    
                    </div>    
                           
                        
                        </div>
                    <br>
                    <div class="fila">
                        <a href='#' onClick='signOut();'>Cerrar sesión<span class='fas fa-power-off'></span></a>
                    </div>
            </div>
            </div>
            <div class="perfil">
                <div class="if_estilos">
                    <span>Estilos de Aprendizaje VARK</span><span class="help far fa-question-circle">
                        <div id="help_ea"><span class="triangle"></span><span class="texto">El test de VARK clasifica a las personas de acuerdo a su preferencia 
                                sensorial al procesar información. Las preferencias modales sensoriales son (Visual= Visual, Aural = Auditivo, 
                                Read/Write = lectura/escritura, Kinesthetic = Quinestésico por las siglas en inglés)."</span>
                        </div>
                        
                    </span>
                    <% if(!band)out.print("<div class='alert alert-warning' role='alert'>Por favor, conteste el"
                                + "cuestionario de VARK para determinar su estilo de aprendizaje predominante.</div>");
                        %>
                    <div class="estilo"><span class="v_etiqueta">Visual: </span>
                        <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-primary" style="width: <%out.print(porcent[0]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcent[0]);%>%</div>
                        </div>
                    </div>
                    <div class="estilo"><span class="v_etiqueta">Aural: </span>
                        <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-success" style="width: <%out.print(porcent[1]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcent[1]);%>%</div>
                        </div>
                    </div>
                    <div class="estilo"><span class="v_etiqueta">Read-write: </span>
                        <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-info" style="width: <%out.print(porcent[2]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcent[2]);%>%</div>
                        </div>
                    </div>
                    <div class="estilo"><span class="v_etiqueta">Kinesthetic: </span>
                         <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-danger" style="width: <%out.print(porcent[3]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcent[3]);%>%</div>
                        </div>
                    </div>
                        
                        <a class="btn btn-test" href="testea.jsp" role="button"><%if(!band){out.print("Realizar Test");}else{out.print("Repetir Test");}%></a>
                </div>
                <div class="if_inteligencias">
                    <span>Inteligencias múltiples</span><span class="help far fa-question-circle">
                        <div id="help_ea"><span class="triangle"></span><span class="texto">El teste de vark es un test que consiste en 4 habilidades
                        la habiliidad visula, auditiva, lectura escritua y movimiento."</span></div>
                    </span>
                    <% if(!bandim)out.print("<div class='alert alert-warning' role='alert'>Por favor, conteste el"
                                + "cuestionario de Gardener para determinar sus inteligencias múltiples.</div>");
                        %>
                    <div class="inteligencia"><span class="v_etiqueta">Verbal: </span>
                        <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-primary" style="width: <%out.print(porcentim[0]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcentim[0]);%>%</div>
                        </div>
                    </div>
                    <div class="inteligencia"><span class="v_etiqueta">Lógico-matemática: </span>
                        <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-secondary" style="width: <%out.print(porcentim[1]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcentim[1]);%>%</div>
                        </div>
                    </div>
                    <div class="inteligencia"><span class="v_etiqueta">Visual-espacial: </span>
                        <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-success" style="width: <%out.print(porcentim[2]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcentim[2]);%>%</div>
                        </div>
                    </div>
                    <div class="inteligencia"><span class="v_etiqueta">Kinestesica: </span>
                         <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-danger" style="width: <%out.print(porcentim[3]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcentim[3]);%>%</div>
                        </div>
                    </div>
                    <div class="inteligencia"><span class="v_etiqueta">Músical: </span>
                         <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-warning" style="width: <%out.print(porcentim[4]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcentim[4]);%>%</div>
                        </div>
                    </div>
                    <div class="inteligencia"><span class="v_etiqueta">Intrapersonal: </span>
                         <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-info" style="width: <%out.print(porcentim[5]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcentim[5]);%>%</div>
                        </div>
                    </div>
                    <div class="inteligencia"><span class="v_etiqueta">Interpersonal: </span>
                         <div class="progress">
                            <div class="progress-bar progress-bar-striped bg-dark" style="width: <%out.print(porcentim[6]);%>%" aria-valuemin="0" aria-valuemax="100"><%out.print(porcentim[6]);%>%</div>
                        </div>
                    </div>
                        
                        <a class="btn btn-test" href="testim.jsp" role="button"><%if(!bandim){out.print("Realizar Test");}else{out.print("Repetir Test");}%></a>
                
                </div>
            </div>
        </div>
    </body>
    <jsp:include page="vistas/footer.jsp"/>
</html>
