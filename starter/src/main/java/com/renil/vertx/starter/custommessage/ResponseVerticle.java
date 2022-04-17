package com.renil.vertx.starter.custommessage;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    eventBus.registerDefaultCodec(Pong.class, new PongMessageCodec());
    eventBus.<Ping>consumer(MainVerticle.MESSAGE_CHANNEL, req -> {
      log.info("request received : {}", req.body());
      req.reply(new Pong("All good..."));
    }).exceptionHandler(err -> {
      log.error("Response failed...", err.getMessage());
    });
    startPromise.complete();
  }
}
