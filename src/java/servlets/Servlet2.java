/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.JadeGateway;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import sma.AgenteGestorRepositorio;
import sma.*;

/**
 *
 * @author alexr
 */
@WebServlet(name = "Servlet2", urlPatterns = {"/Servlet2"})
public class Servlet2 extends HttpServlet{  
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
        String user = (String)session.getAttribute("user");
         try{
            String keyword = new String(request.getParameter("keyword").getBytes("ISO-8859-1"),"UTF-8"); 
            String checkboxValues  []= request.getParameterValues("disciplina");
            String idioma = request.getParameter("idioma");
            String check = request.getParameter("checkbox");
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
            
            ArrayList<String[]> al =(ArrayList<String[]>) mensaje.getRespuesta();
            request.setAttribute("listado", al);
            request.setAttribute("ky", keyword);
            getServletContext().getRequestDispatcher("/resultados.jsp").forward(request, response);
        }catch(Exception e){
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
