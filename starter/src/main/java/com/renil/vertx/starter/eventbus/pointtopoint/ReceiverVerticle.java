package com.renil.vertx.starter.eventbus.pointtopoint;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiverVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    eventBus.<String>consumer(MainVerticle.MESSAGE_CHANNEL, message -> {
      log.info("received message : {}", message.body());
      message.reply("test");
    });
    startPromise.complete();
  }
}
