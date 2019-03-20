/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sma;

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
import modelo.Dao;

/**
 *
 * @author alexr
 */
@WebServlet(name = "Registrar", urlPatterns = {"/Registrar"})
public class Registrar extends HttpServlet {
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession respuesta = request.getSession(true);
        PrintWriter out = response.getWriter();
        //Declaro e inicio las variables
        String nombreUsuario = request.getParameter("_iptNombres");
        String emailUsuario = request.getParameter("_iptEmail");
        String password = request.getParameter("_iptPassword");
        Pattern p = Pattern.compile("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$");
        Matcher m = p.matcher(emailUsuario);
        Validador v = new Validador();
        Dao d = new Dao();
        //Comienzo con las validaciones
        /*
         * Podemos hacer un monton de validaciones, por ejemplo:
         * Campos no vacios, direccion de email valida, nombre de usuario y contraseña
         * sin espacios y/o caracteres especiales.
         */
        
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
                                    respuesta.setAttribute("error", "Esa direeción de correo ya fue registrada");
                                } else {
                                    //Legado a este punto significa que todo esta correcto, por lo tanto ingreso a la DB
                                    d.registerUser(emailUsuario, password, nombreUsuario);
                                    respuesta.setAttribute("user",emailUsuario);
                                }
                            
                            d.desconectar();
                             
                        } catch (Exception e) { System.out.println("Ocurrio la sig exception: " +e); }
                        
                    
                } else {
                    respuesta.setAttribute("error", "La contraseña no es válida");
                   
                }
                
                
            }
        }
        
        response.sendRedirect("index.jsp");
        
    }
}
