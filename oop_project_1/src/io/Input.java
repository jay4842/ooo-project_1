/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;
import util.*;
import java.util.Scanner;
/**
 *
 * @author Jimmy
 * 
 * We have to keep using nextLine because of how the Scanner works.
 * If we do not use nextLine it will affect how input is taken.
 */
public class Input {
    Scanner in;
    public Input(){
        this.in = new Scanner(System.in);
    }
    
    // add exception handling later
    
    // get a tring from the user
    public String StringPrompt(String prompt){
        Util.println(prompt);
        String out = (in.nextLine()).replace("\n", "");
        
        return out;
    }
    
    // get an int from the user
    public int IntPrompt(String prompt){
        Util.println(prompt);
        int out = -1;
        try{
            out = Integer.parseInt((in.nextLine()).replace("\n", ""));
        }catch (NumberFormatException e){
            // a non number/integer string was entered
        }
        return out;
    }
    
    // get a double from the user
    public double DoublePrompt(String prompt){
        Util.println(prompt);
        double out = -1.0;
        try{
            out = Double.parseDouble((in.nextLine()).replace("\n", ""));
        }catch(NumberFormatException e){
            // a non number string was entered
        }
        return out;
    }
    
    // to get an enter key wait
    public void keyStroakPrompt(String prompt){
        Util.println(prompt);
        String hold = (in.nextLine()).replace("\n", "");
         
    }
    // more?
}
