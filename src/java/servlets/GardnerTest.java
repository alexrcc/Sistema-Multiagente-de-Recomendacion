package servlets;

import controller.Mensaje;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.gateway.JadeGateway;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "GardnerTest", urlPatterns = {"/GardnerTest"})
public class GardnerTest extends HttpServlet {
    private JadeGateway gateway = null;
    Properties pp = new Properties();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String resp_im [] = new String[35];
            HttpSession session = request.getSession(true);
            String user = (String)session.getAttribute("user");
            Mensaje mensaje = new Mensaje();
            
            for (int i = 0; i < resp_im.length; i++) {
                String values = request.getParameter("p"+(i+1));
                if(values != null){
                        resp_im[i]=values; 
                }
            }
            for (int i = 0; i < resp_im.length; i++) {
                System.out.println(resp_im[i]);
            }
            mensaje.setMensaje("IM");
            mensaje.setUsuario(user);
            mensaje.setArgumentos(resp_im); 
            try{
                JadeGateway.execute(mensaje); 
            }catch(Exception e){
                e.printStackTrace();
            }
            if(mensaje.getRespuesta().toString().compareToIgnoreCase("Perfil Guardado Correctamente")!=0){
                session.setAttribute("errors",mensaje.getRespuesta());
                response.sendRedirect("testim.jsp");
            }else{
                session.setAttribute("msgAP1",mensaje.getRespuesta());
                response.sendRedirect("perfil.jsp");
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
