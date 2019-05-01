package sma;



import controller.Mensaje;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgenteCoordinador extends Agent {
    @Override
    protected void setup(){
         System.out.println("Hola"+getAID().getName()+"soy coordinador");
         
         addBehaviour( new CyclicBehaviour() {
              @Override
            public void action() {
                ACLMessage msg = receive();
                if(msg != null)
                {
                    try {
                        Mensaje mensaje= (Mensaje)msg.getContentObject();
                        System.out.println("Agent " +
                                myAgent.getAID().getLocalName() +
                                " Argumentos = " + mensaje.getArgumentos()
                        );
                        mensaje.setRespuesta("La respuesta del agente Coordinador");
                        //ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContentObject(mensaje);
                        //msg.addReceiver(new AID("AgenteInterfaz", AID.ISLOCALNAME));
                        //msg.setContentObject(mensaje);
                        //msg.addReceiver(new AID("Agenteinterfaz", AID.ISLOCALNAME));
                        //msg.setLanguage("English");
                        //msg.setOntology("busqueda");
                        //Mensaje mensaje = (Mensaje)args ;
                        //msg.setContentObject(mensaje);
                        send(reply);
                        doDelete();
                      
                    } catch (IOException ex) {
                        System.out.println("error");
                        Logger.getLogger(AgenteCoordinador.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnreadableException ex) {
                        System.out.println("error");
                        Logger.getLogger(AgenteCoordinador.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
            }   
         
         }
         
       });
       
        
        
        
    }
    
    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("---------");
       // this.doDelete();
    }
}
