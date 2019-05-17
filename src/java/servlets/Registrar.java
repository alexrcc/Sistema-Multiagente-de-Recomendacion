package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Dao;


@WebServlet(name = "Registrar", urlPatterns = {"/Registrar"})
public class Registrar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession respuesta = request.getSession(true);
        //Declaro e inicio las variables
        String nombreUsuario = request.getParameter("_iptNombres");
        String emailUsuario = request.getParameter("_iptEmail");
        String password = request.getParameter("_iptPassword");
        Pattern p = Pattern.compile("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$");
        Matcher m = p.matcher(emailUsuario);
        Validador v = new Validador();
        Dao d = new Dao();
        //Comienzo con las validaciones
        
        //campos vacios
        if(nombreUsuario.isEmpty() || emailUsuario.isEmpty() || password.isEmpty()){
            respuesta.setAttribute("error", "Hay campos vacios");
            
        } else {
            //No hay campos vacios, veo que la direccion de email sea válida
            if(!m.find()){
                respuesta.setAttribute("error", "La dirección de email no es correcta");
                
            } else {
                //La direccion de email si es correcta, verifico que la contraseña tambien lo sea
               
                if(v.isUsernameOrPasswordValid(password)){    
                        try {
                            d.conectar();
                                if(d.isEmailRegistered(emailUsuario)){
                                    respuesta.setAttribute("error", "Esa direción de correo ya fue registrada");
                                } else {
                                    //Legado a este punto significa que todo esta correcto, por lo tanto ingreso a la DB
                                    d.registerUser(emailUsuario, password, nombreUsuario,0,0);
                                    respuesta.setAttribute("user",emailUsuario);
                                    respuesta.setAttribute("name",nombreUsuario);
                                }
                            d.desconectar();
                             
                        } catch (Exception e) { System.out.println("Ocurrio la sig exception: " +e); }
                        
                    
                } else {
                    respuesta.setAttribute("error", "La contraseña no es válida");
                }
                
                
            }
        }
        
        if(respuesta.getAttribute("error")!=null)
            response.sendRedirect("index.jsp");
        else{
            respuesta.setAttribute("msg_registro","Se ha registrado Correctamente");
            response.sendRedirect("cuestionario.jsp");
        }
    }
}
