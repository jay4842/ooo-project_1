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
    ArrayList<String> history; // what the user has purchased
    ArrayList<String> cart;    // what the user plans to purchase but has not yet.
    
    String UserName;
    String UserPass; // might add an encryption of some type for now no.
    String email;
    boolean uClub = false;
    
    public User(){
        this.UserName = "";
        this.UserPass = "";
        this.email = "";
        history = new ArrayList<>();
        cart = new ArrayList<>();
    }
    
    public User(String Name, String Pass){
        this.UserName = Name;
        this.UserPass = Pass;
        this.email = "";
        history = new ArrayList<>();
        cart = new ArrayList<>();
    }
    
    public User(String Name, String Pass, String Email){
        this.UserName = Name;
        this.UserPass = Pass;
        this.email = Email;
        history = new ArrayList<>();
        cart = new ArrayList<>();
    }
    
    public boolean isMember(){return this.uClub;}
    public String get_userName(){return this.UserName;}
    public String get_userPass(){return this.UserPass;}
    public String get_userEmail(){return this.email;}
    public ArrayList<String> get_history(){return this.history;}
    public ArrayList<String> getcart(){return this.cart;}
    
    public void setMember(boolean b){this.uClub = b;}
    public void set_userName(String s){this.UserName = s;}
    public void set_userPass(String s){this.UserPass = s;}
    public void set_userEmail(String s){this.email = s;}
    public void add_history(String i){this.history.add(i);} // we never want to completly delete/overrite
    public void add_history(ArrayList<String> cart){
        for(int i = 0; i < cart.size(); i++){
            this.history.add(cart.get(i));
        }
    }//
    public void add_cart(String i){this.cart.add(i);}
    
    @Override
    public String toString(){
        return "UserName : " + this.UserName + " "+ this.UserPass +  " Email : " + this.email;
    }
    // exception handling later
    
    
    
}
