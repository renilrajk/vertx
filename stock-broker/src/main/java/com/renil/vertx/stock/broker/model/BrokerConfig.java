package com.renil.vertx.stock.broker.model;

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
      .build();
  }
}
