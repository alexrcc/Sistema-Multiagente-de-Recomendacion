/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sma;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.IdTokenVerifierAndParser;
import modelo.Dao;

/**
 *
 * @author alexr
 */
@WebServlet(name = "RegistrarGoogle", urlPatterns = {"/RegistrarGoogle"})
public class RegistrarGoogle extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        Dao d = new Dao();
        boolean exist = true; 

        try {
            String idToken = request.getParameter("id_token");
            GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
            String name = (String) payLoad.get("name");
            String email = payLoad.getEmail();
            //System.out.println("User name: " + name);
            //System.out.println("User email: " + email);
            HttpSession respuesta = request.getSession(true);
            try {
                d.conectar();
                if(!d.isEmailRegistered(email)){
                    d.registerUser(email, "*", name,1,0);
                    exist=false;
                }
                respuesta.setAttribute("user",email);
                respuesta.setAttribute("name",name);
                
            if(!exist&&(d.getPefil(email)!=1)){
                response.sendRedirect("cuestionario.jsp");
                d.desconectar();
            }
            else{
                response.sendRedirect("index.jsp");
                d.desconectar();
            }

        } catch (Exception e) { System.out.println("Ocurrio la sig exception: " +e); }
            
            

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
