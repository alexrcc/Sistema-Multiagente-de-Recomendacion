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

@WebServlet(name = "AgregarRepositorio", urlPatterns = {"/AgregarRepositorio"})
public class AgregarRepositorio extends HttpServlet {
        private JadeGateway gateway = null;
        Properties pp = new Properties();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = new String(request.getParameter("url")
                    .getBytes("ISO-8859-1"),"UTF-8"); 
        response.setContentType( "text/html; charset=iso-8859-1" );
        PrintWriter out = response.getWriter();
        Mensaje mensaje = new Mensaje();
        mensaje.setMensaje("AG");
        mensaje.setArgumentos(url);
            try{
                JadeGateway.execute(mensaje); 
            }catch(Exception e){
                mensaje.setRespuesta("Ocurrió un error al ejecutar los agentes");
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
