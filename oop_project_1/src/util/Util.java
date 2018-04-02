/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;
import java.text.NumberFormat;

/**
 *
 * @author Jimmy
 * 
 * Will hold utility functions such as calculations, printing to the screen,
 * and some other functions as well.
 */
public class Util {
    private static final NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
    
    public static void println(String s){
        System.out.println(s);
    }
    
    public static void clear(){
        try{
            final String os = System.getProperty("os.name");
            if(os.contains("Windows")){ // to handle the differnt ways to clear the screen.
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else{
                System.out.print("\033[H\033[2J");  
                System.out.flush();  
            }
        }catch(Exception e){
            
        }
        
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
    
    public static String dollar_format(double d){
        return defaultFormat.format(d);
    }
        
}
