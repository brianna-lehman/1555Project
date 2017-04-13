import java.sql.*;

public class Admin {
  String login;
  String name;
  String email;
  String address;
  Statement st;

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


}
