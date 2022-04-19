package com.renil.vertx.stock.broker.util;

import java.util.List;

public interface Constants {
  String SERVER_PORT = "SERVER_PORT";
  List<String> ASSETS = List.of("AAPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX", "TSLA");
  String CONFIG_FILE = "application.yml";
}
