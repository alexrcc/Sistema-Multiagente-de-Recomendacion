package sma;

import controller.Mensaje;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Virtuoso;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;


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
         System.out.println("Agente: "+getAID().getName()+" inicializado");
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
                                System.out.println("Entro a BA");
                                BusquedaAvanzada(0);
                            break;
                            case "BA-R":
                                System.out.println("Entro a BA");
                                BusquedaAvanzada(1);
                            break;
                            case "BI":
                                System.out.println("Entro a BI");
                                BusquedaInteligencias(0);
                            break;
                            case "BI-R":
                                System.out.println("Entro a BI-R");
                                BusquedaInteligencias(1);
                            break;
                            default:System.out.println("NO se encuentra");
                        }
                        if(mensaje.getMensaje().equals("BS")){
                        System.out.println("Entra a Resp BS");
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContentObject(mensaje);
                        send(reply);
                        }
                        else if(mensaje.getMensaje().equals("BA-R")||mensaje.getMensaje().equals("BI-R")){
                            System.out.println("Entra a Resp BA-R");
                            ACLMessage reply = new ACLMessage(ACLMessage.REQUEST);
                            reply.setPerformative(ACLMessage.PROPOSE);
                            System.out.println(mensaje.getRemitente().getName());
                            reply.addReceiver((mensaje.getRemitente()));
                            reply.setContentObject(mensaje);
                            send(reply);
                            System.out.println("envia-msg");
                        }
                        
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

    private void BusquedaSimple() throws IOException{
        String keyaux = (String)mensaje.getArgumentos();
        String [] keywords = keyaux.split(" ");
        Virtuoso bdv = new Virtuoso();
        bdv.conectar("http://LearningObjects");
        ResultSet results=bdv.BusquedaSimple(keyaux,keywords);
        ArrayList<String[]> al = new ArrayList<String[]>();
       while(results.hasNext()){
           String [] res = new String[4];
           QuerySolution qs = results.next();
           res[0]=qs.getResource("LearningObject").toString();
           res[1]=qs.get("title").toString();
           res[2]=qs.get("entry").toString();
           res[3]=qs.get("avatar").toString();
           al.add(res);
       }
     mensaje.setRespuesta(al);
     bdv.desconectar();

    }
    
    private void BusquedaAvanzada(int band) throws IOException{
        ArrayList<String []> aux =(ArrayList<String[]>) mensaje.getArgumentos();
        String [] keyaux = aux.get(0);
        if(band==0&&keyaux.length==2){
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(new AID("AgenteEstudiante", AID.ISLOCALNAME));
            msg.setContentObject(mensaje);
            send(msg);
        }else{
            String [] keywords = keyaux[0].split(" ");
            String [] materias = aux.get(1);
            for (String materia : materias) {
                System.out.println(materia);
            }
            int idioma = Integer.parseInt(keyaux[1]);
            int estilo_dominante;
            if(keyaux.length>2){
                estilo_dominante = Integer.parseInt(keyaux[2]);
                mensaje.setMensaje("BA-R");
            }else{
                int [] estilos ;
                ArrayList<int[]> perfil;
                perfil=(ArrayList<int[]>)mensaje.getRespuesta();
                estilos=(int[])perfil.get(0);
                estilo_dominante=Dominante(estilos);
            }          
        Virtuoso bdv = new Virtuoso();
        bdv.conectar("http://LearningObjects");
        ResultSet results=bdv.BusquedaAvanzada(idioma,keyaux,keywords,materias,estilo_dominante);
        ArrayList<String[]> al = new ArrayList<String[]>();
       while(results.hasNext()){
           String [] res = new String[4];
           QuerySolution qs = results.next();
           res[0]=qs.getResource("LearningObject").toString();
           res[1]=qs.get("title").toString();
           res[2]=qs.get("entry").toString();
           res[3]=qs.get("avatar").toString();
           al.add(res);
       }
     mensaje.setRespuesta(al);
     bdv.desconectar();
        
        }
    }
    private void BusquedaInteligencias(int band) throws IOException{
        if(band==0){
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(new AID("AgenteEstudiante", AID.ISLOCALNAME));
            msg.setContentObject(mensaje);
            send(msg);
        }else{
            int inteligencia_dominante;
            int inteligencia_faltante;
            int [] inteligencias;
            ArrayList<int[]> perfil;
            perfil=(ArrayList<int[]>)mensaje.getRespuesta();
            inteligencias=(int[])perfil.get(0);
            if(inteligencias!=null){
                inteligencia_dominante=Dominante(inteligencias);
                inteligencia_faltante=Faltante(inteligencias);
                Virtuoso bdv = new Virtuoso();
                bdv.conectar("http://LearningObjects");
                ResultSet results=bdv.BusquedaInteligencias(inteligencia_dominante,inteligencia_faltante);
                ArrayList<String[]> al = new ArrayList<>();
                while(results.hasNext()){
                   String [] res = new String[5];
                   QuerySolution qs = results.next();
                   res[0]=qs.getResource("LearningObject").toString();
                   res[1]=qs.get("title").toString();
                   res[2]=qs.get("entry").toString();
                   res[3]=qs.get("avatar").toString();
                   res[4]=qs.get("url_full").toString();
                   al.add(res);
               }
             mensaje.setRespuesta(al);
             bdv.desconectar();
            }
        }
    }
    private int Dominante(int [] perfil){
        int mayor=-1;
        int indice=0;
        boolean band = false;
        int cont =0;
        int aux [] = new int[perfil.length]; 
        
            for (int i = 0; i < perfil.length; i++) {
                if(perfil[i]>=mayor){
                    mayor=perfil[i];
                    indice = i; 
                }
                aux[i]=-1;
            }
            aux[0]=indice;
            cont++;
            for (int i = 0; i < perfil.length&&i!=-1; i++) {
                if(perfil[i]==mayor&&i!=indice){
                    aux[cont]=i;
                    cont++;
                    band=true;
                }
            }
           
           if(band==true){
            Random rnd = new Random();
            indice = aux[rnd.nextInt(cont) ];
           }
           return indice;
    }
    private int Faltante(int [] perfil){
        int menor=100;
        int indice=0;
        boolean band = false;
        int cont =0;
        int aux [] = new int[perfil.length]; 
        
            for (int i = 0; i < perfil.length; i++) {
                if(perfil[i]<=menor){
                    menor=perfil[i];
                    indice = i; 
                }
                aux[i]=-1;
            }
            aux[0]=indice;
            cont++;
            for (int i = 0; i < perfil.length&&i!=-1; i++) {
                if(perfil[i]==menor&&i!=indice){
                    aux[cont]=i;
                    cont++;
                    band=true;
                }
            }
           
           if(band==true){
            Random rnd = new Random();
            indice = aux[rnd.nextInt(cont) ];
           }
           return indice;
    }
    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("--------------------------------------------------");
    }
}
