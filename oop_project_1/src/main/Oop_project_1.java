/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import data.Inventory;

/**
 *
 * @author Jimmy
 */
public class Oop_project_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    
        // testing the classes before adding the UI component
        Inventory inv = new Inventory();
        try{
            inv.populate_data();
        }catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
        
    }
    
}
