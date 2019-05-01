package controller;

public class Mensaje implements java.io.Serializable{
    private String mensaje = new String("");
    private Object obj;
    private Object respuesta;
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
    
}
