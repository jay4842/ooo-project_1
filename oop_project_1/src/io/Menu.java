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

import java.util.ArrayList;

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
 * 
 * The functions are ordered by menu code, they also have their code 
 * along with the definition.
 * 
 * 
 */
public class Menu {
    UserManager userManager;
    User CurrentUser;
    int currentUser_index; // will be used to remember the position of the 
                           // user in the user arraylist
    
    int menu_code;
    boolean running;
    Input in;
    Inventory inv;
    
    public Menu(){
        this.userManager = new UserManager();
        this.CurrentUser = new User();
        this.menu_code = 0; // login screen
        this.running = true;
        this.in = new Input();
        this.inv = new Inventory();
        
        try{
            inv.populate_data(); // try populating our inventory data
        }catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
        
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
    
    // TODO:: ADD A HELP COMMAND OPTION TO PROVIDE HELP AT ANYTIME.
    // The logic will be handled here
    
    // main menu
    public void MainMenu() throws InterruptedException{ // 0
        Util.clear();
        Util.println("----- Main -----");
        Util.println("1) Login ");
        Util.println("2) New User ");
        Util.println("3) Exit ");
        
        int choice = in.IntPrompt("-> ");
        if(choice == 1){
            this.menu_code = 1; // take to login screen 
            return;
        }
        //
        if(choice == 2){
            this.menu_code = 2; // take to the new user setup
            return;
        }
        //
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
            in.keyStroakPrompt("The UserName entered was not found!\n[Enter]");
            
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
   
     // for adding new user information
    
    // need to add a way to cancel the creation of a new user
    public void NewUserMenu(){ // 2
        // get desired user name
        // - check if available
        Util.clear();
        boolean done = false;
        String uPass = "";
        // end of variable set up
        
        Util.println("----- New User -----");
        String uName = in.StringPrompt("Desired userName -> ");
        if(this.userManager.checkUsername(uName) != -1){
            // the user name is already taken
            in.keyStroakPrompt("That user name is already taken.\n[Enter]");
        }else{   
            // set email (Add checking later)
            String uEmail = in.StringPrompt("Enter email address linked to account -> ");
            // set password
            do{
                uPass = in.StringPrompt("Create a password  -> ");
                // - enter in password twice to verify
                String verify = in.StringPrompt("Re-enter password -> ");
                if(uPass.equals(verify))
                    done = true;
                else
                    in.keyStroakPrompt("The passwords did not match, please try again\n[Enter]");
            }while(!done);
            // and user to all_users
            this.userManager.addUser(new User(uName,uPass,uEmail));
            this.userManager.saveUsers(); // update our JSON file too
            // 
            // keep this user as the current user too
            this.currentUser_index = this.userManager.checkUsername(uName);
            
            // take the user to the main shop menu
            in.keyStroakPrompt("Welcome " + uName + "\n[Enter]");
            this.menu_code = 3;
        }
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
        switch(choice){
            case 1: ShopMenu();
                    break;
            case 2: CheckoutMenu();
                    break;
            case 3: UserSettingsMenu();
                    break;
            case 4:{
                    Util.clear();
                    this.menu_code = 0;
                    Util.println("Goodbye " + this.CurrentUser.get_userName());
                    this.currentUser_index = -1;
                    break;
            }
                    
        }
    }// end of that
    
    // forgot password
    public void ForgotPasswordMenu(){ // 4
        // users will enter in their email and if the email mathces their 
        //  account email they will be emailed a code.
        // They will then enter in the code to change thier password.
        // - enter it in twice for verification
        
        // then keep user logged in as current user
    }
    
    // shop
    public void ShopMenu(){ // 5
        // shop will have a few things..
        // - Search for items
        //   - by name
        //   - by catagory
        //   - by price
        int shop_code = 0; // we will use some sub menus here
        Util.clear();
        Util.println("---------- Shop ----------");
        Util.println("1) Search by item type");
        Util.println("2) Search by name");
        Util.println("3) Search by price");
        Util.println("4) Checkout < cart " + this.CurrentUser.getcart().size() + " >");
        Util.println("5) Return ");
        
        int choice = in.IntPrompt("-> ");
        switch(choice){
            case 1: type_search();
                    break;
            case 2: name_search();
                    break;
            case 3: price_search();
                    break;
            case 4: CheckoutMenu();
                    break;
            case 5: this.menu_code = 3;
                    break;
        }
        // shopping will allow users to view a list of items.
        // they will then be able add items to their cart.
        // once they are done they will go to the checkout menu.
    }
    
    // shop sub menus
    private void type_search(){
        boolean searching = true;
        boolean done = false;
        
        int item_choice = -1;
        do{ // incase they want to add multiple items to the cart, or view multiple items
            int [] item_idxs = new int[0]; // empty
            Util.clear();
            Util.println("---------- Search by Type ----------");
            Util.println("Enter a type of item.\nExample: -> Rug");
            String type = in.StringPrompt(" -> ");
            type = type.toLowerCase(); // convert to lower case for simplicity 
            
            do{
                Util.clear();
                // have to use if statemet ;~;
                if(type.equals("return") || type.equals("exit") || type.equals("end")){
                    //done = true;
                    return;
                    
                }
                else{ // 
                    if(type.equals("rug") || type.equals("mat") || type.equals("Rug")){ // rug check
                        // show rugs
                        type = "Rug";
                        item_idxs = this.inv.display_items_by_type(type);

                    }
                    else if(type.equals("furniture") || type.equals("Furniture")){ // not many synonyms for this 
                        // show
                        type = "Furniture";
                        item_idxs = this.inv.display_items_by_type(type);

                    }
                    // decor
                    else if(type.equals("decor") || type.equals("ornimation") || type.equals("decoration") || type.equals("Decor")){
                        type = "Decor";
                        item_idxs = this.inv.display_items_by_type(type);
                    }
                    //kitchen
                    else if(type.equals("kitchen") || type.equals("cooking") || type.equals("Kitchen")){
                        type = "Kitchen";
                        item_idxs = this.inv.display_items_by_type(type);
                    }
                    //bed and bath
                    else if(type.equals("bed") || type.equals("bath") 
                            || type.equals("bath and bath") || type.equals("bed & bath")
                             || type.equals("bed&bath") || type.equals("Bed & Bath")){
                        type = "Bed & Bath";
                        item_idxs = this.inv.display_items_by_type(type);
                    }
                    // home improvement
                    else if(type.equals("tools") || type.equals("home improvement") || type.equals("Home Improvement")){
                        type = "Home Improvement";
                        item_idxs = this.inv.display_items_by_type(type);

                    }
                    //Outdoor
                    else if(type.equals("outdoors") || type.equals("outside") || type.equals("outdoor") || type.equals("Outdoor")){ 
                        // show
                        type = "Outdoor";
                        item_idxs = this.inv.display_items_by_type(type);


                    }
                    // end of display setup
                    if(item_idxs.length > 0){
                        String check = in.StringPrompt("Select an item -> ");
                        if(check.equals("return") || check.equals("exit") || check.equals("end"))
                             done = true;
                        else{
                             Util.clear();
                            item_choice = Integer.parseInt(check);
                            item_choice -= 1;
                            try{
                                 // display item in depth
                                Util.println("---------------------------------------");
                                Util.println(this.inv.get_database().get(type).get(item_choice).get_item_name());
                                Util.println("" + Util.dollar_format(this.inv.get_database().get(type).get(item_choice).get_price()));
                                Util.println(this.inv.get_database().get(type).get(item_choice).get_item_desc());
                                Util.println("\n---------------------------------------");
                                Util.println("1) add to cart"); // see if they want to get the item
                                Util.println("2) look at another item ");
                                Util.println("3) search again ");
                                int sub_choice = in.IntPrompt("-> ");

                                switch(sub_choice){
                                    case 1: // add the item
                                        this.CurrentUser.add_cart(this.inv.get_database().get(type).get(item_choice).get_item_id());
                                        break;
                                    case 2: // return
                                        break;
                                    case 3: // look at a different item
                                        done = true;
                                        break;
                                }
                            }catch(Exception e){
                                // error occured
                                //e.printStackTrace(); // uncomment to produce in depth error
                                in.keyStroakPrompt(type + " [" + item_choice + "] There was a problem processing your choice. [Enter]");
                            }
                        }
                    }else{
                        in.keyStroakPrompt("There was nothing found ;~;\n[Enter]");
                    
                    }// this looks a little messy
                    
                }
                // take in input
            }while(!done);

        }while(searching);
    }
    private void name_search(){
        // addded a helper in the iventory to help this search
        boolean searching = true;
        int item_choice = -1;
        boolean done = false;
        do{ // incase they want to add multiple items to the cart, or view multiple items
            int [] item_idxs = new int[0]; // empty
            Util.clear();
            Util.println("---------- Search by Name ----------");
            Util.println("Enter a keyword.\nExample: -> Table");
            
            String key = in.StringPrompt(" -> ");
            if(key.equals("return") || key.equals("exit") || key.equals("end")){
                searching = false;
                return;
            }
            do{
                Util.clear();
                ArrayList<Item> items_found = this.inv.display_items_by_name(key);

                if(items_found.size() > 0){
                    String check = in.StringPrompt("Select an item -> ");
                    if(check.equals("return") || check.equals("exit") || check.equals("end"))
                        done = true;
                    else{
                        // select an item
                        item_choice = Integer.parseInt(check);
                        item_choice -= 1;
                        try{
                             // display item in depth
                            Util.println("---------------------------------------");
                            Util.println(items_found.get(item_choice).get_item_name());
                            Util.println("" + Util.dollar_format(items_found.get(item_choice).get_price()));
                            Util.println(items_found.get(item_choice).get_item_desc());
                            Util.println("\n---------------------------------------");
                            Util.println("1) add to cart "); // see if they want to get the item
                            Util.println("2) look at another item ");
                            Util.println("3) search again ");
                            int sub_choice = in.IntPrompt("-> ");

                            switch(sub_choice){
                                case 1: // add the item
                                    this.CurrentUser.add_cart(items_found.get(item_choice).get_item_id());
                                    break;
                                case 2: 
                                    break;
                                case 3: // return
                                    done = true;
                                    break;
                            }
                        }catch(Exception e){
                            // error occured
                            //e.printStackTrace(); // uncomment to produce in depth error
                            in.keyStroakPrompt(key + " [" + item_choice + "] There was a problem processing your choice. [Enter]");
                        }
                    }
                }
            }while(!done);
        }while(searching);
    }
    private void price_search(){
        boolean searching = true;
        int item_choice = -1;
        boolean done = false;
        do{ // incase they want to add multiple items to the cart, or view multiple items
            int [] item_idxs = new int[0]; // empty
            Util.clear();
            Util.println("---------- Search by Price ----------");
            Util.println("Enter a price.\nExample: -> 10.00");
            String price = in.StringPrompt("-> ");
            
            if(price.equals("return") || price.equals("exit") || price.equals("end")){
                searching = false;
                return;
            }
            
            if(price.charAt(0) == '$'){
                price = price.substring(1); // cut it off
            }
            do{
                double d_price = Double.parseDouble(price);
                Util.clear();
                ArrayList<Item> items_found = this.inv.display_items_by_price(d_price);

                 if(items_found.size() > 0){
                    // select an item
                    Util.clear();
                    String check = in.StringPrompt("Select an item -> ");
                    if(check.equals("return") || check.equals("exit") || check.equals("end"))
                        done = true;
                    else{
                        // select an item
                        item_choice = Integer.parseInt(check);
                        item_choice -= 1;
                        try{
                            // display item in depth
                            Util.println("---------------------------------------");
                            Util.println(items_found.get(item_choice).get_item_name());
                            Util.println("" + Util.dollar_format(items_found.get(item_choice).get_price()));
                            Util.println(items_found.get(item_choice).get_item_desc());
                            Util.println("\n---------------------------------------");
                            Util.println("1) add to cart"); // see if they want to get the item
                            Util.println("2) look at another item ");
                            Util.println("3) search again ");
                            int sub_choice = in.IntPrompt("-> ");

                            switch(sub_choice){
                                case 1: // add the item
                                    this.CurrentUser.add_cart(items_found.get(item_choice).get_item_id());
                                    break;
                                case 2: 
                                    break;
                                case 3: // return
                                    done = true;
                                    break;
                            }
                        }catch(Exception e){
                            // error occured
                            //e.printStackTrace(); // uncomment to produce in depth error
                            in.keyStroakPrompt(price + " [" + item_choice + "] There was a problem processing your choice. [Enter]");
                        }
                    }//
                }
            }while(!done);
             
        }while(searching);
    } 
    // end of shop sub menus // 
    
    // checkout
    public void CheckoutMenu(){ // 6
        // will ask user to check out
        // - enter in payment info
        // - verify they want the items
        // processes cart
        // - remove items qty
        // show purchase complete
        // return to shop menu
        
    }
    
    // settings
    public void UserSettingsMenu(){ // 7
        // upgrade to UCLUB member
        // view history.
        // cancel orders(maybe)
        // change password
        // change username
        // delete account
        // - ADMIN cannot be deleted
    }
    
        // admin menu
    public void AdminMenu(){ // 8
        // View user historys
        // - purchase historys
        // - activity
        // View payment stubs
        // view item invintory
        
    }
    
    
}
