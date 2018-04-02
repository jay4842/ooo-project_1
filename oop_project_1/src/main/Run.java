/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import io.Menu;

/**
 *https://github.com/jay4842/ooo-project_1.git
 * @author Jimmy
 */
public class Run {

    /**
     * @param args the command line arguments
     */
    public Menu menu;
    
    public Run(){
        menu = new Menu();
    }
    
    
    
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
    
        // testing the classes before adding the UI component
        Run sys = new Run();
        
        // implement runnable? maybe later
        
        ////////////////////////////////
        sys.menu.run_menu();
    }
    
}
