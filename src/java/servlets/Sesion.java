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
import model.Virtuoso;
import org.apache.jena.query.ResultSet;

@WebServlet(name = "Sesion", urlPatterns = {"/Sesion"})
public class Sesion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {HttpSession respuesta = request.getSession(true);
        PrintWriter out = response.getWriter();
        //Declaro e inicio las variables
        String emailUsuario = request.getParameter("_isEmail");
        String password = request.getParameter("_isPassword");
        Pattern p = Pattern.compile("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$");
        Matcher m = p.matcher(emailUsuario);
        Validador v = new Validador();
        Dao d = new Dao();
       
        if(emailUsuario.isEmpty() || password.isEmpty()){
            respuesta.setAttribute("serror", "Hay campos vacios");
            
        } else {
            //No hay campos vacios, veo que la direccion de email sea válida
            if(!m.find()){
                respuesta.setAttribute("serror", "La dirección de email no es correcta");
                
            } else {
                //La direccion de email si es correcta, verifico que la contraseña tambien lo sea
               
                if(v.isUsernameOrPasswordValid(password)){    
                        try {
                            d.conectar();
                                if(!d.isEmailRegistered(emailUsuario)){
                                    respuesta.setAttribute("serror", "Esa direción de correo no está registrada");
                                } else if(d.isAcountExists(emailUsuario, password)) {
                                    //Legado a este punto significa que todo esta correcto, por lo tanto ingreso a la DB
                                    if(d.getTipo(emailUsuario)==1)
                                        respuesta.setAttribute("serror", "Para esta dirección debe ingresar con Google");
                                    else{   
                                    respuesta.setAttribute("user",emailUsuario);
                                    respuesta.setAttribute("name",d.getNombre(emailUsuario));
                                 
                                    }
                                }
                                else{
                                    respuesta.setAttribute("serror", "La contraseña es incorrecta");
                                }
                            
                            d.desconectar();
                             
                        } catch (Exception e) { System.out.println("Ocurrio la sig exception: " +e); }
                        
                    
                } else {
                    respuesta.setAttribute("serror", "La contraseña contiene caracteres no permitidos");
                   
                }
                
                
            }
        }
        
        response.sendRedirect("index.jsp");
    }



}
