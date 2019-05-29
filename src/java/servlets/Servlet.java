package servlets;

import controller.Mensaje;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.gateway.JadeGateway;
import java.util.ArrayList;


@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet{  
    private JadeGateway gateway = null;
    Properties pp = new Properties();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try{
        doPost(request, response);
        }catch(Exception e){
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String keywords = request.getParameter("keywords");
            Mensaje mensaje = new Mensaje();
            mensaje.setMensaje("BS");
            mensaje.setArgumentos(keywords);
            try{
                JadeGateway.execute(mensaje); 
            }catch(Exception e){
                e.printStackTrace();
            }

            ArrayList<String[]> al =(ArrayList<String[]>) mensaje.getRespuesta();
            request.setAttribute("listado", al);
            request.setAttribute("ky", keywords);
            getServletContext().getRequestDispatcher("/resultados.jsp").forward(request, response);
  
    }
    
    @Override
    public void init()throws ServletException{
        pp.setProperty(Profile.MAIN_HOST, "localhost");
        pp.setProperty(Profile.MAIN_PORT, "1099");
       //pp.setProperty(Profile.MAIN,"main");
        JadeGateway.init("sma.AgenteInterfaz",pp);
    }

}
