/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Jimmy
 * 
 */
public class Item {
    double price;     // 0000.00
    int qty;          // 10
    String item_id;   // F0000
    String type;      // ex: Furniture
    String item_name; // ex: Cherry Brown Wooden Table 5 Piece Set
    String item_desc; // ex: A counter hight table with four chairs.
    
    public Item(){
        this.qty = 0;
        this.price = 0.0;
        this.item_id = "";
        this.type = "";
        this.item_name = "";
        this.item_desc = "";
    }
    
    // param setup
    public Item(double p,int q, String i, String t, String n, String d){
        this.price = p;
        this.qty = q;
        this.item_id = i;
        this.type = t;
        this.item_name = n;
        this.item_desc = d;
    }
    
    // the getters
    public int get_qty(){return this.qty;}
    public double get_price(){return this.price;}
    public String get_item_id(){return this.item_id;}
    public String get_type(){return this.type;}
    public String get_item_name(){return this.item_name;}
    public String get_item_desc(){return this.item_desc;}
    
    // setters
    public void reduce_qty(int q){
        if(this.qty - q >= 0){
            this.qty -= q;
        }
    }//
    
    // for outputting
    @Override
    public String toString(){
        return this.qty + " " + this.type + " " + this.item_name + " " + this.price;
    }
    
    // exception handling later
}

