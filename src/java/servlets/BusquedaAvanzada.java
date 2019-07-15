package servlets;

import controller.Mensaje;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.gateway.JadeGateway;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;


/**
 *
 * @author alexr
 */
@WebServlet(name = "Servlet2", urlPatterns = {"/Servlet2"})
public class BusquedaAvanzada extends HttpServlet{  
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
        HttpSession session = request.getSession(true);
        request.getSession().removeAttribute("listado");
        request.getSession().removeAttribute("ky");
        String user = (String)session.getAttribute("user");
         try{
            String keyword = new String(request.getParameter("keyword").getBytes("ISO-8859-1"),"UTF-8"); 
            String checkboxValues []=null;
            if(request.getHeader("disciplina")!=null)
              checkboxValues = request.getParameterValues("disciplina");
            
           
            
          
            String idioma = request.getParameter("idioma");
            String check = request.getParameter("fancy-checkbox-warning");
            ArrayList<String []> parametros = new ArrayList<>();
            String [] ba;
            if(check!=null){
                ba= new String [2];}
            else{
                String estilo = request.getParameter("estilo");
                String inteligencia = request.getParameter("inteligencia");
                ba= new String [4];
                ba[2]=estilo;
                ba[3]=inteligencia;
            }
            ba[0]=keyword;
            ba[1]=idioma;
            parametros.add(ba);
            parametros.add(checkboxValues);
            Mensaje mensaje = new Mensaje();
            mensaje.setUsuario(user);
            mensaje.setMensaje("BA");
            mensaje.setArgumentos(parametros);
            try{
                JadeGateway.execute(mensaje); 
            }catch(Exception e){
                e.printStackTrace();
            }
            ArrayList<String[]> al = new ArrayList<String[]>();
            if(mensaje.getRespuesta() instanceof ArrayList){
                al =(ArrayList<String[]>) mensaje.getRespuesta();
                session.setAttribute("listado", al);
                session.setAttribute("ky", keyword);
            }
            else{
                session.setAttribute("listado", al);
                session.setAttribute("ky",keyword);
                session.setAttribute("errorv",true);
            }
            response.sendRedirect("resultados.jsp?page=1");
        }catch(Exception e){
            System.out.println("Exception"+e);
          
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
