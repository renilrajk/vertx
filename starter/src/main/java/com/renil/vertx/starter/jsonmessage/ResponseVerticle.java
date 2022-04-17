package com.renil.vertx.starter.jsonmessage;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    eventBus.<JsonObject>consumer(MainVerticle.MESSAGE_CHANNEL, req -> {
      log.info("request received : {}", req.body());
      req.reply(new JsonArray().add("user added").add("all good").add(JsonObject.mapFrom(new Message(1, "good"))));
    });
    startPromise.complete();
  }
}
