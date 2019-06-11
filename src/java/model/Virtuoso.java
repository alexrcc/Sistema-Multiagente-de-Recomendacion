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
    private static final String URL = "jdbc:virtuoso://localhost:1111";
    private static final String uid = "dba";
    private static final String pwd = "dba";
    VirtModel model=null;
    public void conectar(String bd){
         model = VirtModel.openDatabaseModel("Perfiles", URL, uid, pwd);
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
    public void desconectar(){
         model.close();
    }
}
