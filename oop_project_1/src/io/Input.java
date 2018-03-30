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
        String out = in.nextLine();
        
        return out;
    }
    
    // get an int from the user
    public int IntPrompt(String prompt){
        Util.println(prompt);
        int out = in.nextInt();
        
        return out;
    }
    
    // get a double from the user
    public double DoublePrompt(String prompt){
        Util.println(prompt);
        double out = in.nextDouble();
        
        return out;
    }
    
    // to get an enter key wait
    public void keyStroakPrompt(String prompt){
         Util.println(prompt);
         String hold = in.nextLine();
    }
    // more?
}
