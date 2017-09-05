package Connections;

public enum StockExchanges {

  CBT ("Chicago Board of Trade", ".CBT"),
  CME ("Chicago Mercantile Exchange", ".CME"),
  NYB ("New York Board of Trade", ".NYB"),
  CMX ("New York Commodities Exchange", ".CMX"),
  NYM ("New York Mercantile Exchange", ".NYM"),
  BA ("Buenos Aires Stock Exchange", ".BA"),
  VI ("Vienna Stock Exchange", ".VI"),
  AX ("Australian Stock Exchange", ".AX"),
  BR ("NYSE Euronext - Brussels", ".BR"),
  SA ("BOVESPA - Sao Paolo Stock Exchange", ".SA"),
  TO ("Toronto Stock Exchange", ".TO"),
  V ("TSX Venture Exchange", ".V"),
  SN ("Santiago Stock Exchange", ".SN"),
  SS ("Shanghai Stock Exchange", ".SS"),
  SZ ("Shenzhen Stock Exchange", ".SZ"),
  PR ("Prague Stock Exchange Index", ".PR"),
  CO ("Copenhagen Stock Exchange", ".CO"),
  CA ("Egyptian Exchange Index", ".CA"),
  TL ("Nasdaq OMX Tallinn", ".TL"),
  HE ("Nasdaq OMX Helsinki", ".HE"),
  NX ("Euronext", ".NX"),
  PA ("NYSE Euronext - Paris", ".PA"),
  BE ("Berlin Stock Exchange", ".BE"),
  BM ("Bremen Stock Exchange", ".BM"),
  DU ("Dusseldorf Stock Exchange", ".DU"),
  F ("Frankfurt Stock Exchange", ".F"),
  HM ("Hamburg Stock Exchange", ".HM"),
  HA ("Hanover Stock Exchange", ".HA"),
  MU ("Munich Stock Exchange", ".MU"),
  SG ("Stuttgart Stock Exchange", ".SG"),
  DE ("XETRA Stock Exchange", ".DE"),
  AT ("Athens Stock Exchange", ".AT"),
  HK ("Hong Kong Stock Exchange", ".HK"),
  IC ("Nasdaq OMX Iceland", ".IC"),
  BO ("Bombay Stock Exchange", ".BO"),
  NS ("National Stock Exchange of India", ".NS"),
  JK ("Jakarta Stock Exchange", ".JK"),
  IR ("Irish Stock Exchange", ".IR"),
  TA ("Tel Aviv Stock Exchange", ".TA"),
  TI ("EuroTLX", ".TI"),
  MI ("Milan Stock Exchange", ".MI"),
  RG ("Nasdaq OMX Riga", ".RG"),
  VS ("Nasdaq OMX Vilnius", ".VS"),
  KL ("Bursa Malaysia", ".KL"),
  MX ("Mexico Stock Exchange", ".MX"),
  AS ("NYSE Euronext - Amsterdam", ".AS"),
  NZ ("New Zealand Stock Exchange", ".NZ"),
  OL ("Oslo Stock Exchange", ".OL"),
  LS ("NYSE Euronext - Lisbon", ".LS"),
  QA ("Qatar Stock Exchange", ".QA"),
  ME ("Moscow Interbank Currency Exchange (MICEX)", ".ME"),
  SI ("Singapore Stock Exchange", ".SI"),
  KS ("Korea Stock Exchange", ".KS"),
  KQ ("KOSDAQ", ".KQ"),
  BC ("Barcelona Stock Exchange", ".BC"),
  BI ("Bilbao Stock Exchange", ".BI"),
  MF ("Madrid Fixed Income Market", ".MF"),
  MC ("Madrid SE C.A.T.S.", ".MC"),
  MA ("Madrid Stock Exchange", ".MA"),
  VA ("Valencia Stock Exchange", ".VA"),
  ST ("Stockholm Stock Exchange", ".ST"),
  SW ("Swiss Exchange", ".SW"),
  TWO ("Taiwan OTC Exchange", ".TWO"),
  TW ("Taiwan Stock Exchange", ".TW"),
  BK ("Stock Exchange of Thailand (SET)", ".BK"),
  IS ("Borsa Istanbul", ".IS"),
  L ("London Stock Exchange", ".L"),
 //Cannot find anything online that shows that this exists IL ("London Stock Exchange", ".IL");
  CR ("Caracas Stock Exchange", ".CR");

  public final String name;
  public final String suffix;

  StockExchanges(String name, String suffix){
    this.name = name;
    this.suffix = suffix;
  }

}
