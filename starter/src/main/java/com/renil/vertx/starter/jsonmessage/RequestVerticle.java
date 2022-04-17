package com.renil.vertx.starter.jsonmessage;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    eventBus.<JsonArray>request(MainVerticle.MESSAGE_CHANNEL,
      new JsonObject()
        .put("id", 2)
        .put("name", "Renil")
        .put("isMinor", false)
      , resp -> {
      if(resp.failed()) {
        log.error("failed : {}", resp.cause());
        return;
      }
      log.info("response received : {}", resp.result().body());
    });
    startPromise.complete();
  }
}
