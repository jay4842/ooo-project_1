/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Doina Alexandrina
 */
import java.util.Random;
import java.util.UUID;


public class Pass_Reset {
    
    public String Alpha_Numeric_Symbolic(int lenght) {
        
        char[] ch = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', '@','#','$','%','^','&','*','~'};
        
        char[] c = new char[lenght]; 
        Random random = new Random();
        
        for (int i = 0; i<lenght; i++){
            c[i] = ch[random.nextInt(ch.length)];
        }
        
        UUID uuid=UUID.randomUUID();
        String str=uuid.toString().replace("-", "");
        
        return new String(c)+str;
    }
    
    //
    public static String generate_string(int length){
        Random rand = new Random();
        String out = "";
        for(int i = 0; i < length; i++){
            if(rand.nextInt(100) > 50)
                out += (rand.nextInt(10) + ""); // add a number
            else{
                int temp = (rand.nextInt(25));
                out += (char)(temp + 'a'); // add a letter
            }
        }
        
        return out;
    }
}
