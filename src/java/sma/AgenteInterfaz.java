package sma;

import controller.Mensaje;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.gateway.GatewayAgent;
import java.util.logging.Level;
import java.util.logging.Logger;


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
            System.out.println(operacion);
            Object args = mensaje.getArgumentos();

                switch(operacion){
                case "BS": 
                    addBehaviour(new ComportamientoBusqueda(mensaje));
                    addBehaviour(new RecibirMensaje());
                break;
                case "EA":
                   addBehaviour(new ComportamientoPerfil(mensaje));
                   addBehaviour(new RecibirMensaje());
                break;
                case "IM":
                   addBehaviour(new ComportamientoPerfil(mensaje));
                   addBehaviour(new RecibirMensaje());
                break;
                case "BA":
                   addBehaviour(new ComportamientoBusqueda(mensaje));
                   addBehaviour(new RecibirMensaje());
                break;
                case "BI":
                   addBehaviour(new ComportamientoBusqueda(mensaje));
                   addBehaviour(new RecibirMensaje());
                break;
                case "IA":
                    System.out.println("Entra a IA");
                   addBehaviour(new ComportamientoInicializacion(mensaje));
                break;
                case "AG":
                   addBehaviour(new ComportamientoRepositorio(mensaje));
                break;
                case "DP":
                   addBehaviour(new OffPlataform());
                break;
                default:System.out.println("Operación en interfaz incorrecta");         
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
    
    private class ComportamientoInicializacion extends Behaviour{
        private final Object args;
        public ComportamientoInicializacion(Object mensaje){
            args = mensaje; 
        }
        @Override
        public void action() {
            try {
                System.out.println("Comportamiento Inicialización");
                AgentContainer c = getContainerController();
                Object argumentos[] = {"IA"};
                AgentController a = c.createNewAgent("AgenteCoordinador",AgenteCoordinador.class.getName(),argumentos);
                a.start();  
                mensaje.setRespuesta("Agentes Incializados Correctamente");
          
            } catch ( Exception ex) {
                System.out.println("Error al enviar mensaje al AgenteRecomendador: "+ex);
                Logger.getLogger(AgenteInterfaz.class.getName()).log(Level.SEVERE, null, ex);
                mensaje.setRespuesta("No se ha podido incializar los agentes");
            }
            
            releaseCommand(mensaje); 
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
        private class ComportamientoRepositorio extends Behaviour{
        private final Object args;
        public ComportamientoRepositorio(Object mensaje){
            args = mensaje; 
        }
        @Override
        public void action() {
            try {
                System.out.println("Comportamiento de Gestor de Repositorio");
                
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("AGR", AID.ISLOCALNAME));
                Mensaje mensaje = (Mensaje)args ;
                mensaje.setRemitente(myAgent.getAID());
                msg.setContentObject(mensaje);
                send(msg);
                mensaje.setRespuesta("Se agregó el repositorio, el AGR trabajará para descargar los OAS.");
            } catch ( Exception ex) {
                System.out.println("Error al enviar mensaje al AGR: "+ex);
                Logger.getLogger(AgenteInterfaz.class.getName()).log(Level.SEVERE, null, ex);
                mensaje.setRespuesta("Ocurrió un error al agregar el repositorio");
            }
            releaseCommand(mensaje); 
        }

        @Override
        public boolean done() {
            System.out.println("Finaliza el Comportamiento " + this.getBehaviourName());
            System.out.println("--------------------------------------------------");
            return true;
        }

    }
        
    private class OffPlataform extends Behaviour{
               @Override
        public void action() {
            try {
                System.out.println("Comportamiento Apagar");
                Codec codec = new SLCodec();    
                Ontology jmo = JADEManagementOntology.getInstance();
                getContentManager().registerLanguage(codec);
                getContentManager().registerOntology(jmo);
                ACLMessage msgoff = new ACLMessage(ACLMessage.REQUEST);
                msgoff.addReceiver(getAMS());
                msgoff.setLanguage(codec.getName());
                msgoff.setOntology(jmo.getName());
                getContentManager().fillContent(msgoff, new Action(getAID(), new ShutdownPlatform()));
                send(msgoff);
                mensaje.setRespuesta("La plataforma se ha detenido correcamente.");
            }
            catch (Exception e) {
                mensaje.setRespuesta("No se pudo apagar la plataforma.");
            }
                              
            
            releaseCommand(mensaje); 
        }

        @Override
        public boolean done() {
            System.out.println("Finaliza el Comportamiento " + this.getBehaviourName());
            System.out.println("--------------------------------------------------");
            return true;
        }

    }
 

   
}
