/**
*Generic BankAccount class for a Bank System written for project 2
 in CS212
*@author Claudia Bandali
*/

import java.util.*;
import java.io.PrintWriter;
import java.util.regex.Pattern;

class BankAccount{
  //Global Scanner object
  static Scanner in;

  //Fields
  private String firstName;
  private String lastName;
  private String address;
  private int balance;
  private String userName;
  private String pin;

  //Constructor
  public BankAccount(String fn, String ln){

    firstName = fn;
    lastName = ln;

  }

  public BankAccount( BankAccount ba ){

    firstName = ba.firstName;
    lastName = ba.lastName;
    address = ba.address;
    balance = ba.balance;
    userName = ba.userName;
    pin = ba.pin;

  }

  /**
  *This BankAccount constructor takes in as arguments
  *@param in which is a scanner that scans from a file
  *@param bank declairs what bank to register the accounts to
  *A line from the scanner is saved to a String line. Scanner
  *reads in a format equivalent to the format used in
  *the writeToFile method. The string splits
  *the line from the file based on the '|' char
  *and approprately declairs each variable.
  *It then creates a new bank account registers the account
  *and sets the rest of the variables in the newly
  *registered bank account.
  */
  public BankAccount( Scanner in, Bank bank){

    if(in.hasNextLine()){
      String line = in.nextLine();
      String[] info = line.split("\\|");
      firstName = info[0];
      lastName = info[1];
      address = info[2];
      balance = Integer.parseInt(info[3]);
      userName = info[4];
      pin = info[5];
      BankAccount ba = new BankAccount (firstName, lastName);
      bank.registerAccount( ba, bank );
      ba.setAddress( address );
      ba.addBalance( balance );
      ba.setUserName( userName );
      ba.setPIN( pin );
    }

  }

  /**
  *The getters and setters set and get the BankAccount variables
  */
  public String getFirstName(){

    return firstName;

  }

  public String getLastName(){

    return lastName;

  }

  public String getAddress(){

    return address;

  }

  public int getBalance(){

    return balance;

  }

  public String getUserName(){

    return userName;

  }

  public String getHashOfPIN(){

      return pin;

  }

  //Setters
  public void setFirstName( String firstName ){

    this.firstName = firstName;

  }

  public void setLastName( String lastName ){

    this.lastName = lastName;

  }

  public void setAddress( String address ){

    this.address = address;

  }

  public void setUserName( String username ){

    this.userName = username;

  }

  public void setPIN( String pin ){

    this.pin = pin;

  }

  //Methods

  /**
  *This method writes to a file via the argument out
  *@param out is a PrintWriter to a file
  */
  public void writeToFile(PrintWriter out){

    if( out.equals(null) ){
      return;
    }

    out.println(firstName + '|' + lastName + '|' + address + '|' +
                balance + '|' + userName + '|' + pin);

    out.checkError(); //flushes stream and checks error state.

  }

  /**
  *This method reads from a file via a scanner argument
  *@param scanner is a scanner that reads from a file.
  */
  public String readFromFile( Scanner in ){

    String line = in.nextLine().trim();
    return line;

  }

  /**
  *This method creats a hash of the argument pin
  *@param pin a string pin from the user input
  *@param bank is the bank that is currently in use
  */
  public static String hashOf(String pin, Bank bank){
    Scanner in = new Scanner(System.in);
    // Ignore if the PIN is invalid
    boolean keep = BankAccount.isValidPIN( pin );
    if( !keep ){
      return null;
    }

    // Run a cryptic algorithm
    String hash = "";
    for ( int i = 0; i < 4; ++i ) {
        int digit = (int)pin.charAt(i);
        hash += "" + ( digit*i | ( digit*9 )%4 );
        hash += "" + ( ( (digit^i) & (9|i) ) << digit ) + i;
        hash += "" + ( digit | ( (7&3) | i ) );
    }

    // Return the hash
    return hash;
}

  /**
  *Adds money to account
  *@param amount is an integer value
  */
  public void addBalance ( int amount ) {

    if( amount > 0 ){

      this.balance = balance + amount;

    }

  }

  /**
  *Subtracts money from account
  *@param amount is an integer value
  */
  public void subtractBalance ( int amount ) {

    if( amount > 0 && amount < balance ){

      this.balance = balance - amount;

    }

  }
  /**
  *Check that the aname is valild
  *@param fn String input associated with a first name
  *@param ln String input associated with a last name
  */
  public static boolean isValidName( String fn, String ln ) {

    if( fn.equals(null) || ln.equals(null) ){
      return false;
    }

    if( fn.length() == 0 || ln.length() == 0){
      return false;
    }

    for( int i = 0; i < fn.length(); i++){
      char c = fn.charAt(i);
      if( !Character.isLetter(c) && c != '-' && c != ' ' ){
        return false;
      }
    }

    for( int i = 0; i < ln.length(); i++){
      char c = ln.charAt(i);
      if( !Character.isLetter(c) && c != '-' && c != ' ' ){
        return false;
      }
    }

    return true;

  }
  /**
  *Validates a username input
  *@param username a string input associated with a username
  */
  public static boolean isValidUsername( String username ){

    username.trim();

    if( username.equals(null) ){
      return false;
    }

    if( username.length() == 0 ){
      return false;
    }

    for( int i = 0; i < username.length(); i++){
      char c = username.charAt(i);
      if (!Character.isDigit(c) && !Character.isLetter(c)
          && c != '-' && c != '_'){
        return false;
      }
    }
    return true;

  }
  /**
  *Validates a pin input
  *@param pin a string input associated with a pin
  */
  public static boolean isValidPIN( String pin ){

    if( pin.length() < 4 || pin.length() > 4 ){
      return false;
    }

    for( int i = 0; i < pin.length(); i++ ){
      char c = pin.charAt(i);
      if( !Character.isDigit(c) ){
        return false;
      }
    }

    return true;

  }
  /**
  *Returns a string version of a BankAccount
  *@param account current account that is logged into
  */
  public static String toString( BankAccount account ){

    return account.getFirstName() + "|" + account.getLastName() + "|" +
           account.getAddress() + "|" + account.getBalance() + "|" +
           account.getUserName() + "|" + account.getHashOfPIN();

  }

}
