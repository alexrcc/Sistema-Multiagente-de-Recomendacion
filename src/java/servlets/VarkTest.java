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
import sma.AgenteInterfaz1;

/**
 *
 * @author alexr
 */
@WebServlet(name = "VarkTest", urlPatterns = {"/VarkTest"})
public class VarkTest extends HttpServlet {
    private String resp_vark [][] = new String[16][4];
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            for (int i = 1; i <=16; i++) {
                System.out.println("p"+i);
                String checkboxValues  []= request.getParameterValues("p"+i);
                // System.out.println("dddddddddddd"+checkboxValues[0]);
                if(checkboxValues != null){
                    for (int j = 0;  j<checkboxValues.length; j++) {
                        resp_vark[i-1][j]=checkboxValues[j];
                        // System.out.println(checkboxValues[j]);
                    }
                }
            }
            Object args [] = {resp_vark};
            AgentController agenteController;
            //Entorno de ejecución, crea una instancia de ejecución
            jade.core.Runtime runtime = jade.core.Runtime.instance();
            //CreateMainContainer necesita un Profile que guarda las configuraciones necesarias para iniciar JADE
            Profile profile = new ProfileImpl(null, 1099, null);
            AgentContainer mainContainer = runtime.createMainContainer(profile);
            agenteController = mainContainer.createNewAgent("AgenteInterfaz",AgenteInterfaz1.class.getName(),args);
            agenteController.start();
//            for (int i = 0; i <16; i++) {
//                for (int j = 0; j < 4; j++) {
//                    System.out.println(resp_vark[i][j]+"-");
//                }
//                System.out.println("\n");
//            }
        } catch (StaleProxyException ex) {
            Logger.getLogger(VarkTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
