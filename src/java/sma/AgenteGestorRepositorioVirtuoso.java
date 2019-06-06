package sma;

//Agentes
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentContainer;
import java.io.BufferedInputStream;

//Externos
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//Ontologia
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.json.JSONException;
import static sma.AgentePerfilEstudiante.URL;
import static sma.AgentePerfilEstudiante.smas;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;


public class AgenteGestorRepositorioVirtuoso extends Agent{
    
    private static int total_pages;
    static final String uid = "dba";
    static final String pwd = "dba";
    static final String URL = "jdbc:virtuoso://localhost:1111";
    VirtModel set;
    private static OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
    private static final String ns = "http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#"; 
    private static final String adms = "http://www.w3.org/ns/adms#"; 
    private static final String dcat = "http://www.w3.org/ns/dcat#" ;
    private static final String foaf = "http://xmlns.com/foaf/0.1/" ;
    private static final String fabio = "http://purl.org/spar/fabio/" ;
    private static final String rad = "http://www.w3.org/ns/radion#" ;
    private static final String owl = "http://www.w3.org/2002/07/owl#" ;
    private static final String vcard = "http://www.w3.org/2006/vcard/ns#" ;
    private static final String gold = "http://purl.org/linguistics/gold/" ;
    private static final String xsd = "http://www.w3.org/2001/XMLSchema#" ;
    private static final String com = "http://vocab.resc.info/communication#" ;
    private static final String rdfs = "http://www.w3.org/2000/01/rdf-schema#" ;
    private static final String nco = "http://oscaf.sourceforge.net/nco.html#" ;
    private static final String lcy = "http://purl.org/vocab/lifecycle/schema#" ;
    private static final String stac = "http://securitytoolbox.appspot.com/stac#" ;
    private static final String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#" ;
    private static final String discovery = "http://rdf-vocabulary.ddialliance.org/discovery#" ;
    private static final String ebu = "http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#" ;
    private static final String smas = "http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#";
    private static String url_base;
    
    
    @Override
    protected void setup(){
        System.out.println("Agente: "+getAID().getName()+"inicializado");
        Object [] args = getArguments();

        if(args != null && args.length>0){
            
            System.out.println (new File (".").getAbsolutePath ());
            System.out.println("Argumentos: "+args[0]+args[1]);
            model.read("C:\\Users\\alexr\\Documents\\NetBeansProjects\\sma_web//OntologiaBase.owl","RDF/XML");

            set  = VirtModel.openDatabaseModel("http://LearningObjects", URL, uid, pwd);
            addBehaviour(new ComportamientoAGR(args));
        }else{
            System.out.println("No se enviaron argumentos");
            doDelete();
        }
    }


    private class ComportamientoAGR extends Behaviour{
        private final Object [] args;
        public ComportamientoAGR( Object argumentos[]){
            args = argumentos; 
        }
        @Override
        public void action() {
            for(int z=0;z<args.length;z++){
                try{
                String url_base=args[z].toString();
                String url = url_base+"/apis/search?type=Resource&page=";
                total_pages = GetPages(url+"1");
                //System.out.println(total_pages);
                for (int i = 1; total_pages !=0 && i <= total_pages; i++) {
                    System.out.println("-----------------------PÁGINA ACTUAL "+i+"--------------------------");
                    JSONObject learning_objects = GetLernaingObject(url+i);
                    JSONArray results = learning_objects.getJSONArray("results");
                    //System.out.println(results.length());

                    for (int j = 0; j <results.length() ; j++) { //results.length()
                        JSONObject aux = results.getJSONObject(j);
                        System.out.println(aux.getString("id"));
                        String avatar;
                        String url_full;
                        if(aux.has("avatar_url"))
                            avatar = aux.getString("avatar_url");
                        else
                            avatar = "null";
                        if(aux.has("url_full"))
                            url_full = aux.getString("url_full");
                        else if(aux.has("file_url"))
                            url_full = aux.getString("file_url");
                        else if(aux.has("url"))
                            url_full=aux.getString("url");
                        else
                            url_full="null";
                        
                        String type = aux.getString("type");
                        String id = aux.getString("id");
                        String dir = aux.getString("url");
                        String in ="";
                        Actualizar(avatar, id,url_full);
//                        if(type.compareTo("Excursion")==0){
//                            String[] a = id.split(":");
//                            int valor = Integer.parseInt(a[1].split("@")[0]);
//                           in = (GetMetadata(url_base+"/excursions/",valor,id,avatar,url_full));
//                        }else{
//                            int v_aux = GetCodeMetadata(dir);
//                            System.out.println(v_aux);
//                            in = GetMetadata(url_base+"/activity_objects/",v_aux,id,avatar,url_full);
//                        }
                    }
                }
                
                }catch(Exception e){
                       System.out.println(e);
                }
            
            }
//            try{
//            File file = new File("Individuos.owl");
//           
//                if (!file.exists()){
//                     file.createNewFile();
//                }
//                model.write(new PrintWriter(file));
//                set.add(model);
//            }catch(Exception e){
//                System.out.println("Error al guardar el modelo: "+e);
//            }
        }

        @Override
        public boolean done() {

            System.out.println("Finaliza el " + this.getBehaviourName());
            System.out.println("--------------------------------------------------");
            doDelete();

            return true;
        }

    }
    
    
    
    
    
    
    
    private static int GetCodeMetadata(String url)throws Exception{

        URL url_aux = new URL(url);
        HttpURLConnection con_aux = (HttpURLConnection) url_aux.openConnection();
        int responseCode_aux = con_aux.getResponseCode();
        String valor = "0";

        if(responseCode_aux!=404&&responseCode_aux!=500){
            BufferedReader in_aux = new BufferedReader(new InputStreamReader(con_aux.getInputStream()));
            String inputLine_aux;
            while ((inputLine_aux = in_aux.readLine()) != null) {
                boolean band = inputLine_aux.contains("metadata.xml");
                if(band){
                    String[] a = inputLine_aux.split("/");
                    valor = a[4];
                    System.out.println("Valor para metadato: "+valor);
                    break;
                }

            }
            in_aux.close();
        }
        con_aux.disconnect();
        return Integer.parseInt(valor);
    }
    private void Actualizar(String url, String id,String url_full){

        String str ="PREFIX smas: <"+smas+">"+
                " INSERT INTO GRAPH <http://LearningObjects> {<"
                        +smas+id+"> smas:avatar '"+url+"'.}";
        System.out.println(str);
            VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
            vur.exec();
        str ="PREFIX smas: <"+smas+">"+
                " INSERT INTO GRAPH <http://LearningObjects> {<"
                        +smas+id+"> smas:url_full '"+url_full+"'.}";
        System.out.println(str);
            vur = VirtuosoUpdateFactory.create(str, set);
            vur.exec();
    }
    private static String GetMetadata(String url,int codigo, String id,String avatar,String url_full) throws Exception{

        String metadatos=null;
        String url_metadata = url+codigo+"/metadata.xml";
        URL Obj_metadata = new URL(url_metadata);
        
        HttpURLConnection metadata = (HttpURLConnection) Obj_metadata.openConnection();
        System.out.println("URL-META"+Obj_metadata);
        int rspmetadata = metadata.getResponseCode();
        if(rspmetadata!=404&&rspmetadata!=500){
            InputStream in = new BufferedInputStream(metadata.getInputStream());
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = (Document) db.parse(in);
            OntClass cls1 = model.getOntClass(ns+"LearningObject");
            Individual LO = model.createIndividual(ns+id,cls1);
            LO.addProperty(model.getDatatypeProperty(smas+"avatar"),avatar);
            LO.addProperty(model.getDatatypeProperty(smas+"url_full"),url_full);
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            Node lom = doc.getDocumentElement();
            NodeList hijos = lom.getChildNodes();
            for (int i = 1; i < hijos.getLength(); i=i+2 ) {
                String nodo = hijos.item(i).getNodeName();
                ObjectProperty is_c_of = model.getObjectProperty(ns+"isComprisedOf");
                switch(nodo){
                    case "general":
                        System.out.println("general-----------");
                        OntClass cls_gen = model.getOntClass(ns+"General");
                        Individual ont_gen = model.createIndividual(ns+"Gen_"+id,cls_gen);
                        LO.addProperty(is_c_of,ont_gen);
                         
                        Node general = hijos.item(i);
                        NodeList keywords = general.getChildNodes();
                        for (int j = 1; j < keywords.getLength(); j=j+2) {
                            Element eElement = (Element) keywords.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");
                            if(keywords.item(j).getNodeName().compareTo("identifier")==0){
                                OntClass identifier = model.getOntClass(adms+"Identifier");
                                Individual idm = model.createIndividual(ns+"Id_"+id,identifier);
                                idm.setPropertyValue(model.getDatatypeProperty(ns+"entry"), model.createTypedLiteral(eElement.getElementsByTagName("entry").item(0).getTextContent()));
                                idm.setPropertyValue(model.getDatatypeProperty(ns+"catalog"), model.createTypedLiteral(eElement.getElementsByTagName("catalog").item(0).getTextContent()));
                                ObjectProperty opg = model.getObjectProperty(ns+"hasIdentifier");
                                ont_gen.addProperty(opg,idm);
                            }
                            else if(keywords.item(j).getNodeName().compareTo("title")==0){
                                ont_gen.addProperty(model.getDatatypeProperty(vcard+"title"),eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("language")==0){
                                ont_gen.addProperty(model.getDatatypeProperty(vcard+"language"),eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("description")==0){
                                ont_gen.addProperty(model.getDatatypeProperty(ns+"description"),eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("keyword")==0){
                                ont_gen.addProperty(model.getDatatypeProperty(rad+"keyword"),eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("coverage")==0){
                                ont_gen.addProperty(model.getDatatypeProperty(ns+"coverage"),eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("structure")==0){
                                ont_gen.addProperty(model.getDatatypeProperty(ns+"structure"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("aggregationLevel")==0){
                                ont_gen.addProperty(model.getDatatypeProperty(ns+"aggregationLevel"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                        }
                    break;

                    case "lifeCycle":
                        System.out.println("LifeCycle-----------");
                        OntClass cls_lc = model.getOntClass(lcy+"Lifecycle");
                        Individual ont_lc = model.createIndividual(ns+"Lc_"+id,cls_lc);
                        LO.addProperty(is_c_of,ont_lc);
                        
                        Node lifecicle = hijos.item(i);
                        NodeList lc = lifecicle.getChildNodes();
                        for (int j = 1; j < lc.getLength(); j=j+2) {
                            Element eElement = (Element) lc.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");

                            if(lc.item(j).getNodeName().compareTo("version")==0){
                                ont_lc.addProperty(model.getDatatypeProperty(rad+"version"),eElement.getTextContent());
                            }
                            else if(lc.item(j).getNodeName().compareTo("status")==0){
                                ont_lc.addProperty(model.getDatatypeProperty(foaf+"status"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(lc.item(j).getNodeName().compareTo("contribute")==0){
                                OntClass contribute = model.getOntClass(ns+"Contribute");
                                Individual idm = model.createIndividual(ns+"CLc_"+id,contribute);
                                NodeList cnodes = lc.item(j).getChildNodes();
                                for (int k = 1; k < cnodes.getLength(); k=k+2) {
                                   Element cElement = (Element) cnodes.item(k);
                                   if(cnodes.item(k).getNodeName().compareTo("role")==0)
                                    idm.addProperty(model.getDatatypeProperty(vcard+"role"), cElement.getElementsByTagName("value").item(0).getTextContent());
                                   else if(cnodes.item(k).getNodeName().compareTo("entity")==0)
                                    idm.addProperty(model.getDatatypeProperty(ns+"entity"),cElement.getTextContent());
                                   else if(cnodes.item(k).getNodeName().compareTo("date")==0)
                                    idm.addProperty(model.getDatatypeProperty(ns+"date"),cElement.getElementsByTagName("dateTime").item(0).getTextContent());
                                }
                                ObjectProperty opg = model.getObjectProperty(ns+"hasContribute");
                                ont_lc.addProperty(opg,idm);
                            }
                        }
                    break;
                    case "metaMetadata":
                        System.out.println("MetaMetadata-----------");
                        OntClass cls_meta = model.getOntClass(ns+"Metametadata");
                        Individual ont_meta = model.createIndividual(ns+"M_"+id,cls_meta);
                        LO.addProperty(is_c_of,ont_meta);
                        
                        Node mMeta = hijos.item(i);
                        NodeList nmeta = mMeta.getChildNodes();
                        for (int j = 1; j < nmeta.getLength(); j=j+2) {
                            Element eElement = (Element) nmeta.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");

                            if(nmeta.item(j).getNodeName().compareTo("identifier")==0){
                                //System.out.println("catalog : " + eElement.getElementsByTagName("catalog").item(0).getTextContent());
                                //System.out.println("entry : " + eElement.getElementsByTagName("entry").item(0).getTextContent());
                                OntClass identifier = model.getOntClass(adms+"Identifier");
                                Individual idm = model.createIndividual(ns+"IdM_"+id,identifier);
                                idm.setPropertyValue(model.getDatatypeProperty(ns+"entry"), model.createTypedLiteral(eElement.getElementsByTagName("entry").item(0).getTextContent()));
                                idm.setPropertyValue(model.getDatatypeProperty(ns+"catalog"), model.createTypedLiteral(eElement.getElementsByTagName("catalog").item(0).getTextContent()));
                                ObjectProperty opg = model.getObjectProperty(ns+"hasIdentifier");
                                ont_meta.addProperty(opg,idm);
                            }
                            else if(nmeta.item(j).getNodeName().compareTo("contribute")==0){
                                OntClass contribute = model.getOntClass(ns+"Contribute");
                                Individual idm = model.createIndividual(ns+"CM_"+id,contribute);
                                NodeList cnodes = nmeta.item(j).getChildNodes();
                                for (int k = 1; k < cnodes.getLength(); k=k+2) {
                                   Element cElement = (Element) cnodes.item(k);
                                   if(cnodes.item(k).getNodeName().compareTo("role")==0)
                                    idm.addProperty(model.getDatatypeProperty(vcard+"role"), cElement.getElementsByTagName("value").item(0).getTextContent());
                                   else if(cnodes.item(k).getNodeName().compareTo("entity")==0)
                                    idm.addProperty(model.getDatatypeProperty(ns+"entity"),cElement.getTextContent());
                                   else if(cnodes.item(k).getNodeName().compareTo("date")==0)
                                    idm.addProperty(model.getDatatypeProperty(ns+"date"),cElement.getElementsByTagName("dateTime").item(0).getTextContent());
                                }
                                ObjectProperty opg = model.getObjectProperty(ns+"hasContribute");
                                ont_meta.addProperty(opg,idm);
                            }
                            else if(nmeta.item(j).getNodeName().compareTo("metadataSchema")==0){
                                ont_meta.addProperty(model.getDatatypeProperty(ns+"metadataScheme"),eElement.getTextContent());
                            }
                            else if(nmeta.item(j).getNodeName().compareTo("language")==0){
                                ont_meta.addProperty(model.getDatatypeProperty(vcard+"language"),eElement.getTextContent());
                            }
                        }
                        break;
                    case "technical":
                        System.out.println("Technical-----------");
                        OntClass cls_tech = model.getOntClass(ns+"Technical");
                        Individual ont_tech = model.createIndividual(ns+"T_"+id,cls_tech);
                        LO.addProperty(is_c_of,ont_tech);
                        
                        Node tech = hijos.item(i);
                        NodeList htech = tech.getChildNodes();
                        for (int j = 1; j < htech.getLength(); j=j+2) {
                            Element eElement = (Element) htech.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");

                            if(htech.item(j).getNodeName().compareTo("location")==0){
                                ont_tech.addProperty(model.getDatatypeProperty(ns+"location"),eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("format")==0){
                                ont_tech.addProperty(model.getDatatypeProperty(ns+"format"),eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("requirement")==0){
                                NodeList cnodes = htech.item(j).getChildNodes();
                                for (int k = 1; k < cnodes.getLength(); k=k+2) {
                                    
                                   if(cnodes.item(k).getNodeName().compareTo("orComposite")==0){
                                       NodeList orcomposite = cnodes.item(k).getChildNodes();
                                       OntClass composite = model.getOntClass(ns+"Composite");
                                       Individual idm = model.createIndividual(ns+"Com_"+"i_"+id,composite);
                                       for (int l = 1; l < orcomposite.getLength(); l=l+2) {

                                           Element ocElement = (Element) orcomposite.item(l);
                                           if(orcomposite.item(l).getNodeName().compareTo("type")==0)
                                               idm.addProperty(model.getDatatypeProperty(ns+"type"),ocElement.getElementsByTagName("value").item(0).getTextContent());
                                           else if(orcomposite.item(l).getNodeName().compareTo("name")==0)
                                               idm.addProperty(model.getDatatypeProperty(ns+"name"),ocElement.getElementsByTagName("value").item(0).getTextContent());
                                           else if(orcomposite.item(l).getNodeName().compareTo("minimumVersion")==0)
                                               idm.addProperty(model.getDatatypeProperty(ns+"minimumVersion"),ocElement.getTextContent());
                                           else if(orcomposite.item(l).getNodeName().compareTo("maximumVersion")==0)
                                               idm.addProperty(model.getDatatypeProperty(ns+"maximumVersion"),ocElement.getTextContent());
                                       }
                                       ObjectProperty opg = model.getObjectProperty(ns+"hasComposite");
                                       ont_tech.addProperty(opg,idm);
                                   }
                                }
                            }
                            else if(htech.item(j).getNodeName().compareTo("installationRemarks")==0){
                                ont_tech.addProperty(model.getDatatypeProperty(ns+"installationRemarks"),eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("otherPlatformRequirements")==0){
                                ont_tech.addProperty(model.getDatatypeProperty(ns+"otherPlatformRequirements"),eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("duration")==0){
                                ont_tech.addProperty(model.getDatatypeProperty(ns+"duration"),eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("size")==0){
                                ont_tech.addProperty(model.getDatatypeProperty(ns+"size"),eElement.getTextContent());
                            }
                        }
                        break;
                    case "educational":
                        System.out.println("Educational-----------");
                        OntClass cls_educ = model.getOntClass(ns+"Educational");
                        Individual ont_educ = model.createIndividual(ns+"Educ_"+id,cls_educ);
                        LO.addProperty(is_c_of,ont_educ);
                        
                        Node educational  = hijos.item(i);
                        NodeList heduc = educational.getChildNodes();
                        for (int j = 1; j < heduc.getLength(); j=j+2) {
                            Element eElement = (Element) heduc.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");
                            if(heduc.item(j).getNodeName().compareTo("interactivityType")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(ns+"interactivityType"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("learningResourceType")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(ns+"learningResourceType"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("interactivityLevel")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(ns+"interactivityLevel"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("intendedEndUserRole")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(ns+"intendedEndUserRole"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("context")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(ns+"context"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("typicalAgeRange")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(ns+"typicalAgeRange"),eElement.getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("difficulty")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(ns+"difficulty"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("typicalLearningTime")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(ns+"typicalLearningTime"),eElement.getElementsByTagName("duration").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("description")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(ns+"description"),eElement.getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("language")==0){
                                ont_educ.addProperty(model.getDatatypeProperty(vcard+"language"),eElement.getTextContent());
                            }
                        }
                        break;
                    case "rights":
                        System.out.println("Rigths-----------");
                        OntClass cls_rigths = model.getOntClass(ns+"Rights");
                        Individual ont_rigths = model.createIndividual(ns+"R_"+id,cls_rigths);
                        LO.addProperty(is_c_of,ont_rigths);
                        
                        Node rights  = hijos.item(i);
                        NodeList hrights = rights.getChildNodes();
                        for (int j = 1; j < hrights.getLength(); j=j+2) {
                            Element eElement = (Element) hrights.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");
                            if(hrights.item(j).getNodeName().compareTo("cost")==0){
                                ont_rigths.addProperty(model.getDatatypeProperty(ns+"cost"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(hrights.item(j).getNodeName().compareTo("copyrightAndOtherRestrictions")==0){
                                ont_rigths.addProperty(model.getDatatypeProperty(ns+"copyrightAndOtherRestrictions"),eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(hrights.item(j).getNodeName().compareTo("description")==0){
                                ont_rigths.addProperty(model.getDatatypeProperty(ns+"description"),eElement.getTextContent());
                            }
                        }
                        break;
                    default: System.out.println("Elemento no está en estandar LOM");
                        break;
                }

            }
        }else{
            System.out.println("No se pudo conectar: "+rspmetadata);
            }
        metadata.disconnect();
        return metadatos;
    }
    private static JSONObject GetLernaingObject(String url)throws Exception{
        URL obj = new URL(url);
        JSONObject myResponse=null;
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        int responseCode = con.getResponseCode();

        if(responseCode!=404&&responseCode!=500){
            String valor=null;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
               response.append(inputLine);
            }
            in.close();
            myResponse = new JSONObject(response.toString());

        }
        con.disconnect();
        return myResponse;
    }
    private int GetPages(String url) throws Exception{
        int pages=0;
        URL obj = new URL(url);
        JSONObject myResponse=null;
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        int responseCode = con.getResponseCode();

        if(responseCode!=404&&responseCode!=500){
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
               response.append(inputLine);
            }
            in.close();
            myResponse = new JSONObject(response.toString());
            pages = myResponse.getInt("total_pages");

        }else{
            System.out.println("NO se ha podido conectar: EROR CODE "+responseCode);
        }
        con.disconnect();
        return pages;
    }

    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("--------------------------------------------------");
    }
}
