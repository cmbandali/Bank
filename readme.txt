This is a Bank ATM Simulator!

Bank Class:

  For the loadFromFileMenu I print out the BankAccounts that
  were registered from the file before i return the user to
  the landing menu.

BankAccount Class:

  For the hashOf(pin) method i changed the cryptic algorithm
  a little to make it slightly more complicated.

  For the BankAccount constructor that takes a scanner as a parameter,
  Instead of using the useDelimiter() function call for scanner. I
  used the split() function call for a string because i think that
  it adds clarity to what the constructor does. I also include a bank
  object as a parameter to be able to register the accounts taken from
  the file

Bank Class:

  For the readFromFile(in) method I return an ArrayList of bank
  accounts that have been registered.
