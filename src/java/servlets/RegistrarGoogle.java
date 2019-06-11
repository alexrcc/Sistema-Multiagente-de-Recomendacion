package servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.IdTokenVerifierAndParser;
import model.Dao;

@WebServlet(name = "RegistrarGoogle", urlPatterns = {"/RegistrarGoogle"})
public class RegistrarGoogle extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession respuesta = request.getSession(true);
        response.setContentType("text/html");
        Dao d = new Dao();
        boolean exist = true; 
        System.out.println("llega");
        try {
            String idToken = request.getParameter("id_token");
            GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
            String name = (String) payLoad.get("name");
            String email = payLoad.getEmail();
       
                d.conectar();
                if(!d.isEmailRegistered(email)){
                    d.registerUser(email, "*", name,1,0);
                    exist=false;
                }
                respuesta.setAttribute("user",email);
                respuesta.setAttribute("name",name);
                
            if(!exist&&(d.getPefil(email)!=1)){
                respuesta.setAttribute("msg_registro","Se ha registrado Correctamente");
                response.sendRedirect("testea.jsp");

            }
            else{
                response.sendRedirect("index.jsp");
            }
        d.desconectar();
        } catch (Exception e) { 
            System.out.println("Ocurrio la sig exception: " +e);
            respuesta.setAttribute("error","Ha ocurrido un error durante el Registro.");
            response.sendRedirect("index.jsp");
                
        }
    }

}
