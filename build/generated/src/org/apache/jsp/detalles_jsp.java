package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Query;
import virtuoso.jena.driver.VirtModel;
import java.util.ArrayList;

public final class detalles_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
  String variable = (String)session.getAttribute("error");
    String e_sesion = (String)session.getAttribute("serror");
    String url = request.getParameter("dir");
    String learningObject  = request.getParameter("lo");
    String smas ="http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#";
    String vcard= "http://www.w3.org/2006/vcard/ns#";
    boolean band = true;
    VirtModel model=null;
    
    if(learningObject!=null){
        String URL = "jdbc:virtuoso://localhost:1111";
        String uid = "dba";
        String pwd = "dba";
        try{
            model = VirtModel.openDatabaseModel("http://LearningObjects", URL, uid, pwd);
        }catch(Exception e){
            band=false;
            out.print("<div class='alert alert-danger' role='alert'><center>Lo sentimos... "
                + "No se puede conectar con VIRTUSO OPENLIK!</center></div>");
    }

      out.write("\n");
      out.write("\n");
      out.write(" <html lang=\"es\">\n");
      out.write("        <head>\n");
      out.write("            <title>SMA-OA UNL</title>\n");
      out.write("            <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n");
      out.write("            <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.2/css/all.css\" integrity=\"sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr\" crossorigin=\"anonymous\">\n");
      out.write("            <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n");
      out.write("            <script src=\"https://code.jquery.com/jquery-3.3.1.min.js\"></script>\n");
      out.write("            <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>\n");
      out.write("            <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>\n");
      out.write("            <script src=\"assets/js/busqueda.js\"></script>\n");
      out.write("            <link rel=\"stylesheet\" type=\"text/css\" href=\"assets/css/detalles.css\"/>\n");
      out.write("            \n");
      out.write("            ");
if(variable!=null)
                    out.print("<script>$(document).ready(function(){$(\"#registroModal\").modal(\"show\");});</script>");
              if(e_sesion!=null)
                    out.print("<script>$(document).ready(function(){$(\"#iniciarSesion\").modal(\"show\");});</script>");
            
      out.write("\n");
      out.write("        </head>\n");
      out.write("   \n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "vistas/nav.jsp", out, false);
      out.write("\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "vistas/searchbar.jsp", out, false);
      out.write("\n");
      out.write("       \n");
      out.write("    <body>\n");
      out.write("        <div class=\"objeto\">\n");
      out.write("            <iframe src=\"");
out.print(url);
      out.write(".full\" width=\"80%\" height=\"600\" style=\"margin: 1% 10%;\" webkitAllowFullScreen=\"true\" allowfullscreen=\"true\" mozallowfullscreen=\"true\"></iframe>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"metadatos\">\n");
      out.write("            <div class=\"m_contenedor\">\n");
      out.write("                <h3>");
String stringQuery = "PREFIX smas: <"+smas+">"
                  + "SELECT ?title WHERE {?LearningObject smas:isComprisedOf ?General.?General vcard:title ?title.}"; 
            Query query = QueryFactory.create(stringQuery);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();
           while(results.hasNext()){
               QuerySolution qs = results.next();
               out.print(qs.getLiteral("visual").getString());
            }
      out.write("</h3>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "vistas/footer.jsp", out, false);
      out.write("\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
