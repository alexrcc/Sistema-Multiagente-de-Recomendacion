package sma;
import controller.Mensaje;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
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
import org.apache.jena.query.ResultSet;
import virtuoso.jena.driver.*;

public class AgentePerfilEstudiante extends Agent{
    public static Mensaje mensaje;
    static final String URL = "jdbc:virtuoso://localhost:1111";
    static final String smas = "http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#";
    static final String rdf = "http://www.w3.org/2000/01/rdf-schema#";
    static final String ns = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    static final String uid = "dba";
    static final String pwd = "dba";
    @Override
    protected void setup(){
        System.out.println("Hola"+getAID().getName()+" soy Agente Estudiante");
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
                         EstiloAprendizaje();
                        System.out.println("la respuesta que voy a devolver: "+mensaje.getRespuesta());
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContentObject(mensaje);
                         send(reply);
                        
                    } catch (UnreadableException ex) {
                        System.out.println("Error agente Estudiante: "+ ex);
                        Logger.getLogger(AgenteCoordinador.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        System.out.println("Error agente Estudiante: "+ ex);
                        Logger.getLogger(AgentePerfilEstudiante.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }else{
                block();
                }
        } 
            
       });   
    }
    
    private void EstiloAprendizaje(){
        int estilos [] = {0,0,0,0}; //estilos de aprendizaje[visual,auditivo,lectura/escritura,Quinestésico]
        String a [][] = (String [][])mensaje.getArgumentos();
        for (int i = 0; i <16; i++) {
            for (int j = 0; j < 4; j++) {
                if(a[i][j]!=null&&a[i][j].compareTo("V")==0)
                    estilos[0]++;
                else if(a[i][j]!=null&&a[i][j].compareTo("A")==0)
                    estilos[1]++;
                else if(a[i][j]!=null&&a[i][j].compareTo("R")==0)
                    estilos[2]++;
                else if(a[i][j]!=null&&a[i][j].compareTo("K")==0)
                    estilos[3]++;
            }  
        }
        if(GuardarPerfil(estilos,mensaje.getUsuario()))
            mensaje.setRespuesta("Perfil Guardado Correctamente");
        else
            mensaje.setRespuesta("Ha ocurrido un error!");
    }
        
    private boolean GuardarPerfil(int array [], String user){
        try{
            VirtModel model = VirtModel.openDatabaseModel("Perfiles", URL, uid, pwd);
            VirtGraph set = new VirtGraph (URL, uid, pwd);
            String stringQuery = "PREFIX smas: <"+smas+">"+ "SELECT * WHERE {"
                                + "<"+smas+user+"> smas:consistOf ?LearningStyle.}";
            Query query = QueryFactory.create(stringQuery);

            // Ejecutar la consulta y obtener los resultados
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();
            //Si esque ya hay datos, se  borran para reemplazarlos
            if(results.hasNext()){ 
                stringQuery ="PREFIX smas: <"+smas+">" +
                "PREFIX rdf: <"+rdf+">" +
                "DELETE FROM GRAPH <Perfiles> " +
                " { ?individual ?p ?v }" +
                "WHERE" +
                " { ?individual ?p ?v ." +
                "    ?p rdf:domain smas:LearningStyle." +
                "   FILTER regex( ?individual,'LE-"+user+"')" +
                "   ?individual ?p ?v" +
                " }";
                VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(stringQuery, set);
                vur.exec();
            }
            //Se ingresa las nueva información de Estilos de Aprendizaje
             String str = "INSERT INTO GRAPH <Perfiles> { <"
                        +smas+user+"> <"+ns+"type> <"+smas+"studentProfile>."
                        +"<"+smas+"LE-"+user+"> <"+ns+"type> <"+smas+"LearningStyle>."
                        + "<"+smas+user+"> <"+smas+"consistOf> <"+smas+"LE-"+user+">."
                        + "<"+smas+"LE-"+user+"> <"+smas+"visual> '"+array[0]+"'."
                        + "<"+smas+"LE-"+user+"> <"+smas+"aural> '"+array[1]+"'."
                        + "<"+smas+"LE-"+user+"> <"+smas+"readwrite> '"+array[2]+"'."
                        + "<"+smas+"LE-"+user+"> <"+smas+"kinesthetic> '"+array[3]+"'.}";
            VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
            vur.exec();
        }catch(Exception e){
            System.out.println("Error:"+e);
            return false;
        } 
        return true;
    }
    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("---------");
       // this.doDelete();
    }
}
    
    
