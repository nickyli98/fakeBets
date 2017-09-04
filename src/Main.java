import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {

  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (Exception ex) {
      // handle the error
    }
    Connection c = null;
    try {
      c = DriverManager.getConnection("jdbc:mysql://" + "192.168.1.86:3306" + "/" + Keys.DB_NAME + "?autoReconnect=true&useSSL=false", Keys.DB_USER, Keys.DB_PASS);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}