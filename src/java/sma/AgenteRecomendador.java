package sma;

import controller.Mensaje;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
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
    static final String rn = "http://www.w3.org/ns/radion#";
    static final String adms = "http://www.w3.org/ns/adms#";
    static final String vcard = "http://www.w3.org/2006/vcard/ns#";
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
                                System.out.println("Entro a BS");
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
    private boolean esConector(String Cadena){
        String conectores [] ={"ante","bajo","cabe","con","de","desde","en",
            "entre","hacia","hasta","para","por","segun","según","sin","so","sobre",
            "tras","el","la","los","las","lo","al","del","un","uno","una","unos"};
        for(String c:conectores){
            if(c.equalsIgnoreCase(Cadena))
                return true;
        }
        return false;
    }
    private void BusquedaSimple(){
        String keyaux = (String)mensaje.getArgumentos();
        String [] keywords = keyaux.split(" ");
        VirtModel model = VirtModel.openDatabaseModel("http://LearningObjects", URL, uid, pwd);
        String stringQuery = "PREFIX smas: <"+smas+">"+
                "PREFIX rn: <"+rn+">"+
                "PREFIX adms: <"+adms+">"+
                "PREFIX vcard: <"+vcard+">"+
                
                "SELECT DISTINCT ?Identifier ?title ?entry ?avatar WHERE {"+
                "?LearningObject smas:isComprisedOf ?General."+
                "?LearningObject smas:avatar ?avatar."+
                "?General smas:hasIdentifier ?Identifier."+
                "?Identifier smas:entry ?entry."+
                "?General smas:description ?desc."+
                "?General vcard:title ?title."+
                "?General rn:keyword ?ky."+
                "FILTER(regex(?title,'"+keyaux+"','i')";
        for(String a:keywords)
            if(!esConector(a)){
                stringQuery=stringQuery + "||regex(?title,'"+a+"','i')";
                stringQuery=stringQuery + "||regex(?ky,'"+a+"','i')";
                stringQuery=stringQuery + "||regex(?desc,'"+a+"','i')";
            }
        stringQuery = stringQuery +").}";
        System.out.println(stringQuery);
        Query query = QueryFactory.create(stringQuery);
        // Ejecutar la consulta y obtener los resultados
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        
        ResultSet results = qe.execSelect();
        ArrayList<String[]> al = new ArrayList<String[]>();
       while(results.hasNext()){
           String [] res = new String[4];
           QuerySolution qs = results.next();
           res[0]=qs.getResource("Identifier").toString();
           res[1]=qs.get("title").toString();
           res[2]=qs.get("entry").toString();
           res[3]=qs.get("avatar").toString();
           al.add(res);
       }
     mensaje.setRespuesta(al);
    }
    
    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("--------------------------------------------------");
    }
}
