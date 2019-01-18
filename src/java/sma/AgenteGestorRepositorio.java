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
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



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
            String url = "https://roa.cedia.edu.ec/apis/search?type=Resource&page=";
            String url_metadata = "https://roa.cedia.edu.ec/";
            
            //String url = "http://vishub.org/apis/search?type=Resource&page=1";
            total_pages = GetPages(url+"1");
            //System.out.println(total_pages);
            for (int i = 1; total_pages !=0 && i <= 1; i++) {
                JSONObject learning_objects = GetLernaingObject(url+i);
                JSONArray results = learning_objects.getJSONArray("results");
                //System.out.println(results.length());
                
                for (int j = 0; j < 3; j++) { //results.length()
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
                    case "metaMetadata":
                        break;
                    case "technical":
                        break; 
                    case "educational":
                        break;
                    case "rights":
                        break;
                    default: System.out.println("Elemento no está en estandar LOM");
                        break;
                }
                if(hijos.item(i).hasChildNodes())
                    
                System.out.println(hijos.item(i).getNodeName());
                //System.out.println("Contenido: "+hijos.item(i).getNodeValue());
                
            }
//            NodeList nList = doc.getElementsByTagName("general");
//            for (int temp = 0; temp < nList.getLength(); temp++) {
//                Node nNode = nList.item(temp);
//                System.out.println("\nCurrent Element :" + nNode.getNodeName());
////                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
////                    Element eElement = (Element) nNode;
////                    System.out.println("Team1 : " + eElement.getElementsByTagName("set").item(0).getTextContent());
////                    System.out.println("Team2 : " + eElement.getElementsByTagName("team2setSpec").item(0).getTextContent());
////                }
//            }
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
