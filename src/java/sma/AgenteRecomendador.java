package sma;

import controller.Mensaje;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import virtuoso.jena.driver.VirtModel;



public class AgenteRecomendador extends Agent{
    Mensaje mensaje;
    static final String URL = "jdbc:virtuoso://localhost:1111";
    static final String smas = "http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#";
    static final String rdf = "http://www.w3.org/2000/01/rdf-schema#";
    static final String ns = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    static final String uid = "dba";
    static final String pwd = "dba";
    
    @Override
    protected void setup(){
         System.out.println("Agente: "+getAID().getName()+"inicializado");
         addBehaviour( new CyclicBehaviour() {
              @Override
            public void action() {
                ACLMessage msg = receive();
                if(msg != null){
                    try {
                        mensaje = (Mensaje)msg.getContentObject();
                        System.out.println("Mensaje de:  " +
                                myAgent.getAID().getLocalName() +
                                " Argumentos = " + mensaje.getMensaje()
                        );
                        String opc = mensaje.getMensaje();
                        switch (opc){
                            case "BS":
                                BusquedaSimple();
                            break;
                            case "BA":
                            break;
                            default:
                        }
                       
                        
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContentObject(mensaje);
                        send(reply);
                        
                    } catch (UnreadableException ex) {
                        System.out.println("Error agente Recomendador: "+ ex);
                        Logger.getLogger(AgenteCoordinador.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(AgenteRecomendador.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }else{
                block();
                }
        } 
            
       });
    }
    
    private void BusquedaSimple(){
        String keyaux = (String)mensaje.getArgumentos();
        String [] keywords = keyaux.split(" ");
        for(String a:keywords)
            System.out.println(a);
        VirtModel model = VirtModel.openDatabaseModel("Perfiles", URL, uid, pwd);
//        String stringQuery = 
//        "PREFIX smas: <"+smas+">"
//              + "SELECT * WHERE {"
//              + "<"+smas+"LE-"+user+"> smas:visual ?visual."
//              + "<"+smas+"LE-"+user+"> smas:aural ?aural."
//              + "<"+smas+"LE-"+user+"> smas:kinesthetic ?kinesthetic."
//              + "<"+smas+"LE-"+user+"> smas:readwrite ?readwrite}";     
//        Query query = QueryFactory.create(stringQuery);
//        // Ejecutar la consulta y obtener los resultados
//        QueryExecution qe = QueryExecutionFactory.create(query, model);
//        
//        ResultSet results = qe.execSelect();
//       while(results.hasNext()){
//           QuerySolution qs = results.next();
//           estilos[0]=qs.getLiteral("visual").getInt();
//           estilos[1]=qs.getLiteral("aural").getInt();
//           estilos[3]=qs.getLiteral("kinesthetic").getInt();
//           estilos[2]=qs.getLiteral("readwrite").getInt();
//       }
     
    }
    
    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("--------------------------------------------------");
    }
}
