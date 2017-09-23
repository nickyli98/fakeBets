package Connections.Betfair;

import Connections.Betfair.XMLObjects.Event;
import Connections.Betfair.XMLObjects.EventType;
import Connections.Betfair.XMLObjects.MarketFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import static Connections.Betfair.XMLObjects.JsonUtils.dateParse;
import static Connections.Keys.*;

public class Client {

  public static void main(String[] args) throws ParseException {
    try {
      CloseableHttpClient client = HttpClientBuilder.create().build();
      MarketFilter filter = new MarketFilter();
      HashSet<String> set = new HashSet();
      set.add("1.134230643");
      filter.setMarketIds(set);
      HttpResponse response = getResponse(client, BETFAIR_API_ENDPOINT + "listMarketCatalogue/", "{\"filter\":" + filter + ", \"marketProjection\": [\"RUNNER_DESCRIPTION\"], \"maxResults\": \"10\"}");

      BufferedReader reader  = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

      String s;
      while((s = reader.readLine()) != null){
      System.out.println(s);
      }

      System.out.println("---------------------------");
      client.close();
      client = HttpClientBuilder.create().build();
      response = getResponse(client, BETFAIR_API_ENDPOINT + "listRunnerBook/", "{\"marketId\":\"1.134230643\", \"selectionId\": \"48322\", \"priceProjection\":{\"priceData\":[\"EX_BEST_OFFERS\"]}}");
      reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      while((s = reader.readLine()) != null){
        System.out.println(s);
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  private static String getSessionToken() throws IOException {
    CloseableHttpClient client = HttpClientBuilder.create().build();
    HttpPost request = new HttpPost("https://identitysso.betfair.com/api/login");

    request.addHeader("Accept", "application/json");
    request.addHeader("X-Application", BETFAIR_API_KEY);
    request.addHeader("Content-Type", "application/x-www-form-urlencoded");
    //Hide password?? lol or new acc
    request.setEntity(new StringEntity("username=nickyli98&password=", "UTF-8"));
    HttpResponse response = client.execute(request);
    BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
    StringBuilder json = new StringBuilder();
    String output;
    while ((output = br.readLine()) != null) {
      json.append(output);
    }
    client.close();
    String jsonString = json.toString().replaceAll("[\"{}]", "");
    String[] jsonSplit = jsonString.split(",");
    if(!jsonSplit[2].split(":")[1].equals("SUCCESS")){
      throw new IllegalStateException("Failed to retrieve session token from Betfair");
    }
    return jsonSplit[0].split(":")[1];
  }

  //TODO: See below, updated once a day, store in DB
  public static List<Event> getEvents(MarketFilter filter) throws ParseException {
    try {
      CloseableHttpClient client = HttpClientBuilder.create().build();

      HttpResponse response = getResponse(client, BETFAIR_API_ENDPOINT + "listEvents/", "{\"filter\":" + filter + "}");

      XMLInputFactory factory = XMLInputFactory.newInstance();
      XMLStreamReader reader = factory.createXMLStreamReader(
          response.getEntity().getContent());
      List<Event> list = new ArrayList<>();
      String tag = null;
      String country = null;
      int id = 0;
      String name = null;
      String dateS = null;
      while(reader.hasNext()){
        int event = reader.next();
        switch (event) {
          case XMLStreamConstants.CHARACTERS:
            tag = reader.getText().trim();
            break;
          case XMLStreamConstants.END_ELEMENT:
            switch(reader.getLocalName()){
              case "id":
                id = Integer.parseInt(tag);
                break;
              case "countryCode":
                country = tag;
              case "name":
                name = tag;
                break;
              case "openDate":
                dateS = tag;
                break;
              case "timezone":
                Date date = dateParse(dateS, tag);
                list.add(new Event(id, name, country, tag, date));
                break;
            }
            break;
        }
      }
      return list;
    } catch (IOException | XMLStreamException e) {
      e.printStackTrace();
    }
    return null;
  }

  //TODO: This should only be called once every day or something, stored in DB
  public static List<EventType> getEventTypes(){
    try {
      CloseableHttpClient client = HttpClientBuilder.create().build();

      HttpResponse response = getResponse(client, BETFAIR_API_ENDPOINT + "listEventTypes/", "{\"filter\":{}}");

      XMLInputFactory factory = XMLInputFactory.newInstance();
      XMLStreamReader reader = factory.createXMLStreamReader(
          response.getEntity().getContent());
      List<EventType> list = new ArrayList<>();
      String tag = null;
      int id = 0;
      String name = null;
      while(reader.hasNext()){
        int event = reader.next();
        switch (event) {
          case XMLStreamConstants.CHARACTERS:
            tag = reader.getText().trim();
            break;
          case XMLStreamConstants.END_ELEMENT:
            switch(reader.getLocalName()){
              case "id":
                id = Integer.parseInt(tag);
                break;
              case "name":
                name = tag;
                break;
              case "marketCount":
                list.add(new EventType(id, name, Integer.parseInt(tag)));
                break;
            }
            break;
        }
      }
      return list;
    } catch (IOException | XMLStreamException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static HttpResponse getResponse(CloseableHttpClient client, String url, String data)
      throws IOException {
    HttpPost request = new HttpPost(url);
    request.addHeader("X-Application", BETFAIR_API_KEY);
    request.addHeader("X-Authentication", BETFAIR_SESSION_TOKEN);
    request.addHeader("content-type", "application/json");
    request.setEntity(new StringEntity(data, "UTF-8"));
    HttpResponse response = client.execute(request);
    if(response.getStatusLine().getStatusCode() == 400){
      //Need to update session token
      BETFAIR_SESSION_TOKEN = getSessionToken();
      request.setHeader("X-Authentication", BETFAIR_SESSION_TOKEN);
      return client.execute(request);
    }
    return response;
  }

}
