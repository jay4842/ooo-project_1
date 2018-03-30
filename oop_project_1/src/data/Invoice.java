/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import data.Item;
import java.util.ArrayList;

/**
 *
 * @author Jay
 * Purpose: Keep track of a purchase.
 * 
 */
public class Invoice {
    
    String userName;
    String InvoiceCode;
    ArrayList<Item> items;
    Double amount;
    
    // will just hold items
    
    // invoces will be stored in a JSON as well
    public Invoice(){
        this.userName = "";
        this.InvoiceCode = "";
        this.items = new ArrayList<>();
        this.amount = 0.0;
        
    }
    
    public void Invoice(String u, String c, ArrayList<Item> i, double a){
        this.userName = u;
        this.InvoiceCode = c;
        this.items = i;
        this.amount = a;
    }
    
    // some helpers
    public String get_User(){return this.userName;}
    public String get_InvoiceCode(){return this.InvoiceCode;}
    public ArrayList<Item> get_Items(){return this.items;}
    public double get_Amount(){return this.amount;}
    
    public void set_User(String s){this.userName = s;}
    public void set_InvoiceCode(String s){this.InvoiceCode = s;}
    public void add_item(Item i){this.items.add(i);}
    public void set_Amount(double d){this.amount = d;}
    
    // more?
    
}
