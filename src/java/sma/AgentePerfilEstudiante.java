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
   // private static Mensaje mensaje;
    @Override
    protected void setup(){
        System.out.println("Hola"+getAID().getName()+" soy Agente Estudiante");
                
         addBehaviour( new CyclicBehaviour() {
              @Override
            public void action() {
                ACLMessage msg = receive();
                if(msg != null){
                    try {
                        Mensaje mensaje = (Mensaje)msg.getContentObject();
                        System.out.println("Agent " +
                                myAgent.getAID().getLocalName() +
                                " Argumentos = " + mensaje.getMensaje()
                        );
                        addBehaviour(new EstiloAprendizaje(mensaje));
                        mensaje.setRespuesta("Perfil Guardado Correctamente");
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
    
    public class EstiloAprendizaje extends Behaviour{
         private final Object args;
         private static final String smas = "http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#";
         private int estilos [] = {0,0,0,0}; //estilos de aprendizaje[visual,auditivo,lectura/escritura,Quinestésico]
         
        public EstiloAprendizaje(Object mensaje){
            args = mensaje; 
        }
        @Override
        public void action() {
            System.out.println("Aqui voy a calcular el EA");
            Mensaje msg = (Mensaje)args;
            System.out.println(msg.getMensaje());
            String a [][] = (String [][])msg.getArgumentos();
            
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
            
           
            System.out.println("Visual "+estilos[0]);
            System.out.println("Auditivo "+estilos[1]);
            System.out.println("L/E "+estilos[2]);
            System.out.println("Quinestésico "+estilos[3]);
            GuardarPerfil(estilos,msg.getUsuario());
            
             
        }
        @Override
        public boolean done() {

            System.out.println("Finaliza el Comportamiento PE " + this.getBehaviourName());
            System.out.println("--------------------------------------------------");
            return true;
        }
        private void GuardarPerfil(int array [], String user){
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            
            model.read("C:\\Users\\alexr\\Documents\\NetBeansProjects\\sma_web//OntologiaBase.owl","RDF/XML");
            
//            
//            System.out.println("IND: "+ model.getIndividual(smas+"arcondoyc@unl.edu.ec"));
//            
//
//            OntClass cls1 = model.getOntClass(smas+"studentProfile");
//            Individual person = model.createIndividual(smas+user,cls1);
//            //Individual person1 = model.createIndividual(smas+"--..asa",cls1);
//            
//            
//            ObjectProperty co = model.getObjectProperty(smas+"consistOf");
//            
//            OntClass le_sty = model.getOntClass(smas+"LearningStyle");
//            Individual styles = model.createIndividual(smas+"LE-"+user,le_sty); 
//            
//            styles.setPropertyValue(model.getDatatypeProperty(smas+"visual"),model.createTypedLiteral(array[0]));
//            styles.setPropertyValue(model.getDatatypeProperty(smas+"aural"),model.createTypedLiteral(array[1]));
//            styles.setPropertyValue(model.getDatatypeProperty(smas+"read-write"),model.createTypedLiteral(array[2]));
//            styles.setPropertyValue(model.getDatatypeProperty(smas+"kinesthetic"),model.createTypedLiteral(array[3]));
//            person.addProperty(co,styles);
//            

             try{
            File file = new File("Perfiles.owl");
           
                if (!file.exists()){
                    System.out.println("Entró");
                     file.createNewFile();
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
                            "        smas:aural        \""+array[0]+"\"^^xsd:int ;\n" +
                            "        smas:kinesthetic  \""+array[1]+"\"^^xsd:int ;\n" +
                            "        smas:read-write   \""+array[2]+"\"^^xsd:int ;\n" +
                            "        smas:visual       \""+array[3]+"\"^^xsd:int .");
               fichero.close();
               pw.close();
               Dao d = new Dao();
               d.conectar();
               if(!d.setPerfilInteligente(user))
                     System.out.println("Ocurrió un error al ingresar la bandera en la BD");
            }catch(Exception e){
                System.out.println("Error al guardar el modelo: "+e);
            }
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
