package Connections.Betfair.XMLObjects;

public class EventType {

  private final int id;
  private final String name;
  private final int marketCount;

  public EventType(int id, String name, int marketCount) {
    this.id = id;
    this.name = name;
    this.marketCount = marketCount;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getMarketCount() {
    return marketCount;
  }

  @Override
  public String toString(){
    return name + " - ID: " + id + " - Market Count: " + marketCount;
  }
}
