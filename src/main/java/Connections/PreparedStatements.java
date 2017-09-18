package Connections;

public class PreparedStatements {

  public static String CREATE_USER_INFO = "INSERT INTO user_info (name, email, sign_up_date, password, salt) VALUES (?, ?, ?, ?, ?);";
  public static String GET_SALT = "SELECT salt FROM user_info WHERE email = ?";
  public static String CHECK_LOGIN = "SELECT EXISTS (SELECT * FROM user_info WHERE email = ? AND password = ?);";
  public static String PRODUCT_BUY_TRANSACTION  = "START TRANSACTION;\n"
      + "SET @type = ?, @amount = ?, @description = ?, @user_id = ?, @currency = ?, @price = ?;\n"
      + "INSERT INTO product (type, amount, description, user_id, currency) VALUES (@type, @amount, @description, @user_id, @currency);\n"
      + "SET @prod_id = LAST_INSERT_ID();\n"
      + "INSERT INTO transaction (price, date, product_id, buy) VALUES (@price, NOW(), @prod_id, 1);\n"
      + "COMMIT;\n";
  public static String PRODUCT_UPDATE_TRANSACTION = "START TRANSACTION;\n"
      + "SET @product_id = ?, @amount = ?, @price = ?, @buy = ?;\n"
      + "SELECT @currency := `currency` FROM product WHERE id = @product_id;\n"
      + "SELECT @user_id := `user_id` FROM product WHERE id = @product_id;\n"
      + "UPDATE product SET amount = amount - (@amount * @price) WHERE user_id = @user_id AND currency = @currency AND type = 3;\n"
      + "UPDATE product SET amount = amount + @amount WHERE id = @product_id;\n"
      + "INSERT INTO transaction (price, date, product_id, buy) VALUES (@price, NOW(), @product_id, @buy);\n"
      + "COMMIT;";
  public static String PRODUCT_UPDATE_CURRENCY_TRANSACTION = "START TRANSACTION;\n"
      + "SET @product_id = ?, @amount = ?, @buy = ?, @outputCurrency = ?, @rate = ?;\n"
      + "SELECT @currency := `currency` FROM product WHERE id = @product_id;\n"
      + "SELECT @user_id := `user_id` FROM product WHERE id = @product_id;\n"
      + "UPDATE product SET amount = amount - (@amount * @rate) WHERE user_id = @user_id AND currency = @outputCurrency AND type = 3;\n"
      + "UPDATE product SET amount = amount + @amount WHERE id = @product_id;\n"
      + "INSERT INTO transaction (price, date, product_id, buy) VALUES (@price, NOW(), @product_id, @buy);\n"
      + "COMMIT;";

}
