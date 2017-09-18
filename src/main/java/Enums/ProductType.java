package Enums;

public enum ProductType {

  BET (1),
  STOCK (2),
  CURRENCY (3);

  public final int dbValue;

  private ProductType(int val){
    this.dbValue = val;
  }

}
