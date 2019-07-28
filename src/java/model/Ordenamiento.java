package model;

import java.util.ArrayList;


public class Ordenamiento {
    public static ArrayList<String[]> Ordenar(ArrayList<String[]> datos, String ky){
        ArrayList<String[]> ordenado = new ArrayList<>(datos.size());
        for (int i = 0; i < datos.size(); i++) {
            String [] aux = datos.get(i);
            String [] aux2 ;
            if((aux[1].toLowerCase()).contains(ky.toLowerCase())){
                ordenado.add(0,aux);
            }else{
                ordenado.add(aux);
            }
        }
    return ordenado;
    }
}
