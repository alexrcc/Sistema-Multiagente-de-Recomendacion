/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


public class Acentos {
   public static String eliminarAcentos(String ky){
       String cadena =ky;
       cadena = cadena.replace('á','a');
       cadena = cadena.replace('é','e');
       cadena = cadena.replace('í','i');
       cadena = cadena.replace('ó','o');
       cadena = cadena.replace('ú','u');
        cadena = cadena.replace('Á','A');
       cadena = cadena.replace('É','E');
       cadena = cadena.replace('Í','I');
       cadena = cadena.replace('Ó','O');
       cadena = cadena.replace('Ú','U');
       return cadena;
   } 
}
