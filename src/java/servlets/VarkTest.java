package servlets;

import controller.Mensaje;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.gateway.JadeGateway;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Virtuoso;
import org.apache.jena.query.ResultSet;


/**
 *
 * @author alexr
 */
@WebServlet(name = "VarkTest", urlPatterns = {"/VarkTest"})
public class VarkTest extends HttpServlet {
    private JadeGateway gateway = null;
    Properties pp = new Properties();
    private String resp_vark [][] = new String[16][4];
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Se reinicia los valores por si se recarga la página
            for (int i = 1; i <= 16; i++) {
                for (int j = 0;  j<4; j++) {
                    resp_vark[i-1][j]=null;
                }
            }
            HttpSession session = request.getSession(true);
            String user = (String)session.getAttribute("user");
            Mensaje mensaje = new Mensaje();
            for (int i = 1; i <=16; i++) {
                String checkboxValues  []= request.getParameterValues("p"+i);
                if(checkboxValues != null){
                    for (int j = 0;  j<checkboxValues.length; j++) {
                        resp_vark[i-1][j]=checkboxValues[j];
                    }
                }
            }
            mensaje.setMensaje("EA");
            mensaje.setUsuario(user);
            mensaje.setArgumentos(resp_vark); 
            try{
                JadeGateway.execute(mensaje); 
            }catch(Exception e){
                e.printStackTrace();
            }
            if(mensaje.getRespuesta().toString().compareToIgnoreCase("Perfil Guardado Correctamente")!=0){
                session.setAttribute("errors",mensaje.getRespuesta());
                session.setAttribute("intelligentProfile",true);
                                    
                response.sendRedirect("testea.jsp");
            }else{
                session.setAttribute("msgAP",mensaje.getRespuesta());
                response.sendRedirect("testim.jsp");
            }               
                
            
           
            
            
        
    }
    
    @Override
    public void init()throws ServletException{
        pp.setProperty(Profile.MAIN_HOST, "localhost");
        pp.setProperty(Profile.MAIN_PORT, "1099");
        pp.setProperty(Profile.CONTAINER_NAME,"main");
        JadeGateway.init("sma.AgenteInterfaz",pp);
    }

}
