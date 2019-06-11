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
import model.Virtuoso;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;


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
                        else if(mensaje.getMensaje().equals("BA"))
                            GetEstilos();
                        else if(mensaje.getMensaje().equals("BI"))
                            GetInteligencias();
                        System.out.println("la respuesta que voy a devolver a recomen: "+mensaje.getRespuesta());
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
    private void GetInteligencias(){
        int [] inteligencias = new int[8];
        System.out.println("RecuperarInteligencias");
        String user=mensaje.getUsuario();
        
        Virtuoso bdv = new Virtuoso();
        bdv.conectar("Perfiles");
        ResultSet results=bdv.GetInteligencias(user);
        while(results.hasNext()){
           QuerySolution qs = results.next();
           inteligencias[0]=qs.getLiteral("verbal").getInt();
           inteligencias[1]=qs.getLiteral("logical").getInt();
           inteligencias[2]=qs.getLiteral("visual").getInt();
           inteligencias[3]=qs.getLiteral("kinesthetic").getInt();
           inteligencias[4]=qs.getLiteral("musical").getInt();
           inteligencias[5]=qs.getLiteral("intrapersonal").getInt();
           inteligencias[6]=qs.getLiteral("interpersonal").getInt();
        }
        ArrayList<int[]> perfil = new ArrayList<>();
        perfil.add(inteligencias);
        mensaje.setRespuesta(perfil);
        mensaje.setMensaje("BI-R");
        bdv.desconectar();
    }
    private void GetEstilos(){
        int [] estilos = new int[4];
        System.out.println("RecuperarPerfil");
        String user=mensaje.getUsuario();
        
        Virtuoso bdv = new Virtuoso();
        bdv.conectar("Perfiles");
        ResultSet results=bdv.GetEstilos(user);
       while(results.hasNext()){
           QuerySolution qs = results.next();
           estilos[0]=qs.getLiteral("visual").getInt();
           estilos[1]=qs.getLiteral("aural").getInt();
           estilos[3]=qs.getLiteral("kinesthetic").getInt();
           estilos[2]=qs.getLiteral("readwrite").getInt();
       }
       
        ArrayList<int[]> perfil = new ArrayList<>();
        perfil.add(estilos);
        mensaje.setRespuesta(perfil);
        mensaje.setMensaje("BA-R");
        bdv.desconectar();
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
        Virtuoso bdv = new Virtuoso();
        bdv.conectar("Perfiles");
        if(bdv.SetEstilos(estilos,mensaje.getUsuario()))
            mensaje.setRespuesta("Perfil Guardado Correctamente");
        else
            mensaje.setRespuesta("Ha ocurrido un error!");
        bdv.desconectar();
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
        Virtuoso bdv = new Virtuoso();
        bdv.conectar("Perfiles");
        if(bdv.SetInteligencias(inteligencias,mensaje.getUsuario()))
            mensaje.setRespuesta("Perfil Guardado Correctamente");
        else
            mensaje.setRespuesta("Ha ocurrido un error!");
        bdv.desconectar();
    }
    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("---------");
       // this.doDelete();
    }
}
    
    
