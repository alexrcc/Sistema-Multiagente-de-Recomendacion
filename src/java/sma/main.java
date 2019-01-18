package sma;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;


public class main {
    //Agente que gestiona el SMA
    static AgentController agenteController;
    //Entorno de ejecución, crea una instancia de ejecución
    static jade.core.Runtime runtime = jade.core.Runtime.instance();
    //CreateMainContainer necesita un Profile que guarda las configuraciones necesarias para iniciar JADE
    static Profile profile = new ProfileImpl(null, 1099, null);
    static AgentContainer mainContainer = runtime.createMainContainer(profile);
    
    
    public static void main(String[] args) throws StaleProxyException, Throwable{
        System.out.println("Bienvenido al SMA para recomendación de OAS");
        Object argumentos[] = {"Hola", "q hace"};
        agenteController = mainContainer.createNewAgent("AGR",AgenteGestorRepositorio.class.getName(),argumentos);
        agenteController.start();
    }
}
