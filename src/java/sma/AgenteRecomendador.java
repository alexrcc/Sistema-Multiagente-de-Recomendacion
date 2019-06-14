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
    private void BusquedaSimple() throws IOException{
        String keyaux = (String)mensaje.getArgumentos();
        String [] keywords = keyaux.split(" ");
        VirtModel model = VirtModel.openDatabaseModel("http://LearningObjects", URL, uid, pwd);
        String stringQuery = "PREFIX smas: <"+smas+">"+
                "PREFIX rn: <"+rn+">"+
                "PREFIX adms: <"+adms+">"+
                "PREFIX vcard: <"+vcard+">"+
                
                "SELECT DISTINCT ?LearningObject ?title ?entry ?avatar ?url_full WHERE {"+
                "?LearningObject smas:isComprisedOf ?General."+
                "?LearningObject smas:avatar ?avatar."+
                "?General smas:hasIdentifier ?Identifier."+
                 "?LearningObject smas:url_full ?url_full."+
                "?Identifier smas:entry ?entry."+
                "?General vcard:title ?title."+
                "OPTIONAL{?General rn:keyword ?ky}."+
                "OPTIONAL{?General smas:description ?desc}."+
                "FILTER(regex(?title,'"+keyaux+"','i')||regex(?desc,'"+keyaux+"','i')";
        for(String a:keywords)
            if(!esConector(a)){
                stringQuery=stringQuery + "||regex(?title,'"+a+"','i')";
                stringQuery=stringQuery + "||regex(?ky,'"+a+"','i')";
            }
        stringQuery = stringQuery +").}";
        System.out.println(stringQuery);
        Query query = QueryFactory.create(stringQuery);
        // Ejecutar la consulta y obtener los resultados
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        
        ResultSet results = qe.execSelect();
        ArrayList<String[]> al = new ArrayList<String[]>();
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
            int idioma = Integer.parseInt(keyaux[1]);
            
            int estilo_dominante;
            if(keyaux.length>2){
                estilo_dominante = Integer.parseInt(keyaux[2]);
                mensaje.setMensaje("BA-R");
            }else{
                int [] estilos ;
                int [] inteligencias;
                ArrayList<int[]> perfil;
                perfil=(ArrayList<int[]>)mensaje.getRespuesta();
                estilos=(int[])perfil.get(0);
                estilo_dominante=Dominante(estilos);
            }          
            VirtModel model = VirtModel.openDatabaseModel("http://LearningObjects", URL, uid, pwd);
            String stringQuery = "PREFIX smas: <"+smas+">"+
                "PREFIX rn: <"+rn+">"+
                "PREFIX adms: <"+adms+">"+
                "PREFIX vcard: <"+vcard+">"+
                
                "SELECT DISTINCT ?LearningObject ?title ?entry ?avatar ?url_full WHERE {"+
                "?LearningObject smas:isComprisedOf ?General."+
                "?LearningObject smas:avatar ?avatar."+
                "?LearningObject smas:url_full ?url_full."+
                "?General smas:hasIdentifier ?Identifier."+
                "?Identifier smas:entry ?entry.";
                if(idioma!=0)
                stringQuery=stringQuery + "OPTIONAL{?General vcard:language ?language}.";
                else
                stringQuery=stringQuery + "?General vcard:title ?title."+
                "OPTIONAL{?General smas:description ?desc}."+
                "OPTIONAL{?General rn:keyword ?ky}.";
                    if(!keyaux[0].equals("")&&!keyaux[0].equals(" "))
                        stringQuery=stringQuery + "FILTER((regex(?title,'"+keyaux[0]+"','i')||regex(?desc,'"+keyaux[0]+"','i')";
                    else
                        stringQuery=stringQuery + "FILTER((regex(?LearningObject,'LO')";
        for(String a:keywords)
            if(!esConector(a)){
                stringQuery=stringQuery + "||regex(?title,'"+a+"','i')";
                stringQuery=stringQuery + "||regex(?ky,'"+a+"','i')";
            }
         
        if(estilo_dominante!=-1){
            if(estilo_dominante==0){
                stringQuery = stringQuery + ")&&(regex(?LearningObject,'Picture','i')"
                        + "||regex(?LearningObject,'Video','i')"
                        + "||regex(?LearningObject,'Swf','i')"
                        + "||regex(?LearningObject,'Link','i')"
                        + "||regex(?LearningObject,'Excursion','i')"
                        + "||regex(?LearningObject,'Scormfile','i'))";
            }
            if(estilo_dominante==1)    
                stringQuery =stringQuery + ")&&(regex(?LearningObject,'Audio','i')"
                        + "||regex(?LearningObject,'Excursion','i')"
                        + "||regex(?LearningObject,'Scormfile','i'))";
            if(estilo_dominante==2)
                stringQuery = stringQuery +")&&(regex(?LearningObject,'Officedoc','i')"
                        + "||regex(?LearningObject,'Excursion','i')"
                        + "||regex(?LearningObject,'Workshop','i')"
                        + "||regex(?LearningObject,'EdiphyDocument','i'))";
            if(estilo_dominante==3)
                stringQuery = stringQuery +")&&(regex(?LearningObject,'Excursion','i')"
                        + "||regex(?LearningObject,'Swf','i')"
                        + "||regex(?LearningObject,'Link','i')"
                        + "||regex(?LearningObject,'Webapp','i')"
                        + "||regex(?LearningObject,'Zipfile','i'))";
        }else{
            stringQuery = stringQuery + ")";
        }
            if(idioma!=0){
                if(idioma==1)
                    stringQuery = stringQuery +"&&regex(?language,'es','i')";
                else if(idioma==2)
                    stringQuery = stringQuery +"&&regex(?language,'en','i')";
            }
            if(materias!=null){
                stringQuery=stringQuery + "&&(regex(?ky,'"+materias[0]+"','i')";
                for (int i = 1; i < materias.length; i++) {
                    stringQuery=stringQuery + "||regex(?ky,'"+materias[1]+"','i')";
                }
                stringQuery=stringQuery + ")";
            }
        stringQuery = stringQuery +").}";
        System.out.println(stringQuery);
        Query query = QueryFactory.create(stringQuery);
        // Ejecutar la consulta y obtener los resultados
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        
        ResultSet results = qe.execSelect();
        ArrayList<String[]> al = new ArrayList<String[]>();
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
                System.out.println("ID"+inteligencia_dominante);
                
                inteligencia_faltante=Faltante(inteligencias);
                System.out.println("IF"+inteligencia_faltante);
                VirtModel model = VirtModel.openDatabaseModel("http://LearningObjects", URL, uid, pwd);
                String stringQuery = "PREFIX smas: <"+smas+">"+
                    "PREFIX rn: <"+rn+">"+
                    "PREFIX adms: <"+adms+">"+
                    "PREFIX vcard: <"+vcard+">"+
                    "SELECT DISTINCT ?LearningObject ?title ?entry ?avatar ?url_full WHERE {"+
                    "?LearningObject smas:isComprisedOf ?General."+
                    "?LearningObject smas:avatar ?avatar."+
                    "?LearningObject smas:url_full ?url_full."+
                    "?General smas:hasIdentifier ?Identifier."+
                    "?General vcard:title ?title."+
                    "?Identifier smas:entry ?entry."+
                    "OPTIONAL{?General smas:description ?desc}."+
                    "OPTIONAL{?General rn:keyword ?ky}.";
//                verbal,lógico/matemática,visual/espacial,kinestesica/corporal,musical/rítmica,intrapersonal,interpersonal]
                    if(inteligencia_dominante==0||inteligencia_faltante==0)
                        stringQuery = stringQuery +"filter(regex(?LearningObject,'Officedoc','i')).";
                    else if(inteligencia_dominante==1||inteligencia_faltante==1)
                        stringQuery = stringQuery +"filter(regex(?ky,'maths','i')||regex(?ky,'matemáticas','i')||regex(?ky,'logical','i')).";
                    else if(inteligencia_dominante==2||inteligencia_faltante==2)
                        stringQuery = stringQuery +"filter(regex(?ky,'map','i')||regex(?ky,'mapa','i')||contains(?title,'mapa conceptual')||contains(?title,'conceptual map')).";
                    else if(inteligencia_dominante==3||inteligencia_faltante==3)
                        stringQuery = stringQuery +"filter(regex(?ky,'Game','i')||regex(?LearningObject,'Swf','i')).";
                    else if(inteligencia_dominante==4||inteligencia_faltante==4)
                        stringQuery = stringQuery +"filter(regex(?LearningObject,'Audio','i')||regex(?ky,'music','i')||regex(?ky,'Audio','i')||regex(?ky,'podcast','i')).";
                    else if(inteligencia_dominante==5||inteligencia_faltante==5)
                        stringQuery = stringQuery +"filter(regex(?ky,'intrapersonal','i')||regex(?ky,'intrapersonal','i')||regex(?desc,'intrapersonal','i')||regex(?title,'intrapersonal','i')).";
                    else if(inteligencia_dominante==6||inteligencia_faltante==6)
                        stringQuery = stringQuery +"filter(regex(?ky,'interpersonal','i')||regex(?ky,'interpersonal','i||regex(?desc,'interpersonal','i')||regex(?title,'interpersonal','i')).";
                    stringQuery = stringQuery +"}ORDER BY RAND() LIMIT 9";
                 
                    Query query = QueryFactory.create(stringQuery);
                    QueryExecution qe = QueryExecutionFactory.create(query, model);

                    ResultSet results = qe.execSelect();
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
