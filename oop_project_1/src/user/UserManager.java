/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import data.Item;
import java.io.FileNotFoundException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Util;
import io.Input;

/**
 *
 * @author Jay
 */
public class UserManager {
   ArrayList<User> all_users; // just using a list of users for now.
   Input in;
   public UserManager(){
       in = new Input();
       all_users = new ArrayList<>();
       try {
           // load user json file
           loadUsers();
       } catch (Exception ex) {
           Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       in.keyStroakPrompt("->");
       
   }
   
   // open the users.json file to populate the all_users arraylist
   public final void loadUsers() throws Exception{
        Object obj = new JSONParser().parse(new FileReader("src/res/users.json"));
        
        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;
       
        // getting list of items
        JSONArray ja = (JSONArray)jo.get("Users");
        
        // iterating Items
        Iterator itr = ja.iterator();
        
        // go through the array to get all the maps
        while(itr.hasNext()){
             JSONObject in = (JSONObject)itr.next();
             JSONArray hist,cart;
             
             User temp = new User();
             
             // dont forget to go through the history
             try{ // the history might be empty!
                hist = (JSONArray)jo.get("History");
                for(int i = 0; i < hist.size(); i++){
                    String s = hist.get(i).toString();
                    temp.add_history(s);
                }//hist end
                
                
             }catch(Exception e){
                 // if we get here, there was a null value for history
             }
             // They need to be seperate to ensure they both try to run
             try{
                 cart = (JSONArray)jo.get("Cart");
                 // cart start
                for(int i = 0; i < cart.size(); i++){
                    String s = cart.get(i).toString();
                    temp.add_cart(s);
                }
             }catch(Exception e){
                 // if we get here, there was a null value for cart
             }
             try{ // needs to be on its own for now
                 temp.setMember((boolean)jo.get("Member"));
             }catch(Exception e){
                 // if we get here, there was a null value for cart
             }
             
             //(double p, String i, String t, String n, String d)
             
             temp.set_userName((String)in.get("UserName"));
             temp.set_userPass((String)in.get("UserPass"));
             temp.set_userEmail((String)in.get("Email"));
             
             all_users.add(temp);
             Util.println(temp.toString());
        }
   }
   
   // write the all_users info to the users.json file
   public void saveUsers(){
       JSONObject users = new JSONObject();
       JSONArray list = new JSONArray();
       // go through all the users and add them to the list
       for(int i = 0; i < all_users.size(); i++){
           JSONObject obj = new JSONObject();
           JSONArray hist = new JSONArray();
           JSONArray cart = new JSONArray();
           obj.put("UserName", all_users.get(i).get_userName());
           obj.put("UserPass", all_users.get(i).get_userPass());
           obj.put("Email",all_users.get(i).get_userEmail());
           obj.put("Member", all_users.get(i).isMember());
           for(int x = 0; x < all_users.get(i).get_history().size(); x++){
               hist.add(all_users.get(i).get_history().get(x));
           }// end of for
           obj.put("History", hist);
           
            for(int x = 0; x < all_users.get(i).getcart().size(); x++){
               cart.add(all_users.get(i).getcart().get(x));
           }// end of for
           obj.put("Cart", cart);
           
           list.add(obj);
           
       }// end of for
       users.put("Users", list);
       // now write the file!
       
       try (FileWriter file = new FileWriter("src/res/users.json")) {

            file.write(users.toJSONString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
   }// end
   
   // Other Helpers here ->
   public ArrayList<User> get_allUsers(){return this.all_users;}
   public void addUser(User u){this.all_users.add(u);} // checks will be made else where
   
   // return the position of the user for future user
   public int checkUsername(String s){
       for(int i = 0; i < this.all_users.size(); i++){
          
           if(s.equals(all_users.get(i).get_userName()))
               return i;
       }
       return -1; // if user not found
   }
   // the check for password
   public boolean login(String s, int pos){
       
       if(s.equals(this.all_users.get(pos).get_userPass()))
           return true;
       else
           return false;
   }// done with that
   
}
