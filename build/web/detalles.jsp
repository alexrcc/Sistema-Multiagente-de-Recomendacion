<%@page import="model.Virtuoso"%>
<%@page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1" %>
<%@page import="org.apache.jena.query.QuerySolution"%>
<%@page import="org.apache.jena.query.ResultSet"%>
<%@page import="org.apache.jena.query.QueryExecutionFactory"%>
<%@page import="org.apache.jena.query.QueryExecution"%>
<%@page import="org.apache.jena.query.QueryFactory"%>
<%@page import="org.apache.jena.query.Query"%>
<%@page import="virtuoso.jena.driver.VirtModel"%>
<%@page import="java.util.ArrayList"%>

<%  String variable = (String)session.getAttribute("error");
    String e_sesion = (String)session.getAttribute("serror");
    String user = (String)session.getAttribute("user");
    String bintelligent = (String)session.getAttribute("bintelligent");
    String url = null;
    String rep_url = request.getParameter("url");
    String learningObject  = request.getParameter("lo");
    String smas ="http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#";
    String vcard= "http://www.w3.org/2006/vcard/ns#";
    String ns = "http://www.w3.org/ns/radion#";
    VirtModel model=null;
    boolean band = true;
    
    if(learningObject!=null){
        //String URL = "jdbc:virtuoso://localhost:1111";
        String URL = "jdbc:virtuoso://104.210.144.119:1111";
        String uid = "dba";
        String pwd = "dba";
        try{
            model = VirtModel.openDatabaseModel("http://LearningObjects", URL, uid, pwd);
        }catch(Exception e){
            band=false;
            out.print("<div class='alert alert-danger' role='alert'><center>Lo sentimos... "
                + "No se puede conectar con VIRTUSO OPENLIK!</center></div>");
    }
    }
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
            <link rel="stylesheet" type="text/css" href="assets/css/detalles.css"/>
            
            <%if(variable!=null)
                    out.print("<script>$(document).ready(function(){$(\"#registroModal\").modal(\"show\");});</script>");
              if(e_sesion!=null)
                    out.print("<script>$(document).ready(function(){$(\"#iniciarSesion\").modal(\"show\");});</script>");
            %>
            <%
    
            if(bintelligent==null&&user!=null){
                try{
                    Virtuoso bdv = new Virtuoso();
                    boolean bandi = false;
                    bdv.conectar("Perfiles");
                    ResultSet resultsi=bdv.GetInteligencias(user);
                    while(resultsi.hasNext()){
                           bandi=true;
                           QuerySolution qs = resultsi.next();
                       }
                    if (bandi==true){
                        session.setAttribute("bintelligent","si");
                        bintelligent=(String)session.getAttribute("bintelligent");}
                    else{
                        session.setAttribute("bintelligent","no");
                        bintelligent=(String)session.getAttribute("bintelligent");
                    }
                    bdv.desconectar();
                    }catch(Exception e){
                        System.out.println("Error al conectar con virtuoso: "+e);
                    }
            }
    %>
            <%if(user!=null&&bintelligent.equals("si")){%>
            <script>
                $(document).ready(function() {
                                // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                $.post('BusquedaInteligencias', {
                                        user :'<%out.print(user);%>'
                                }, function(responseText) {
                                    
                                        $('#tabla').html(responseText);
                                });
                       
                });
            </script>
        <%}%>
        </head>
   
        <jsp:include page="vistas/nav.jsp"/>
        <jsp:include page="vistas/searchbar.jsp"/>
       
    <body>
        
        <h3>  <%
                    String stringQuery = "PREFIX vcard:<"+vcard+"> "+
                    "PREFIX smas: <"+smas+"> "
                    + "SELECT ?title ?url_full  WHERE {?LearningObject smas:url_full ?url_full. "
                            +"?LearningObject smas:isComprisedOf ?General."
                            +"?General vcard:title ?title."
                    + "FILTER(regex(?LearningObject,'"+smas+learningObject+"')).}"; 
                    Query query = QueryFactory.create(stringQuery);
                    QueryExecution qe = QueryExecutionFactory.create(query, model);
                    ResultSet results = qe.execSelect();
                    while(results.hasNext()){
                        QuerySolution qs = results.next();
                        out.print(qs.get("title"));
                        url=qs.get("url_full").toString();
                    }
                    %></h3>
        <div class="objeto">
            <%if(learningObject.split(":")[0].equals("Zipfile")||learningObject.split(":")[0].equals("Workshop")){%>
            
            <a class="btn btn-primary" href="<%
                String dir = url.replace(".zip","");
                out.print(dir);
            %>">Ir a recurso</a>
            <%}else if(learningObject.split(":")[0].equals("Officedoc")){%>
            <iframe src="https://docs.google.com/viewer?url=https:<%out.print(url.split(":")[1]);%>&embedded=true" width="100%" height="600" style="border: none;" webkitAllowFullScreen="true" allowfullscreen="true" mozallowfullscreen="true"></iframe>
            <%}else if(learningObject.split(":")[0].equals("Link")){%>
                <iframe src="<%out.print(url);%>" width="100%" height="600" style="" webkitAllowFullScreen="true" allowfullscreen="true" mozallowfullscreen="true"></iframe>
                <div class="enlace_oa"><span><a href="<%out.print(url);%>" target="_blank"><%out.print(url);%></a></span></div>
            <%}else if(learningObject.split(":")[0].equals("Video")){%>
                <iframe src="<%String dir = url.replace(".mov",".mp4");out.print(dir);%>" width="100%" height="600" style="" webkitAllowFullScreen="true" allowfullscreen="true" mozallowfullscreen="true"></iframe>
            <%}else{%>
            <iframe src="<%out.print(url);%>" width="100%" height="600" style="" webkitAllowFullScreen="true" allowfullscreen="true" mozallowfullscreen="true"></iframe>
            <%}%>
        </div>
        <div class="metadatos">
            <h5 class="m_title">Información</h5>
            <div class="m_contenedor">
                <div><%
                    stringQuery = "PREFIX vcard:<"+vcard+"> "+
                    "PREFIX smas: <"+smas+"> "
                    + "SELECT ?title  WHERE {?General vcard:title ?title. "
                    + "FILTER(regex(?General,'"+smas+"Gen_"+learningObject+"')).}"; 
                     query = QueryFactory.create(stringQuery);
                     qe = QueryExecutionFactory.create(query, model);
                    results = qe.execSelect();
                    while(results.hasNext()){
                        QuerySolution qs = results.next();
                        out.print("<span class='etiqueta'>Titulo: </span><span class='valor'>"+qs.get("title")+"</span>");
                    }
                    //Descripción
                     stringQuery = "PREFIX smas: <"+smas+"> "
                    + "SELECT ?desc WHERE {"
                    +"?General smas:description ?desc. "
                    + "FILTER(regex(?General,'"+smas+"Gen_"+learningObject+"')).}"; 
                    query = QueryFactory.create(stringQuery);
                    qe = QueryExecutionFactory.create(query, model);
                    results = qe.execSelect();
                   while(results.hasNext()){
                       QuerySolution qs = results.next();
                       out.print("<span class='etiqueta'>Descripción: </span><span class='valor'>"+qs.get("desc")+"</span>");
                   }
                   //Idioma
                    stringQuery = "PREFIX vcard:<"+vcard+"> "+
                    "PREFIX smas: <"+smas+"> "
                    + "SELECT ?language WHERE {"
                    +"?General vcard:language ?language. "
                    + "FILTER(regex(?General,'"+smas+"Gen_"+learningObject+"')).}"; 
                    query = QueryFactory.create(stringQuery);
                    qe = QueryExecutionFactory.create(query, model);
                    results = qe.execSelect();
                   while(results.hasNext()){
                       QuerySolution qs = results.next();
                       out.print("<span class='etiqueta'>Idioma: </span><span class='valor'>"+qs.get("language")+"</span>");
                   }
                   //Keywords
                   stringQuery = "PREFIX ns:<http://www.w3.org/ns/radion#> "+
                    "PREFIX smas: <"+smas+"> "
                    + "SELECT ?ky WHERE {"
                    +"?General ns:keyword ?ky. "
                    + "FILTER(regex(?General,'"+smas+"Gen_"+learningObject+"')).}"; 
                    query = QueryFactory.create(stringQuery);
                    qe = QueryExecutionFactory.create(query, model);
                    results = qe.execSelect();
                    out.print("<span class='etiqueta'>Keywords: </span>");
                   while(results.hasNext()){
                       QuerySolution qs = results.next();
                       if(qs.contains("ky"))
                            out.print("<span class='keys bg-info'>"+qs.get("ky")+"</span>");

                   }
                   //Autor
                   stringQuery = 
                    "PREFIX ns:<http://www.w3.org/ns/radion#> "+
                    "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#> "+
                    "PREFIX smas: <"+smas+"> "
                    + "SELECT ?entity ?role WHERE {"
                    +"?Metametadata smas:hasContribute  ?Contribute. "
                    +"?Contribute smas:entity  ?entity. "
                    +"?Contribute vcard:role  ?role. "
                    + "FILTER(regex(?Metametadata,'"+smas+"M_"+learningObject+"')).}"; 
                    query = QueryFactory.create(stringQuery);
                    qe = QueryExecutionFactory.create(query, model);
                    results = qe.execSelect();
                    while(results.hasNext()){
                       QuerySolution qs = results.next();
                        if(qs.contains("role")&&((qs.get("role").toString()).equals("creator"))){
                            String aux =qs.getLiteral("entity").getValue().toString();
                            aux=aux.split(":")[3];
                            aux=aux.replaceAll("FN","");
                            out.print("<span class='etiqueta' >Autor: </span><span class='valor'>"+aux+"</span>");}
                   }
                   
                   //Autor
                   stringQuery = 
                    "PREFIX ns:<http://www.w3.org/ns/radion#> "+
                    "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#> "+
                    "PREFIX smas: <"+smas+"> "
                    + "SELECT ?entry WHERE {"
                    +"?Metametadata smas:hasIdentifier  ?Identifier. "
                    +"?Identifier smas:entry  ?entry. "
                    + "FILTER(regex(?Metametadata,'"+smas+"M_"+learningObject+"')).}"; 
                    query = QueryFactory.create(stringQuery);
                    qe = QueryExecutionFactory.create(query, model);
                    results = qe.execSelect();
                    while(results.hasNext()){
                        QuerySolution qs = results.next();
                        out.print("<span class='etiqueta'>Metadata: </span><span class='valor'><a href='"+qs.get("entry")+"' target='_blank'>"+qs.get("entry")+"</a></span>");
                   }
                    //Rights
                   stringQuery = 
                    "PREFIX ns:<http://www.w3.org/ns/radion#> "+
                    "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#> "+
                    "PREFIX smas: <"+smas+"> "
                    + "SELECT ?desc WHERE {"
                    +"?Rights smas:description  ?desc. "
                    + "FILTER(regex(?Rights,'"+smas+"R_"+learningObject+"')).}"; 
                    query = QueryFactory.create(stringQuery);
                    qe = QueryExecutionFactory.create(query, model);
                    results = qe.execSelect();
                    while(results.hasNext()){
                        QuerySolution qs = results.next();
                        out.print("<span class='etiqueta'>Derechos de Autor: </span><span class='valor'>"+qs.get("desc")+"</span>");
                   }
                %>
                <span class='etiqueta'>URL Repositorio: </span><span class='valor'><a href="<%out.print(rep_url);%>" target="_blank"><%out.print(rep_url);%></a></span>
                </div>
            </div>
        </div>
        
        <div id="tabla"></div>
    </body>
    
    <jsp:include page="vistas/footer.jsp"/>
</html>
