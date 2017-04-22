import java.sql.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Admin {
  String login;
  String name;
  String email;
  String address;
  public static Scanner kb = new Scanner(System.in);
  private Connection connection;
  private Statement statement;
  private ResultSet res;
  private String query, update;
  private PreparedStatement ps;

  public Admin() {
    try {
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      String url = "jdbc:oracle:thin:@db10.cs.pitt.edu:1521:dbclass";
      connection = DriverManager.getConnection(url, "bml49", "3985224");
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
    boolean exists = false;
    // if user is an admin
    if (isAdmin == true) 
      query = "select count(login) from ADMINISTRATOR where login = ?";

    // if registered user is a customer
    else
      query = "select count(login) from CUSTOMER where login = ?";

    try {
      ps = connection.prepareStatement(query);
      ps.setString(1, userlogin);
      res = ps.executeQuery();

      if (res.getInt(1) == 0)
        exists = false;

      else 
        exists = true;
    } catch (Exception ex) { ex.printStackTrace(); }

    return exists;
  } // end checkLogin

  // updates the shares in the specified mutual fund
  public void updateShare(String choice) {
    System.out.println("Here are the following share quotes for this mutual fund:");

    try {
      // Embedded SQL code
      query = "select * from CLOSINGPRICE where symbol = ?";
      ps = connection.prepareStatement(query);
      ps.setString(1, choice);
      res = ps.executeQuery();

      System.out.println("Symbol\nPrice\nDate");

      while (res.next()) {
        System.out.println(res.getString(1)+"\t"+res.getFloat(2)+"\t"+res.getDate(3));
      }

      System.out.print("\nWhat price would you like to update the shares to: $");
      float updatePrice = kb.nextFloat();

      // updates the information
      // embedded SQL code here
      update = "insert into CLOSINGPRICE values (?, ?, 29-05-95";
      ps = connection.prepareStatement(update);
      ps.setString(1, symbol);
      ps.setFloat(2, updatePrice);

      ps.executeUpdate();
    } catch (Exception ex) { ex.printStackTrace(); }

    System.out.println("The shares have been updated successfully!");
  } // end updateShare(String)

  // inserts a new mutual fund
  public void addFund(String symbol, String name, String description, String category) {
    // embedded SQL
    // INSERT INTO MUTUALFUND VALUES ();

    try {
      update = "insert into MUTUALFUND values(?, ?, ?, ?, '07-12-45");
      ps = connection.prepareStatement(update);
      ps.setString(1, symbol);
      ps.setString(2, name);
      ps.setString(3, description);
      ps.setString(4, category);
      ps.executeUpdate();
    } catch (Exception ex) { ex.printStackTrace(); }

    System.out.println("The mutual fund has been added successfully!");
  } // end addFund(String, String)

  // updates the time and date requested from the administrator
  public void updateTime(String time, String date) {
  System.out.println("The time and date have been updated successfully!");
  } // end updateTime(String, String)

  public void printStats(int monthNum, int topK) {

  } // end printStats(int, int)
}
