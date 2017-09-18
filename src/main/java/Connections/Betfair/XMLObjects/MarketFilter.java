package Connections.Betfair.XMLObjects;

import static Connections.Betfair.XMLObjects.JsonUtils.setToString;

import java.util.HashSet;
import java.util.Set;

public class MarketFilter {

	private String textQuery;
	private Set<String> eventTypeIds;
	private Set<String> eventIds;
	private Set<String> competitionIds;
	private Set<String> marketIds;

  public MarketFilter(){}

  public String getTextQuery() {
    return textQuery;
  }

  public void setTextQuery(String textQuery) {
    this.textQuery = textQuery;
  }

  public Set<String> getEventTypeIds() {
    return eventTypeIds;
  }

  public void setEventTypeIds(Set<String> eventTypeIds) {
    this.eventTypeIds = eventTypeIds;
  }

  public Set<String> getEventIds() {
    return eventIds;
  }

  public void setEventIds(Set<String> eventIds) {
    this.eventIds = eventIds;
  }

  public Set<String> getCompetitionIds() {
    return competitionIds;
  }

  public void setCompetitionIds(Set<String> competitionIds) {
    this.competitionIds = competitionIds;
  }

  public Set<String> getMarketIds() {
    return marketIds;
  }

  public void setMarketIds(Set<String> marketIds) {
    this.marketIds = marketIds;
  }

  @Override
  public String toString(){
    String s =  "{"
        + textQueryPrint()
        + setToString(eventTypeIds, "eventTypeIds")
        + setToString(eventIds, "eventIds")
        + setToString(competitionIds, "competitionIds")
        + setToString(marketIds, "marketIds")
        + "}";
    return s.replaceAll(", }", "}");
  }

  private String textQueryPrint(){
    if(textQuery == null){
      return "";
    } else {
      return "\"textQuery\" : \"" + textQuery + "\", ";
    }
  }
}
