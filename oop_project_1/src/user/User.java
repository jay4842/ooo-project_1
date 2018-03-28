/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.util.ArrayList;
import data.Item;

/**
 *
 * @author Jay
 * Used to store user info only.
 * 
 */
public class User {
    ArrayList<Item> history;
    String UserName;
    String UserPass; // might add an encryption of some type for now no.
    
    public User(){
        this.UserName = "";
        this.UserPass = "";
        history = new ArrayList<>();
    }
    
    public User(String Name, String Pass){
        this.UserName = Name;
        this.UserPass = Pass;
        history = new ArrayList<>();
    }
    
    public String get_userName(){return this.UserName;}
    public String get_userPass(){return this.UserPass;}
    public ArrayList<Item> get_history(){return this.history;}
    
    public void set_userName(String s){this.UserName = s;}
    public void set_userPass(String s){this.UserPass = s;}
    public void add_item(Item i){this.history.add(i);} // we never want to completly delete/overrite
    
    // exception handling later
    
    
    
}
