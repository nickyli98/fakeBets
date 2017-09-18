package Connections.Betfair.XMLObjects;

import java.util.Date;

public class Event {

	private final int id;
	private final String name;
	private final String countryCode;
	private final String timezone;
	private final Date openDate;

  public Event(int id, String name, String countryCode, String timezone, Date openDate) {
    this.id = id;
    this.name = name;
    this.countryCode = countryCode;
    this.timezone = timezone;
    this.openDate = openDate;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getTimezone() {
    return timezone;
  }

  public Date getOpenDate() {
    return openDate;
  }

  public String toString() {
    return name + " - ID: " + id + " - Country: " + countryCode
        + " - Date: " + openDate + ", " + timezone;
	}

}
