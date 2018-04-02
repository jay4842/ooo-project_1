/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import user.User;
import user.UserManager;
import data.Inventory;
import data.Invoice;
import data.InvoiceManager;
import data.Item;
import util.Util;
import data.Pass_Reset;

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
    InvoiceManager invoiceManager;
    
    public Menu(){
        this.userManager = new UserManager();
        this.CurrentUser = new User();
        this.invoiceManager = new InvoiceManager();
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
        Util.println("------- Main -------");
        Util.println("1) Login ");
        Util.println("2) New User ");
        Util.println("3) Forgot password");
        Util.println("4) Exit ");
        
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
        if(choice == 3){
            this.menu_code = 4; // take to the forgot password section 
            return;
        }
        //
        if(choice == 4){
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
                this.menu_code = 0;
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
                in.keyStroakPrompt("Incorrect Password [ENTER]");
                this.menu_code = 0;
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
        if(this.CurrentUser.get_userName().equals("ADMIN")){
            this.menu_code = 8;
        }else{
            Util.println("----- User Menu -----");
            Util.println("1) Shop ");
            Util.println("2) Checkout < cart " + this.CurrentUser.getcart().size() + " >");
            Util.println("3) User Settings ");
            Util.println("4) Logout ");

            int choice = in.IntPrompt("-> "); 
            switch(choice){
                case 1: ShopMenu();
                        break;

                case 2: this.menu_code = 6;
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
        }
       
    }// end of that
    
    // forgot password
    public void ForgotPasswordMenu(){ // 4
        // users will enter in their email and if the email mathces their
        Util.clear();
        String UserName = in.StringPrompt("Enter your User Name\n->");
        int user_index = userManager.checkUsername(UserName);
        if( user_index != -1){
            String in_email = in.StringPrompt("\nEnter the email linked to your account\n->");
            //  account email they will be emailed a code.
            if(userManager.get_allUsers().get(user_index).get_userEmail().equals(in_email)){
                // They will then enter in the code to change thier password.
                // - enter it in twice for verification
                EmailBot bot = new EmailBot();
                // generate random code
                String code = Pass_Reset.Alpha_Numeric_Symbolic(7);
                // send the code
                String [] send_to = new String []{userManager.get_allUsers().get(user_index).get_userEmail()};
                bot.sendFromGMail(send_to, "UberStock Password Recovery Code", code);
                Util.println("A randomly generated code was sent to your email.");
                
                String user_code = in.StringPrompt("Enter the code -> ");
                if(code.toLowerCase().equals(user_code.toLowerCase())){
                    // reset password
                    boolean done = false;
                    String uPass = "";
                    do{
                        Util.clear();
                        uPass = in.StringPrompt("Create a password  -> ");
                        // - enter in password twice to verify
                        String verify = in.StringPrompt("Re-enter password -> ");
                        if(uPass.equals(verify))
                            done = true;
                        else
                            in.keyStroakPrompt("The passwords did not match, please try again\n[Enter]");
                    }while(!done);
                    
                    userManager.get_allUsers().get(user_index).set_userPass(uPass);
                    userManager.saveUsers(); // and update the JSON!
                }
                // then keep user logged in as current user
                this.CurrentUser = userManager.get_allUsers().get(user_index);
                this.menu_code = 3; // take to the main user menu now
            }
            
        }else{
            in.keyStroakPrompt("That username was not found [Enter]");
        }
           
        
        
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
            case 4: this.menu_code = 6;
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
        // display cart items
        ArrayList<Item> temp = new ArrayList<>();
        double total = 0.0;
        Util.clear();
        Util.println("------------ Checkout ------------");
        for(String id : this.CurrentUser.getcart()){
            Item item = inv.return_by_id(id);
            temp.add(item);
            Util.println(item.get_item_name());
            Util.println(Util.dollar_format(item.get_price()));
            // add up the total
            total += item.get_price();
            
        }
        // add tax to the total 
        double tax = (total * .0825);
        total += tax;
        // If they are UCLUB they get the 5% cashback after they purchase the item(s)..
        //  This cashback will be given
        if(this.CurrentUser.isMember()){
            // do the 5% cashback here
        Util.println("UCLUB 5% - " + Util.dollar_format(total));
        }
        // add the UCLUB stuff here once its implemented //
        Util.println("tax      - " + Util.dollar_format(tax));
        Util.println("Total    - " + Util.dollar_format(total));
        Util.println("----------------------------------");
        Util.println("1) Continue to checkout");
        Util.println("2) Return to menu ");
        int choice = in.IntPrompt("-> ");
        // will ask user to check out
        if(choice == 2){
            this.menu_code = 3;
        }
        else if(choice == 1){
            Util.clear();
            // - enter in payment info
            String card_num  = in.StringPrompt("Enter in your card number -> ");
            String card_name = in.StringPrompt("Enter full name on card   ->");
            
            
            // processes cart
            for(String id : this.CurrentUser.getcart()){
                this.inv.reduce_item(id, 1); // qty is going to be 1 for now
                this.CurrentUser.add_history(id); // make sure we remember what users purchase
                // - remove items qty
            }
            // now make an invoice and add it to the invoice manager
            Invoice invoice = new Invoice();
            invoice.set_Amount(choice);
            invoice.set_User(this.CurrentUser.get_userName());
            invoice.set_InvoiceCode("" + this.invoiceManager.get_invoices().size());
            this.invoiceManager.addInvoice(invoice);
            // show purchase complete
            Util.clear();
            in.keyStroakPrompt("Order Placed!\n[Enter]");
            
            this.CurrentUser.getcart().clear(); // clear the users cart
            // return to shop menu
            this.menu_code = 3;
        }
        
    }
    
    // settings
    public void UserSettingsMenu(){ // 7
        // upgrade to UCLUB member
        Util.clear();
        Util.println("------ User Settings ------");
        Util.println("1) UCLUB Membership");
        Util.println("2) Order History");
        Util.println("3) Return ");
        int input = in.IntPrompt("->");
        switch(input){
            case 1: uclubSettings();
                    break;
            case 2: viewHistory();
                    break;
            case 3: this.menu_code = 3;
                    break;
        }
        // view history.
        // change password
        // - ADMIN cannot be deleted
    }
    private void uclubSettings(){
        Util.clear();
        Util.println("------ UCLUB ------");
        if(this.CurrentUser.isMember()){
            Util.println("1) Cancel Membership");
        }else{
            Util.println("1) Signup");
        }
        Util.println("2) return");
        
        // input
        int input = in.IntPrompt("-> ");
        Util.clear();
        if(input == 1){
            if(this.CurrentUser.isMember()){
                Util.println("------- UCLUB -------");
                String yn = in.StringPrompt("Are you sure you want to cancel your membership?\n (y/n) -> ");
                if(yn.toLowerCase().equals("y") || yn.toLowerCase().equals("yes")){
                    Util.clear();
                    this.CurrentUser.setMember(false);
                    this.userManager.saveUsers();
                    in.keyStroakPrompt("[Enter]");
                    
                }//
            }else{
                Util.println("------- UCLUB -------");
                Util.println("Total - 19.95");
                
                String card_num  = in.StringPrompt("Enter in your card number -> ");
                String card_name = in.StringPrompt("Enter full name on card   ->");
                this.CurrentUser.setMember(true);
                this.userManager.saveUsers();
                in.keyStroakPrompt("[Enter]");
                
            }//
        }
    }
    
    // view the history of the user
    private void viewHistory(){
        Util.clear();
        Util.println("---------- History -----------");
        for(String id : this.CurrentUser.get_history()){
            Item temp = this.inv.return_by_id(id);
            Util.println(temp.get_item_name());
            Util.println(Util.dollar_format(temp.get_price()));
            Util.println("------------------------------");
        }
        in.keyStroakPrompt("[Enter]");
    }
    
        // admin menu
    public void AdminMenu(){ // 8
        Util.clear();
        Util.println("-------- ADMIN --------");
        // View user historys
        // - search user
        // - view all users (except ADMIN)
        // View payment stubs
        // - show sums at top 
        // - stubs after it
        // view item invintory
        
    }
    
    private void viewUserHistory(){
        
    }
    
    private void viewInventory(){
        
    }
    
    private void viewInvoices(){
        
    }
    
    public void HelpMenu(){ // not a normal menu
        // To get here a user can type help/assistance or other words similar to help.
        
        // based on the current menu state, help lines will appear to guide a user.
        switch(this.menu_code){
                case 0: Util.println("Main Menu Help");
                        break;
                case 1: Util.println("Login Menu Help");
                        break;
                case 2: Util.println("New User Menu Help");
                        break;
                case 3: Util.println("Main User Menu Help");
                        break;
                case 4: Util.println("Forgot Password Help");
                        break;
                case 5: Util.println("Shop Menu Help");
                        break;
                case 6: Util.println("Checkout Menu Help");
                        break;
                case 7: Util.println("User Settings Menu Help");
                        break;
                case 8: Util.println("ADMIN Menu Help");
                        break;
            }
        
    }
    
    
    
    
}
