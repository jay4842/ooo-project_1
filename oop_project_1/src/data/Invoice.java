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
    
    // will just hold items
    
    // invoces will be stored in a JSON as well
    public Invoice(){
        userName = "";
        InvoiceCode = "";
        items = new ArrayList<>();
        
    }
}
