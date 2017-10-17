/**
*Generic BankMenu class for a Bank System written for project 2
 in CS212
*@author Claudia Bandali
*/

import java.lang.*;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class BankMenu{

  //Methods

  /**
  *The landing Menu function. The first menu seen by the user after
  *a bank is declaired in the main method.
  *This method returns void. The bank argument is a specifier that
  *must specify an absolute bank name.
  *<p>
  *This method features 6 options for the user to choose from.
  *For each option a different menu is called. When the option
  *exit is chosen, the method confirms the users choice and
  *the program terminates. Every menu that is called from the
  *landingMenu calls the landing menu before it end the method.
  *
  *@param bank an absolute bank giving the base location for
  *storing Bank Accounts
  */
  public static void landingMenu( Bank bank ){
    Scanner in = new Scanner(System.in);

    System.out.println("Bank Menu");
    System.out.println();

    System.out.println("1.Log In");
    System.out.println("2.Sign Up");
    System.out.println("3.Terminate Account");
    System.out.println("4.Save Bank account list to a file");
    System.out.println("5.Load Bank account list from a file");
    System.out.println("6.Exit");

    int optionChosen = in.nextInt();

    switch( optionChosen ){
      case 1:
        BankMenu.loginMenu( bank );
        break;
      case 2:
        BankMenu.signUpMenu( bank );
        break;
      case 3:
        BankMenu.terminateAccountMenu( bank );
        break;
      case 4: /*save bank account list to a file*/
        try{
          BankMenu.saveToFileMenu( bank );
          throw new IOException();
        }catch(IOException e){
          System.out.println("IOException caught " + e.getMessage());
        }
        break;
      case 5: /*Load bank account list from a file*/
        try{
          BankMenu.loadFromFileMenu( bank );
          throw new IOException();
        }catch(IOException e){
          System.out.println("IOException caught " + e.getMessage());
        }
        break;
      case 6:
        System.out.println("Are you sure that you would like to exit");
        System.out.println("1.Yes\n2.No");
        int option = in.nextInt();
        switch(option){
          case 1: System.exit(0);
            break;
          case 2: BankMenu.landingMenu(bank);
            break;
          default: System.out.println("An error has occured");
            break;
        }
        break;
      default:
        System.out.println("An error has occured");
        break;
    }

  }

  /**
  *The loginMenu is called from the landing menu based on user input.
  *This method returns void. The bank argument must specify an absolute
  *bank object.
  *<p>
  *This method prompts the user for a userName and pin. It immediately
  *validates that the pin is a valid input, hashes the pin and checks
  *if the bank account exists based on the username and pin. If the
  *account exists, the user is redirected to the bank's Mainmenu.
  *If it does not exist. The user is redirected back to the landing
  *menu.
  *@param bank an absolute bank giving the base location for
  *storing Bank Accounts
  */
  public static void loginMenu( Bank bank ){
    Scanner in = new Scanner(System.in);

    String username, pin;
    BankAccount account;

    System.out.println("Please enter your username and pin below");
    System.out.print("Username:");
    username = in.nextLine();
    System.out.print("Pin:");
    pin = in.nextLine();

    while( !BankAccount.isValidPIN( pin ) ){
      System.out.println("Invalid pin input");
      System.out.println("Please enter a pin that contains 4 digits: ");
      pin = in.nextLine();
    }

    pin = BankAccount.hashOf( pin, bank);
    System.out.println();
    System.out.println("I am now processing your request...");

    if( bank.accountExists(username, pin) ){
        System.out.println("Valid input accepted.\nTransfering to main menu...");
        System.out.println();

        account = bank.getAccount(username, pin);

        BankMenu.mainMenu( account, bank );
    }

    System.out.println("Account not found. redirecting you to the Bank Menu..");
    BankMenu.landingMenu( bank );

  }

  /**
  *The signUpMenu is called from the landingMenu based on user input.
  *This method returns void. The bank argument specifies an absolute
  *bank to create a bank account it.
  *<p>
  *This method asks the user to input a first name, last name, address,
  *username, and pin. It Then checks if the user used a the valid
  *characters for each input and checks if the account exists. If the
  *account does not exist is creates and registers the new Bank Account.
  *
  *@param bank an absolute bank giving the base location for
  *storing Bank Accounts
  */
  public static void signUpMenu( Bank bank ){
    Scanner in = new Scanner(System.in);

    String firstname, lastname, address, username, pin;
    boolean exists;

    System.out.println("Please Enter the following information");

    System.out.println("First Name: ");
    firstname = in.nextLine();
    System.out.println("Last Name: ");
    lastname = in.nextLine();

    boolean isValid = BankAccount.isValidName( firstname, lastname );
    while( !isValid ){
      System.out.println("You have input a character that is not part of\n"+
                         "a name. Please only enter letters, dashes and\n"+
                         "spaces");
      System.out.println();
      System.out.println("Please enter a valid First Name:");
      firstname= in.nextLine();
      System.out.println("Please enter a valid Last Name: ");
      lastname = in.nextLine();
      isValid = BankAccount.isValidName( firstname, lastname );
    }

    System.out.println("Address: ");
    address = in.nextLine();

    System.out.println("Username: ");
    username = in.nextLine();

    isValid = BankAccount.isValidUsername( username );

    while( !isValid ){
      System.out.println("Please enter a valid username with digits, letters,");
      System.out.println("-, and _ : ");
      username = in.nextLine();
      isValid = BankAccount.isValidUsername( username );
    }

    System.out.println("PIN:");
    pin = in.nextLine();

    isValid = BankAccount.isValidPIN( pin );
    while( !isValid ){
      System.out.println("Please enter a pin that contains 4 digits: ");
      pin = in.nextLine();
      isValid = BankAccount.isValidUsername( pin );
    }

    pin = BankAccount.hashOf( pin, bank);
    System.out.println("Your pin is valid");



    //Loop if account exists
    exists = bank.accountExists(username, pin);

    while ( exists ) {
      System.out.println("This account is already registered");
      System.out.println("Would you like to try again?");
      System.out.println("1.YES\n2.NO");

      int again = in.nextInt();

      switch ( again ) {
        case 1:
          System.out.println("First Name: ");
          firstname = in.nextLine();
          System.out.println("Last Name: ");
          lastname = in.nextLine();

          boolean valid = BankAccount.isValidName( firstname, lastname );
          while( !valid ){
            System.out.println("You have input a character that is not part of\n"+
                               "a name. Please only enter letters, dashes and\n"+
                               "spaces");
            System.out.println();
            System.out.println("Please enter a valid First Name:");
            firstname= in.nextLine();
            System.out.println("Please enter a valid Last Name: ");
            lastname = in.nextLine();
            valid = BankAccount.isValidName( firstname, lastname );
          }

          System.out.println("Address: ");
          address = in.nextLine();

          System.out.println("Username: ");
          username = in.nextLine();

          valid = BankAccount.isValidUsername( username );

          while( !valid ){
            System.out.println("Please enter a valid username with digits, letters,");
            System.out.println("-, and _ : ");
            username = in.nextLine();
            valid = BankAccount.isValidUsername( username );
          }

          System.out.println("PIN:");
          pin = in.nextLine();

          valid = BankAccount.isValidPIN( pin );
          while( !valid ){
            System.out.println("Please enter a pin that contains 4 digits: ");
            pin = in.nextLine();
            valid = BankAccount.isValidUsername( pin );
          }

          pin = BankAccount.hashOf( pin, bank);
          System.out.println("Your pin is valid");
          exists = bank.accountExists(username, pin);
          break;
        case 2:
          BankMenu.landingMenu( bank );
          break;
        default:
          System.out.println("System Error");
          break;
      }

    }

    System.out.println("We are now registering your account....");

    //Setting up bank account
    BankAccount ba = new BankAccount( firstname, lastname );
    ba.setFirstName( firstname );
    ba.setLastName( lastname );
    ba.setAddress( address );
    ba.setUserName( username );
    ba.setPIN( pin );

    //Register account
    boolean reg = bank.registerAccount( ba, bank );

    if( reg ){
      System.out.println("Your account has been succesfully registered ");
      System.out.println();
      System.out.println("Your account information is:");
      System.out.println(BankAccount.toString( ba ));
      System.out.println();
      BankMenu.landingMenu( bank );
    }else if( !reg ){
      System.out.println("Account could not be registered ");
      System.out.println();
      BankMenu.landingMenu( bank );
    }

  }

  /**
  *The terminateAccountMenu method is called from the landingMenu
  *and terminates an existing account. The bank argument specidies
  *an absolute bank object.
  *<p>
  *This method always returns void. If promps the user for a
  *username and pin to validate that the account exists. If
  *the account exists. The method prompts the user one last
  *time to confirm that their choice to terminate the account
  *is absolute. If the user confirms, the account is unregistered
  *from the bank.
  *
  *@param bank an absolute bank giving the base location for
  *storing Bank Accounts.
  */
  public static void terminateAccountMenu( Bank bank ){
    Scanner in = new Scanner(System.in);

    System.out.println("Please enter your username: ");
    String username = in.nextLine();
    BankAccount.isValidUsername( username );

    System.out.println("Please enter a pin : ");
    String pin = in.nextLine();
    BankAccount.isValidPIN( pin );
    pin = BankAccount.hashOf( pin, bank );

    boolean valid = bank.accountExists( username, pin );
    while( !valid ){
      System.out.println("This account does not exist");
      System.out.println("Would you like to try again?");
      System.out.println("1.YES\n2.NO");
      int again = in.nextInt();
      switch( again ){
        case 1:
          System.out.println("Please enter a username : ");
          username = in.nextLine();
          BankAccount.isValidUsername( username );

          System.out.println("Please enter a pin : ");
          pin = in.nextLine();
          BankAccount.isValidPIN( pin );
          pin = BankAccount.hashOf( pin, bank );

          valid = bank.accountExists( username, pin );
          break;

        case 2:
          System.out.println("Redirecting you to the bank menu...");
          System.out.println();
          BankMenu.landingMenu( bank );
          break;

        default:
          System.out.println("I didnt get that");
          System.out.println("Redirecting you to the bank menu...");
          System.out.println();
          BankMenu.landingMenu( bank );
          break;
      }

    }

    boolean term = bank.terminateAccount( username, pin, bank );
    if( term ){
      System.out.println("Your account has been terminated");
      BankMenu.landingMenu( bank );
    }else if( !term ){
      System.out.println("We have not terminated your account.");
      System.out.println("Redirecting you to the bank menu...");
      System.out.println();
      BankMenu.landingMenu( bank );
    }

  }

  /**
  *The saveToFileMenu method saves the existing bank records to
  *a file. This method returns void. The bank argument specifies
  *the bank from which the Bank Accounts would be saved.
  *<p>
  *This method prompts the user for a file name. It then saves the file,
  *checks that the file exists and creates a PrintWriter for the file.
  *The writeToFile bank method is then called and the user is made aware
  *that the records have been successfully saved. The user is then
  *redirected to the landingMenu.
  *
  *@param bank an absolute bank giving the base location for
  *finding Bank Accounts
  */
  public static void saveToFileMenu( Bank bank ){
    Scanner in = new Scanner(System.in);

    System.out.println("Please enter the file name that you");
    System.out.println("would like to save your bank records to:");
    String fileName = in.next();
    File f = new File( fileName );

    boolean usable = f.exists();
    System.out.println("File exists: " + usable);

    PrintWriter out = null;
    try{
      if( usable ){
        out = new PrintWriter( f );
      }
    }catch(IOException e){
      System.out.println("Caught IOException " + e.getMessage());
      BankMenu.landingMenu( bank );
    }

    bank.writeToFile( out );

    System.out.println("Your bank records have been saved to " + fileName);
    out.checkError(); //flushes stream and checks error state.
    BankMenu.landingMenu( bank );

  }

  /**
  *The loadFromFileMenu method prompts the user for a file name
  *creates the file and confirms that is exists. If then Loads
  *the accounts from the file through the function call readFromFile
  *in the bank class
  *<p>
  *@param bank an absolute bank giving the base location for
  *storing Bank Accounts
  */
  public static void loadFromFileMenu( Bank bank ){
    Scanner in = new Scanner(System.in);

    System.out.println("Please enter the name of the file that");
    System.out.println("You would like to load your bank records");
    System.out.println("From:");
    String fileName = in.next();

    File f = new File( fileName );
    boolean usable;

    usable = f.exists();

    System.out.println("File exists: " + usable);

    ArrayList<BankAccount> load = new ArrayList<>();
    Scanner inF = null;
    try{
      if( usable ){
        inF = new Scanner( f );
      }
      load = Bank.readFromFile( inF, bank );
    }catch(IOException e){
      System.out.println("Caught IOException " + e.getMessage());
      BankMenu.landingMenu( bank );
    }



    //check that Bank Accounts were loaded from file
    System.out.println();
    System.out.println("The bank accounts that have been read from the");
    System.out.println("file are:");
    System.out.println();
    for( int i = 0; i < load.size(); i++ ){
      System.out.println( BankAccount.toString(load.get(i)) );
    }

    System.out.println("closing file..");
    inF.close();

    System.out.println("Transfering you back to the main menu..");
    BankMenu.landingMenu( bank );
  }

  /**
  *The mainMenu function is the function that is called after
  *a user has signed into their bank account. The user can
  *manage their account from this method.
  *
  *@param account is the account that is currently logged into.
  *@param bank is the bank that the user is using
  */
  public static void mainMenu( BankAccount account, Bank bank ){
    Scanner in = new Scanner(System.in);

    System.out.println("What action would you like to perform " +
                        account.getFirstName() + ":" );
    System.out.println("1.Deposit Money\n2.Withdraw Money");
    System.out.println("3.Account Details\n4.Log Out");

    int option = in.nextInt();

    switch( option ){
      case 1:
        BankMenu.depositMenu( account, bank );
        break;
      case 2:
        BankMenu.withdrawMenu( account, bank );
        break;
      case 3:
        System.out.println("Transfering you to your account details");
        BankMenu.accountMenu( account, bank );
        break;
      case 4:
        System.out.println();
        System.out.println("Logging you out...");
        System.out.println();
        BankMenu.landingMenu( bank );
        break;
      default:
        System.out.println("An error has occured");
        System.out.println();
        BankMenu.landingMenu( bank );
        break;
    }

  }

  /**
  *This method deposits money into the users account. It prompts the
  *user for the amount of money to deposit and calculates the total
  *account balance.
  *
  *@param account is the account that the user is logged into
  *@param bank is the bank that the user is using
  */
  public static void depositMenu( BankAccount account, Bank bank ){
    Scanner in = new Scanner(System.in);

    System.out.println("How much money would you like to deposit today?");
    int money = in.nextInt();

    while( money < 0){
      System.out.println("This is not a valid input. Please input a");
      System.out.println("positive number: ");
      money = in.nextInt();
    }

    account.addBalance( money );
    System.out.println("Your new balance is: " + account.getBalance() );
    System.out.println();

    BankMenu.mainMenu( account, bank );

  }

  /**
  *The withdrawMenu is a function that is used to subtract a given
  *amount of money from the users account balance. The user inputs
  *how much money they would like to withdraw.
  *
  *@param account the bank account that is logged into
  *@param bank the bank that the user is using
  */
  public static void withdrawMenu( BankAccount account, Bank bank ){
    Scanner in = new Scanner(System.in);

    System.out.println("How much would you like to withdraw from");
    System.out.println("your account today?");
    int money = in.nextInt();

    while( money < 0 ){
      System.out.println("You cannot withdraw a negative amount of money");
      System.out.println("Please enter a positive number: ");
      money = in.nextInt();
    }

    while (money > account.getBalance()){
      System.out.println("You only have $" + account.getBalance() );
      System.out.println("present in your account. Enter a number less than");
      System.out.println("your account balance:");
      money = in.nextInt();
    }

    System.out.println("We are processing your request");

    account.subtractBalance( money );
    System.out.println("You have withdrawn " + money);
    System.out.println("You have " + account.getBalance() +" left in your account");
    System.out.println();

    BankMenu.mainMenu( account, bank );

  }

  /**
  *The accountManu method is where the user can change their
  *account information such as name, address, username and pin
  *Every input is validated and changed.
  *
  *@param account the account that is currently logged into
  *@param bank the bank that the user is using
  */
  public static void accountMenu( BankAccount account, Bank bank ){
    Scanner in = new Scanner(System.in);
    System.out.println("Account Details");
    System.out.println();
    System.out.println("1.Change Fist Name\n2.Change Last Name");
    System.out.println("3.Change Address\n4.Change Username");
    System.out.println("5.Change PIN\n6.Exit");
    int option = in.nextInt();

    switch( option ){
      case 1: System.out.println("Redirecting you to change your first name");
        BankMenu.changeFirstNameMenu( account, bank );
        break;
      case 2: System.out.println("Redirecting you to change your last name");
        BankMenu.changeLastNameMenu( account, bank );
        break;
      case 3: System.out.println("Redirecting you to update your address");
        BankMenu.changeAddressMenu( account, bank );
        break;
      case 4: System.out.println("Redirecting you to change your username");
        BankMenu.changeUsernameMenu( account, bank );
        break;
      case 5: System.out.println("Keep your account protected by changing your");
        System.out.println("PIN every 6 to 8 months");
        BankMenu.changePINMenu( account, bank );
        break;
      case 6: System.out.println("Exiting...");
        System.out.println();
        BankMenu.mainMenu( account, bank );
        break;
      default: System.out.println("An error has occured");
        System.out.println();
        BankMenu.mainMenu( account, bank );
        break;

    }

  }

  /**
  *The changeFirstNameMenu is a mathod called from the accountMenu
  *it changes the first name in the current bank account
  *
  *@param account the account that is currently logged into
  *@param bank the bank that the user is using
  */
  public static void changeFirstNameMenu( BankAccount account, Bank bank ){
    Scanner in = new Scanner(System.in);

    boolean correctPIN = BankMenu.promptPIN( account, bank );
    while( !correctPIN ){
      System.out.println("Your pin was invalid");
      correctPIN = BankMenu.promptPIN( account, bank );
    }

    System.out.println("What would you like to change your");
    System.out.println("first name to?");
    String fn = in.nextLine();
    if( !BankAccount.isValidName( fn, account.getLastName() ) ){
      System.out.println("Please try again.");
      System.out.println("Only use letters dashes and spaces");
      fn = in.nextLine();
    }else{
      account.setFirstName( fn );
      System.out.println("We have reset your first name to " +
                         account.getFirstName());
      System.out.println();
    }

    BankMenu.accountMenu( account, bank );

  }

  /**
  *The changeLastNameMenu is a mathod called from the accountMenu
  *it changes the last name in the current bank account
  *
  *@param account the account that is currently logged into
  *@param bank the bank that the user is using
  */
  public static void changeLastNameMenu( BankAccount account, Bank bank ){
    Scanner in = new Scanner(System.in);

    boolean correctPIN = BankMenu.promptPIN( account, bank );
    while( !correctPIN ){
      System.out.println("Your pin was invalid");
      correctPIN = BankMenu.promptPIN( account, bank );
    }

    System.out.println("What would you like to change your");
    System.out.println("last name to?");
    String ln = in.nextLine();
    if( !BankAccount.isValidName( account.getFirstName(), ln ) ){
      System.out.println("Please try again.");
      System.out.println("Only use letters dashes and spaces");
      ln = in.nextLine();
    }else{
      account.setLastName( ln );
      System.out.println("We have reset your last name to " +
                         account.getLastName());
      System.out.println();
    }

    BankMenu.accountMenu( account, bank );

  }

  /**
  *The changeAddressMenu is a mathod called from the accountMenu
  *it changes the address in the current bank account
  *
  *@param account the account that is currently logged into
  *@param bank the bank that the user is using
  */
  public static void changeAddressMenu( BankAccount account, Bank bank ){
    Scanner in = new Scanner(System.in);

    boolean correctPIN = BankMenu.promptPIN( account, bank );
    while( !correctPIN ){
      System.out.println("Your pin was invalid");
      correctPIN = BankMenu.promptPIN( account, bank );
    }

    System.out.println("What would you like to change your");
    System.out.println("address to?");
    String address = in.nextLine();
    account.setAddress( address );
    System.out.println("We have reset your address to " +
                       account.getAddress());
    System.out.println();

    BankMenu.accountMenu( account, bank );

  }

  /**
  *The changeUsernameMenu is a mathod called from the accountMenu
  *it changes the username in the current bank account
  *
  *@param account the account that is currently logged into
  *@param bank the bank that the user is using
  */
  public static void changeUsernameMenu( BankAccount account, Bank bank ){
    Scanner in = new Scanner(System.in);

    boolean correctPIN = BankMenu.promptPIN( account, bank );
    while( !correctPIN ){
      System.out.println("Your pin was invalid");
      correctPIN = BankMenu.promptPIN( account, bank );
    }

    System.out.println("What would you like to change your");
    System.out.println("Username to?");
    String un = in.nextLine();
    if( !BankAccount.isValidUsername( un ) ){
      System.out.println("Please try again.");
      System.out.println("Only use digits, _ , - , and letters");
      un = in.nextLine();
    }else{
      account.setUserName( un );
      System.out.println("We have reset your Username to " +
                         account.getUserName());
      System.out.println();
    }

    BankMenu.accountMenu( account, bank );

  }

  /**
  *The changePINMenu is a mathod called from the accountMenu
  *it changes the pin in the current bank account
  *
  *@param account the account that is currently logged into
  *@param bank the bank that the user is using
  */
  public static void changePINMenu( BankAccount account, Bank bank ){
    Scanner in = new Scanner(System.in);

    boolean correctPIN = BankMenu.promptPIN( account, bank );
    while( !correctPIN ){
      System.out.println("Your pin was invalid");
      correctPIN = BankMenu.promptPIN( account, bank );
    }

    System.out.println("What would you like to change your");
    System.out.println("pin to?");
    String pin = in.nextLine();

    if( !BankAccount.isValidPIN( pin ) ){
      System.out.println("Please try again.");
      System.out.println("Your pin must be of length four and only digits");
      pin = in.nextLine();
    }
    else{
      pin = BankAccount.hashOf( pin, bank);
      account.setPIN( pin );
      System.out.println("We have reset your pin to " +
                         account.getHashOfPIN());
      System.out.println();
    }

    BankMenu.accountMenu( account, bank );

  }

  /**
  *This method prompt the user for their pin, creates a hash of their
  *pin and returns a boolean as to weather the pinExists or not.
  *
  *@param account the account that is currently logged into
  *@param bank the bank that the user is using
  */
  public static boolean promptPIN( BankAccount account, Bank bank ){

    Scanner in = new Scanner(System.in);

    System.out.println("Please enter your pin: ");
    String pin = in.nextLine();

    pin = BankAccount.hashOf( pin, bank);

    System.out.println("Validating your pin...");
    if( bank.pinExists( pin ) ){
      return true;
    }
    return false;

  }

  /**
  *The main method welcomes the user to the Banking experience
  *offered
  */

  public static void main(String[] args){
    Scanner in = new Scanner(System.in);

    System.out.println();
    System.out.println("Welcome to the Ultimate Banking Experience");
    System.out.println("This system offeres many conveniences such as:");
    System.out.println("Creating a new account, Terminating an existing account,");
    System.out.println("Depositing Money, Withdrawing Money, Managing Your account(s)");
    System.out.println("And so much more. No matter what your banking needs,");
    System.out.println("we are here to serve you.");
    System.out.println();
    System.out.println("What bank would you like to work with today?");
    System.out.print("Bank: ");

    String bName = in.nextLine();

    Bank bank = new Bank( bName );

    System.out.println("Processing your request...");

    BankMenu.landingMenu( bank );

  }

}
