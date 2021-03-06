/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import controller.Mensaje;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.gateway.JadeGateway;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "IniciarAgentes", urlPatterns = {"/IniciarAgentes"})
public class IniciarAgentes extends HttpServlet {
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
        //Agente que gestiona el SMA
        AgentController agenteController;
        //Entorno de ejecución, crea una instancia de ejecución
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        //CreateMainContainer necesita un Profile que guarda las configuraciones necesarias para iniciar JADE
         Profile profile = new ProfileImpl("localhost", 1099, null);
         AgentContainer mainContainer = runtime.createMainContainer(profile);
         
        PrintWriter out = response.getWriter();
        Mensaje mensaje = new Mensaje();
        mensaje.setMensaje("IA");
        try{
            JadeGateway.execute(mensaje); 
        }catch(Exception e){
            e.printStackTrace();
        }
        out.print(mensaje.getRespuesta());
    }

    @Override
     public void init()throws ServletException{

        pp.setProperty(Profile.MAIN_HOST, "localhost");
        pp.setProperty(Profile.MAIN_PORT, "1099");
        pp.setProperty(Profile.CONTAINER_NAME,"main");
        JadeGateway.init("sma.AgenteInterfaz",pp);
    }

}
