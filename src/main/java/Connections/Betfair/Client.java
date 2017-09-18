package Connections.Betfair;

import Connections.Betfair.XMLObjects.EventType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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


import static Connections.Keys.*;

public class Client {

  public static void main(String[] args) {
    for(EventType e : getEventTypes()){
      System.out.println(e);
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
