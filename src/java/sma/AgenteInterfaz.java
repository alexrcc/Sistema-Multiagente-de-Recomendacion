package sma;

import controller.Mensaje;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.gateway.GatewayAgent;
import java.util.logging.Level;
import java.util.logging.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.io.IOException;

public class AgenteInterfaz extends GatewayAgent{
    Mensaje mensaje=null;
    Mensaje respuesta=null;
    
    @Override
    public void setup(){
        super.setup();
    }
    
    @Override
    protected void processCommand(java.lang.Object obj){
       if(obj instanceof Mensaje){
          
            mensaje = (Mensaje) obj;
            System.out.println("Hola soy "+getAID().getName()+" estoy listo");
            String operacion = mensaje.getMensaje();
            Object args = mensaje.getArgumentos();
            if(args!=null){
                switch(operacion){
                case "BS": 
                    System.out.println("--Entró a BS");
                    addBehaviour(new ComportamientoBusqueda(mensaje));
                    addBehaviour(new RecibirMensaje());
                break;
                case "EA":
                    System.out.println("--Entró a EA");
                   addBehaviour(new ComportamientoPerfil(mensaje));
                   addBehaviour(new RecibirMensaje());
                break;
                case "IM":
                   System.out.println("--Entró a IM");
                   addBehaviour(new ComportamientoPerfil(mensaje));
                   addBehaviour(new RecibirMensaje());
                break;
                case "BA":
                   System.out.println("--Entró a BA");
                   addBehaviour(new ComportamientoBusqueda(mensaje));
                   addBehaviour(new RecibirMensaje());
                break;
                default:System.out.println("Operación en interfaz incorrecta");         
            } 
        }
           System.out.println("Si llega");        
   }
       
      
   }
    private class RecibirMensaje extends CyclicBehaviour{
        @Override
        public void action() {
        ACLMessage msg = receive();
            if(msg != null){
                try {
                    respuesta = (Mensaje)msg.getContentObject();
                    mensaje.setRespuesta(respuesta.getRespuesta());
                    System.out.println("Agent " +
                            getAID().getLocalName() +
                            " Argumentos = " + respuesta.getRespuesta()+ "---"+mensaje.getRespuesta().toString()
                    );


                } catch (UnreadableException ex) {
                    Logger.getLogger(AgenteCoordinador.class.getName()).log(Level.SEVERE, null, ex);
                }
                    releaseCommand(mensaje); 
            }else{
                block();
            }
        }
       
    
    }
    private class ComportamientoBusqueda extends Behaviour{
        private final Object args;
        public ComportamientoBusqueda(Object mensaje){
            args = mensaje; 
        }
        @Override
        public void action() {
            try {
                System.out.println("Comportamiento de Busqueda Simple");
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("AgenteRecomendador", AID.ISLOCALNAME));
                Mensaje mensaje = (Mensaje)args ;
                mensaje.setRemitente(myAgent.getAID());
                msg.setContentObject(mensaje);
                send(msg);
            } catch ( Exception ex) {
                System.out.println("Error al enviar mensaje al AgenteRecomendador: "+ex);
                Logger.getLogger(AgenteInterfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public boolean done() {
            System.out.println("Finaliza el Comportamiento " + this.getBehaviourName());
            System.out.println("--------------------------------------------------");
            return true;
        }

    }
        
    private class ComportamientoPerfil extends Behaviour{
        private final Object args;
        public ComportamientoPerfil(Object mensaje){
            args = mensaje; 
        }
        @Override
        public void action() {
            AgentContainer c = getContainerController();
            try {
//                AgentController a = c.createNewAgent("AgenteEstudiante",AgentePerfilEstudiante.class.getName(),null);
//                a.start();
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("AgenteEstudiante", AID.ISLOCALNAME));
                Mensaje mensaje = (Mensaje)args ;
                msg.setContent("saveProfile");
                msg.setContentObject(mensaje);
                send(msg);
            } catch (Exception ex) {
                System.out.println("Error al crear agente Estudiante: "+ ex);
                Logger.getLogger(AgenteInterfaz.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }

        @Override
        public boolean done() {

            System.out.println("Finaliza el Comportamiento PE " + this.getBehaviourName());
            System.out.println("--------------------------------------------------");
            return true;
        }

    }
 

   
}
