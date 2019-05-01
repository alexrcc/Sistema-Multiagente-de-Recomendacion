/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sma.AgenteCoordinador;

/**
 *
 * @author alexr
 */
@WebServlet(name = "IniciarAgentes", urlPatterns = {"/IniciarAgentes"})
public class IniciarAgentes extends HttpServlet {
     //Agente que gestiona el SMA
    public  AgentController agenteController;
    //Entorno de ejecución, crea una instancia de ejecución
    public static jade.core.Runtime runtime = jade.core.Runtime.instance();
    //CreateMainContainer necesita un Profile que guarda las configuraciones necesarias para iniciar JADE
    public static Profile profile = new ProfileImpl("localhost", 1099, null);
    public static AgentContainer mainContainer = runtime.createMainContainer(profile);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, StaleProxyException {
        response.setContentType("text/html;charset=UTF-8");
        
   

   
        System.out.println("***Bienvenido al SMA para recomendación de OAS***");
        Object argumentos[] = {"https://roa.cedia.edu.ec", "http://vishub.org"};
        agenteController = mainContainer.createNewAgent("AgenteCoordinador",AgenteCoordinador.class.getName(),null);
        agenteController.start();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet IniciarAgentes</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IniciarAgentes at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        try {
            processRequest(request, response);
        } catch (StaleProxyException ex) {
            Logger.getLogger(IniciarAgentes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (StaleProxyException ex) {
            Logger.getLogger(IniciarAgentes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
