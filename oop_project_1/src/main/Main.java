/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import data.Inventory;
import io.Menu;

/**
 *
 * @author Jimmy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public Inventory inv;
    public Menu menu;
    
    public Main(){
        inv = new Inventory();
        menu = new Menu();
         try{
            inv.populate_data();
        }catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    
    
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
    
        // testing the classes before adding the UI component
        Main sys = new Main();
        
        // implement runnable? maybe later
        
        ////////////////////////////////
        sys.menu.run_menu();
    }
    
}
