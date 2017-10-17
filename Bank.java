/**
*Generic Bank class for a Bank System written for project 2
 in CS212
*@author Claudia Bandali
*/

import java.lang.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;

class Bank{
  //Scanner
  Scanner in = new Scanner(System.in);

  //Fields
  private ArrayList<BankAccount> accounts = new ArrayList<>();
  private String name;

  //Constructor
  public Bank(String name){

    this.name = name;

  }
  //Copy Constructor
  public Bank(Bank b){
    accounts = b.accounts;
    name = b.name;
  }

  //Methods

  /**
  *Writes bank accounts to printwriter
  @param out a PrintWriter that prints to a file
  */
  public void writeToFile( PrintWriter out ){

    if( out.equals(null) ){
      return;
    }

    for( BankAccount account : accounts ){
      account.writeToFile( out );
    }

    out.checkError(); //flushes stream and checks error state.

  }

  /**
  *Reads bank accounts from scanner
  *@param in a scanner that scanns to a file
  *@param bank current bank that accounts would be save to
  */
  public static ArrayList<BankAccount> readFromFile( Scanner in, Bank bank ){

    //Returns Array List of Bank Accounts
    ArrayList<BankAccount> ba = new ArrayList<>();

    while( in.hasNextLine() ){
      BankAccount bankA = new BankAccount( in, bank );
      ba.add(bankA);
    }

    return ba;

  }

  /**
  *Gets a bank account
  *@param userName username associated with bank account
  *@param pin pin associated with bank account
  */
  public BankAccount getAccount( String userName, String pin ){

    //LinearSearch
    int index = 0;

    for( int i=0; i < accounts.size(); i++){
      //searches for username
      if(accounts.get(i).getUserName().equals(userName)){
        index = i;
        break;
      }

    }
    //searches for PIN
    if ( accounts.get(index).getHashOfPIN().equals(pin) ){
      return accounts.get(index);
    } else{
      return null;
    }

  }

  //These methods add and subtract from the array accounts



  /**
  *Register a bank account
  *@param account the bank account to be registered
  *@param bank the bank to regiter the account to
  */
  public boolean registerAccount( BankAccount account, Bank bank ){

    boolean exists = bank.accountExists( account.getUserName(), account.getHashOfPIN() );

    if( !exists ){
      accounts.add( account );
      return true;
    }

    return false;

  }



  /**
  *Terminates a bank account
  *@param userName stringassociated with account
  *@param pin string associated with pin
  *@param bank bank associated with account
  */
  public boolean terminateAccount( String userName, String pin, Bank bank ){

    /*deletes an exsisting account and moves account over in array*/
    System.out.println("Are you sure that you would like to terminate your account?");
    System.out.println("1. Yes\n2. No");
    int option= in.nextInt();
    boolean exists = bank.accountExists( userName, pin );

    if( !exists ){
      System.out.println("This account does not exist");
      return false;
    }

    while( exists ){

      switch( option ){
        case 1:

          for( int i=0; i<accounts.size(); i++){

            if( accounts.get(i).equals(bank.getAccount(userName, pin)) ){

                //check pin
                while( !bank.pinExists( pin ) ){
                  System.out.println("Please enter the correct pin:");
                  pin = in.nextLine();
                }

                //terminate account and resize account
                accounts.remove( i );

                return true;

            }

          }
          break;

        case 2:
          return false;

        default:
          System.out.println("I didnt get that");
          break;
      }

    }

    return false;

  }





  /**Checks if an account exists
  *@param userName string associated with username
  *@param pin string associated with pin
  */
  public boolean accountExists( String userName, String pin){
    //if no accounts are present, returns false
    if( accounts.size() < 1 ){
      return false;
    }
    //if accounts are equal to null, returns false
    for( int i = 0; i < accounts.size(); i++ ){

      if(accounts.get(i) == null){
        return false;

      }

      //If an account is found, promps user and returns true
      if(accounts.get(i).getUserName().equals(userName)){
        if (accounts.get(i).getHashOfPIN().equals(pin)){
          System.out.println("This username and pin are both\n"+
                             "associated with an existing account");
          return true;
        }
      }

    }

    return false;

  }

  /**Checks if pin exists
  *@param pin string associated with pin
  */
  public boolean pinExists( String pin ){

    //if accounts are equal to null, returns false
    for( int i = 0; i < accounts.size(); i++ ){

      if(accounts.get(i) == null){
        return false;

      }

      //check if pin exists
      if (accounts.get(i).getHashOfPIN().equals(pin)){
        System.out.println("This pin is associated with an\n"+
                          "existing account");
        return true;
      }

    }

    return false;

  }

}
