package sma;
import controller.Mensaje;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import model.Dao;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AgentePerfilEstudiante extends Agent{
    public static Mensaje mensaje;
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
                        System.out.println("Agent " +
                                myAgent.getAID().getLocalName() +
                                " Argumentos = " + mensaje.getMensaje()
                        );
                         EstiloAprendizaje();
                        System.out.println("la respuesta que voy a devolver: "+mensaje.getRespuesta());
                        //mensaje.setRespuesta("Perfil Guardado Correctamente");
                        //ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContentObject(mensaje);
                         send(reply);
                        
                    } catch (UnreadableException ex) {
                        System.out.println("Error agente Estudiante: "+ ex);
                        Logger.getLogger(AgenteCoordinador.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
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

            System.out.println(mensaje.getMensaje());
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
             try {
                 if(GuardarPerfil(estilos,mensaje.getUsuario()))
                    mensaje.setRespuesta("Perfil Guardado Correctamente");
             } catch (SQLException | ClassNotFoundException ex) {
                 Logger.getLogger(AgentePerfilEstudiante.class.getName()).log(Level.SEVERE, null, ex);
             }
            
             
        }
        
        private boolean GuardarPerfil(int array [], String user) throws SQLException, ClassNotFoundException{
            String smas = "http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#";
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);           
            model.read("C:\\Users\\alexr\\Documents\\NetBeansProjects\\sma_web//OntologiaBase.owl","RDF/XML");
            Dao d = new Dao();
            d.conectar();
            //Guardar bandera en la base de Datos
            try{
                if(d.getPefil(user)==1){
                    System.out.println("Entra");
                    mensaje.setRespuesta("Sus datos ya estan registrados");
                    return false;
                }else if(!d.setPerfilInteligente(user,1)){
                    mensaje.setRespuesta("Ocurrió un error, porfavor intente nuevamente.");
                    return false;
                }
            }catch(SQLException ex){
                System.out.println("Exception: "+ex);
                mensaje.setRespuesta("Ocurrió un error, porfavor intente nuevamente.");
                return false;
            }
            
            //Guardar resultados del test de VARK en la Ontología
            try{
                File file = new File("Perfiles.owl");
                if (!file.exists()){
                    file.createNewFile();
                    System.out.println("Se creo el archivo");
                    model.write(new PrintWriter(file),"Turtle");
               }
                
                FileWriter fichero = null;
                PrintWriter pw = null;       
                fichero = new FileWriter("Perfiles.owl",true);
                pw = new PrintWriter(fichero);
                pw.println("<"+smas+user+">\n" +
                            "        a               smas:studentProfile ;\n" +
                            "        smas:consistOf  <"+smas+"LE-"+user+"> .");
                
                pw.println("<"+smas+"LE-"+user+">\n" +
                            "        a                 smas:LearningStyle ;\n" +
                            "        smas:visual        \""+array[0]+"\" ;\n" +
                            "        smas:aural  \""+array[1]+"\" ;\n" +
                            "        smas:readwrite   \""+array[2]+"\" ;\n" +
                            "        smas:kinesthetic  \""+array[3]+"\" .");
               fichero.close();
               pw.close();
               
            }catch(Exception e){
                System.out.println("Error al guardar el modelo en la Ontología: "+e);
                mensaje.setRespuesta("Ocurrió un error, porfavor intente nuevamente.");
                d.setPerfilInteligente(user,0);
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
    
    
