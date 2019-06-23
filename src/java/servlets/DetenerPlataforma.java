
package servlets;

import controller.Mensaje;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Profile;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Properties;
import jade.wrapper.gateway.JadeGateway;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alexr
 */
@WebServlet(name = "DetenerPlataforma", urlPatterns = {"/DetenerPlataforma"})
public class DetenerPlataforma extends HttpServlet {
    private JadeGateway gateway = null;
    Properties pp = new Properties();
    


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType( "text/html; charset=iso-8859-1" );
        PrintWriter out = response.getWriter();
        Mensaje mensaje = new Mensaje();
        mensaje.setMensaje("DP");
            try{
                JadeGateway.execute(mensaje); 
            }catch(Exception e){
                e.printStackTrace();
            }
            out.print(mensaje.getRespuesta());
    }

     @Override
    public void init()throws ServletException{
        pp.setProperty(Profile.MAIN_HOST, "localhost");
        pp.setProperty(Profile.MAIN_PORT, "1099");
        pp.setProperty(Profile.CONTAINER_NAME,"main");
        JadeGateway.init("sma.AgenteInterfaz",pp);
    }

}
