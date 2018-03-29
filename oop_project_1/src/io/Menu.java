/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import user.User;
import data.Inventory;
import data.Item;

/**
 *
 * @author Jimmy
 * 
 * This will handle the different menus that will be used by the system.
 * 
 * Login, New User
 * Main Menu
 * - Shopping menu
 * - User Profile Settings Menu
 * - Logout
 * 
 * Admin menu
 * - Search User
 * - Look at history
 * - See sales
 *   - See sales by Type
 *   - See sales by data
 * - Logout
 * 
 * For now this will be a text based system.
 */
public class Menu {
    User CurrentUser;
    int menu_code;
    boolean running;
    
    public Menu(){
        this.CurrentUser = new User();
        this.menu_code = 0; // login screen
        this.running = true;
    }
    /////////
    public void run_menu(){
        while(this.running){
            switch(this.menu_code){
                
            }
        }
    }
    // The logic will be handled here
    
    // login menu
    public void LoginMenu(){
        
    }
    
    
}
