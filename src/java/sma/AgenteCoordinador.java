package sma;




import jade.core.Agent;

import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;


public class AgenteCoordinador extends Agent {
    @Override
    protected void setup(){
         System.out.println("Hola"+getAID().getName()+"soy coordinador");
          Object [] args = getArguments();

        if(args != null && args.length==1){
            addBehaviour( new OneShotBehaviour() {
                @Override
                public void action() {
                    if(args[0].equals("IA")){
                        try{
                             AgentContainer c = getContainerController();
                                AgentController controlador;
                                controlador = c.createNewAgent("AgenteEstudiante",AgentePerfilEstudiante.class.getName(),null);
                                controlador.start();
                                controlador=c.createNewAgent("AgenteRecomendador",AgenteRecomendador.class.getName(),null);
                                controlador.start();

                                controlador=c.createNewAgent("AGR",AgenteGestorRepositorioVirtuoso.class.getName(),null);
                                controlador.start();
                                
                        }catch(Exception e){
                            System.out.println("No se pudo");
                        }
                    }
                }
            });
        
        
        }  
    }
    
    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("---------");
        this.doDelete();
    }
}
