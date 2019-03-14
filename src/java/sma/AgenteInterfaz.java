package sma;



import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.wrapper.gateway.GatewayAgent;
import sma.*;


public class AgenteInterfaz extends GatewayAgent{
    @Override
    protected void processCommand(java.lang.Object u){
        Object r = u;
        System.out.println(r.toString());
    
    }
    @Override
    protected void setup(){
        System.out.println("Hola"+getAID().getName()+"estoy listo");
       Object [] args = getArguments();
        System.out.println(args[0].toString());
    
    }
    
    @Override
    protected void takeDown() {
        System.out.println("Finaliza el "
                + this.getLocalName());
        System.out.println("---------");
       // this.doDelete();
    }
}
