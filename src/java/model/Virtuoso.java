package model;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;


public class Virtuoso {
    private static final String smas ="http://www.semanticweb.org/alexr/ontologies/2018/10/OntologiaTesis#";
    private static final String rdf = "http://www.w3.org/2000/01/rdf-schema#";
    private static final String ns = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String rn = "http://www.w3.org/ns/radion#";
    private static final String adms = "http://www.w3.org/ns/adms#";
    private static final String vcard = "http://www.w3.org/2006/vcard/ns#";
    
    private static final String URL = "jdbc:virtuoso://localhost:1111";
    private static final String uid = "dba";
    private static final String pwd = "dba";
    VirtModel model=null;
    String bd ;
    public void conectar(String bd){
         model = VirtModel.openDatabaseModel(bd, URL, uid, pwd);
         this.bd=bd;
    }
    public boolean SetEstilos(int [] array,String user){
        try{
            VirtGraph set = new VirtGraph (URL, uid, pwd);
            String stringQuery = "PREFIX smas: <"+smas+">"+ "SELECT * WHERE {"
                                + "<"+smas+user+"> smas:consistOf ?LearningStyle.}";
            Query query = QueryFactory.create(stringQuery);
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
             return false;   
        }
            return true;
    }
     public boolean SetInteligencias(int [] array,String user){
        try{
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
             return false;   
        }
            return true;
    }
    public ResultSet GetEstilos(String user){
        String stringQuery = 
        "PREFIX smas: <"+smas+">"
              + "SELECT * WHERE {"
              + "<"+smas+"LE-"+user+"> smas:visual ?visual."
              + "<"+smas+"LE-"+user+"> smas:aural ?aural."
              + "<"+smas+"LE-"+user+"> smas:kinesthetic ?kinesthetic."
              + "<"+smas+"LE-"+user+"> smas:readwrite ?readwrite}";     
        Query query = QueryFactory.create(stringQuery);
        // Ejecutar la consulta y obtener los resultados
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        
        ResultSet results = qe.execSelect();
        return results;
    }
    public ResultSet GetInteligencias(String user){
        String stringQuery = 
        "PREFIX smas: <"+smas+">"
              + "SELECT * WHERE {"
              + "<"+smas+"MI-"+user+"> smas:verbal ?verbal."
              + "<"+smas+"MI-"+user+"> smas:logicalmath ?logical."
              + "<"+smas+"MI-"+user+"> smas:visual ?visual."
              + "<"+smas+"MI-"+user+"> smas:kinesthetic ?kinesthetic."
              + "<"+smas+"MI-"+user+"> smas:musical ?musical."
              + "<"+smas+"MI-"+user+"> smas:intrapersonal ?intrapersonal."
              + "<"+smas+"MI-"+user+"> smas:interpersonal ?interpersonal}";        
        Query query = QueryFactory.create(stringQuery);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }
    
    public ResultSet BusquedaAvanzada(int idioma,  String [] keyaux,String [] keywords,
            String [] materias,int estilo_dominante){
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
                        + "||regex(?LearningObject,'Embed','i')"
                        + "||regex(?LearningObject,'Scormfile','i'))";
            }
            if(estilo_dominante==1)    
                stringQuery =stringQuery + ")&&(regex(?LearningObject,'Audio','i')"
                        + "||regex(?LearningObject,'Embed','i')"
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
                        + "||regex(?LearningObject,'Embed','i')"
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
                    stringQuery=stringQuery + "||regex(?ky,'"+materias[i]+"','i')";
                }
                stringQuery=stringQuery + ")";
            }
        stringQuery = stringQuery +").}";
        System.out.println(stringQuery);
        Query query = QueryFactory.create(stringQuery);
        // Ejecutar la consulta y obtener los resultados
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }
    public ResultSet BusquedaInteligencias(int inteligencia_dominante, int inteligencia_faltante){
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
                stringQuery = stringQuery +"filter(regex(?LearningObject,'Officedoc','i')||regex(?ky,'verbal','i')).";
            else if(inteligencia_dominante==1||inteligencia_faltante==1)
                stringQuery = stringQuery +"filter(regex(?ky,'maths','i')||regex(?ky,'matemáticas','i')||regex(?title,'maths','i')||regex(?title,'matemáticas','i')||regex(?ky,'logical','i')||regex(?ky,'lógico','i')).";
            else if(inteligencia_dominante==2||inteligencia_faltante==2)
                stringQuery = stringQuery +"filter(regex(?LearningObject,'Picture','i')||regex(?LearningObject,'Video','i')||regex(?LearningObject,'Swf','i')||regex(?ky,'map','i')||regex(?ky,'mapa','i')||contains(?title,'mapa conceptual')||contains(?title,'conceptual map')).";
            else if(inteligencia_dominante==3||inteligencia_faltante==3)
                stringQuery = stringQuery +"filter(regex(?ky,'Game','i')||regex(?LearningObject,'Swf','i')||regex(?LearningObject,'Webapp','i')||regex(?ky,'juego','i')||regex(?ky,'experimento','i')||regex(?ky,'experiment','i')).";
            else if(inteligencia_dominante==4||inteligencia_faltante==4)
                stringQuery = stringQuery +"filter(regex(?LearningObject,'Audio','i')||regex(?ky,'music','i')||regex(?ky,'Audio','i')||regex(?ky,'podcast','i')||regex(?title,'audio','i')||regex(?title,'music','i')).";
            else if(inteligencia_dominante==5||inteligencia_faltante==5)
                stringQuery = stringQuery +"filter(regex(?ky,'intrapersonal','i')||regex(?desc,'intrapersonal','i')||regex(?title,'intrapersonal','i')||regex(?ky,'reflesive','i')).";
            else if(inteligencia_dominante==6||inteligencia_faltante==6)
                stringQuery = stringQuery +"filter(regex(?ky,'interpersonal','i')||regex(?desc,'interpersonal','i')||regex(?title,'interpersonal','i')).";
            stringQuery = stringQuery +"}ORDER BY RAND() LIMIT 9";

            Query query = QueryFactory.create(stringQuery);
            QueryExecution qe = QueryExecutionFactory.create(query, model);

            ResultSet results = qe.execSelect();
            return results;
    }
    public void desconectar(){
         model.close();
    }
    public ResultSet BusquedaSimple(String keyaux,String [] keywords){
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
        return results;
    }
    public ResultSet GetRepositorio(){
        String stringQuery = "PREFIX smas: <"+smas+">"+ "SELECT ?url WHERE {"
                                + "?Repositorio smas:uri ?url.}";
            Query query = QueryFactory.create(stringQuery);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();
            return results;
    }
    public boolean SetRepositorio(String url){
         try{
            VirtGraph set = new VirtGraph (URL, uid, pwd);
          
            String str = "INSERT INTO GRAPH <"+bd+"> { <"+smas+"REP-"+url+"> <"+ns+"type> <"+smas+"Repository>."
                        + "<"+smas+"REP-"+url+"> <"+smas+"uri> '"+url+"'.}";
            VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
            vur.exec();
            
             
        }catch(Exception e){
             return false;   
        }
            return true;
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
}
