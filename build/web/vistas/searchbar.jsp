
<%@page import="org.apache.jena.query.QuerySolution"%>
<%@page import="org.apache.jena.query.ResultSet"%>
<%@page import="model.Virtuoso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String profile = (String)session.getAttribute("intelligentProfile");
    String usuario = (String)session.getAttribute("user");
    if(profile==null&&usuario!=null){
        try{
            Virtuoso bdv = new Virtuoso();
            boolean band = false;
            bdv.conectar("Perfiles");
            ResultSet results=bdv.GetEstilos(usuario);
            while(results.hasNext()){
                   band=true;
                   QuerySolution qs = results.next();
               }
            if (band==true){
                session.setAttribute("intelligentProfile","si");
                profile=(String)session.getAttribute("intelligentProfile");}
            else{
                session.setAttribute("intelligentProfile","no");
                profile=(String)session.getAttribute("intelligentProfile");
            }
            bdv.desconectar();
            }catch(Exception e){
                System.out.println("Error al conectar con virtuoso: "+e);
            }
    }
%>
<link rel="stylesheet" href="assets/css/searchbar.css"/>
<div id="navsearch">
    <div class="volver">
            <button type="button" class="btn btn-success btn-circle"  onclick="history.back()"><i class="fas fa-arrow-left"></i>
            </button>
        </div>
    <div class="input_search">
        <form action="Servlet" method="POST">
<div class="input-group md-form form-sm form-2 pl-0">
    
  <input class="form-control my-0 py-1 amber-border" type="text" placeholder="Buscar..." name ="keywords" aria-label="Search" autocomplete="off">
  <div class="input-group-append">
      <button type="submit" class="input-group-text amber lighten-3" id="basic-text1"><i class="fas fa-search text-grey"
        aria-hidden="true"></i></button>
  </div>
</div>
<%if(profile!=null&&(profile.equals("si"))){%>
        <div class="[ form-group ]">
            <input type="checkbox" name="fancy-checkbox-warning" id="fancy-checkbox-warning" autocomplete="off" checked/>
            <div class="[ btn-group ]">
                <label for="fancy-checkbox-warning" class="[ btn btn-warning ]">
                    <span class="fas fa-check"></span>
                    <span class="fas fa-minus"></span>
                </label>
                <label for="fancy-checkbox-warning" class="[ btn btn-default active ]">
                    Filtrar según mi estilo de aprendizaje
                </label>
            </div>
        </div>
        <%}%>
              </form>

        </div>
    <div class="enlace">
        <a href="busqueda_avanzada.jsp"><span>Búsqueda avanzada <i class="fas fa-long-arrow-alt-right"></i></a>
    </div>

</div>