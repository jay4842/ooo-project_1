/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import util.*;


import java.io.FileReader;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Hashtable;

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
    Hashtable database = new Hashtable();
    
    ArrayList<Item> Furniture = new ArrayList<>();
    ArrayList<Item> Kitchen = new ArrayList<>();
    ArrayList<Item> Decor = new ArrayList<>();
    ArrayList<Item> BedBath = new ArrayList<>();
    ArrayList<Item> HomeImprovement = new ArrayList<>();
    ArrayList<Item> Outdoor = new ArrayList<>();
    
    public Inventory(){
        database.put("Furniture", Furniture);
        database.put("Kitchen", Kitchen);
        database.put("Decor", Decor);
        database.put("Bed & Bath", BedBath);
        database.put("Home Improvement", HomeImprovement);
        database.put("Outdoor",Outdoor);
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
			"ID"    : 1000,
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
       
        // getting phoneNumbers
        JSONArray ja = (JSONArray)jo.get("Items");
         
        // iterating phoneNumbers
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
             
        }
    }
}
