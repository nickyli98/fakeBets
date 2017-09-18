package Connections;

import Enums.StockExchanges;
import java.io.IOException;
import java.math.BigDecimal;
import yahoofinance.YahooFinance;

public class APIConnector {

  public static BigDecimal getAsk(String stockCode, StockExchanges ex) throws IOException {
    return YahooFinance.get(stockCode + ex.suffix).getQuote().getAsk();
  }

  public static BigDecimal getBid(String stockCode, StockExchanges ex) throws IOException {
    return YahooFinance.get(stockCode + ex.suffix).getQuote().getBid();
  }



}
