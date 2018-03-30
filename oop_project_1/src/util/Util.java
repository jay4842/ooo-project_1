/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Jimmy
 * 
 * Will hold utility functions such as calculations, printing to the screen,
 * and some other functions as well.
 */
public class Util {
    public static void println(String s){
        System.out.println(s);
    }
    
    public static void clear(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
    public final static void clearConsole(){
        try{
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else{
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e){
            //  Handle any exceptions.
            e.printStackTrace();
        }
    }
    
    
}
