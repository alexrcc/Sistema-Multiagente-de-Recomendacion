package controller;

import jade.core.AID;

public class Mensaje implements java.io.Serializable{
    private String mensaje = new String("");
    private Object obj;
    private Object respuesta;
    private String user;
    private AID remitente;
    public void setMensaje (String mensaje){
        this.mensaje = mensaje;
    }
    public String getMensaje (){
        return mensaje;
    }
    public void setArgumentos(java.lang.Object obj){
        this.obj = obj;
    }
    public Object getArgumentos(){
        return obj;
    }
     public void setRespuesta(java.lang.Object resp){
        this.respuesta = resp;
    }
    public Object getRespuesta(){
        return respuesta;
    }
    public void setUsuario(String user){
        this.user = user;
    }
    public String getUsuario(){
        return user;
    }
    public void setRemitente(AID r){
        this.remitente=r;
    }
    public AID getRemitente(){
        return remitente;
    }
    
}
