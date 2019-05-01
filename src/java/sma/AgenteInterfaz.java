/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sma;

import controller.Mensaje;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.GatewayAgent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexr
 */
public class AgenteInterfaz extends GatewayAgent{
    Mensaje mensaje=null;
    Mensaje mensaje1=null;

   protected void processCommand(java.lang.Object obj){
       AgentContainer c = getContainerController();
       if(obj instanceof Mensaje){
            mensaje = (Mensaje) obj;
            System.out.println("Hola soy "+getAID().getName()+" estoy listo");
            
            String operacion = mensaje.getMensaje();
            Object args = mensaje.getArgumentos();
            if(args!=null){
                System.out.println(operacion);
                switch(operacion){
                case "BS": 
                    System.out.println("--Entro a BS");
                    addBehaviour(new ComportamientoBusqueda(mensaje));
                    addBehaviour(new CyclicBehaviour() {

           @Override
           public void action() {
               System.out.println("Hola soy setup");
       ACLMessage msg = receive();
                if(msg != null)
                {
                    try {
                        mensaje1= (Mensaje)msg.getContentObject();
                        mensaje.setRespuesta(mensaje1.getRespuesta());
                        System.out.println("Agent " +
                                getAID().getLocalName() +
                                " Argumentos = " + mensaje1.getRespuesta()+ "---"+mensaje.getRespuesta().toString()
                        );
                        
                      
                    } catch (UnreadableException ex) {
                        Logger.getLogger(AgenteCoordinador.class.getName()).log(Level.SEVERE, null, ex);
                    }
          
           
                    releaseCommand(mensaje); 
                }else{
                block();
                }
           }
       });
                break;
                case "PE":
                   addBehaviour(new ComportamientoPerfil(mensaje));
                break;
                default:System.out.println("Operación en interfaz incorrecta");
            
            }
            
            
            
        }
           System.out.println("Si llega"); 
            
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
                msg.addReceiver(new AID("AgenteCoordinador", AID.ISLOCALNAME));
                msg.setLanguage("English");
                msg.setOntology("busqueda");
                Mensaje mensaje = (Mensaje)args ;
                msg.setContentObject(mensaje);
                send(msg);
            } catch ( Exception ex) {
                System.out.println(ex);
                Logger.getLogger(AgenteInterfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public boolean done() {

            System.out.println("Finaliza el Comportamiento " + this.getBehaviourName());
            System.out.println("--------------------------------------------------");
            //doDelete();

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
 

   public void setup(){
    super.setup();
       
}
}
