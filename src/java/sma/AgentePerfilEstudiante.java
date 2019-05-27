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
                        if(mensaje.getMensaje().equals("EA"))
                            EstiloAprendizaje();
                        else if(mensaje.getMensaje().equals("IM"))
                            InteligenciasMultiples();
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
    private void InteligenciasMultiples(){
        //inteligencias mutiples[verbal,lógico/matemática,visual/espacial,kinestesica/corporal,musical/rítmica,intrapersonal,interpersonal]
         int inteligencias [] = {0,0,0,0,0,0,0}; 
        String a [] = (String[])mensaje.getArgumentos();
        for (int i = 0; i <a.length; i++) {
                if((i==8||i==9||i==16||i==21||i==29)&&a[i].equalsIgnoreCase("V"))
                    inteligencias[0]++;
                else if((i==4||i==6||i==14||i==19||i==24)&&a[i].equalsIgnoreCase("V"))
                    inteligencias[1]++;
                else if((i==0||i==10||i==13||i==22||i==26)&&a[i].equalsIgnoreCase("V"))
                    inteligencias[2]++;
                else if((i==7||i==15||i==18||i==20||i==28)&&a[i].equalsIgnoreCase("V"))
                    inteligencias[3]++;
                else if((i==2||i==3||i==12||i==23||i==27)&&a[i].equalsIgnoreCase("V"))
                    inteligencias[4]++;
                else if((i==1||i==5||i==25||i==30||i==32)&&a[i].equalsIgnoreCase("V"))
                    inteligencias[5]++;
                else if((i==11||i==17||i==31||i==33||i==34)&&a[i].equalsIgnoreCase("V"))
                    inteligencias[6]++;
        }
        if(GuardarInteligencias(inteligencias,mensaje.getUsuario()))
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
    private boolean GuardarInteligencias(int array [], String user){
        try{
            VirtModel model = VirtModel.openDatabaseModel("Perfiles", URL, uid, pwd);
            VirtGraph set = new VirtGraph (URL, uid, pwd);
            String stringQuery = "PREFIX smas: <"+smas+">"+ "SELECT * WHERE {"
                                + "<"+smas+user+"> smas:consistOf ?multipleIntelligences.}";
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
                "    ?p rdf:domain smas:MultipleIntelligences." +
                "   FILTER regex( ?individual,'MI-"+user+"')" +
                "   ?individual ?p ?v" +
                " }";
                VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(stringQuery, set);
                vur.exec();
            }
            //Se ingresa las nueva información de Estilos de Aprendizaje
            //verbal,lógico/matemática,visual/espacial,kinestesica/corporal,musical/rítmica,intrapersonal,interpersonal
             String str = "INSERT INTO GRAPH <Perfiles> { <"
                        +smas+user+"> <"+ns+"type> <"+smas+"studentProfile>."
                        +"<"+smas+"MI-"+user+"> <"+ns+"type> <"+smas+"multipleIntelligences>."
                        + "<"+smas+user+"> <"+smas+"consistOf> <"+smas+"MI-"+user+">."
                        + "<"+smas+"MI-"+user+"> <"+smas+"verbal> '"+array[0]+"'."
                        + "<"+smas+"MI-"+user+"> <"+smas+"logicalmath> '"+array[1]+"'."
                        + "<"+smas+"MI-"+user+"> <"+smas+"visual> '"+array[2]+"'."
                        + "<"+smas+"MI-"+user+"> <"+smas+"kinesthetic> '"+array[3]+"'."
                        + "<"+smas+"MI-"+user+"> <"+smas+"musical> '"+array[4]+"'."
                        + "<"+smas+"MI-"+user+"> <"+smas+"intrapersonal> '"+array[5]+"'."
                        + "<"+smas+"MI-"+user+"> <"+smas+"interpersonal> '"+array[6]+"'.}";
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
    
    
