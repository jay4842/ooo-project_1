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
    public void run_menu() throws InterruptedException{
        while(this.running){
            Thread.sleep(100); // wait for user input
            switch(this.menu_code){
                case 0: MainMenu();
                        break;
                case 1: LoginMenu();
                        break;
                case 2: NewUserMenu();
                        break;
                case 3: MainUserMenu();
                        break;
                case 4: ForgotPasswordMenu();
                        break;
                case 5: ShopMenu();
                        break;
                case 6: CheckoutMenu();
                        break;
                case 7: UserSettingsMenu();
                        break;
                case 8: AdminMenu();
                        break;
            }
        }
    }
    // The logic will be handled here
    
    // main menu
    public void MainMenu() throws InterruptedException{
        Util.clear();
        Util.println("----- Main -----");
        Util.println("1) Login ");
        Util.println("2) New User ");
        Util.println("3) Exit ");
        
        int choice = in.IntPrompt("-> ");
        if(choice == 1){
            this.menu_code = 1;
            return;
        }
        if(choice == 3){
            // EXIT program
            Util.clear();
            this.running = false;
            Util.println("Goodbye");
        }
    }
    
    // login menu
    public void LoginMenu() throws InterruptedException{ // 1
        Util.clear();
        String uName = in.StringPrompt("User Name     ->");
        String uPass = in.StringPrompt("User Password ->");
        
        // now check if this user is registered in the system.
        //  If the user name shows up and the password is wrong:
        //  - notify user the password does not match
        //  - give option to forgot password
        //    - to change password enter in the email address attachted to the user.
        int pos = this.userManager.checkUsername(uName);
        if(pos == -1){
            // user not found
            Util.println("The UserName entered was not found!");
            
            // prompt user if they want to create a new profile
            
        }else{
            // now check if the password was correct
            if(this.userManager.login(uPass, pos)){
                this.currentUser_index = pos;
                this.CurrentUser = this.userManager.get_allUsers().get(pos);
                
                if(uName.equals("ADMIN"))
                    this.menu_code = 8;
                else
                    this.menu_code = 3; // menu switch
                
                return; // now were done here
            }
            else{
                in.keyStroakPrompt("Incorrect Password hit [ENTER] to try again.");
            }
        }
        
    }// end of login
    
    // admin menu
    public void AdminMenu(){ // 8
        
    }
    
    // normal user menu
    // - Search
    // - Checkout
    // - settings
    // - Logout
    public void MainUserMenu(){ // 3
        Util.clear();
        Util.println("----- User Menu -----");
        Util.println("1) Shop ");
        Util.println("2) Checkout < cart " + this.CurrentUser.getcart().size() + " >");
        Util.println("3) User Settings ");
        Util.println("4) Logout ");
        
        int choice = in.IntPrompt("-> "); 
        
        if(choice == 4){
            // return to main menu
            Util.clear();
            this.menu_code = 0;
            Util.println("Goodbye " + this.CurrentUser.get_userName());
            this.currentUser_index = -1;
            return;
            
        }
    }// end of that
    
    
    // for adding new user information
    public void NewUserMenu(){
        // get desired user name
        // - check if available
        // set password
        // - enter in password twice to verify
        // and user to all_users
        // 
        // keep this user as the current user too
    }
    
    // shop
    public void ShopMenu(){
        // shop will have a few things..
        // - Search for items
        //   - by name
        //   - by catagory
        //   - by price
        
        // shopping will allow users to view a list of items.
        // they will then be able add items to their cart.
        // once they are done they will go to the checkout menu.
    }
    
    
    // forgot password
    public void ForgotPasswordMenu(){
        // users will enter in their email and if the email mathces their 
        //  account email they will be emailed a code.
        // They will then enter in the code to change thier password.
        // - enter it in twice for verification
        
        // then keep user logged in as current user
    }
    
    // settings
    public void UserSettingsMenu(){
        // upgrade to UCLUB member
        // view history.
        // cancel orders(maybe)
        // change password
        // change username
        // delete account
        // - ADMIN cannot be deleted
    }
    
    // checkout
    public void CheckoutMenu(){
        // will ask user to check out
        // - enter in payment info
        // - verify they want the items
        // processes cart
        // - remove items qty
        // show purchase complete
        // return to shop menu
        
    }
    
    
    
}
