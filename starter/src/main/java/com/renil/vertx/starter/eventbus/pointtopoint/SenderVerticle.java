package com.renil.vertx.starter.eventbus.pointtopoint;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

import static com.renil.vertx.starter.eventbus.pointtopoint.MainVerticle.MESSAGE_CHANNEL;

@Slf4j
public class SenderVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    vertx.setPeriodic(5000, id -> {
      log.info("sending message : {}", id);
      eventBus.send(MESSAGE_CHANNEL, "new message");
    });
    startPromise.complete();
  }
}
