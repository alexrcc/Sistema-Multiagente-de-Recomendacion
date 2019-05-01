package sma;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.gateway.GatewayAgent;
import sma.*;


public class AgenteInterfaz1 extends GatewayAgent{
    
    @Override
    protected void setup(){
        System.out.println("Soy Interfaz");
        addBehaviour(new ProcessExternalRequests());
        
//        String operacion;
//        System.out.println("Hola "+getAID().getName()+" estoy listo");
//        Object [] args = getArguments();
//        if(args!=null){
//            operacion = (String)args[0];
//            System.out.println(operacion);
//            switch(operacion){
//                case "BS": 
//                    System.out.println(operacion);
//                    addBehaviour(new ComportamientoBusqueda(args));
//                break;
//                case "PE":
//                   addBehaviour(new ComportamientoPerfil(args));
//                break;
//                default:System.out.println("Operación en interfaz incorrecta");
//            
//            }
//            
//            
//            
//        }
        
    }
        
    private class ProcessExternalRequests extends CyclicBehaviour {
        private MessageTemplate mt;

        public void action() {
        System.out.println("Processing ProcessExternalRequests:action");
        ACLMessage msg = receive();
        if(msg != null)
        {
            String content = msg.getContent();
            System.out.println("Agent " +
            myAgent.getAID().getLocalName() +
            " Content = " + content
            );
        }
        else
        {
        block();
        }
        
        //doDelete();
        }
        
}
        private class ComportamientoBusqueda extends Behaviour{
        private final Object [] args;
        public ComportamientoBusqueda(Object argumentos[]){
            args = argumentos; 
        }
        @Override
        public void action() {
            System.out.println("Aqui voy a crear el agente De Busqueda");
        }

        @Override
        public boolean done() {

            System.out.println("Finaliza el Comportamiento " + this.getBehaviourName());
            System.out.println("--------------------------------------------------");
            doDelete();

            return true;
        }

    }
        
    private class ComportamientoPerfil extends Behaviour{
        private final Object [] args;
        public ComportamientoPerfil(Object argumentos[]){
            args = argumentos; 
        }
        @Override
        public void action() {
            System.out.println("Aqui voy a crear agente Perfil");
        }

        @Override
        public boolean done() {

            System.out.println("Finaliza el Comportamiento " + this.getBehaviourName());
            System.out.println("--------------------------------------------------");
            doDelete();

            return true;
        }

    }
    
    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("---------");
       // this.doDelete();
    }
}

