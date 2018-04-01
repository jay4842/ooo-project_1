/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import util.*;
import io.Input;

import java.io.FileReader;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.filechooser.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 * https://github.com/jay4842/ooo-project_1.git
 * @author Jimmy
 */
public class Inventory {
    
    // how should all the data be held?
    //  Split them up by type
    Input in = new Input();
    ArrayList<String> keys = new ArrayList<>();
    HashMap<String, ArrayList<Item>> database = new HashMap<String, ArrayList<Item>>();
    
    ArrayList<Item> Furniture = new ArrayList<>();
    ArrayList<Item> Kitchen = new ArrayList<>();
    ArrayList<Item> Decor = new ArrayList<>();
    ArrayList<Item> BedBath = new ArrayList<>();
    ArrayList<Item> HomeImprovement = new ArrayList<>();
    ArrayList<Item> Outdoor = new ArrayList<>();
    ArrayList<Item> Rug = new ArrayList<>();
    
    public Inventory(){
        database.put("Furniture", Furniture);
        keys.add("Furniture");
        
        database.put("Kitchen", Kitchen);
        keys.add("Kitchen");
        
        database.put("Decor", Decor);
        keys.add("Decor");
        
        database.put("Bed & Bath", BedBath);
        keys.add("Bed & Bath");
        
        database.put("Home Improvement", HomeImprovement);
        keys.add("Home Improvement");
        
        database.put("Outdoor",Outdoor);
        keys.add("Outdoor");
        
        database.put("Rug",Rug);
        keys.add("Rug");
        
        // done putting in all the boys in the hashtable
    }
    public void populate_data()throws Exception{
        
        // load the text file and populate our data based on that
    
        // a json will have an items object first
        //  ex:
        /*
            "Items" :[
		{// Cherry table set
			"QTY"   : 10,
			"ID"    : "F1000",
			"Price" : 215.00,
			"Type"  : "Furniture",
			"Name"  : "Cherry Brown Wooden Table 5 Piece Set",
			"Des"   : "A counter hight table with four chairs."
		}//
                ...
        */
        
       Object obj = new JSONParser().parse(new FileReader("src/res/items.json"));
        
        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;
       
        // getting list of items
        JSONArray ja = (JSONArray)jo.get("Items");
         
        // iterating Items
        Iterator itr = ja.iterator();
        
        // go through the array to get all the maps
        while(itr.hasNext()){
             JSONObject in = (JSONObject)itr.next();
             //(double p, String i, String t, String n, String d)
             Item temp = new Item((double)in.get("Price"),
                     Integer.parseInt(in.get("QTY").toString()),
                     in.get("ID").toString(),
                     in.get("Type").toString(),
                     in.get("Name").toString(),
                     in.get("Des").toString());
             Util.println(temp.toString());
             
             // now add by type
             String type = temp.get_type();
             database.get(type).add(temp);
             
        }
        in.keyStroakPrompt("->");
        
    }
    
    // Add a save function
    
    //
    public HashMap<String, ArrayList<Item>> get_database(){return this.database;}
    
    
    // helpers to assist the shop menu
    public int[] display_items_by_type(String type){
        // returns indicies of items at that key
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i = 0; i < this.database.get(type).size(); i++){
            temp.add(i);// add an index
            
            // display the item info (name and price)
            Util.println("["+(i+1) + "] " + this.database.get(type).get(i).get_item_name());
            Util.println("    " + Util.dollar_format(this.database.get(type).get(i).get_price()));
        }
        
        // assign the boys
        int[] out = new int[temp.size()];
        for(int i = 0; i < out.length; i++){
            out[i] = temp.get(i);
        }
        
        return out;
        
    }
    
    public ArrayList<Item> display_items_by_name(String name){
        name = name.toLowerCase();
        ArrayList<Item> temp = new ArrayList<>();
        for(int i = 0; i < this.database.size(); i++){
            for(int j = 0; j < this.database.get(keys.get(i)).size(); j++){
                if(this.database.get(keys.get(i)).get(j).get_item_name().toLowerCase().contains(name)){ // change things to lowercase to make finding things easier
                    temp.add(this.database.get(keys.get(i)).get(j));
                }
            }
        } // done checking to see if desired string is there
        // display the items found
        for(int i = 0; i < temp.size(); i++){
             Util.println("["+(i+1) + "] " + temp.get(i).get_item_name());
            Util.println("    " + Util.dollar_format(temp.get(i).get_price()));
        }
        
        return temp;
    }
    
    // show items by price, ex: items 10 and under
    public ArrayList<Item> display_items_by_price(double price){
         ArrayList<Item> temp = new ArrayList<>();
        for(int i = 0; i < this.database.size(); i++){
            for(int j = 0; j < this.database.get(keys.get(i)).size(); j++){
                if(this.database.get(keys.get(i)).get(j).get_price() <= price)
                    temp.add(this.database.get(keys.get(i)).get(j));
            }
        }
        // display
        for(int i = 0; i < temp.size(); i++){
             Util.println("["+(i+1) + "] " + temp.get(i).get_item_name());
            Util.println("    " + Util.dollar_format(temp.get(i).get_price()));
        }
        
        return temp;
    }// end
    
    // Find item by item_ID
    public int find_by_id(String id){
        // first use the firt leter of the id to identify the key
        //  each key starts with the first letter of the ID.
        String key = "";
        for(String k : keys){
            if(k.charAt(0) == id.charAt(0)){
                key = k;
                break;
            }
        }// if the key is still "" return -1
        if(key.equals("")) return -1;
        for(int j = 0; j < this.database.get(key).size(); j++){
            if(this.database.get(key).get(j).get_item_id().equals(id))
                return j;
        }
        
        
        return -1;
    }
    
    // reduce qty by finding item by id
    public void reduce_item(String id,int qty){
        // first use the firt leter of the id to identify the key
        //  each key starts with the first letter of the ID.
        String key = "";
        for(String k : keys){
            if(k.charAt(0) == id.charAt(0)){
                key = k;
                break;
            }
        }// if the key is still "" return -1
        if(key.equals("")){
            in.keyStroakPrompt("The item was not found [Enter]");// debugging only
            
        }else{
            for(int j = 0; j < this.database.get(key).size(); j++){
                // deduct
                if(this.database.get(key).get(j).get_item_id().equals(id)){
                    this.database.get(key).get(j).reduce_qty(qty);
                }
                // later will add the feature to purchase multiple qtys of an item
            }
        }//
        
        //done
    }

    // exception handling later
    
   
}
