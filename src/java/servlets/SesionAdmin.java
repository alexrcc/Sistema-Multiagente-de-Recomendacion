
package servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Dao;


@WebServlet(name = "SesionAdmin", urlPatterns = {"/SesionAdmin"})
public class SesionAdmin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession respuesta = request.getSession(true);
        String usuario = request.getParameter("InputUser");
        String password = request.getParameter("InputPassword");
        Pattern p = Pattern.compile("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$");
        Matcher m = p.matcher(usuario);
        Validador v = new Validador();
        Dao d = new Dao();
       
        if(usuario.isEmpty() || password.isEmpty()){
            respuesta.setAttribute("admerror", "Hay campos vacios");
            
        } else {
            if(v.isUsernameOrPasswordValid(password)){    
                try {
                    d.conectar();
                        if(!d.isAdminExists(usuario,password)){
                            respuesta.setAttribute("admerror", "Usuario o contraseña incorrectos");
                        } else{
                            respuesta.setAttribute("admuser",usuario);
                        }
                    d.desconectar();

                } catch (Exception e) { System.out.println("Ocurrió la sig exception: " +e); 
                    respuesta.setAttribute("admerror", "Ocurrió un error");
                }


            } else {
                respuesta.setAttribute("admerror", "La contraseña contiene caracteres no permitidos");
            }     
        }
        if(respuesta.getAttribute("admerror")!=null)
            response.sendRedirect("vistas/sesionadmin.jsp");
        else
            response.sendRedirect("admin.jsp");
    }
}


