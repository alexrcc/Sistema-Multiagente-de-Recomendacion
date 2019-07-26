/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Virtuoso;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

/**
 *
 * @author alexr
 */
@WebServlet(name = "ObtenerEstadisticas", urlPatterns = {"/ObtenerEstadisticas"})
public class ObtenerEstadisticas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int estilos [] = {0,0,0,0};
        int inteligencias [] = {0,0,0,0,0,0,0};
        int porcent[] = new int[4];
        int porcentim [] = new int[7];
        boolean bande=false, bandi = false;
        PrintWriter out = response.getWriter();
        response.setContentType( "text/html; charset=iso-8859-1" );
        Virtuoso dbv = new Virtuoso();
        dbv.conectar("Perfiles");
        ResultSet results=dbv.GetStaditicsEstilos();
        while(results.hasNext()){
            bande=true;
           QuerySolution qs = results.next();
           estilos[0]=estilos[0]+qs.getLiteral("visual").getInt();
           estilos[1]=estilos[1]+qs.getLiteral("aural").getInt();
           estilos[3]=estilos[2]+qs.getLiteral("kinesthetic").getInt();
           estilos[2]=estilos[3]+qs.getLiteral("readwrite").getInt();
           
       } 
        results=dbv.GetStaditicsInteligencias();
        while(results.hasNext()){
           QuerySolution qs = results.next();
           inteligencias[0]=inteligencias[0]+qs.getLiteral("verbal").getInt();
           inteligencias[1]=inteligencias[1]+qs.getLiteral("logical").getInt();
           inteligencias[2]=inteligencias[2]+qs.getLiteral("visual").getInt();
           inteligencias[3]=inteligencias[3]+qs.getLiteral("kinesthetic").getInt();
           inteligencias[4]=inteligencias[4]+qs.getLiteral("musical").getInt();
           inteligencias[5]=inteligencias[5]+qs.getLiteral("intrapersonal").getInt();
           inteligencias[6]=inteligencias[6]+qs.getLiteral("interpersonal").getInt();
           bandi=true;
       } 
        if(bande){
            int sumae = estilos[0]+estilos[1]+estilos[2]+estilos[3];
             double aux []= new double[4];
            for(int i =0;i<estilos.length;i++){
                aux[i]=(double)(estilos[i]*100)/sumae;
                porcent[i]=(int)(Math.round(aux[i]));
            }
        }
        if(bandi){
            int sumai = 0;
            for (int inteligencia : inteligencias) {
                sumai=sumai+inteligencia;
            }

           double auxim []= new double[7];
           for(int i =0;i<inteligencias.length;i++){
               auxim[i]=(double)(inteligencias[i]*100)/sumai;
               porcentim[i]=(int)(Math.round(auxim[i]));
           }
        }
        
        out.print("<div style='width:100%; height:150px; overflow-y:scroll;overflow-x: hidden;'><span><strong>Estilos de Aprendizaje</strong></span><br>");
        out.print("<span>Visual: </span>"+porcent[0]+"%<br>");
        out.print("<span>Auditivo: </span>"+porcent[1]+"%<br>");
        out.print("<span>Lectura-Escritura: </span>"+porcent[2]+"%<br>");
        out.print("<span>Kinestésico: </span>"+porcent[3]+"%<br><br>");
        
        out.print("<span><strong>Inteligencias Múltiples</strong></span><br>");
        out.print("<span>Verbal: </span>"+porcentim[0]+"%<br>");
        out.print("<span>Lógica: </span>"+porcentim[1]+"%<br>");
        out.print("<span>Visual: </span>"+porcentim[2]+"%<br>");
        out.print("<span>Kinestésica: </span>"+porcentim[3]+"%<br>");
        out.print("<span>Musical: </span>"+porcentim[4]+"%<br>");
        out.print("<span>Intrapersonal: </span>"+porcentim[5]+"%<br>");
        out.print("<span>interpersonal: </span>"+porcentim[6]+"%<br></div>");
    }

}
