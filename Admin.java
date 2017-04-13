import java.sql.*;
import java.util.*;

public class Admin {
  String login;
  String name;
  String email;
  String address;

  // Parameterized constructor
  public Admin(String login, String name, String email, String address) {
    this.login = login;
    this.name = name;
    this.email = email;
    this.address = address;
  } // end Customer(String, String, String, String)

  // checks if the login has been used before in either db
  public boolean checkLogin(String userlogin, boolean isAdmin) {
    boolean ifExists = false;
    // if user is an admin
    if (isAdmin == true) {
      // Embedded SQL code
      // IF EXISTS (SELECT login FROM ADMINISTRATOR WHERE l0gin = userlogin)
      // BEGIN
      // return false;
      // ELSE
      // BEGIN
      // return true;
    // if registered user is a customer
    } else {
      // Embedded SQL code
      // IF EXISTS (SELECT login FROM CUStOMER WHERE l0gin = userlogin)
      // BEGIN
      // return false;
      // ELSE
      // BEGIN
      // return true;
    }
  } // end checkLogin

  // updates the shares in the specified mutual fund
  public void updateShare(String choice) {
    Scanner kb = new Scanner(System.in);
    System.out.println("Here are the following share quotes for this mutual fund:");
    // Embedded SQL code
    // select *
    // from CLOSINGPRICE
    // where name = choice
    System.out.print("What price would you like to update the shares to: $");
    float updatePrice = kb.next();

    // updates the information
    // embedded SQL code here
    // UPDATE ()
    System.out.println("The shares have been updated successfully!");
  } // end updateShare(String)

  // inserts a new mutual fund
  public void addFund(String fund, String, category) {
    // embedded SQL
    // INSERT INTO MUTUALFUND VALUES ();

    System.out.println("The mutual fund has been added successfully!");
  } // end addFund(String, String)

  // updates the time and date requested from the administrator
  public void updateTime(String time, String date) {
    System.out.println("The time and date have been updated successfully!");
  } // end updateTime(String, String)

  public void printStats(int monthNum, int topK) {
    
  } // end printStats(int, int)
}
