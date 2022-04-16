package com.renil.vertx.starter.eventbus.publishsubscribe;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer2Verticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    eventBus.<String>consumer(MainVerticle.PUBLISH_CHANNEL, message -> {
      log.info("message received : {}", message.body());
      message.reply("received");
    });
    startPromise.complete();
  }
}
