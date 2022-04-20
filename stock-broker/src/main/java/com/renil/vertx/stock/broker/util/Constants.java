package com.renil.vertx.stock.broker.util;

import java.util.List;

public interface Constants {
  String SERVER_PORT = "SERVER_PORT";
  List<String> ASSETS = List.of("AAPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX", "TSLA");
  String CONFIG_FILE = "application.yml";
  String DB_HOST = "DB_HOST";
  String DB_PORT = "DB_PORT";
  String DB_DATABASE = "DB_DATABASE";
  String DB_USER = "DB_USER";
  String DB_PASSWORD = "DB_PASSWORD";
}
