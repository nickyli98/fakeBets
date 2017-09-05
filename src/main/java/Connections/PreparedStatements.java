package Connections;

public class PreparedStatements {

  public static String CREATE_USER_INFO = "INSERT INTO user_info (name, email, sign_up_date, password, salt) VALUES (?, ?, ?, ?, ?);";
  public static String GET_SALT = "SELECT salt FROM user_info WHERE email = ?";
  public static String CHECK_LOGIN = "SELECT EXISTS (SELECT * FROM user_info WHERE email = ? AND password = ?);";


}
