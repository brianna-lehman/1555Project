import java.sql.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Admin {
  String login;
  String name;
  String email;
  String address;
  public static Scanner = new Scanner(System.in);
  private Connection connection;
  private BufferedReader br;

  public Admin() {
    try {
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      String url = "jdbc:oracle:thin:@db10.cs.pitt.edu:1521:dbclass";
      connection = DriverManager.getConnection(url, "vtt2", "password");
    } catch (SQLException e) { e.printStackTrace();}
  } // end Admin()

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
    float updatePrice = kb.nextFloat();

    // updates the information
    // embedded SQL code here
    // UPDATE ()
    System.out.println("The shares have been updated successfully!");
  } // end updateShare(String)

  // inserts a new mutual fund
  public void addFund(String fund, String symbol) {
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
