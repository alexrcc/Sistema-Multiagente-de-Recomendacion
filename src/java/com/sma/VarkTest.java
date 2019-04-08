/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sma;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alexr
 */
@WebServlet(name = "VarkTest", urlPatterns = {"/VarkTest"})
public class VarkTest extends HttpServlet {
    private String resp_vark [][] = new String[16][4];
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        for (int i = 1; i <=16; i++) {
            System.out.println("p"+i);
            String checkboxValues  []= request.getParameterValues("p"+i);
           // System.out.println("dddddddddddd"+checkboxValues[0]);
            if(checkboxValues != null){
                for (int j = 0;  j<checkboxValues.length; j++) {
                    resp_vark[i-1][j]=checkboxValues[j];
                   // System.out.println(checkboxValues[j]);
                }
            }
        }
        
        for (int i = 0; i <16; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(resp_vark[i][j]+"-");
            }
            System.out.println("\n");
        }
    }

}
