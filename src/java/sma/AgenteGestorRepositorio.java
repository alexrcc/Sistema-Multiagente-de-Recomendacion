package sma;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import java.io.BufferedInputStream;


//Externos

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//Ontologia




public class AgenteGestorRepositorio extends Agent{
    private static int total_pages;

    @Override
    protected void setup(){
        System.out.println("Hola soy el : "+getAID().getName()+" estoy listo");
        Object [] args = getArguments();

        if(args != null && args.length>0){
            System.out.println("Me envian este argumento: "+args[0]+args[1]);
        }else{
            System.out.println("No se enviaron argumentos");
            doDelete();
        }
        addBehaviour(new ComportamientoAGR());
    }


    private class ComportamientoAGR extends Behaviour{

        @Override
        public void action() {
//            Object[] args = getArguments();
//            if (args != null) {
//                System.out.println("Digo:" + args[0] + args[1]);
//            }
//
            try{
                
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            model.read("file:///C://Users///alexr//Desktop//Prueba.owl","RDF/XML");
            
            String url = "https://roa.cedia.edu.ec/apis/search?type=Resource&page=";
            String url_metadata = "https://roa.cedia.edu.ec/";

            //String url = "http://vishub.org/apis/search?type=Resource&page=1";
            total_pages = GetPages(url+"1");
            //System.out.println(total_pages);
            for (int i = 1; total_pages !=0 && i <= total_pages; i++) {
                JSONObject learning_objects = GetLernaingObject(url+i);
                JSONArray results = learning_objects.getJSONArray("results");
                //System.out.println(results.length());

                for (int j = 0; j <results.length() ; j++) { //results.length()
                    JSONObject aux = results.getJSONObject(j);
                    System.out.println(aux.getString("id"));
                    String type = aux.getString("type");
                    String id = aux.getString("id");
                    String dir = aux.getString("url");
                    String in ="";
                    if(type.compareTo("Excursion")==0){
                        String[] a = id.split(":");
                        int valor = Integer.parseInt(a[1].split("@")[0]);
                       in = (GetMetadata(url_metadata+"excursions/",valor));
                    }else{
                        int v_aux = GetCodeMetadata(dir);
                        System.out.println(v_aux);
                        in = GetMetadata(url_metadata+"activity_objects/",v_aux);
                    }
                }
            }

            }catch(Exception e){
                   System.out.println(e);
            }
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
        return Integer.parseInt(valor);
    }
    private static String GetMetadata(String url,int codigo) throws Exception{
        String metadatos=null;
        String url_metadata = url+codigo+"/metadata.xml";
        URL Obj_metadata = new URL(url_metadata);
        HttpURLConnection metadata = (HttpURLConnection) Obj_metadata.openConnection();
        int rspmetadata = metadata.getResponseCode();
        if(rspmetadata!=404&&rspmetadata!=500){
            InputStream in = new BufferedInputStream(metadata.getInputStream());
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = (Document) db.parse(in);
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            Node lom = doc.getDocumentElement();
            NodeList hijos = lom.getChildNodes();
            for (int i = 1; i < hijos.getLength(); i=i+2 ) {
                String nodo = hijos.item(i).getNodeName();
                switch(nodo){
                    case "general":
                        System.out.println("general-----------");
                        Node general = hijos.item(i);
                        NodeList keywords = general.getChildNodes();
                        for (int j = 1; j < keywords.getLength(); j=j+2) {
                            Element eElement = (Element) keywords.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");
                            if(keywords.item(j).getNodeName().compareTo("identifier")==0){
                                System.out.println("catalog : " + eElement.getElementsByTagName("catalog").item(0).getTextContent());
                                System.out.println("entry : " + eElement.getElementsByTagName("entry").item(0).getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("title")==0){
                                System.out.println("Titulo : " + eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("language")==0){
                                System.out.println("Lenguaje : " + eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("description")==0){
                                System.out.println("Description : " + eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("keyword")==0){
                                System.out.println("Keyword: "+eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("coverage")==0){
                                System.out.println("Coverage: "+eElement.getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("structure")==0){
                                System.out.println("structure : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(keywords.item(j).getNodeName().compareTo("aggregationLevel")==0){
                                System.out.println("Agregación : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                        }
                    break;

                    case "lifeCycle":
                        System.out.println("LifeCycle-----------");
                        Node lifecicle = hijos.item(i);
                        NodeList lc = lifecicle.getChildNodes();
                        for (int j = 1; j < lc.getLength(); j=j+2) {
                            Element eElement = (Element) lc.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");

                            if(lc.item(j).getNodeName().compareTo("version")==0){
                                System.out.println("Version : " + eElement.getTextContent());
                            }
                            else if(lc.item(j).getNodeName().compareTo("status")==0){
                                System.out.println("Status: " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(lc.item(j).getNodeName().compareTo("contribute")==0){
                                NodeList cnodes = lc.item(j).getChildNodes();
                                for (int k = 1; k < cnodes.getLength(); k=k+2) {
                                   Element cElement = (Element) cnodes.item(k);
                                   if(cnodes.item(k).getNodeName().compareTo("role")==0)
                                    System.out.println("role : " + cElement.getElementsByTagName("value").item(0).getTextContent());
                                   else if(cnodes.item(k).getNodeName().compareTo("entity")==0)
                                    System.out.println("entity : " + cElement.getTextContent());
                                   else if(cnodes.item(k).getNodeName().compareTo("date")==0)
                                    System.out.println("date : " + cElement.getElementsByTagName("dateTime").item(0).getTextContent());
                                }
                            }
                        }
                    break;
                    case "metaMetadata":
                        System.out.println("MetaMetadata-----------");
                        Node mMeta = hijos.item(i);
                        NodeList nmeta = mMeta.getChildNodes();
                        for (int j = 1; j < nmeta.getLength(); j=j+2) {
                            Element eElement = (Element) nmeta.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");

                            if(nmeta.item(j).getNodeName().compareTo("identifier")==0){
                                System.out.println("catalog : " + eElement.getElementsByTagName("catalog").item(0).getTextContent());
                                System.out.println("entry : " + eElement.getElementsByTagName("entry").item(0).getTextContent());
                            }
                            else if(nmeta.item(j).getNodeName().compareTo("contribute")==0){
                                NodeList cnodes = nmeta.item(j).getChildNodes();
                                for (int k = 1; k < cnodes.getLength(); k=k+2) {
                                   Element cElement = (Element) cnodes.item(k);
                                   if(cnodes.item(k).getNodeName().compareTo("role")==0)
                                    System.out.println("role : " + cElement.getElementsByTagName("value").item(0).getTextContent());
                                   else if(cnodes.item(k).getNodeName().compareTo("entity")==0)
                                    System.out.println("entity : " + cElement.getTextContent());
                                   else if(cnodes.item(k).getNodeName().compareTo("date")==0)
                                    System.out.println("date : " + cElement.getElementsByTagName("dateTime").item(0).getTextContent());
                                }
                            }
                            else if(nmeta.item(j).getNodeName().compareTo("metadataSchema")==0){
                                System.out.println("metadataSchema: " + eElement.getTextContent());
                            }
                            else if(nmeta.item(j).getNodeName().compareTo("language")==0){
                                System.out.println("Lenguaje: " + eElement.getTextContent());
                            }
                        }
                        break;
                    case "technical":
                        System.out.println("Technical-----------");
                        Node tech = hijos.item(i);
                        NodeList htech = tech.getChildNodes();
                        for (int j = 1; j < htech.getLength(); j=j+2) {
                            Element eElement = (Element) htech.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");

                            if(htech.item(j).getNodeName().compareTo("location")==0){
                                System.out.println("Locacion: " + eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("format")==0){
                                System.out.println("Format: " + eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("requirement")==0){
                                NodeList cnodes = htech.item(j).getChildNodes();
                                for (int k = 1; k < cnodes.getLength(); k=k+2) {

                                   if(cnodes.item(k).getNodeName().compareTo("orComposite")==0){
                                       NodeList orcomposite = cnodes.item(k).getChildNodes();
                                       for (int l = 1; l < orcomposite.getLength(); l=l+2) {

                                           Element ocElement = (Element) orcomposite.item(l);
                                           if(orcomposite.item(l).getNodeName().compareTo("type")==0)
                                               System.out.println("type : " + ocElement.getElementsByTagName("value").item(0).getTextContent());
                                           else if(orcomposite.item(l).getNodeName().compareTo("name")==0)
                                               System.out.println("Name : " + ocElement.getElementsByTagName("value").item(0).getTextContent());
                                           else if(orcomposite.item(l).getNodeName().compareTo("minimumVersion")==0)
                                               System.out.println("minimunVersion : " + ocElement.getTextContent());
                                           else if(orcomposite.item(l).getNodeName().compareTo("maximumVersion")==0)
                                               System.out.println("maximunVersion: " + ocElement.getTextContent());
                                       }
                                   }
                                }
                            }
                            else if(htech.item(j).getNodeName().compareTo("installationRemarks")==0){
                                System.out.println("installationRemarks: " + eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("languageotherPlatformRequirements")==0){
                                System.out.println("otherPlatformRequirements: " + eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("duration")==0){
                                System.out.println("duration: " + eElement.getTextContent());
                            }
                            else if(htech.item(j).getNodeName().compareTo("size")==0){
                                System.out.println("Size: " + eElement.getTextContent());
                            }
                        }
                        break;
                    case "educational":
                        System.out.println("Educational-----------");
                        Node educational  = hijos.item(i);
                        NodeList heduc = educational.getChildNodes();
                        for (int j = 1; j < heduc.getLength(); j=j+2) {
                            Element eElement = (Element) heduc.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");
                            if(heduc.item(j).getNodeName().compareTo("interactivityType")==0){
                                System.out.println("Inteactivity type : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("learningResourceType")==0){
                                System.out.println("learning Type : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("interactivityLevel")==0){
                                System.out.println("Interactivity Level : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("intendedEndUserRole")==0){
                                System.out.println("End User Role : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("context")==0){
                                System.out.println("Context: "+eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("typicalAgeRange")==0){
                                System.out.println("Age Range: "+eElement.getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("difficulty")==0){
                                System.out.println("Dificultad: " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("typicalLearningTime")==0){
                                System.out.println("Agregación : " + eElement.getElementsByTagName("duration").item(0).getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("description")==0){
                                System.out.println("Descripcion: " + eElement.getTextContent());
                            }
                            else if(heduc.item(j).getNodeName().compareTo("language")==0){
                                System.out.println("Lenguaje: " + eElement.getTextContent());
                            }
                        }
                        break;
                    case "rights":
                        System.out.println("Rigths-----------");
                        Node rights  = hijos.item(i);
                        NodeList hrights = rights.getChildNodes();
                        for (int j = 1; j < hrights.getLength(); j=j+2) {
                            Element eElement = (Element) hrights.item(j);
                            System.out.println("----"+eElement.getNodeName()+"----");
                            if(hrights.item(j).getNodeName().compareTo("cost")==0){
                                System.out.println("Cost : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(hrights.item(j).getNodeName().compareTo("copyrightAndOtherRestrictions")==0){
                                System.out.println("copyrightAndOtherRestrictions: " + eElement.getElementsByTagName("value").item(0).getTextContent());
                            }
                            else if(hrights.item(j).getNodeName().compareTo("description")==0){
                                System.out.println("Descripción : " + eElement.getTextContent());
                            }
                        }
                        break;
                    default: System.out.println("Elemento no está en estandar LOM");
                        break;
                }

            }
        }

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

//            System.out.println("--------"+myResponse);
//            String dir = myResponse.getString("results");
//            System.out.println(dir);
        }
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

        }
        return pages;
    }

    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("--------------------------------------------------");
    }
}
