package servlets;

import controller.Mensaje;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.JadeGateway;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sma.AgenteInterfaz1;

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
            HttpSession session = request.getSession(true);
            String user = (String)session.getAttribute("user");
            Mensaje mensaje = new Mensaje();
            for (int i = 1; i <=16; i++) {
                System.out.println("p"+i);
                String checkboxValues  []= request.getParameterValues("p"+i);
                // System.out.println("dddddddddddd"+checkboxValues[0]);
                if(checkboxValues != null){
                    for (int j = 0;  j<checkboxValues.length; j++) {
                        resp_vark[i-1][j]=checkboxValues[j];
                        // System.out.println(checkboxValues[j]);
                    }
                }
            }
            mensaje.setMensaje("CP");
            mensaje.setUsuario(user);
            mensaje.setArgumentos(resp_vark); 
            try{
                JadeGateway.execute(mensaje); 
            }catch(Exception e){
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("Contestación :" +mensaje.getRespuesta()+"<br>");
            out.flush();
            out.close();
            
            
        
    }
    
    @Override
    public void init()throws ServletException{
        pp.setProperty(Profile.MAIN_HOST, "localhost");
        pp.setProperty(Profile.MAIN_PORT, "1099");
       //pp.setProperty(Profile.MAIN,"main");
        JadeGateway.init("sma.AgenteInterfaz",pp);
    }

}
