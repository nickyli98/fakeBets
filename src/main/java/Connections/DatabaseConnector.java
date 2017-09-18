package Connections;

import Enums.Currency;
import Enums.ProductType;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Calendar;
import java.util.TimeZone;

import static Connections.Keys.*;
import static Connections.PreparedStatements.*;

public class DatabaseConnector {

  private static Connection getConnection(){
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      return DriverManager.getConnection("jdbc:mysql://" + DB_CONNECTION + "/" + DB_NAME + "?autoReconnect=true&useSSL=false&allowMultiQueries=true", DB_USER, DB_PASS);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  //TODO: Make sure to catch integrity constraint
  public static boolean createUser(String name, String email, String password) {
    Connection con = getConnection();
    if(con != null){
      try {
        PreparedStatement ps = con.prepareStatement(CREATE_USER_INFO);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setTimestamp(3, new Timestamp(Calendar.getInstance(TimeZone.getTimeZone("Asia/Hong_Kong")).getTime().getTime()));
        String salt = getSalt();
        ps.setString(4, getSecurePassword(password + salt));
        ps.setString(5, salt);
        ps.executeUpdate();
        ps.close();
        con.close();
        return true;
      } catch (MySQLIntegrityConstraintViolationException e){
        //Hacky fix, other methods cant catch Integrity exception for some reason
        throw new IllegalArgumentException("Email already exists");
      } catch (SQLException | NoSuchAlgorithmException e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
  }

  //Catch SQLException for email not existing
  public static boolean checkLogin(String email, String password) throws SQLException {
    Connection con = getConnection();
    PreparedStatement ps = con.prepareStatement(GET_SALT);
    ps.setString(1, email);
    ResultSet rs = ps.executeQuery();
    rs.next();
    String salt = rs.getString("salt");
    ps = con.prepareStatement(CHECK_LOGIN);
    try {
      ps.setString(1, email);
      ps.setString(2, getSecurePassword(password + salt));
      rs = ps.executeQuery();
      ps.close();
      con.close();
      return rs.next();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return true;
  }

  private static String getSalt() throws NoSuchAlgorithmException {
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[16];
    sr.nextBytes(salt);
    //Substring to remove the '==' padding in the end
    return Base64.getEncoder().encodeToString(salt).substring(0, 22);
  }

  //Assume password meets minimum length etc.
  private static String getSecurePassword(String passwordToHash) throws NoSuchAlgorithmException {
    String generatedPassword = null;
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(passwordToHash.getBytes(), 0, passwordToHash.length());
    return new BigInteger(1, md.digest()).toString(16);
  }

  public static void addProduct(ProductType type, Currency currency, double amount, int userId, double price)
      throws SQLException {
    addProduct(type, currency, amount, userId, "", price);
  }

  public static void addProduct(ProductType type, Currency currency, double amount, int userId, String descAppend, double price)
      throws SQLException {
    Connection con = getConnection();
    //TODO
    PreparedStatement ps = con.prepareStatement(PRODUCT_BUY_TRANSACTION);
    ps.setInt(1, type.dbValue);
    ps.setDouble(2, amount);
    ps.setString(3, type.dbValue + descAppend);
    ps.setInt(4, userId);
    ps.setString(5, currency.code);
    ps.setDouble(6, price);
    ps.executeUpdate();
    ps.close();
    con.close();
  }

  //PRE: User has enough money to buy this product, not for currency
  public static void updateProduct(int productId, boolean buy, double amount) throws SQLException {
    Connection con = getConnection();
    //TODO
    double price = 1.0;
    PreparedStatement ps = con.prepareStatement(PRODUCT_UPDATE_TRANSACTION);
    ps.setInt(1, productId);
    ps.setDouble(2, buy ? amount : (amount * -1));
    ps.setDouble(3, price);
    ps.setInt(4, buy ? 1 : 0);
    ps.executeUpdate();
    ps.close();
    con.close();
  }

  //Same pre, only for currency
  public static void updateProduct(int productId, boolean buy, double amount, Currency outputCurrency) throws SQLException{
    Connection con = getConnection();
    //TODO
    double rate = 0.5;
    PreparedStatement ps = con.prepareStatement(PRODUCT_UPDATE_CURRENCY_TRANSACTION);
    ps.setInt(1, productId);
    ps.setDouble(2, buy ? amount : (amount * -1));
    ps.setInt(3, buy ? 1 : 0);
    ps.setString(4, outputCurrency.code);
    ps.setDouble(5, rate);
    ps.executeUpdate();
    ps.close();
    con.close();
  }

  //Gives user (id) with starting amount 1,000,000 USD
  public static void giveStartingAmount(int id) throws SQLException {
    addProduct(ProductType.CURRENCY, Currency.USD, 1000000, id, 0);
  }

}
