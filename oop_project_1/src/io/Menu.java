/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import user.User;
import user.UserManager;
import data.Inventory;
import data.Item;
import util.Util;


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
    UserManager userManager;
    User CurrentUser;
    int currentUser_index; // will be used to remember the position of the user in the user arraylist
    
    int menu_code;
    boolean running;
    Input in;
    
    public Menu(){
        this.userManager = new UserManager();
        this.CurrentUser = new User();
        this.menu_code = 0; // login screen
        this.running = true;
        in = new Input();
    }
    /////////
    public void run_menu(){
        while(this.running){
            switch(this.menu_code){
                case 0: LoginMenu();
                        break;
                case 1: MainUserMenu();
                        break;
                case 7: AdminMenu();
                        break;
            }
        }
    }
    // The logic will be handled here
    
    // login menu
    public void LoginMenu(){
        Util.clear();
        Util.println("----- Login -----");
        String uName = in.StringPrompt("User Name     ->");
        String uPass = in.StringPrompt("User Password ->");
        
        // now check if this user is registered in the system.
        //  If the user name shows up and the password is wrong:
        //  - notify user the password does not match
        //  - give option to forgot password
        //    - to change password enter in the email address attachted to the user.
        int pos = this.userManager.checkUsername(uPass);
        if(pos == -1){
            // user not found
            Util.println("The UserName entered was not found!");
            
            // prompt user if they want to create a new profile
            
        }else{
            // now check if the password was correct
            if(this.userManager.login(uPass, pos)){
                if(uName.equals("ADMIN"))
                    this.menu_code = 7;
                else
                    this.menu_code = 1; // menu switch
                
                return; // now were done here
            }
            else{
                in.keyStroakPrompt("Incorrect Password hit [ENTER] to try again.");
            }
        }
        
    }// end of login
    
    // admin menu
    public void AdminMenu(){ // 7
        
    }
    
    // normal user menu
    public void MainUserMenu(){ // 1
        
    }
    
    
    
}
