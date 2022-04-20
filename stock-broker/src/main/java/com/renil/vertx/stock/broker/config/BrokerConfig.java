package com.renil.vertx.stock.broker.config;

import com.renil.vertx.stock.broker.util.Constants;
import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Builder
@Getter
@ToString
public class BrokerConfig {
  int serverPort;
  String version;
  DbConfig dbConfig;

  public static BrokerConfig fromJson(final JsonObject json) {
    Integer serverPort = json.getInteger(Constants.SERVER_PORT);
    if(Objects.isNull(serverPort)) {
      throw new RuntimeException(Constants.SERVER_PORT + " is not configured.");
    }
    String version = json.getString("version");
    if(Objects.isNull(version)) {
      throw new RuntimeException("version is not configured in config file.");
    }
    return BrokerConfig.builder()
      .serverPort(serverPort)
      .version(version)
      .dbConfig(parseDbConfig(json))
      .build();
  }

  private static DbConfig parseDbConfig(JsonObject json) {
    var host = json.getString(Constants.DB_HOST);
    if(Objects.isNull(host)) {
      throw new RuntimeException(Constants.DB_HOST + " is not configured.");
    }
    var port = json.getInteger(Constants.DB_PORT);
    if(Objects.isNull(port)) {
      throw new RuntimeException(Constants.DB_PORT + " is not configured.");
    }
    var database = json.getString(Constants.DB_DATABASE);
    if(Objects.isNull(database)) {
      throw new RuntimeException(Constants.DB_DATABASE + " is not configured.");
    }
    var user = json.getString(Constants.DB_USER);
    if(Objects.isNull(user)) {
      throw new RuntimeException(Constants.DB_USER + " is not configured.");
    }
    var password = json.getString(Constants.DB_PASSWORD);
    if(Objects.isNull(password)) {
      throw new RuntimeException(Constants.DB_PASSWORD + " is not configured.");
    }
    return DbConfig.builder()
      .host(host)
      .port(port)
      .database(database)
      .user(user)
      .password(password)
      .build();
  }
}
