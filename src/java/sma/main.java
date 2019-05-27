package sma;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;


public class main extends Agent{
    //Agente que gestiona el SMA
    public static AgentController agenteController;
    //Entorno de ejecución, crea una instancia de ejecución
    public static jade.core.Runtime runtime = jade.core.Runtime.instance();
    //CreateMainContainer necesita un Profile que guarda las configuraciones necesarias para iniciar JADE
    public static Profile profile = new ProfileImpl("localhost", 1099, null);
    public static AgentContainer mainContainer = runtime.createMainContainer(profile);

    public static void main(String args []) throws StaleProxyException, Throwable{
        System.out.println("***Bienvenido al SMA para recomendación de OAS***");
        Object argumentos[] = {"https://roa.cedia.edu.ec", "http://vishub.org"};
        agenteController = mainContainer.createNewAgent("AgenteEstudiante",AgentePerfilEstudiante.class.getName(),null);
        agenteController.start();
        agenteController = mainContainer.createNewAgent("AgenteRecomendador",AgenteRecomendador.class.getName(),null);
        agenteController.start();
//        agenteController = mainContainer.createNewAgent("AGR",AgenteGestorRepositorioVirtuoso.class.getName(),argumentos);
//        agenteController.start();
    }
    
}
