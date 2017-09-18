package Connections.Betfair.XMLObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

public class JsonUtils {

  public static String setToString(Set<String> set, String name){
    if(set == null){
      return "";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("\"").append(name).append("\" : [");
    for(String s : set){
      sb.append("\"").append(s).append("\", ");
    }
    int length = sb.length();
    sb.replace(length - 2, length, "");
    sb.append("], ");
    return sb.toString();
  }

  public static Date dateParse(String date, String timezone) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    sdf.setTimeZone(TimeZone.getTimeZone(timezone));
    return sdf.parse(date);
  }

}
