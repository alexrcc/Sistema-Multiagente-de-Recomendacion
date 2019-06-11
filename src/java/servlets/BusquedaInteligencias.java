package servlets;

import controller.Mensaje;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.gateway.JadeGateway;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BusquedaInteligencias", urlPatterns = {"/BusquedaInteligencias"})
public class BusquedaInteligencias extends HttpServlet {
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
        response.setContentType( "text/html; charset=iso-8859-1" );
        PrintWriter out = response.getWriter();
        String user = new String(request.getParameter("user").getBytes("ISO-8859-1"),"UTF-8");
        Mensaje mensaje = new Mensaje();
        mensaje.setMensaje("BI");
        mensaje.setUsuario(user);
        System.out.println("Ajax");
        try{
            JadeGateway.execute(mensaje); 
        }catch(Exception e){
            System.out.println("Ha ocuerrido una exceptción: "+e);
        }

        ArrayList<String[]> al =(ArrayList<String[]>) mensaje.getRespuesta();
        out.print("<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.2/css/all.css\" integrity=\"sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr\" crossorigin=\"anonymous\">");
        out.print("<link rel='stylesheet' type='text/css' href='assets/css/resultados.css'/>");
        for (int i = 0; i < al.size()&&i<6; i++) {
            String [] aux = al.get(i);
            
            out.print("<div class='cont_im'>");
           
            if(aux[3].equals("null")){
                out.print("<div class='img_avatar' style ='opacity: 0.8;background-image:url(assets/img/img_null.png);'>");
            }else{
                out.print("<div class='img_avatar' style ='background-image:url("+aux[3]+");'>");
            }
            out.print("<div class='loavatar'><i");
            String loavatar = aux[0].split("#")[1].split(":")[0];
                if(loavatar.equals("Officedoc"))
                    out.print(" class='fas fa-file-al'");
                else if(loavatar.equals("Excursion"))
                    out.print(" class='fas fa-puzzle-piece'");
                else if(loavatar.equals("Audio"))
                    out.print(" class='fas fa-headphones'");
                else if(loavatar.equals("Embed"))
                    out.print(" class='fas fa-code'");
                else if(loavatar.equals("Swf"))
                    out.print(" class='fas fa-puzzle-piece'");
                else if(loavatar.equals("Link"))
                    out.print(" class='fas fa-link'");
                else if(loavatar.equals("Picture"))
                    out.print(" class='fas fa-image'");
                else if(loavatar.equals("Scormfile"))
                    out.print(" class='fas fa-puzzle-piece'");
                else if(loavatar.equals("Video"))
                    out.print(" class='fas fa-file-video'");
                else if(loavatar.equals("Webapp"))
                    out.print(" class='fas fa-puzzle-piece'");
                else if(loavatar.equals("Workshop"))
                    out.print(" class='fas fa-file-archive'");
                else
                    out.print(" class='fas fa-puzzle-piece'");
            out.print("></i>");
            out.print("</div>");
            out.print("</div>");
           
           out.print("<div class='cont_info'>");
            out.print("<div class='titulo'>");
                 out.print("<h6 >"+aux[1]+"</h6>");
                 out.print("</div>");
                 out.print("<div class='boton'>");
                     out.print("<a href='detalles.jsp?dir="+aux[4]+"&lo="+aux[0].split("#")[1]+"&url="+aux[2]+"' class='btn btn-success'>Ver</a>");
                out.print("</div>");
           out.print("</div>");
           out.print("</div>");  
        }
        
    }
    @Override
    public void init()throws ServletException{
        pp.setProperty(Profile.MAIN_HOST, "localhost");
        pp.setProperty(Profile.MAIN_PORT, "1099");
       //pp.setProperty(Profile.MAIN,"main");
        JadeGateway.init("sma.AgenteInterfaz",pp);
    }

}
