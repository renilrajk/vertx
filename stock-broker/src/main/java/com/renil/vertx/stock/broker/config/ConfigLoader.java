package com.renil.vertx.stock.broker.config;

import com.renil.vertx.stock.broker.util.Constants;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class ConfigLoader {

  public static final Future<BrokerConfig> load(Vertx vertx) {
    final var EXPOSED_KEYS = List.of(
      Constants.SERVER_PORT,
      Constants.DB_HOST,
      Constants.DB_PORT,
      Constants.DB_DATABASE,
      Constants.DB_USER,
      Constants.DB_PASSWORD
    );
    final var exposedKeys = new JsonArray();
    EXPOSED_KEYS.forEach(exposedKeys::add);

    var envStore = new ConfigStoreOptions()
      .setType("env")
      .setConfig(new JsonObject().put("keys", exposedKeys));

    var sysStore = new ConfigStoreOptions()
      .setType("sys")
      .setConfig(new JsonObject().put("cache", false));

    var yamlStore = new ConfigStoreOptions()
      .setType("file")
      .setFormat("yaml")
      .setConfig(new JsonObject().put("path", Constants.CONFIG_FILE));

    var retriever = ConfigRetriever.create(vertx,
      new ConfigRetrieverOptions()
        .addStore(yamlStore)
        .addStore(sysStore)
        .addStore(envStore)
    );

    return retriever.getConfig().map(BrokerConfig::fromJson);
  }
}
